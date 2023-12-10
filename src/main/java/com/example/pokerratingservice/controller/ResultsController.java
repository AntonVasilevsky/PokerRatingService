package com.example.pokerratingservice.controller;

import com.example.pokerratingservice.model.Hand;
import com.example.pokerratingservice.model.Player;
import com.example.pokerratingservice.model.PlayerDataRequest;
import com.example.pokerratingservice.model.PlayerNet;
import com.example.pokerratingservice.service.HandService;
import com.example.pokerratingservice.service.PlayerNetService;
import com.example.pokerratingservice.service.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/get-results")
@AllArgsConstructor
public class ResultsController {
    private final HandService handService;
    private final PlayerService playerService;
    private final PlayerNetService playerNetService;
    @GetMapping("")
    public List<PlayerNet> getAllResultsByName(@RequestParam String name) {
        return playerNetService.getAllByName(name);
    }
    @GetMapping("/one")
    public List<Hand> getOne(@RequestBody PlayerDataRequest request) {
        System.out.println(request.getName());
        Player player = playerService.getById(request.getName()).orElseThrow();
        player.getHandList().forEach(System.out::println);

        return player.getHandList();
    }
}
