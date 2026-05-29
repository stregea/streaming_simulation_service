package com.streaming.simulation_service.engine;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class SimulationScheduler {
    // todo

    private final SimulationOrchestrator orchestrator;

    public SimulationScheduler(SimulationOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    public void schedule() {
        this.orchestrator.simulate();
    }

}
