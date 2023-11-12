package com.example.pokerratingservice.service;

import com.example.pokerratingservice.model.Hand;
import com.example.pokerratingservice.model.Player;
import com.example.pokerratingservice.util.SiteDetector;
import com.example.pokerratingservice.util.enums.PokerSiteName;
import com.example.pokerratingservice.util.handparser.HandParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Service

public class UploadService {
    private final SiteDetector siteDetector;
    private final Map<PokerSiteName, HandParser> parserMap;
    private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

    public UploadService(List<HandParser> parsers, SiteDetector siteDetector) {
        this.siteDetector = siteDetector;
        parserMap = parsers.stream().collect(Collectors.toMap(HandParser::getPokerSiteName, x -> x));

    }

    public void process(MultipartFile file) throws IOException {
        logger.debug("Reading file: {}", file);
        try (
                InputStreamReader isr = new InputStreamReader(file.getInputStream());
                BufferedReader reader = new BufferedReader(isr)) {

            reader.mark(1024);
            PokerSiteName pokerSiteName = siteDetector.detectSiteName(reader);
            reader.reset();
            Map<Player, Void> playerMapGlobal = new HashMap<>();
            Set<Player> playerSetAssigned = new HashSet<>();
            Set<Hand> handSetAssigned = new HashSet<>();

            HandParser handParser = parserMap.get(pokerSiteName);
            handParser.parse(reader, playerSetAssigned, handSetAssigned, playerMapGlobal);
        }
    }


}
