package com.pollub.lab_6;

import com.pollub.lab_6.dao.UserDao;
import com.pollub.lab_6.entities.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Lab6Application {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Lab6Application.class, args);
    }

    @PostConstruct
    public void init() {
        userDao.save(User.builder()
                .name("Piotr")
                .surname("Piotrowski")
                .login("admin")
                .password(passwordEncoder.encode("admin"))
                .build()
        );
        userDao.save(User.builder()
                .name("Ania")
                .surname("Annowska")
                .login("ania")
                .password(passwordEncoder.encode("ania"))
                .build()
        );
    }
}
