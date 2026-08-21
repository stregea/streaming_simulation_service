package com.streaming.simulation_service.model.progress;

import com.streaming.simulation_service.model.enums.Sport;
import com.streaming.simulation_service.model.event.MatchEvent;

/**
 * Defines the contract for tracking the progression and state of a simulated match.
 *
 * <p>Implementations are responsible for managing time-based progression of matches,
 * advancing the match timeline as events are generated, and determining when a match
 * has expired or concluded. This interface enables sport-specific implementations to
 * define their own match duration rules and progression logic.</p>
 *
 * @see Sport
 * @see MatchEvent
 */
public interface MatchProgress {

    /**
     * Returns the {@link Sport} associated with this {@code MatchProgress} tracker.
     *
     * @return The {@link Sport} this progress instance is tracking
     */
    Sport getSport();

    /**
     * Advances the match progression timeline based on a newly generated {@link MatchEvent}.
     *
     * <p>This method updates the internal match state to reflect the passage of time and
     * the occurrence of the event. Implementations should update elapsed time, periods,
     * or any other time-based metrics according to the sport's rules.</p>
     *
     * @param matchEvent The {@link MatchEvent} containing the timestamp to advance the match to
     * @throws NullPointerException if {@code matchEvent} is null.
     */
    void advance(MatchEvent matchEvent);

    /**
     * Determines whether the match has expired or concluded.
     *
     * <p>Returns {@code true} when the match duration has ended according to the sport's
     * rules (e.g., full-time in soccer, final buzzer in basketball). When expired, no
     * further events should be generated for the match.</p>
     *
     * @return {@code true} if the match has expired, {@code false} if the match is still in progress.
     */
    boolean hasExpired();
}
