package com.diploma.bot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "frequency_of_pulling")
public class FrequencyOfPulling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "category")
    private String category;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "chat_id")
    private String chatId;
}
