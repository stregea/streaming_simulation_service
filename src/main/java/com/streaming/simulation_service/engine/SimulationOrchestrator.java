package com.streaming.simulation_service.engine;

import com.streaming.simulation_service.model.state.SimulationMatchState;
import com.streaming.simulation_service.registry.ActiveMatchesRegistry;
import org.springframework.stereotype.Component;

/**
 * The Orchestrator behind the simulation of all matches. This class communicates with the {@link ActiveMatchesRegistry} and
 * {@link SimulationLifecycleManager} to help manage the match states as well as communicating with the {@link SimulationEngine}
 * to generate events for randomly selected matches.
 *
 * @see SimulationRunner
 * @see ActiveMatchesRegistry
 * @see SimulationLifecycleManager
 * @see SimulationEngine
 */
@Component
public class SimulationOrchestrator {

    /**
     * Registry that contains all active {@link SimulationMatchState}'s within the simulation.
     */
    private final ActiveMatchesRegistry registry;

    /**
     * Object which will manage the lifecycle of matches being simulated.
     */
    private final SimulationLifecycleManager lifecycleManager;

    /**
     * The primary engine that will generate events for the randomly selected matches.
     */
    private final SimulationEngine engine;

    /**
     * Construct a new {@code SimulationOrchestrator}.
     *
     * @param lifecycleManager the {@link SimulationLifecycleManager} for managing match lifecycle.
     * @param engine           the {@link SimulationEngine} for generating events.
     */
    public SimulationOrchestrator(ActiveMatchesRegistry registry, SimulationLifecycleManager lifecycleManager, SimulationEngine engine) {
        this.registry = registry;
        this.lifecycleManager = lifecycleManager;
        this.engine = engine;
    }

    /**
     * Orchestrates the overall simulation process by:
     * <ul>
     *   <li>Selecting a random active match from the lifecycle manager</li>
     *   <li>Invoking the simulation engine to generate an event for that match</li>
     * </ul>
     *
     * <p>
     * This method is called periodically by the {@link SimulationRunner}.
     *
     * @see SimulationRunner#tick()
     * @see ActiveMatchesRegistry#getRandomMatch()
     * @see SimulationEngine#simulateEvent(SimulationMatchState)
     * @see SimulationLifecycleManager#replaceMatch(SimulationMatchState)
     */
    public void simulate() {
        SimulationMatchState match = registry.getRandomMatch().orElse(null);

        if (match == null) {
            System.out.println("No active matches found to simulate.");
            return;
        }

        System.out.printf("Simulating match %s | sport %s...%n", match.getMatch().id(), match.getMatch().sport());

        engine.simulateEvent(match);

        if (match.isMatchOver()) {
            lifecycleManager.replaceMatch(match);
        }
    }
}
