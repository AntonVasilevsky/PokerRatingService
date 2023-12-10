package com.example.pokerratingservice.util.handparser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class HandParserTest {

    @Test
    void getStringByRegex_find_amount_with_usd() {
        //arrange
        String line = "Sharp(Gosu) collected $19.60 from pot";
        String regex = "\\$\\d+(\\.\\d+)?";
        int matcherGroupIndex = 0;
        String expected = "$19.60";
        //act
        String result = HandParser.getStringByRegex(line, regex, matcherGroupIndex);
        //assert
        Assertions.assertEquals(expected, result);
    }
    @Test
    void getStringByRegex_find_name_start_of_the_line() {
        //arrange
        String line = "Sharp(Gosu): collected $19.60 from pot";
        String regex = "^(.*?):";
        int matcherGroupIndex = 1;
        String expected = "Sharp(Gosu)";
        //act
        String result = HandParser.getStringByRegex(line, regex, matcherGroupIndex);
        //assert
        Assertions.assertEquals(expected, result);
    }
    @Test
    void getStringByRegex_line_is_null() {
        //arrange
        String line = null;
        String regex = "^(.*?):";
        int matcherGroupIndex = 1;
        String expected = "Sharp(Gosu)";
        //act
        Executable action = () -> HandParser.getStringByRegex(line, regex, matcherGroupIndex);
        //assert
        Assertions.assertThrows(IllegalArgumentException.class, action);
    }



}