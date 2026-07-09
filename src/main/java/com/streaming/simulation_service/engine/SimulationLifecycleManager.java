package com.streaming.simulation_service.engine;

import com.streaming.simulation_service.model.enums.Sport;
import com.streaming.simulation_service.model.match.Match;
import com.streaming.simulation_service.model.progress.MatchProgress;
import com.streaming.simulation_service.model.match.Score;
import com.streaming.simulation_service.model.team.Team;
import com.streaming.simulation_service.model.state.SimulationGameState;
import com.streaming.simulation_service.registry.ActiveGamesRegistry;
import com.streaming.simulation_service.registry.TeamFactoryRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Manages the lifecycle of all simulated games within the {@link ActiveGamesRegistry}.
 * <p>
 * Responsibilities include:
 * <ul>
 *   <li>Bootstrapping initial games on startup</li>
 *   <li>Providing random active games for event generation</li>
 *   <li>Replacing completed or expired games with new ones</li>
 * </ul>
 *
 * @see ActiveGamesRegistry
 * @see SimulationGameState
 */
@Component
public class SimulationLifecycleManager {
    // todo - Determine if there needs to be a function/scheduler that needs to constantly check the
    //  state of every game to make sure they've completed or not. (So it's not entirely depended on the
    //  SimulationScheduler tick that calls the orchestrator.

    /**
     * Registry that contains all active {@link SimulationGameState}'s within the simulation.
     */
    private final ActiveGamesRegistry registry;

    private final TeamFactoryRegistry teamFactoryRegistry;

    /**
     * Construct a new {@code SimulationLifecycleManager}.
     *
     * @param registry the {@link ActiveGamesRegistry} to manage active games
     */
    public SimulationLifecycleManager(ActiveGamesRegistry registry, TeamFactoryRegistry teamFactoryRegistry) {
        this.registry = registry;
        this.teamFactoryRegistry = teamFactoryRegistry;
    }

    /**
     * Bootstrap initial games into the registry on application startup.
     * <p>
     * Invoked automatically by Spring via {@code @PostConstruct}. Creates 5 randomly
     * generated games and adds them to the {@link ActiveGamesRegistry} for simulation.
     * <p>
     * TODO: Extract max game count to application configuration.
     *
     * @see #createRandomGame()
     * @see ActiveGamesRegistry#addGame(SimulationGameState)
     */
    @PostConstruct
    private void bootStrapGames() {
        // todo: add max games to a config.
        for (int i = 0; i < 5; i++) {
            SimulationGameState gameState = createRandomGame();
            registry.addGame(gameState);
        }
    }

    /**
     * Replace a game within the {@link ActiveGamesRegistry}.
     *
     * @param gameState The {@link SimulationGameState} to replace.
     */
    public void replaceGame(SimulationGameState gameState) {
        if (gameState != null) {
            // Remove previous game simulation from the registry.
            registry.removeGame(gameState);

            // Create a new game.
            SimulationGameState newGameState = createRandomGame();

            // Add the new game to the registry.
            registry.addGame(newGameState);
        }
    }

    /**
     * TODO - IN PROGRESS
     * Generate a randomly created {@link SimulationGameState} with random sport and teams.
     *
     * @return a randomly created {@link SimulationGameState} object, or {@code null} if generation fails
     * @see SimulationGameState
     */
    private SimulationGameState createRandomGame() {
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

        return new SimulationGameState(match, teamA, matchProgress, score, null);
    }

}
