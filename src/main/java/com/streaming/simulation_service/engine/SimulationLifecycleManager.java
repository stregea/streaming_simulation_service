package com.streaming.simulation_service.engine;

import com.streaming.simulation_service.model.enums.Sport;
import com.streaming.simulation_service.model.match.Match;
import com.streaming.simulation_service.model.progress.MatchProgress;
import com.streaming.simulation_service.model.match.Score;
import com.streaming.simulation_service.model.team.Team;
import com.streaming.simulation_service.model.state.SimulationMatchState;
import com.streaming.simulation_service.registry.ActiveMatchesRegistry;
import com.streaming.simulation_service.registry.TeamFactoryRegistry;
import jakarta.annotation.PostConstruct;
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
    // todo - Determine if there needs to be a function/scheduler that needs to constantly check the
    //  state of every match to make sure they've completed or not. (So it's not entirely depended on the
    //  SimulationScheduler tick that calls the orchestrator.

    /**
     * Registry that contains all active {@link SimulationMatchState}'s within the simulation.
     */
    private final ActiveMatchesRegistry registry;

    private final TeamFactoryRegistry teamFactoryRegistry;

    /**
     * Construct a new {@code SimulationLifecycleManager}.
     *
     * @param registry the {@link ActiveMatchesRegistry} to manage active matches
     */
    public SimulationLifecycleManager(ActiveMatchesRegistry registry, TeamFactoryRegistry teamFactoryRegistry) {
        this.registry = registry;
        this.teamFactoryRegistry = teamFactoryRegistry;
    }

    /**
     * Bootstrap initial matches into the registry on application startup.
     * <p>
     * Invoked automatically by Spring via {@code @PostConstruct}. Creates 5 randomly
     * generated matches and adds them to the {@link ActiveMatchesRegistry} for simulation.
     * <p>
     * TODO: Extract max match count to application configuration.
     *
     * @see #createRandomMatch()
     * @see ActiveMatchesRegistry#addMatch(SimulationMatchState)
     */
    @PostConstruct
    private void bootStrapMatches() {
        // todo: add max matches to a config.
        for (int i = 0; i < 5; i++) {
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
        }
    }

    /**
     * TODO - IN PROGRESS
     * Generate a randomly created {@link SimulationMatchState} with random sport and teams.
     *
     * @return a randomly created {@link SimulationMatchState} object, or {@code null} if generation fails
     * @see SimulationMatchState
     */
    private SimulationMatchState createRandomMatch() {
//        List<Sport> sports = List.of(Sport.values());
        List<Sport> sports = List.of(Sport.SOCCER); // todo: uncomment top line once more sports are complete.

        Sport selectedSport = sports.get(ThreadLocalRandom.current().nextInt(sports.size()));

        Team teamA = teamFactoryRegistry.getTeamFactory(selectedSport).createTeam();
        Team teamB = teamFactoryRegistry.getTeamFactory(selectedSport).createTeam();

        Match match = new Match(UUID.randomUUID(), selectedSport, teamA, teamB);

        //todo:
        // - Set time (MatchProgress) based on sport.
        // this will be a placeholder for now, but will need to be set based on the sport. (e.g. Soccer = 90 minutes, Basketball = 48 minutes, etc.)
        // This will be done via a "MatchProgress" parent class, then each sport will have it's own class, eg: SoccerMatchProgress, BasketballMatchProgress, etc.
        // that will extend the parent class and set the time accordingly. This will then be passed to the state.
        MatchProgress matchProgress = new MatchProgress(60);

        Score score = new Score(List.of(teamA, teamB));

        return new SimulationMatchState(match, teamA, matchProgress, score, null);
    }

}
