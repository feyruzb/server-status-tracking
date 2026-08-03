package dev.feyruz.serverstatustracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ServerStatusTrackingApplication {

public static void main(String[] args) {
        SpringApplication.run(ServerStatusTrackingApplication.class, args);
    }

}
