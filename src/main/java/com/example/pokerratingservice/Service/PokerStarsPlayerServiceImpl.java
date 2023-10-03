package com.example.pokerratingservice.Service;

import com.example.pokerratingservice.Model.Player;
import com.example.pokerratingservice.Repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
@Service
@RequiredArgsConstructor
public class PokerStarsPlayerServiceImpl implements PlayerService{
    private final PlayerRepository playerRepository;

    @Override
    public void saveOne(Player player) {
        playerRepository.save(player);
    }

    @Override
    public Player getById(String id) {
        return playerRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Player " + id + " was not found")
        );
    }
}
