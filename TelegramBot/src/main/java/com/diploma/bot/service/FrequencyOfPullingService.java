package com.diploma.bot.service;

import com.diploma.bot.model.FrequencyOfPulling;
import com.diploma.bot.repository.FrequencyOfPullingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FrequencyOfPullingService {

    private final FrequencyOfPullingRepository frequencyOfPullingRepository;

    public void createFrequencyRecord(String userId, String category, String frequency, String chatId) {
        FrequencyOfPulling frequencyOfPulling = FrequencyOfPulling.builder()
                .userId(userId)
                .category(category)
                .frequency(frequency)
                .chatId(chatId)
                .build();
        frequencyOfPullingRepository.saveAndFlush(frequencyOfPulling);
    }
}
