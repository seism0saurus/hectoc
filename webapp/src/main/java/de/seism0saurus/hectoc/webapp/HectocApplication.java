package de.seism0saurus.hectoc.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class HectocApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(HectocApplication.class, args);
    }
}
