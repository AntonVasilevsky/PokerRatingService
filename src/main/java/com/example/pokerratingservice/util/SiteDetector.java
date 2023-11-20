package com.example.pokerratingservice.util;

import com.example.pokerratingservice.util.enums.PokerSiteName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;

import static com.example.pokerratingservice.util.enums.PokerSiteName.POKER_STARS;
@Component
public class SiteDetector {
    private static final Logger logger = LoggerFactory.getLogger(SiteDetector.class);
    private static final String POKER_STARS_FIRST_LINE = "PokerStars";

    public PokerSiteName detectSiteName(BufferedReader reader) throws IOException {

        String line = reader.readLine();
        if (line.contains(POKER_STARS_FIRST_LINE)) {
            return POKER_STARS;
        }else throw new IOException("Unknown file format");

    }

}

