package com.streaming.simulation_service.factory.team;

import com.streaming.simulation_service.model.enums.Sport;
import com.streaming.simulation_service.model.enums.position.Position;
import com.streaming.simulation_service.model.team.Player;
import com.streaming.simulation_service.model.team.Team;

/**
 * Factory responsible for constructing {@link Team} instances for simulated matches.
 *
 * <p>The {@code TeamFactory} delegates player creation to the sport specific factories and
 * assembles a {@link Team} with a generated id, name and a list of players. Centralizing
 * team creation makes it straightforward to evolve naming conventions, player composition,
 * or sport-specific roster rules without cluttering lifecycle code.</p>
 *
 * @see Team
 */
public interface TeamFactory {

    /**
     * Return the sport that this factory produces teams for.
     *
     * <p>Implementations of {@code TeamFactory} are expected to be sport-specific;
     * for example a soccer team factory would return {@link Sport#SOCCER} from this
     * method. This allows callers to discover the appropriate factory for a
     * requested sport when multiple implementations are registered as beans.</p>
     *
     * @return The {@link Sport} handled by this factory.
     */
    Sport getSport();

    /**
     * Create a {@link Player} populated with a unique ID, name, and position.
     *
     * <p>Implementations should return a fully-initialized, non-null {@link Player}.</p>
     *
     * @return A newly constructed {@link Player}; must not be {@code null}.
     */
    Player createPlayer(Position position);

    /**
     * Create a {@link Team} populated with players and a generated display name.
     *
     * <p>Implementations should return a fully-initialized, non-null {@link Team}.
     * The exact roster size and naming conventions are sport-specific.</p>
     *
     * @return A newly constructed {@link Team}; must not be {@code null}.
     */
    Team createTeam();
}
