package com.example.pokerratingservice.util.parserassistant.assistants;

import com.example.pokerratingservice.dto.HandDto;
import com.example.pokerratingservice.dto.PlayerDto;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import com.example.pokerratingservice.util.parserassistant.AssistantData;

import java.util.Map;

public class PokerStarsFlopHandParserAssistant extends HandParserAssistant{

    @Override
    public void assist(String line, HandDto handDto, PlayerDto playerDto, AssistantData assistantData) {
        Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap = assistantData.getStringBuilderMap();
        appendLineToStringBuilderFromMap(getPokerStarsBlockNameEnum(), stringBuilderMap, line);
        processPersonNetDto(assistantData, line);


    }




}
