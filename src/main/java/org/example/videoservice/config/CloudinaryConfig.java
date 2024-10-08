package org.example.videoservice.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
               "cloud_name", "dduofcnvq",
                       "api_key", "294445872797489",
                       "api_secret", "Z2XUSYOgh9sfvzqMhNf-8ngTKLA"
        ));
    }





}


