package com.agc.inspectores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class InspectoresApplication {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().directory("./").ignoreIfMissing().load();

        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );
        SpringApplication.run(InspectoresApplication.class, args);

//        Temporal de acceso a superadmin
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String plainPassword = "contraseñaSegura123";
//        String hashedPassword = passwordEncoder.encode(plainPassword);
//        System.out.println("EL HASH DE CONTRASEÑA PARA EL SUPERADMIN ES: " + hashedPassword);

    }
}
