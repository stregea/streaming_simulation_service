package com.streaming.simulation_service.engine;

import org.springframework.stereotype.Component;

@Component
public class SimulationOrchestrator {

    // todo

    private final SimulationLifecycleManager lifecycleManager;

    public SimulationOrchestrator(SimulationLifecycleManager lifecycleManager) {
        this.lifecycleManager = lifecycleManager;
    }

    public void simulate() {
        System.out.println("Simulating games...");
    }
}
