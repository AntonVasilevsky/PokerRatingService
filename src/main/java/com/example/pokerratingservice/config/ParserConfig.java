package com.example.pokerratingservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParserConfig {
    @Bean
    public ModelMapper getModelmapper() {
        return new ModelMapper();
    }
}
