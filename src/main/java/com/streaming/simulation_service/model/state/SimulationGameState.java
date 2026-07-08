package com.streaming.simulation_service.model.state;

import com.streaming.simulation_service.model.progress.MatchProgress;
import com.streaming.simulation_service.model.game.Score;
import com.streaming.simulation_service.model.team.Team;
import com.streaming.simulation_service.model.game.Game;

import java.time.Instant;

/**
 * Represents an in-memory, simulated snapshot of a game's runtime state used by the
 * simulation service while generating events.
 * This class is intended to be a lightweight, mutable holder of commonly accessed
 * simulation properties such as which team is currently acting (performing the most-recent action or holding initiative), whether the game has ended, and a timestamp
 * of the most recent simulated event. It is not intended
 * to be a persistence entity.
 *
 * @see Game
 * @see Team
 * @see MatchProgress
 * @see Score
 */
public class SimulationGameState {

    /**
     * The {@link Game} this state belongs to.
     */
    private Game game;

    /**
     * The team that is currently acting (performing the most recent action or holding
     * initiative). Note: this is intentionally different from "possession" — the
     * acting team may or may not have physical possession of the ball/puck.
     */
    private Team actingTeam;


    /**
     * The {@link MatchProgress} that tracks the remaining time for the game.
     */
    private MatchProgress matchProgress;

    /**
     * The {@link Score} that tracks the score for the game.
     */
    private Score score;

    /**
     * Timestamp of the last generated event for this game state.
     */
    private Instant lastEventTimestamp;

    /**
     * Create an empty {@code SimulatedGameState} instance. Fields should be populated
     * by the caller before use in a session.
     */
    public SimulationGameState() {
    }

    /**
     * Create a fully-populated {@code SimulationGameState}.
     *
     * @param game               the {@link Game} this state belongs to
     * @param actingTeam         the {@link Team} currently acting (may differ from the team with possession).
     * @param matchProgress      the {@link MatchProgress} tracking the remaining time for the game.
     * @param lastEventTimestamp timestamp of the last generated event (nullable).
     */
    public SimulationGameState(Game game,
                               Team actingTeam,
                               MatchProgress matchProgress,
                               Score score,
                               Instant lastEventTimestamp) {
        this.game = game;
        this.actingTeam = actingTeam;
        this.matchProgress = matchProgress;
        this.score = score;
        this.lastEventTimestamp = lastEventTimestamp;
    }

    /**
     * Returns the {@link Game} this state belongs to.
     *
     * @return {@link Game} instance
     */
    public Game getGame() {
        return game;
    }

    /**
     * Set the {@link Game} for this simulated state.
     *
     * @param game the {@link Game} to set.
     */
    public void setGame(Game game) {
        this.game = game;
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
     * Returns whether the game is over.
     *
     * @return {@code true} if the game is completed, {@code false} otherwise.
     */
    public boolean isGameOver() {
        return matchProgress.hasExpired();
    }

    /**
     * Advance the {@code SimulatedGameState}'s match clock by 'n' seconds.
     *
     * @param seconds the time to advance the clock by.
     */
    public void advanceClock(Integer seconds) {
        this.matchProgress.advance(seconds);
    }

    /**
     * Get the {@link Score} score object to
     * @return the {@link Score} object associated with the current game/match.
     */
    public Score getScore() {
        return score;
    }

    /**
     * Returns the timestamp of the last generated event for this {@code SimulationGameState}.
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
