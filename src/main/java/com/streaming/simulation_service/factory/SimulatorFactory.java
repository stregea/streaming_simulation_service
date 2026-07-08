package com.streaming.simulation_service.factory;

import com.streaming.simulation_service.model.enums.Sport;
import com.streaming.simulation_service.simulators.Simulator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class that will be responsible for providing the correct {@link Simulator} implementation based on the {@link Sport} being simulated.
 *
 * @see Simulator
 * @see Sport
 */
@Component
public class SimulatorFactory {

    /**
     * Map of simulators that will allow for constant-time lookup.
     */
    private final Map<Sport, Simulator> simulators;

    /**
     * Construct a new {@code SimulatorFactory}.
     * <p>
     * Automatically collects all {@link Simulator} implementations from the Spring container
     * and builds a map keyed by {@link Sport} for constant-time lookup. Each simulator's
     * sport type is determined by calling {@link Simulator#getSport()}.
     *
     * @param simulators auto-injected list of all {@link Simulator} implementations.
     */
    public SimulatorFactory(List<Simulator> simulators) {
        this.simulators = simulators
                .stream()
                .collect(
                        Collectors.toMap(
                                Simulator::getSport,
                                simulator -> simulator
                        )
                );
    }

    /**
     * Get the correct simulator based on a {@link Sport}.
     *
     * @param sport The {@link Sport} to retrieve a {@link Simulator} for.
     * @return The corresponding {@link Simulator} to a {@link Sport}.
     */
    public Simulator getSimulator(Sport sport) {
        Simulator simulator = simulators.get(sport);

        if (simulator == null) {
            throw new IllegalArgumentException("No simulator registered for sport: " + sport);
        }

        return simulator;
    }
}
