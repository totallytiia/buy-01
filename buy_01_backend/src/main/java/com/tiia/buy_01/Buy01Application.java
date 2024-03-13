package com.tiia.buy_01;

import com.tiia.buy_01.services.MediaService;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class Buy01Application {

    public static void main(String[] args) {
        SpringApplication.run(Buy01Application.class, args);
    }

    @Bean // This annotation marks the method as a bean provider.
    public ModelMapper getMapper() {
        // Creates and configures an instance of ModelMapper, a utility for object mapping.
        ModelMapper returnValue = new ModelMapper();

        // Sets the configuration to ignore ambiguities and allows private field access.
        returnValue.getConfiguration()
                .setAmbiguityIgnored(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        // Configures the matching strategy to be strict.
        returnValue.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return returnValue; // Returns the configured ModelMapper instance.
    }

}
