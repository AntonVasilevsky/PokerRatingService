package com.example.pokerratingservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "customer")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Customer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "email")
    @Size(min = 2, max = 30)
    @Email()
    @NotEmpty(message = "email should not be empty")
    String email;
    @Column(name = "password")
    @Size(min = 8, message = "password should be at least 8 characters")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[^a-zA-Z]).+$", message = "password must contain at least one letter and one non-letter character")
    String password;
    @Column(name = "role")
    String role;

    public Customer(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
