package com.example.pokerratingservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    void process(MultipartFile file) throws IOException;
}
