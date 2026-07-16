package com.streaming.simulation_service.simulators.soccer;

import com.streaming.simulation_service.model.enums.Sport;
import com.streaming.simulation_service.model.enums.EventType;
import com.streaming.simulation_service.model.event.MatchEvent;
import com.streaming.simulation_service.model.state.SimulationMatchState;
import com.streaming.simulation_service.simulators.Simulator;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Soccer-specific implementation of the {@link Simulator} interface.
 * <p>
 * Generates soccer events (pass, shot, goal, miss, assist) for simulated matches.
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
     * Generate a soccer event for the given match state.
     * <p>
     * Creates a unique event with:
     * <ul>
     *   <li>A randomly selected soccer {@link EventType} (pass, shot, goal, miss, or assist).</li>
     *   <li>Current timestamp.</li>
     *   <li>Simulated payload with player, team, and match information.</li>
     * </ul>
     *
     * @param matchState the {@link SimulationMatchState} for which to generate an event.
     * @return a {@link MatchEvent} containing the generated soccer event data.
     */
    @Override
    public MatchEvent generateEvent(SimulationMatchState matchState) { // todo: should we pass the matchId in?
        UUID eventId = UUID.randomUUID();

        EventType eventType = getEventTypes().get(ThreadLocalRandom.current().nextInt(getEventTypes().size()));
        Instant timestamp = Instant.now();

        // todo
        Map<String, Object> payload = generatePayload();

        // todo
        // need to grab match stat from state, update match.
        // Matches will be created in the engine, need to grab match bean here
        // randomly select a match from the current matches, grab that id, set here.


        return new MatchEvent(eventId, matchState.getMatch().id(), Sport.SOCCER, eventType, timestamp, payload);
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
