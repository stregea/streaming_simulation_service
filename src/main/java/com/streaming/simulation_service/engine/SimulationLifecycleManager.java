package com.streaming.simulation_service.engine;

import com.streaming.simulation_service.factory.team.TeamFactory;
import com.streaming.simulation_service.model.enums.Sport;
import com.streaming.simulation_service.model.match.Match;
import com.streaming.simulation_service.model.match.Score;
import com.streaming.simulation_service.model.progress.MatchProgress;
import com.streaming.simulation_service.model.team.Team;
import com.streaming.simulation_service.model.state.SimulationMatchState;
import com.streaming.simulation_service.registry.ActiveMatchesRegistry;
import com.streaming.simulation_service.registry.MatchProgressRegistry;
import com.streaming.simulation_service.registry.TeamFactoryRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Manages the lifecycle of all simulated matches within the {@link ActiveMatchesRegistry}.
 * <p>
 * Responsibilities include:
 * <ul>
 *   <li>Bootstrapping initial matches on startup</li>
 *   <li>Providing random active matches for event generation</li>
 *   <li>Replacing completed or expired matches with new ones</li>
 * </ul>
 *
 * @see ActiveMatchesRegistry
 * @see SimulationMatchState
 */
@Component
public class SimulationLifecycleManager {

    /**
     * Registry that contains all active {@link SimulationMatchState}'s within the simulation.
     */
    private final ActiveMatchesRegistry registry;

    /**
     * Registry that contains all {@link TeamFactory} implementations.
     */
    private final TeamFactoryRegistry teamFactoryRegistry;

    /**
     * Registry that contains all {@link TeamFactory} implementations.
     */
    private final MatchProgressRegistry matchProgressRegistry;

    /**
     * Max total of allowed matches to run at once.
     */
    @Value("${simulation.maximum-matches}")
    private Integer MAX_MATCHES;

    /**
     * Construct a new {@code SimulationLifecycleManager}.
     *
     * @param registry the {@link ActiveMatchesRegistry} to manage active matches
     */
    public SimulationLifecycleManager(ActiveMatchesRegistry registry, TeamFactoryRegistry teamFactoryRegistry, MatchProgressRegistry matchProgressRegistry) {
        this.registry = registry;
        this.teamFactoryRegistry = teamFactoryRegistry;
        this.matchProgressRegistry = matchProgressRegistry;
    }

    /**
     * Bootstrap initial matches into the registry on application startup.
     * <p>
     * Invoked automatically by Spring via {@code @PostConstruct}. Creates 5 randomly
     * generated matches and adds them to the {@link ActiveMatchesRegistry} for simulation.
     * <p>
     *
     * @see #createRandomMatch()
     * @see ActiveMatchesRegistry#addMatch(SimulationMatchState)
     */
    @PostConstruct
    private void bootStrapMatches() {
        for (int i = 0; i < MAX_MATCHES; i++) {
            SimulationMatchState matchState = createRandomMatch();
            registry.addMatch(matchState);
        }
    }

    /**
     * Replace a match within the {@link ActiveMatchesRegistry}.
     *
     * @param matchState The {@link SimulationMatchState} to replace.
     */
    public void replaceMatch(SimulationMatchState matchState) {
        if (matchState != null) {
            // Remove previous match simulation from the registry.
            registry.removeMatch(matchState);

            // Create a new match.
            SimulationMatchState newMatchState = createRandomMatch();

            // Add the new match to the registry.
            registry.addMatch(newMatchState);

            System.out.println("DEBUGGING: Replacing match with state " + newMatchState.getMatch().id());
        }
    }

    /**
     * Generate a randomly created {@link SimulationMatchState} with random sport and teams.
     *
     * @return a randomly created {@link SimulationMatchState} object, or {@code null} if generation fails
     * @see SimulationMatchState
     */
    private SimulationMatchState createRandomMatch() {
//        List<Sport> sports = List.of(Sport.values());
        List<Sport> sports = List.of(Sport.SOCCER); // todo: uncomment top line once more sports are complete.

        Sport selectedSport = sports.get(ThreadLocalRandom.current().nextInt(sports.size()));

        // Construct the teams.
        Team teamA = teamFactoryRegistry.getTeamFactory(selectedSport).createTeam();
        Team teamB = teamFactoryRegistry.getTeamFactory(selectedSport).createTeam();

        // Construct the match.
        Match match = new Match(UUID.randomUUID(), selectedSport, teamA, teamB);

        // Construct the MatchProgress object based on the sport.
        MatchProgress matchProgress = matchProgressRegistry.getMatchProgressFactory(selectedSport).createMatchProgress();

        // Create a new score (0-0).
        Score score = new Score(List.of(teamA, teamB));

        return new SimulationMatchState(match, teamA, matchProgress, score, null);
    }

}
