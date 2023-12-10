package com.example.pokerratingservice.repository;

import com.example.pokerratingservice.model.PlayerNet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerNetRepository extends JpaRepository<PlayerNet, Integer> {
    List<PlayerNet> findByName(String name);
}
