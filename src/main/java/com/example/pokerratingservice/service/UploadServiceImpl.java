package com.example.pokerratingservice.service;

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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UploadServiceImpl implements UploadService {
    private final SiteDetector siteDetector;
    private final Map<PokerSiteName, HandParser> parserMap;
    private static final Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);

    public UploadServiceImpl(List<HandParser> parsers, SiteDetector siteDetector) {
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

            HandParser handParser = parserMap.get(pokerSiteName);
            handParser.parse(reader);
        }
    }


}
