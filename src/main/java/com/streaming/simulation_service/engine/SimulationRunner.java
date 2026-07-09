package com.streaming.simulation_service.engine;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Periodically triggers simulation ticks at fixed intervals.
 *
 * <p>
 * This component acts as a scheduler that invokes the {@link SimulationOrchestrator}
 * at regular intervals (every 5 seconds by default). Each tick orchestrates the generation
 * of match events for all active simulated matches.</p>
 *
 * @see SimulationOrchestrator
 */
@Component
public class SimulationRunner {

    /** The orchestrator that will handle the entirety of the simulation process. */
    private final SimulationOrchestrator orchestrator;

    /**
     * Construct a new {@code SimulationRunner}.
     *
     * @param orchestrator the {@link SimulationOrchestrator} to invoke on each tick.
     */
    public SimulationRunner(SimulationOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    /**
     * Execute one simulation tick.
     * <p>
     * Invoked automatically every 5 seconds by the Spring scheduler ({@code @Scheduled}).
     * Each tick calls the {@link SimulationOrchestrator} to generate events for all active matches and publish them.
     *
     * @see SimulationOrchestrator#simulate()
     */
    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    public void tick() {
        this.orchestrator.simulate();
    }

}
