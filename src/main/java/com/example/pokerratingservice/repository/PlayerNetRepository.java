package com.example.pokerratingservice.repository;

import com.example.pokerratingservice.model.PlayerNet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerNetRepository extends JpaRepository<PlayerNet, String> {
}
