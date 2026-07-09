package com.streaming.simulation_service.model.state;

import com.streaming.simulation_service.model.match.Match;
import com.streaming.simulation_service.model.progress.MatchProgress;
import com.streaming.simulation_service.model.match.Score;
import com.streaming.simulation_service.model.team.Team;

import java.time.Instant;

/**
 * Represents an in-memory, simulated snapshot of a match's runtime state used by the
 * simulation service while generating events.
 * This class is intended to be a lightweight, mutable holder of commonly accessed
 * simulation properties such as which team is currently acting (performing the most-recent action or holding initiative), whether the match has ended, and a timestamp
 * of the most recent simulated event. It is not intended
 * to be a persistence entity.
 *
 * @see Match
 * @see Team
 * @see MatchProgress
 * @see Score
 */
public class SimulationMatchState {

    /**
     * The {@link Match} this state belongs to.
     */
    private Match match;

    /**
     * The team that is currently acting (performing the most recent action or holding
     * initiative). Note: this is intentionally different from "possession" — the
     * acting team may or may not have physical possession of the ball/puck.
     */
    private Team actingTeam;


    /**
     * The {@link MatchProgress} that tracks the remaining time for the match.
     */
    private MatchProgress matchProgress;

    /**
     * The {@link Score} that tracks the score for the match.
     */
    private Score score;

    /**
     * Timestamp of the last generated event for this match state.
     */
    private Instant lastEventTimestamp;

    /**
     * Create an empty {@code SimulationMatchState} instance. Fields should be populated
     * by the caller before use in a session.
     */
    public SimulationMatchState() {
    }

    /**
     * Create a fully-populated {@code SimulationMatchState}.
     *
     * @param match               the {@link Match} this state belongs to
     * @param actingTeam         the {@link Team} currently acting (may differ from the team with possession).
     * @param matchProgress      the {@link MatchProgress} tracking the remaining time for the match.
     * @param lastEventTimestamp timestamp of the last generated event (nullable).
     */
    public SimulationMatchState(Match match,
                                Team actingTeam,
                                MatchProgress matchProgress,
                                Score score,
                                Instant lastEventTimestamp) {
        this.match = match;
        this.actingTeam = actingTeam;
        this.matchProgress = matchProgress;
        this.score = score;
        this.lastEventTimestamp = lastEventTimestamp;
    }

    /**
     * Returns the {@link Match} this state belongs to.
     *
     * @return the {@link Match} object
     */
    public Match getMatch() {
        return match;
    }

    /**
     * Set the {@link Match} for this state.
     *
     * @param match the {@link Match} to set.
     */
    public void setMatch(Match match) {
        this.match = match;
    }

    /**
     * Returns the {@link Team} currently acting in the simulation.
     *
     * <p>Important: "acting" represents which team performed the most recent action
     * or currently has initiative in the simulation. It is not guaranteed to be the
     * same as the team with physical possession.</p>
     *
     * @return {@link Team} that is acting, or null if unknown
     */
    public Team getActingTeam() {
        return actingTeam;
    }

    /**
     * Set the {@link Team} that is currently acting for the generated event.
     *
     * @param actingTeam The {@link Team} to set for the last generated event.
     */
    public void setActingTeam(Team actingTeam) {
        this.actingTeam = actingTeam;
    }


    /**
     * Returns the current {@link MatchProgress} of this state.
     *
     * @return {@link MatchProgress} instance.
     */
    public MatchProgress getMatchProgress() {
        return matchProgress;
    }

    /**
     * Set the {@link MatchProgress} of this state.
     *
     * @param matchProgress the {@link MatchProgress} to set.
     */
    public void setMatchProgress(MatchProgress matchProgress) {
        this.matchProgress = matchProgress;
    }

    /**
     * Returns whether the match is over.
     *
     * @return {@code true} if the match is completed, {@code false} otherwise.
     */
    public boolean isMatchOver() {
        return matchProgress.hasExpired();
    }

    /**
     * Advance the {@code SimulationMatchState}'s match clock by 'n' seconds.
     *
     * @param seconds the time to advance the clock by.
     */
    public void advanceClock(Integer seconds) {
        this.matchProgress.advance(seconds);
    }

    /**
     * Get the {@link Score} score object.
     *
     * @return the {@link Score} object associated with the current match.
     */
    public Score getScore() {
        return score;
    }

    /**
     * Returns the timestamp of the last generated event for this {@code SimulationMatchState}.
     *
     * @return the {@link Instant} of the last event or null.
     */
    public Instant getLastEventTimestamp() {
        return lastEventTimestamp;
    }

    /**
     * Update the timestamp of the last generated event.
     *
     * @param lastEventTimestamp The timestamp to set.
     */
    public void setLastEventTimestamp(Instant lastEventTimestamp) {
        this.lastEventTimestamp = lastEventTimestamp;
    }
}
