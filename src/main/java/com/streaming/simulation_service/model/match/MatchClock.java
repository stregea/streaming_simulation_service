package com.streaming.simulation_service.model.match;

/**
 * Class that will represent a match clock for a sport.
 */
public class MatchClock {

    /**
     * Variable that will keep track of the remaining seconds within a match.
     */
    private Integer remainingSeconds;

    /**
     * Construct a {@code MatchClock} with the given remaining seconds.
     *
     * @param remainingSeconds The amount of time to set the {@code MatchClock} to.
     */
    public MatchClock(Integer remainingSeconds) {
        this.remainingSeconds = remainingSeconds;
    }

    /**
     * Get the total remaining seconds left from the {@code MatchClock}
     *
     * @return The {@code MatchClock}'s remaining seconds.
     */
    public Integer getRemainingSeconds() {
        return remainingSeconds;
    }

    /**
     * Advance the {@code MatchClock} forward by 'n' seconds. Can never be below 0.
     *
     * @param seconds The total amount of seconds to advance the {@code MatchClock} by.
     */
    public void advance(Integer seconds) {
        remainingSeconds = Math.max(remainingSeconds - seconds, 0);
    }

    /**
     * Reset the {@code MatchClock} to a specific duration. Can never be below 0.
     *
     * @param duration The duration to set the remaining time for the {@code MatchClock}.
     */
    public void reset(Integer duration) {
        remainingSeconds = Math.max(duration, 0);
    }

    /**
     * Determine if the {@code MatchClock} has expired.
     *
     * @return {@code true} if the {@code MatchClock} has hit 0 seconds remaining, {@code false} otherwise.
     */
    public Boolean isExpired() {
        return remainingSeconds == 0;
    }
}
