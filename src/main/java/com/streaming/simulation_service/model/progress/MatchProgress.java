package com.streaming.simulation_service.model.progress;

public class MatchProgress {

    /**
     * The total remaining time left on the clock.
     */
    private Integer remainingSeconds;

    /**
     * Construct a new {@code MatchProgress} object.
     *
     * @param remainingSeconds the total time remaining on the clock.
     */
    public MatchProgress(Integer remainingSeconds) {
        this.remainingSeconds = remainingSeconds;
    }

    /**
     * Get the total remaining seconds left in the match clock.
     * @return the total remaining seconds.
     */
    public Integer getRemainingSeconds() {
        return remainingSeconds;
    }

    /**
     * Set the total remaining seconds left in the match clock.
     * @param remainingSeconds the remaining seconds to set the match clock to.
     */
    public void setRemainingSeconds(Integer remainingSeconds) {
        this.remainingSeconds = remainingSeconds;
    }

    /**
     * Advance the clock forward by 'n' seconds.
     *
     * @param seconds the total time to move the clock forward by.
     */
    public void advance(Integer seconds) {
        // Adjust the match clock to move forward, never let the time go below 0.
        setRemainingSeconds(Math.max(remainingSeconds - seconds, 0));
    }

    /**
     * Determine if the {@code MatchProgress} has expired or not.
     * @return {@code true} if the {@code MatchProgress} has expired, {@code false} otherwise.
     */
    public boolean hasExpired() {
        return remainingSeconds == 0;
    }
}
