package co.ufps.pdau.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "daxahcakc",
                "api_key", "922847878686654",
                "api_secret", "OSdxwvVIx3RbsuX0ThzWXQBNeq4",
                "secure", true
        ));
    }
}