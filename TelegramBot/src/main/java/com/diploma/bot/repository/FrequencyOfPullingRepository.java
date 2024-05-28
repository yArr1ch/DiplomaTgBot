package com.diploma.bot.repository;

import com.diploma.bot.model.FrequencyOfPulling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FrequencyOfPullingRepository extends JpaRepository<FrequencyOfPulling, String> {

}
