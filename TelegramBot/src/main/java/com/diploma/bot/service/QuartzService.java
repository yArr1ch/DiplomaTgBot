package com.diploma.bot.service;

import com.diploma.bot.job.ExecutionJob;
import com.diploma.bot.repository.FrequencyOfPullingRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class QuartzService {

    private final SchedulerFactoryBean schedulerFactory;
    private final FrequencyOfPullingRepository frequencyOfPullingRepository;
    private Set<Pair<JobDetail, Trigger>> jobs = new HashSet<>();

    private static final Map<String, String> CONTROLLERS = Map.of(
            "Crypto Frequency", "CryptoController",
            "Currency Frequency", "CurrencyController",
            "Energy Frequency", "EnergyController",
            "Finance Frequency","FinancialController",
            "Futures Frequency","FuturesController",
            "Real Estate Frequency", "RealEstateController",
            "Stocks: Most Active Frequency", "StockControllerMostActive",
            "Stocks: Gainers Frequency", "StockControllerGainers",
            "Stocks: Losers Frequency", "StockControllerLosers",
            "Technology Frequency", "TechnologyController"
    );

    public void setFrequencyOfCurrentValue(String category, int frequency, String userId) {
        //Scheduler scheduler = schedulerFactory.getScheduler();
        setJobs(category, userId, frequency);
        runJobs(getScheduler());
    }

    private Scheduler getScheduler() {
        return schedulerFactory.getScheduler();
    }

    private String getControllerName(String category) {
        return CONTROLLERS.get(category);
    }

    private void runJobs(Scheduler scheduler) {
        jobs.forEach(j -> {
            try {
                scheduler.start();
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
            try {
                scheduler.scheduleJob(j.getLeft(), j.getRight());
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setJobs(String category, String userId, int frequency) {
        JobDetail jobDetail = JobBuilder.newJob(ExecutionJob.class)
                .withIdentity(category + "_job")
                .usingJobData("controllerName", getControllerName(category))
                .usingJobData("userId", userId)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(category + "_trigger")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(frequency))
                .build();

        jobs.add(Pair.of(jobDetail, trigger));
    }

    public void runExistingJobs(String userId) {
       /* Map<String, String> map = new HashMap<>();

        Set<String> cat = frequencyOfPullingRepository.getCategoryOfPullingByUserId(userId);
        Set<String> frq = frequencyOfPullingRepository.getFrequencyOfPullingByUserId(userId);

        if (cat.size() != frq.size()) {
            throw new IllegalArgumentException("Categories and frequencies must have the same size.");
        }

        cat.forEach(c -> {
            frq.forEach(f -> {
                map.put(c, f);
            });
        });

        if (cat.size() != 0 && frq.size() != 0) {
            map.forEach((c, f) -> setJobs(c, userId, Integer.parseInt(f)));
        }
        runJobs(getScheduler());*/
    }
}
