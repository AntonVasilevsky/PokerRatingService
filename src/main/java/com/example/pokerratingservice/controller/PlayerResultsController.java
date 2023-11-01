package com.example.pokerratingservice.controller;

import com.example.pokerratingservice.dto.HandDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/get-results")
public class PlayerResultsController {

    @GetMapping("")
    public List<HandDto> getResults(@RequestParam String name) {
        return null;
    }
}
