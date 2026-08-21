package com.streaming.simulation_service.factory.matchprogress.soccer;

import com.streaming.simulation_service.factory.matchprogress.MatchProgressFactory;
import com.streaming.simulation_service.model.enums.Sport;
import com.streaming.simulation_service.model.progress.MatchProgress;
import com.streaming.simulation_service.model.progress.soccer.SoccerMatchProgress;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Soccer-specific implementation of {@link MatchProgressFactory}.
 *
 * <p>This factory creates the initial {@link MatchProgress} state used for simulated
 * soccer matches. The created progress object records the start timestamp and serves
 * as the state holder for time-based match progression.</p>
 *
 * @see MatchProgressFactory
 * @see MatchProgress
 * @see SoccerMatchProgress
 */
@Component
public class SoccerMatchProgressFactory implements MatchProgressFactory {

    /**
     * Returns the sport handled by this factory.
     *
     * @return The {@link Sport#SOCCER} sport identifier.
     */
    @Override
    public Sport getSport() {
        return Sport.SOCCER;
    }

    /**
     * Creates a fresh soccer match progress object for a new match.
     * <p>
     * The returned {@link SoccerMatchProgress} is initialized with the current time so
     * that the match timeline begins when the simulation is started.
     *
     * @return A newly initialized {@link MatchProgress} for a soccer match.
     */
    @Override
    public MatchProgress createMatchProgress() {
        return new SoccerMatchProgress(Instant.now());
    }
}
