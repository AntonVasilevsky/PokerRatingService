package com.example.pokerratingservice.config;

import com.example.pokerratingservice.util.HandParserAssistant;
import com.example.pokerratingservice.util.PokerStarsBlockName;
import com.example.pokerratingservice.util.PokerStarsHandParserAssistant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParserConfig {
    @Bean
    public HandParserAssistant getInitPokerStarsParserAssistant() {
        PokerStarsHandParserAssistant assistant = new PokerStarsHandParserAssistant();
        assistant.setPokerStarsBlockNameEnum(PokerStarsBlockName.INIT);
        return assistant;
    }

    @Bean
    public HandParserAssistant getHoleCardsPokerStarsParserAssistant() {
        PokerStarsHandParserAssistant assistant = new PokerStarsHandParserAssistant();
        assistant.setPokerStarsBlockNameEnum(PokerStarsBlockName.HOLE_CARDS);
        return assistant;
    }

    @Bean
    public HandParserAssistant getFlopPokerStarsParserAssistant() {
        PokerStarsHandParserAssistant assistant = new PokerStarsHandParserAssistant();
        assistant.setPokerStarsBlockNameEnum(PokerStarsBlockName.FLOP);
        return assistant;
    }

    @Bean
    public HandParserAssistant getTurnPokerStarsParserAssistant() {
        PokerStarsHandParserAssistant assistant = new PokerStarsHandParserAssistant();
        assistant.setPokerStarsBlockNameEnum(PokerStarsBlockName.TURN);
        return assistant;
    }

    @Bean
    public HandParserAssistant getRiverPokerStarsParserAssistant() {
        PokerStarsHandParserAssistant assistant = new PokerStarsHandParserAssistant();
        assistant.setPokerStarsBlockNameEnum(PokerStarsBlockName.RIVER);
        return assistant;
    }

    @Bean
    public HandParserAssistant getSummaryPokerStarsParserAssistant() {
        PokerStarsHandParserAssistant assistant = new PokerStarsHandParserAssistant();
        assistant.setPokerStarsBlockNameEnum(PokerStarsBlockName.SUMMARY);
        return assistant;
    }
}
