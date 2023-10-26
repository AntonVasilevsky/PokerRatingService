package com.example.pokerratingservice.util.parserassistant;

import com.example.pokerratingservice.model.Hand;
import com.example.pokerratingservice.model.Player;
import com.example.pokerratingservice.service.HandService;
import com.example.pokerratingservice.service.PlayerService;
import com.example.pokerratingservice.util.enums.PokerSiteName;
import com.example.pokerratingservice.util.enums.PokerStarsHandBlockName;
import com.example.pokerratingservice.util.handparser.HandParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SiteDetectorParserAssistant extends HandParserAssistant {
    private static final Logger logger = LoggerFactory.getLogger(SiteDetectorParserAssistant.class);
    private Map<PokerSiteName, HandParser> parserMap;


    public SiteDetectorParserAssistant(Map<PokerSiteName, HandParser> parserMap, List<HandParser> parsers) {
        this.parserMap = parsers.stream().collect(Collectors.toMap());
    }

    @Override
    public void assist(String line, Hand hand, List<Player> playerList, Player player, HandService handService, PlayerService playerService, Set<Player> playerSet, Map<PokerStarsHandBlockName, StringBuilder> stringBuilderMap) {

    }

    public HandParserAssistant detect(MultipartFile file) throws IOException {
        logger.debug("Reading file: {}", file);
        try (
                InputStreamReader isr = new InputStreamReader(file.getInputStream());
                BufferedReader reader = new BufferedReader(isr)) {

                reader.mark(1024);

            return null;
        }
    }
}
