package com.example.pokerratingservice.service;

import com.example.pokerratingservice.model.Hand;
import com.example.pokerratingservice.model.Player;
import com.example.pokerratingservice.util.SiteDetector;
import com.example.pokerratingservice.util.enums.PokerSiteName;
import com.example.pokerratingservice.util.handparser.AssignedData;
import com.example.pokerratingservice.util.handparser.HandParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UploadServiceImpl implements UploadService {
    private final SiteDetector siteDetector;
    private final Map<PokerSiteName, HandParser> parserMap;
    private final HandService handService;
    private final PlayerService playerService;
    public UploadServiceImpl(List<HandParser> parsers, SiteDetector siteDetector, HandService handService, PlayerService playerService) {
        this.siteDetector = siteDetector;
        parserMap = parsers.stream().collect(Collectors.toMap(HandParser::getPokerSiteName, x -> x));

        this.handService = handService;
        this.playerService = playerService;
    }

    public void process(MultipartFile file) throws IOException {
        log.debug("Reading file: {}", file);
        try (
                InputStreamReader isr = new InputStreamReader(file.getInputStream());
                BufferedReader reader = new BufferedReader(isr)) {

            reader.mark(1024);
            PokerSiteName pokerSiteName = siteDetector.detectSiteName(reader);
            reader.reset();

            HandParser handParser = parserMap.get(pokerSiteName);
            AssignedData assignedData = handParser.parse(reader);
            saveAssigned(assignedData.getPlayers(), assignedData.getHands());

        }
    }
    public void saveAssigned(Set<Player> playerSetAssigned, Set<Hand> handSetAssigned) {
        handService.saveAll(handSetAssigned.stream().toList());
        playerService.saveAll(playerSetAssigned.stream().toList());
    }


}
