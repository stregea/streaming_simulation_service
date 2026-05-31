package com.streaming.simulation_service.engine;

import com.streaming.simulation_service.model.event.GameEvent;
import com.streaming.simulation_service.simulators.Simulator;
import com.streaming.simulation_service.simulators.soccer.SoccerSimulator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SimulationEngine {
    // todo
    private final List<Simulator> simulators = List.of(
            new SoccerSimulator() // Add the soccer simulator
    );

    public void simulate() {
        // select a random simulator and generate an event.
        Simulator simulator = simulators.get((int) (Math.random() * simulators.size()));

        GameEvent event = simulator.generateEvent();

        // publish event to kafka
        System.out.println("Generated event: " + event);
    }
    // contains a list of simulators
    // randomly selects a simulator to generate and return an event
    // publishes events to kafka
}
