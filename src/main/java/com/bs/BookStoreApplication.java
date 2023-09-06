package com.bs;

import com.bs.model.Role;
import com.bs.model.User;
import com.bs.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;


@SpringBootApplication
@PropertySource("classpath:application-${spring.profiles.active:default}.properties")
public class BookStoreApplication {
    private static final Logger log = LoggerFactory.getLogger(BookStoreApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "belcaid.abdellah2001@gmail.com";
            String adminPassword = "admin12345";
            if (userRepository.findByEmailIgnoreCase(adminEmail) == null) {
                User adminUser = User.builder()
                        .email(adminEmail)
                        .role(Role.ADMIN)
                        .createTime(LocalDateTime.now())
                        .username("admin")
                        .password(passwordEncoder.encode(adminPassword))
                        .name("belcaid")
                        .build();

                userRepository.save(adminUser);
                log.info("Admin user created. Username: " + adminUser.getUsername() + " - Password: " + adminPassword);
            }
        };
    }

}
