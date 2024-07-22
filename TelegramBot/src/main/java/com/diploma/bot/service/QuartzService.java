package com.diploma.bot.service;

import com.diploma.bot.job.ExecutionJob;
import com.diploma.bot.repository.FrequencyOfPullingRepository;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuartzService {

    private final SchedulerFactoryBean schedulerFactory;
    private final FrequencyOfPullingRepository frequencyOfPullingRepository;
    private final Set<Pair<JobDetail, Trigger>> jobs = new HashSet<>();

    private static final Map<String, String> CONTROLLERS = Map.of(
            "Crypto Frequency", "CryptoController",
            "Currency Frequency", "CurrencyController",
            "Energy Frequency", "EnergyController",
            "Finance Frequency", "FinancialController",
            "Futures Frequency", "FuturesController",
            "Real Estate Frequency", "RealEstateController",
            "Stocks: Most Active Frequency", "StockControllerMostActive",
            "Stocks: Gainers Frequency", "StockControllerGainers",
            "Stocks: Losers Frequency", "StockControllerLosers",
            "Technology Frequency", "TechnologyController"
    );

    public void setFrequencyOfCurrentValue(String category, int frequency) throws SchedulerException {
        setJobs(category, frequency);
        runJobs(getScheduler());
    }

    private Scheduler getScheduler() {
        return schedulerFactory.getScheduler();
    }

    private String getControllerName(String category) {
        return CONTROLLERS.get(category);
    }

    private void runJobs(Scheduler scheduler) throws SchedulerException {
        if (!scheduler.isStarted()) {
            scheduler.start();
        }
        jobs.forEach(j -> {
            try {
                if (!scheduler.checkExists(j.getLeft().getKey())) {
                    scheduler.scheduleJob(j.getLeft(), j.getRight());
                }
            } catch (SchedulerException e) {
                throw new RuntimeException("Error occurred while scheduling jobs: " + e.getMessage());
            }
        });
    }

    private void setJobs(String category, int frequency) {
        String jobName = category + "_job_" + UUID.randomUUID();
        JobKey jobKey = new JobKey(jobName, "DEFAULT");

        JobDetail jobDetail = JobBuilder.newJob(ExecutionJob.class)
                .withIdentity(jobKey)
                .usingJobData("controllerName", getControllerName(category))
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName + "_trigger", "DEFAULT")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(frequency))
                .build();

        log.info("Job key = {}", jobDetail.getKey());
        jobs.add(Pair.of(jobDetail, trigger));
    }

    public void runExistingJobs(String userId) throws SchedulerException {
        Map<String, String> map = new HashMap<>();

        Set<Tuple> jobsValues = frequencyOfPullingRepository.getJobsParametersByUserId(userId);
        jobsValues.forEach(j -> {
            String frequency = j.get("fr", String.class);
            String category = j.get("ct", String.class);
            map.put(category, frequency);
        });

        map.forEach((c, f) -> setJobs(c, Integer.parseInt(f)));
        runJobs(getScheduler());
    }
}
