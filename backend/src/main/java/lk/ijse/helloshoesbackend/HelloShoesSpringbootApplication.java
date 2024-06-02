package lk.ijse.helloshoesbackend;

import jakarta.servlet.annotation.MultipartConfig;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@MultipartConfig(
        location = "/tmp",
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50,
        fileSizeThreshold = 1024 * 1024 * 5
)
@EnableScheduling
public class HelloShoesSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloShoesSpringbootApplication.class, args);
    }

    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }
}
