package com.example.pokerratingservice.repository;

import com.example.pokerratingservice.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, String> {
}
