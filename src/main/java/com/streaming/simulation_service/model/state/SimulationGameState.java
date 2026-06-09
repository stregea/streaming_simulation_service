package com.streaming.simulation_service.model.state;

import com.streaming.simulation_service.model.enums.Sport;
import com.streaming.simulation_service.model.game.Team;
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
 * @see Sport
 */
public class SimulationGameState {

    /**
     * The {@link Game} this state belongs to.
     */
    private Game game;

    /**
     * The sport being simulated (e.g. SOCCER, BASKETBALL).
     */
    private Sport sport;

    /**
     * The team that is currently acting (performing the most recent action or holding
     * initiative). Note: this is intentionally different from "possession" — the
     * acting team may or may not have physical possession of the ball/puck.
     */
    private Team actingTeam;

    /**
     * True when the game has finished and no further play should be generated.
     */
    private boolean gameOver;

    /**
     * Remaining seconds in the current period/quarter/half, or null if not applicable.
     */
    private Integer remainingSeconds;

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
     * @param actingTeam         the team currently acting (may differ from the team with possession)
     * @param gameOver           whether the game has ended
     * @param remainingSeconds   remaining seconds in the current period (nullable)
     * @param lastEventTimestamp timestamp of the last generated event (nullable)
     */
    public SimulationGameState(Game game,
                               Team actingTeam,
                               boolean gameOver,
                               Integer remainingSeconds,
                               Instant lastEventTimestamp) {
        this.game = game;
        this.actingTeam = actingTeam;
        this.gameOver = gameOver;
        this.remainingSeconds = remainingSeconds;
        this.lastEventTimestamp = lastEventTimestamp;
    }

    // --- Getters / Setters ---

    /**
     * Returns the {@link Game} this state belongs to.
     *
     * @return game instance
     */
    public Game getGame() {
        return game;
    }

    /**
     * Set the {@link Game} for this simulated state.
     *
     * @param game non-null game instance
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Returns the team currently acting in the simulation.
     *
     * <p>Important: "acting" represents which team performed the most recent action
     * or currently has initiative in the simulation. It is not guaranteed to be the
     * same as the team with physical possession.</p>
     *
     * @return team that is acting, or null if unknown
     */
    public Team getActingTeam() {
        return actingTeam;
    }

    /**
     * Set the team that is currently acting for the generated event.
     *
     * @param actingTeam The team to set for the last generated event.
     */
    public void setActingTeam(Team actingTeam) {
        this.actingTeam = actingTeam;
    }

    /**
     * Returns whether the game is over.
     *
     * @return {@code true} if the game is completed, {@code false} otherwise.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Mark the game as completed or not.
     *
     * @param gameOver The boolean to set. Set {@code true} if the game is over, {@code false} if active.
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * Returns the remaining seconds in the current {@link Game}, or null when not applicable.
     *
     * @return the remaining seconds or null.
     */
    public Integer getRemainingSeconds() {
        return remainingSeconds;
    }

    /**
     * Update remaining seconds within the current {@link Game}.
     *
     * @param remainingSeconds The remaining seconds or null.
     */
    public void setRemainingSeconds(Integer remainingSeconds) {
        this.remainingSeconds = remainingSeconds;
    }

    /**
     * Returns the timestamp of the last generated event for this {@code SimulationGameState}.
     *
     * @return the instant of the last event or null.
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
