package com.example.pokerratingservice.util.parserassistant.assistants;

import com.example.pokerratingservice.dto.HandDto;
import com.example.pokerratingservice.dto.PlayerDto;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import com.example.pokerratingservice.util.parserassistant.AssistantData;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class PokerStarsTurnHandParserAssistant extends HandParserAssistant{
    @Override
    public void assist(String line, HandDto handDto, PlayerDto playerDto, AssistantData assistantData) {
        Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap = assistantData.getStringBuilderMap();
        appendLineToStringBuilderFromMap(getPokerStarsBlockNameEnum(), stringBuilderMap, line);
        processPersonNetDto(assistantData, line);
    }

    @Override
    public PokerStarsHandBlockName getPokerStarsBlockNameEnum() {
        return PokerStarsHandBlockName.TURN;
    }
}
