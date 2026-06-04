package com.streaming.simulation_service.engine;

import com.streaming.simulation_service.model.enums.Sport;
import com.streaming.simulation_service.simulators.Simulator;
import com.streaming.simulation_service.simulators.soccer.SoccerSimulator;
import org.springframework.stereotype.Component;

import java.util.Map;

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

    public SimulatorFactory(SoccerSimulator soccerSimulator) {
        this.simulators = Map.of(
                Sport.SOCCER, soccerSimulator
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
            throw new IllegalArgumentException("No simulator for sport: " + sport);
        }

        return simulator;
    }
}
