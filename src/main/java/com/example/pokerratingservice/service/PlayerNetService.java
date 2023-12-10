package com.example.pokerratingservice.service;

import com.example.pokerratingservice.dto.PlayerNetDto;
import com.example.pokerratingservice.model.PlayerNet;
import com.example.pokerratingservice.repository.PlayerNetRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PlayerNetService {
    PlayerNetRepository playerNetRepository;

    ModelMapper modelMapper;

    public PlayerNet convertDtoToPlayerNet(PlayerNetDto playerNetDto) {
         return modelMapper.map(playerNetDto, PlayerNet.class);
    }
    public void saveOne(PlayerNet playerNet) {
        playerNetRepository.save(playerNet);
    }
    public void saveAll(List<PlayerNet> playerNetList) {playerNetRepository.saveAll(playerNetList); }

    public List<PlayerNet> getAllByName(String name) {
        return playerNetRepository.findByName(name);
    }
}
