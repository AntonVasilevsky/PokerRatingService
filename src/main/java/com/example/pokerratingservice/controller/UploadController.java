package com.example.pokerratingservice.controller;

import com.example.pokerratingservice.util.handparser.PokerStarsHandParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    PokerStarsHandParser pokerStarsHandParser;

    @PostMapping("")
    public HttpStatus uploadManyHands(@RequestParam("files") MultipartFile[] files) {
        try {
            for (MultipartFile file:files
                 ) {
                if(!file.isEmpty()) {
                    pokerStarsHandParser.parse(file);
                } else return HttpStatus.NOT_FOUND;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading file", e);
        }
        return HttpStatus.ACCEPTED;
    }
}
