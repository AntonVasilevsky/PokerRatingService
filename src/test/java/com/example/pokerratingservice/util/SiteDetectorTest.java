package com.example.pokerratingservice.util;

import com.example.pokerratingservice.util.enums.PokerSiteName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

class SiteDetectorTest {
    public static final String POKER_STARS_FIRST_LINE = "PokerStars";
    @Test
    void detectSiteName_PokerStars() throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new StringReader(POKER_STARS_FIRST_LINE));
        SiteDetector siteDetector = new SiteDetector();


            PokerSiteName result = siteDetector.detectSiteName(bufferedReader);
            Assertions.assertEquals(PokerSiteName.POKER_STARS, result);


    }

    @Test
    void detectSiteName_UnknownFormat() throws IOException {
        //Arrange
        String line = "some line";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(line));
        SiteDetector siteDetector = new SiteDetector();
        //Act &
        //Assert
        IOException ioException = Assertions.assertThrows(IOException.class, () -> siteDetector.detectSiteName(bufferedReader));
        Assertions.assertEquals("Unknown file format", ioException.getMessage());
    }
}