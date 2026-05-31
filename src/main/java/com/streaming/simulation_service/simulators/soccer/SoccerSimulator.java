package com.streaming.simulation_service.simulators.soccer;

import com.streaming.simulation_service.model.enums.Sport;
import com.streaming.simulation_service.model.event.EventType;
import com.streaming.simulation_service.model.event.GameEvent;
import com.streaming.simulation_service.simulators.Simulator;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SoccerSimulator implements Simulator {


    @Override
    public GameEvent generateEvent() { // todo: should we pass the gameId in?
        String eventId = UUID.randomUUID().toString();
        String gameId = UUID.randomUUID().toString(); // need to store in a state session variable

        String eventType = getEventTypes().get((int) (Math.random() * getEventTypes().size())).name();
        Instant timestamp = Instant.now();

        // todo
        Map<String, Object> payload = generatePayload();

        // todo
        // need to grab match stat from state, update game.
        // Game's will be created in the engine, need to grab game bean here
        // randomly select a game from the current games, grab that id, set here.


        return new GameEvent(eventId, gameId, Sport.SOCCER, eventType, timestamp, payload);
    }

    /**
     * Returns a list of soccer related EventType's.
     *
     * @return a list of soccer related EventType's.
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

    @Override
    public Map<String, Object> generatePayload() {
        return Map.of(
                "playerName", "insert_player_name",
                "team", "team1",
                "homeTeam", "team1",
                "awayTeam", "team2");
    }
}
