package be.ucll.ip.reeks562;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class Reeks562Application {

    public static void main(String[] args) {
        SpringApplication.run(Reeks562Application.class, args);
    }

}
