package com.diploma.bot.service;

import com.diploma.bot.model.FrequencyOfPulling;
import com.diploma.bot.repository.FrequencyOfPullingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FrequencyOfPullingService {

    private final FrequencyOfPullingRepository frequencyOfPullingRepository;

    @Transactional
    public String createFrequencyRecord(String userId, String category, String frequency, String chatId) {
        boolean exists = frequencyOfPullingRepository.validateSaveCategoriesByUserId(userId, category);
        if (exists) {
            return "Frequency with existing category already exists, please update if needed";
        }
        FrequencyOfPulling frequencyOfPulling = FrequencyOfPulling.builder()
                .userId(userId)
                .category(category)
                .frequency(frequency)
                .chatId(chatId)
                .build();

        frequencyOfPullingRepository.saveAndFlush(frequencyOfPulling);
        log.info("Data was saved in db: {}", frequencyOfPulling);

        return "Completed";
    }

    public String getAllFrequencies(String userId) {
        List<FrequencyOfPulling> jobs = frequencyOfPullingRepository.getAllByUserId(userId);

        StringBuilder result = new StringBuilder();
        jobs.forEach(j -> {
            result.append("| ").append(j.getCategory()).append(" = ").append(j.getFrequency()).append(" |\n");
        });

        return result.toString();
    }
}
