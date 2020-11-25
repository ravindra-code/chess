package com.ravindra.chess;

import com.ravindra.chess.client.Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Properties;

@SpringBootApplication
@EnableSwagger2
public class ChessApplication {

    public static void main(String[] args) throws Exception{
        SpringApplication application = new SpringApplication(ChessApplication.class);
        Properties properties = new Properties();
        properties.setProperty("spring.main.banner-mode", "log");
        properties.setProperty("logging.file","logs/test.log");
        properties.setProperty("logging.pattern.console", "");
        application.setDefaultProperties(properties);
        application.run(args);

        Client client = new Client();
        client.initiateSimulation();
    }

}
