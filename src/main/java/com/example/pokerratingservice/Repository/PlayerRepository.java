package com.example.pokerratingservice.Repository;

import com.example.pokerratingservice.Model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, String> {
}
