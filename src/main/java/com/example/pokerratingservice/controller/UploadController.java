package com.example.pokerratingservice.controller;

import com.example.pokerratingservice.service.UploadService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
@AllArgsConstructor
public class UploadController {

    private UploadService uploadService;
    @PostMapping("")
    public HttpStatus uploadManyHands(@RequestParam("files") MultipartFile[] files) {
        try {
            for (MultipartFile file : files
            ) {
                if (!file.isEmpty()) {
                    uploadService.process(file);
                } else return HttpStatus.NOT_FOUND;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading file", e);
        }
        return HttpStatus.ACCEPTED;
    }
}

