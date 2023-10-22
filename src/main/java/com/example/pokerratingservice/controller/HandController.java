package com.example.pokerratingservice.controller;

import com.example.pokerratingservice.model.Hand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HandController {
    @GetMapping()
    public List<Hand> getHands() {
        return null;
    }
}
