package com.example.pokerratingservice.util.handparser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandParserTest {

    @Test
    void getStringByRegex() {
        String line = "Sharp(Gosu) collected $19.60 from pot";
        String regex = "\\$\\d+(\\.\\d+)?";
        int matcherGroupIndex = 0;

        String result = HandParser.getStringByRegex(line, regex, matcherGroupIndex);

        String expected = "$19.60";
        Assertions.assertEquals(expected, result);
    }


}