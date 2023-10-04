package com.example.pokerratingservice.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "player")
@Getter
@Setter
@Builder
@AllArgsConstructor

public class Player {
    @Id
    @Column(name = "id")
    private String id;
    @ManyToMany
    @JoinTable(
            name = "player_hand",
            joinColumns = @JoinColumn(name="player_id"),
            inverseJoinColumns = @JoinColumn(name="hand_id")
    )

    private List<Hand> handList;
    public Player(){};
}
