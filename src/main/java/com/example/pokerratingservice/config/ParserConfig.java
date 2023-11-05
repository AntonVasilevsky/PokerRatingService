package com.example.pokerratingservice.config;

import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import com.example.pokerratingservice.util.parserassistant.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParserConfig {
    /*@Bean
    public HandParserAssistant getInitPokerStarsParserAssistant() {
        PokerStarsInitHandParserAssistant assistant = new PokerStarsInitHandParserAssistant();
        assistant.setPokerStarsBlockNameEnum(PokerStarsHandBlockName.INIT);
        return assistant;
    }*/

    @Bean
    public HandParserAssistant getHoleCardsPokerStarsParserAssistant() {
        PokerStarsHoleCardsHandParserAssistant assistant = new PokerStarsHoleCardsHandParserAssistant();
        assistant.setPokerStarsBlockNameEnum(PokerStarsHandBlockName.HOLE_CARDS);
        return assistant;
    }

    @Bean
    public HandParserAssistant getFlopPokerStarsParserAssistant() {
        PokerStarsFlopHandParserAssistant assistant = new PokerStarsFlopHandParserAssistant();
        assistant.setPokerStarsBlockNameEnum(PokerStarsHandBlockName.FLOP);
        return assistant;
    }

    @Bean
    public HandParserAssistant getTurnPokerStarsParserAssistant() {
        PokerStarsTurnHandParserAssistant assistant = new PokerStarsTurnHandParserAssistant();
        assistant.setPokerStarsBlockNameEnum(PokerStarsHandBlockName.TURN);
        return assistant;
    }

    @Bean
    public HandParserAssistant getRiverPokerStarsParserAssistant() {
        PokerStarsRiverHandParserAssistant assistant = new PokerStarsRiverHandParserAssistant();
        assistant.setPokerStarsBlockNameEnum(PokerStarsHandBlockName.RIVER);
        return assistant;
    }
    @Bean
    public HandParserAssistant getShowDownPokerStarsParserAssistant() {
        PokerStarsRiverHandParserAssistant assistant = new PokerStarsRiverHandParserAssistant();
        assistant.setPokerStarsBlockNameEnum(PokerStarsHandBlockName.SHOW_DOWN);
        return assistant;
    }

    /*@Bean
    public HandParserAssistant getSummaryPokerStarsParserAssistant() {
        PokerStarsSummaryHandParserAssistant assistant = new PokerStarsSummaryHandParserAssistant();
        assistant.setPokerStarsBlockNameEnum(PokerStarsHandBlockName.SUMMARY);
        return assistant;
    }*/
    @Bean
    public ModelMapper getModelmapper() {
        return new ModelMapper();
    }
}
