package de.seism0saurus.hectoc.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HectocBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(HectocBotApplication.class, args);
    }
}
