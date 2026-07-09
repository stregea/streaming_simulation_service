package com.streaming.simulation_service.simulators.soccer;

import com.streaming.simulation_service.model.enums.Sport;
import com.streaming.simulation_service.model.event.EventType;
import com.streaming.simulation_service.model.event.MatchEvent;
import com.streaming.simulation_service.model.state.SimulationMatchState;
import com.streaming.simulation_service.simulators.Simulator;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Soccer-specific implementation of the {@link Simulator} interface.
 * <p>
 * Generates soccer events (pass, shot, goal, miss, assist) for simulated games.
 * Each event is timestamped and includes a randomized payload with player
 * and team information.
 *
 * @see Simulator
 * @see MatchEvent
 * @see EventType
 */
@Component
public class SoccerSimulator implements Simulator {

    /**
     * Get the sport handled by this simulator.
     *
     * @return the {@link Sport#SOCCER} enum value
     */
    @Override
    public Sport getSport() {
        return Sport.SOCCER;
    }

    /**
     * Generate a soccer event for the given game state.
     * <p>
     * Creates a unique event with:
     * <ul>
     *   <li>A randomly selected soccer {@link EventType} (pass, shot, goal, miss, or assist).</li>
     *   <li>Current timestamp.</li>
     *   <li>Simulated payload with player, team, and game information.</li>
     * </ul>
     *
     * @param gameState the {@link SimulationMatchState} for which to generate an event.
     * @return a {@link MatchEvent} containing the generated soccer event data.
     */
    @Override
    public MatchEvent generateEvent(SimulationMatchState gameState) { // todo: should we pass the gameId in?
        UUID eventId = UUID.randomUUID();

        String eventType = getEventTypes().get((int) (Math.random() * getEventTypes().size())).name();
        Instant timestamp = Instant.now();

        // todo
        Map<String, Object> payload = generatePayload();

        // todo
        // need to grab match stat from state, update game.
        // Game's will be created in the engine, need to grab game bean here
        // randomly select a game from the current games, grab that id, set here.


        return new MatchEvent(eventId, gameState.getGame().id(), Sport.SOCCER, eventType, timestamp, payload);
    }

    /**
     * Returns a list of all possible events that can occur during a simulated soccer match.
     *
     * @return a {@link List} of valid {@link EventType}s for soccer (pass, shot, goal, miss, assist).
     */
    @Override
    public List<EventType> getEventTypes() {
        return List.of(
                EventType.PASS,
                EventType.SHOT,
                EventType.GOAL,
                EventType.MISS,
                EventType.ASSIST
        );
    }

    /**
     * Generate a simulated payload with soccer event details.
     * <p>
     * Creates a map containing player name, team information, and home/away team names
     * to be included in generated soccer events.
     *
     * @return a {@link Map} with placeholder player and team data.
     */
    @Override
    public Map<String, Object> generatePayload() {
        return Map.of(
                "playerName", "insert_player_name",
                "team", "team1",
                "homeTeam", "team1",
                "awayTeam", "team2");
    }
}
