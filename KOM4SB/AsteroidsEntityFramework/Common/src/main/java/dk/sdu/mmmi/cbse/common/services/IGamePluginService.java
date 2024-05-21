package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * Interface representing a game plugin service.
 * Provides methods to manage the lifecycle of game plugins,
 * specifically their starting and stopping behaviors.
 */
public interface IGamePluginService {

    /**
     * Starts the game plugin. This method is called when a game plugin is activated.
     *
     * Pre-condition: Neither {@code gameData} nor {@code world} should be null.
     * Post-condition: The game plugin is in an active state.
     *
     * @param gameData The game data object containing current game state information.
     * @param world The world object representing the game world.
     * @throws IllegalArgumentException if either gameData or world is null.
     */
    void start(GameData gameData, World world);

    /**
     * Stops the game plugin. This method is called when a game plugin is deactivated.
     *
     * Pre-condition: Neither {@code gameData} nor {@code world} should be null.
     * Post-condition: The game plugin is in an inactive state.
     *
     * @param gameData The game data object containing current game state information.
     * @param world The world object representing the game world.
     * @throws IllegalArgumentException if either gameData or world is null.
     */
    void stop(GameData gameData, World world);
}
