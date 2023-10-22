package com.example.pokerratingservice.repository;

import com.example.pokerratingservice.model.Hand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HandRepository extends JpaRepository<Hand, Long> {
}
