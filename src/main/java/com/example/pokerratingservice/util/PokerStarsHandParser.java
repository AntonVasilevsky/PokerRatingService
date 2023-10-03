package com.example.pokerratingservice.util;


import com.example.pokerratingservice.Model.GameType;
import com.example.pokerratingservice.Model.Hand;
import com.example.pokerratingservice.Model.MaxPlayers;
import com.example.pokerratingservice.Model.Player;
import com.example.pokerratingservice.Repository.HandRepository;
import com.example.pokerratingservice.Repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PokerStarsHandParser implements HandParser {

    private final PlayerRepository playerRepository;            //TODO нарушает ли сингл респо?
    private final HandRepository handRepository;

    private static final Logger logger = LoggerFactory.getLogger(PokerStarsHandParser.class);



    private double getAmountWon(String hand, String bet, String raise, String call, String bigBlind, String smallBlind, String totalPotString) throws IOException {
        return 0;
    }


    @Override
    public double getHeroPutMoneyInPotFromLine(String line) {
        String regex = "\\$([0-9.]+)";
        String valueAfterDollar = HandParser.getStringByRegex(line, regex, 1);

        return Double.parseDouble(valueAfterDollar);
    }

    @Override
    public void readFile(String path) throws IOException {



    }






    @Override
    public String getPlayerNameFromLine(String line) {
        String regex = "Seat\\s\\d+:\\s(.*?)\\s\\(";
        return HandParser.getStringByRegex(line, regex, 1);
    }

    @Override
    public int getMaxPLayersFromLine(String line) {
        if (line.contains("2-max")) {
            return 2;
        } else if (line.contains("6-max")) {
            return 6;
        } else if (line.contains("9-max")) {
            return 9;
        } else if (line.contains("10-max"))
            return 10;
        return -1;
    }
    @Override
    public String getTableNameFromLine(String line) {
        String regex = "'(.*?)'";
        return HandParser.getStringByRegex(line, regex, 1);
    }
    @Override
    public LocalDateTime getDateValueFromLine(String line) {
        String regex = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}";
        String group = HandParser.getStringByRegex(line, regex);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return LocalDateTime.parse(group, dtf);
    }

    @Override
    public double getBigBlindValueFromLine(String string) {
        String regex = "\\$([0-9.]+)";
        String group = HandParser.getStringByRegex(string, regex, 1);

        return Double.parseDouble(group);
    }
    @Override
    public long getHandIdValueFromLine(String line) {
        String regex = "#(\\d+)";
        String group = HandParser.getStringByRegex(line, regex, 1);
        return Long.parseLong(group);
    }

    @Override
    public GameType getGameTypeFromLine(String line) {
        if (line.contains("Hold'em Limit")) {
            return GameType.FL;
        } else if (line.contains("Hold'em No Limit")) {
            return GameType.NL;
        } else
            return GameType.PLO;

    }

}
