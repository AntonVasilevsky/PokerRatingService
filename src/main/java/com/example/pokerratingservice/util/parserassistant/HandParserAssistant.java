package com.example.pokerratingservice.util.parserassistant;

import com.example.pokerratingservice.dto.HandDto;
import com.example.pokerratingservice.dto.PlayerDto;
import com.example.pokerratingservice.service.HandService;
import com.example.pokerratingservice.service.PlayerService;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Getter
@Setter

public abstract class HandParserAssistant  {
    private PokerStarsHandBlockName pokerStarsBlockNameEnum;
    private PlayerService playerService;
    private HandService handService;

    public abstract void assist(String line, HandDto hand, List<PlayerDto> playerList, PlayerDto player, HandService handService, PlayerService playerService, HashSet<PlayerDto> playerSet, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap);
    public void appendLineToStringBuilderFromMap(PokerStarsHandBlockName blockName, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap, String line) {
        stringBuilderMap.get(blockName).append(line).append("\n");
    }





}


