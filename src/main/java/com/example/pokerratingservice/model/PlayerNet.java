package com.example.pokerratingservice.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@Entity
@Table(name = "player_net")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PlayerNet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "name")
    String name;
    @Transient
    long handId;
    @Column(name = "date")
    LocalDateTime date;
    @Transient
    double vpip;
    @Column(name = "won")
    double wonPerHand;
    @Transient
    boolean won;

}
