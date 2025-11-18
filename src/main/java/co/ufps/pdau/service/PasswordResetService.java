package co.ufps.pdau.service;

import co.ufps.pdau.model.Admin;
import co.ufps.pdau.model.PasswordResetToken;
import co.ufps.pdau.repository.AdminRepository;
import co.ufps.pdau.repository.PasswordResetTokenRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository tokenRepository;
    private final TemplateEngine templateEngine;

    @Value("${sendgrid.api-key}")
    private String sendGridApiKey;

    @Value("${sendgrid.from-email}")
    private String fromEmail;

    @Value("${sendgrid.from-name}")
    private String fromName;

    public void sendResetLink(String correo) {
        Admin admin = adminRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Correo no registrado"));

        // Generar token único con expiración
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(
                token, admin, LocalDateTime.now().plusMinutes(15)
        );
        tokenRepository.save(resetToken);

        // Crear enlace de restablecimiento
        String link = "https://pdau-fe.vercel.app/admin_password_confirm?token=" + token;

        // Renderizar plantilla HTML
        Context context = new Context();
        context.setVariable("nombre", admin.getNombre());
        context.setVariable("link", link);

        String htmlContent = templateEngine.process("reset-password-email", context);

        // Enviar correo usando SendGrid
        sendEmail(correo, "Restablecimiento de contraseña", htmlContent);
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido o expirado"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El token ha expirado");
        }

        Admin admin = resetToken.getAdmin();
        admin.setContrasenia(passwordEncoder.encode(newPassword));
        adminRepository.save(admin);

        // Invalida el token
        tokenRepository.delete(resetToken);

        // Renderizar plantilla de confirmación
        Context context = new Context();
        context.setVariable("nombre", admin.getNombre());
        String htmlContent = templateEngine.process("password-updated-email", context);

        // Enviar correo usando SendGrid
        sendEmail(admin.getCorreo(), "Contraseña actualizada", htmlContent);
    }

    private void sendEmail(String toEmail, String subject, String htmlContent) {
        Email from = new Email(fromEmail, fromName);
        Email to = new Email(toEmail);
        Content content = new Content("text/html", htmlContent);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sg.api(request);
        } catch (IOException e) {
            throw new RuntimeException("Error al enviar el correo con SendGrid: " + e.getMessage());
        }
    }
}
