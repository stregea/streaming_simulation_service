package com.streaming.simulation_service.engine;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class SimulationRunner {
    // todo

    private final SimulationOrchestrator orchestrator;

    public SimulationRunner(SimulationOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    public void tick() {
        this.orchestrator.simulate();
    }

}
