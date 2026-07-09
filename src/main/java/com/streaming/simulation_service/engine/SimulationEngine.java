package com.streaming.simulation_service.engine;

import com.streaming.simulation_service.registry.SimulatorRegistry;
import com.streaming.simulation_service.model.event.GameEvent;
import com.streaming.simulation_service.model.state.SimulationGameState;
import com.streaming.simulation_service.simulators.Simulator;
import org.springframework.stereotype.Component;

/**
 * Called by the {@link SimulationOrchestrator}, this class serves as the primary engine to generate {@link GameEvent}'s for randomly
 * selected {@link SimulationGameState}'s. The engine selects a {@link Simulator} that will then generate sport-specific event to
 * publish to Kafka.
 *
 * @see SimulationOrchestrator
 * @see GameEvent
 * @see SimulationGameState
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
     * Randomly simulate a game event for a given {@link SimulationGameState} by selecting a random {@link Simulator} from the injected list of simulators and using it to generate an event.
     * The generated event is then published to Kafka.
     *
     * @param game The {@link SimulationGameState} to generate a game event for.
     */
    public void simulateEvent(SimulationGameState game) {
        // Select the appropriate simulator from the SimulatorRegistry.
//        Simulator simulator = simulatorRegistry.getSimulator(game.getSport());
//        System.out.println("Selected simulator: " + simulator.getClass().getSimpleName() + " for sport: " + game.getSport());

        // Generate an event
//        GameEvent event = simulator.generateEvent(game);
//        System.out.println("Generated event: " + event);

        // todo: publish event to kafka
    }
}
