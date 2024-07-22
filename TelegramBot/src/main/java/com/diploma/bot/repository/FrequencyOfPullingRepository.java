package com.diploma.bot.repository;

import com.diploma.bot.model.FrequencyOfPulling;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface FrequencyOfPullingRepository extends JpaRepository<FrequencyOfPulling, String> {

    @Query(value = "select frequency as fr, category as ct from frequency_of_pulling where user_id = :userId",
            nativeQuery = true)
    Set<Tuple> getJobsParametersByUserId(@Param("userId") String userId);

    List<FrequencyOfPulling> getAllByUserId(@Param("userId") String userId);

    @Query(value = "select count(*) > 1 as exists from frequency_of_pulling where user_id = :userId and category in(:cat)",
            nativeQuery = true)
    boolean validateSaveCategoriesByUserId(@Param("userId") String userId, @Param("cat") String category);
}
