package com.example.pokerratingservice.util.parserassistant;

import com.example.pokerratingservice.model.GameType;
import com.example.pokerratingservice.model.Hand;
import com.example.pokerratingservice.model.Player;
import com.example.pokerratingservice.service.HandService;
import com.example.pokerratingservice.service.PlayerService;
import com.example.pokerratingservice.util.HandParser;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public abstract class HandParserAssistant implements HandParser {
    private PokerStarsHandBlockName pokerStarsBlockNameEnum;
    public abstract void assist(String line, Hand hand, List<Player> playerList, Player player, HandService handService, PlayerService playerService,  Set<Player> playerSet, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap);
    public void appendLineToStringBuilderFromMap(PokerStarsHandBlockName blockName, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap, String line) {
        stringBuilderMap.get(blockName).append(line).append("\n");
    }

    @Override
    public void parse(File file) throws IOException {

    }

    @Override
    public void readFiles(String path) throws IOException {

    }

    @Override
    public String getPlayerNameFromLine(String line) {
        return null;
    }

    @Override
    public int getMaxPLayersFromLine(String line) {
        return 0;
    }

    @Override
    public String getTableNameFromLine(String line) {
        return null;
    }

    @Override
    public LocalDateTime getDateValueFromLine(String line) {
        return null;
    }

    @Override
    public double getBigBlindValueFromLine(String line) {
        return 0;
    }

    @Override
    public double getHeroPutMoneyInPotFromLine(String line) {
        return 0;
    }

    @Override
    public long getHandIdValueFromLine(String line) {
        return 0;
    }

    @Override
    public GameType getGameTypeFromLine(String line) {
        return null;
    }
}


