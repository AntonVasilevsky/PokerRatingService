package com.example.pokerratingservice.util;

import com.example.pokerratingservice.model.GameType;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface HandParser {
    static String getStringByRegex(String line, String regex, int matcherGroupIndex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        matcher.find();
        return matcher.group(matcherGroupIndex);
    }

    static String getStringByRegex(String line, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        matcher.find();
        return matcher.group();
    }
    void parse(File file) throws IOException;
    void readFiles(String path) throws IOException;


    String getPlayerNameFromLine(String line);

    int getMaxPLayersFromLine(String line);

    String getTableNameFromLine(String line);

    LocalDateTime getDateValueFromLine(String line);

    double getBigBlindValueFromLine(String line);
    double getHeroPutMoneyInPotFromLine(String line);

    long getHandIdValueFromLine(String line);

    GameType getGameTypeFromLine(String line);  // return type?
}
