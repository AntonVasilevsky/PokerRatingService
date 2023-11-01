package com.example.pokerratingservice.service;

import com.example.pokerratingservice.dto.PlayerDto;
import com.example.pokerratingservice.model.Player;
import com.example.pokerratingservice.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PokerStarsPlayerServiceImpl implements com.example.pokerratingservice.service.PlayerService {
    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;


    @Override
    public void saveOne(Player player) {
        playerRepository.save(player);
    }

    @Override
    public void saveAll(List<Player> playerList) {
        playerRepository.saveAll(playerList);
    }

    @Override
    public Optional<Player> getById(String id) {
        return playerRepository.findById(id);
    }

    @Override
    public PlayerDto convertPlayerToDto(Player player) {
        return modelMapper.map(player, PlayerDto.class);
    }

    @Override
    public Player convertDtoToPLayer(PlayerDto playerDto) {
        return modelMapper.map(playerDto, Player.class);
    }
}
