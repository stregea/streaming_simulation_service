package com.streaming.simulation_service.engine;

import com.streaming.simulation_service.registry.SimulatorRegistry;
import com.streaming.simulation_service.model.event.MatchEvent;
import com.streaming.simulation_service.model.state.SimulationMatchState;
import com.streaming.simulation_service.simulators.Simulator;
import org.springframework.stereotype.Component;

/**
 * Called by the {@link SimulationOrchestrator}, this class serves as the primary engine to generate {@link MatchEvent}'s for randomly
 * selected {@link SimulationMatchState}'s. The engine selects a {@link Simulator} that will then generate sport-specific event to
 * publish to Kafka.
 *
 * @see SimulationOrchestrator
 * @see MatchEvent
 * @see SimulationMatchState
 * @see Simulator
 */
@Component
public class SimulationEngine {

    /**
     * Object that will select the correct {@link Simulator} based on the sport being simulated.
     */
    private final SimulatorRegistry simulatorRegistry;

    /**
     * Construct a new {@code SimulationEngine}.
     *
     * @param simulatorRegistry the {@link SimulatorRegistry} for retrieving sport-specific simulators
     */
    public SimulationEngine(SimulatorRegistry simulatorRegistry) {
        this.simulatorRegistry = simulatorRegistry;
    }

    /**
     * Simulate a match event for the given {@link SimulationMatchState} by selecting the appropriate {@link Simulator} for the match sport and generating an event.
     * The generated event is then published to Kafka.
     *
     * @param match The {@link SimulationMatchState} to generate a match event for.
     */
    public void simulateEvent(SimulationMatchState match) {
        // Select the appropriate simulator from the SimulatorRegistry.
//        Simulator simulator = simulatorRegistry.getSimulator(match.getSport());
//        System.out.println("Selected simulator: " + simulator.getClass().getSimpleName() + " for sport: " + match.getSport());

        // Generate an event
//        MatchEvent event = simulator.generateEvent(match);
//        System.out.println("Generated event: " + event);

        // todo: publish event to kafka
    }
}
