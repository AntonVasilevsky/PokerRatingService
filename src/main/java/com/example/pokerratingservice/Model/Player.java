package com.example.pokerratingservice.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.List;

@Entity
@Table(name = "player")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Player {
    @Id
    @Column(name = "id")
    private String id;
    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.PERSIST)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinTable(
            name = "player_hand",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "hand_id")
    )
    private List<Hand> handList;

    @Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +

                '}';
    }
}
