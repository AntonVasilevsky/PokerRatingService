package com.example.pokerratingservice.util.parserassistant.assistants;

import com.example.pokerratingservice.dto.HandDto;
import com.example.pokerratingservice.dto.PlayerDto;
import com.example.pokerratingservice.service.HandService;
import com.example.pokerratingservice.service.PlayerService;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import com.example.pokerratingservice.util.parserassistant.AssistantData;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class PokerStarsRiverHandParserAssistant extends HandParserAssistant{

    @Override
    public void assist(String line, HandDto handDto, List<PlayerDto> playerDtoList, PlayerDto playerDto,
                       Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap, AssistantData assistantData) {
        appendLineToStringBuilderFromMap(getPokerStarsBlockNameEnum(), stringBuilderMap, line);
        System.out.println("Assisting in: " + this.getClass().getName());
    }

    @Override
    public void assist(String line, HandDto hand, List<PlayerDto> playerList, PlayerDto player, HandService handService, PlayerService playerService, HashSet<PlayerDto> playerSet, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap, List<HandDto> globalHandDtoList, Map<PlayerDto, Void> globalPlayerDtoList) {
        appendLineToStringBuilderFromMap(getPokerStarsBlockNameEnum(), stringBuilderMap, line);
        System.out.println("Assisting in: " + this.getClass().getName());
    }
}
