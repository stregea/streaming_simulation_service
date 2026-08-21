package com.streaming.simulation_service.model.progress.soccer;

import com.streaming.simulation_service.model.enums.Sport;
import com.streaming.simulation_service.model.event.MatchEvent;
import com.streaming.simulation_service.model.match.MatchClock;
import com.streaming.simulation_service.model.progress.MatchProgress;

import java.time.Duration;
import java.time.Instant;

/**
 * Soccer implementation for the {@link MatchProgress} interface.
 *
 * <p>This class is responsible for tracking the progress of a soccer match, including the match clock and the time elapsed between events.</p>
 */
public class SoccerMatchProgress implements MatchProgress {

    /**
     * The {@link MatchClock} for a Soccer match. Set to 90 seconds/minutes.
     */
    private final MatchClock matchClock = new MatchClock(90); // 90 minutes in seconds

    /**
     * Instant object that keeps track of the previous event time to help with calculating time in between events.
     */
    private Instant previousEventTime;

    /**
     * Construct a {@link SoccerMatchProgress} object.
     *
     * @param matchStartTime The time at which this match started.
     */
    public SoccerMatchProgress(Instant matchStartTime) {
        this.previousEventTime = matchStartTime;
    }

    /**
     * Get the Sport Associated with the {@link SoccerMatchProgress} instance.
     *
     * @return The {@link Sport#SOCCER} enum value
     */
    @Override
    public Sport getSport() {
        return Sport.SOCCER;
    }

    /**
     * Advances the match progression timeline based on a newly generated {@link MatchEvent}.
     *
     * @param matchEvent The {@link MatchEvent} containing the timestamp to advance the match to
     * @throws NullPointerException if {@code matchEvent} is null.
     */
    @Override
    public void advance(MatchEvent matchEvent) {
        Integer elapsedSeconds = determineElapsedSeconds(matchEvent);
        matchClock.advance(elapsedSeconds);
    }

    /**
     * Determine if the soccer match has ended.
     *
     * @return {@code true} if the Soccer {@link MatchClock} has expired/ended, {@code false} otherwise.
     */
    @Override
    public boolean hasExpired() {
        return matchClock.isExpired();
    }

    /**
     * Determine the time elapsed in between each event.
     *
     * @param matchEvent The match event containing the timestamp for the advancement.
     * @return The {@link Integer} difference of time spent between the current and previous match events.
     */
    private Integer determineElapsedSeconds(MatchEvent matchEvent) {
        Instant currentTimestamp = matchEvent.timestamp();

        // Calculate the total elapsed seconds that have occurred between the event timestamps.
        Long elapsedSeconds = Duration.between(previousEventTime, matchEvent.timestamp()).toSeconds();

        if (elapsedSeconds < 0) {
            throw new IllegalArgumentException("Match event timestamp cannot be before the previous event timestamp.");
        }

        previousEventTime = currentTimestamp;
        System.out.println("DEBUGGING: " + matchEvent.matchId());
        System.out.println("DEBUGGING: elapsedSeconds: " + elapsedSeconds);

        return Math.toIntExact(elapsedSeconds);
    }
}
