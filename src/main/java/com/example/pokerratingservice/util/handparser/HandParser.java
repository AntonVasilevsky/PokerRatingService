package com.example.pokerratingservice.util.handparser;

import com.example.pokerratingservice.model.GameType;
import com.example.pokerratingservice.util.enums.PokerSiteName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@AllArgsConstructor
@Getter
public abstract class HandParser implements SiteSupporter{
    private PokerSiteName pokerSiteName;
    public static String getStringByRegex(String line, String regex, int matcherGroupIndex) {
        if(line == null || regex == null || line.isBlank() || regex.isBlank())
            throw new IllegalArgumentException("Line or regex should not be empty");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        matcher.find();
        return matcher.group(matcherGroupIndex);
    }

    public static String getStringByRegex(String line, String regex) {
        if(line == null || regex == null || line.isBlank() || regex.isBlank())
            throw new IllegalArgumentException("Line or regex should not be blank");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        matcher.find();
        return matcher.group();
    }

    public abstract AssignedData parse(BufferedReader reader) throws IOException;

    abstract String getPlayerNameFromLine(String line);

    abstract int getMaxPLayersFromLine(String line);
    abstract String getTableNameFromLine(String line);

    abstract LocalDateTime getDateValueFromLine(String line);

    abstract double getBigBlindValueFromLine(String line);
    abstract double getHeroPutMoneyInPotFromLine(String line);

    abstract long getHandIdValueFromLine(String line);

    abstract GameType getGameTypeFromLine(String line);  // return type?
}
