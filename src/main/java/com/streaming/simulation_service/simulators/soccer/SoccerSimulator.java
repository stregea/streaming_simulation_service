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
    public MatchEvent generateEvent(SimulationMatchState matchState) {
        UUID eventId = UUID.randomUUID();

        EventType eventType = getEventTypes().get(ThreadLocalRandom.current().nextInt(getEventTypes().size()));

        // set the acting team based on rng.
        Integer actingTeam = ThreadLocalRandom.current().nextInt();

        if (actingTeam % 2 == 0) { // even numbers set to the home team.
            matchState.setActingTeam(matchState.getMatch().homeTeam());
        } else {
            matchState.setActingTeam(matchState.getMatch().awayTeam());
        }

        // todo: based on shooting/pass/assist, use rng to determine if goal or not
        //  Also -- match service should be the service maintaining the score, Match Service will have the state there.
        //  will need to refactor.
        switch (eventType) {
            case GOAL, ASSIST -> {
                System.out.println("GOAL!");

                // Increment active team score by 1.
                matchState.getScore().addPoints(matchState.getActingTeam(), 1);

                System.out.println("Score is now " + displayScore(matchState));
            }
            default -> System.out.println(eventType.name());
        }

        Instant timestamp = Instant.now();

        Map<String, Object> payload = generatePayload(matchState);

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
    public Map<String, Object> generatePayload(SimulationMatchState matchState) {
        return Map.of(
                "team", matchState.getActingTeam().name(),
                "homeTeam", matchState.getMatch().homeTeam().name(),
                "awayTeam", matchState.getMatch().awayTeam().name());
    }

    private String displayScore(SimulationMatchState matchState) {
        return matchState.getScore().getScore(matchState.getMatch().homeTeam()) + " - " +
                matchState.getScore().getScore(matchState.getMatch().awayTeam());
    }
}
