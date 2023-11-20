package com.example.pokerratingservice.repository;

import com.example.pokerratingservice.model.Hand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class HandRepositoryTests {
    @Autowired
    private HandRepository handRepository;
    @Test
    public void handRepository_saveOne_returnsSavedHand() {

        Hand hand = Hand.builder()
                .id(123L)
                .build();

        Hand savedHand = handRepository.save(hand);

        Assertions.assertEquals(hand, savedHand);
    }
    @Test
    public void handRepository_saveAll_returnsListOfHands() {

        List<Hand> handList = new ArrayList<>();
        handList.add(Hand.builder().id(1L).build());
        handList.add(Hand.builder().id(2L).build());

        List<Hand> savedHandList = handRepository.saveAll(handList);

        Assertions.assertEquals(handList, savedHandList);
    }
}
