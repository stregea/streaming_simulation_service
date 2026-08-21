package com.streaming.simulation_service.factory.matchprogress;

import com.streaming.simulation_service.model.enums.Sport;
import com.streaming.simulation_service.model.progress.MatchProgress;

/**
 * Factory responsible for constructing {@link MatchProgress} instances for simulated matches.
 *
 * <p>The {@code MatchProgressFactory} delegates match progress creation to the sport specific factories.</p>
 *
 * @see MatchProgress
 */
public interface MatchProgressFactory {

    /**
     * Return the sport that this factory produces teams for.
     *
     * <p>Implementations of {@code MatchProgressFactory} are expected to be sport-specific;
     * for example a soccer team factory would return {@link Sport#SOCCER} from this
     * method. This allows callers to discover the appropriate factory for a
     * requested sport when multiple implementations are registered as beans.</p>
     *
     * @return The {@link Sport} handled by this factory.
     */
    Sport getSport();

    /**
     * Construct a new {@link MatchProgress} instance for a simulated match.
     * <p>Implementations of {@code createMatchProgress} are expected to return sport-specific {@link MatchProgress}
     * objects.</p>
     *
     * @return A newly constructed {@link MatchProgress} instance for a simulated match.
     */
    MatchProgress createMatchProgress();
}
