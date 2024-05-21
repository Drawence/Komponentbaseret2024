package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * Interface representing an entity processing service.
 * Provides methods to process game entities within the game world.
 */
public interface IEntityProcessingService {

    /**
     * Processes game entities based on the provided game data and world state.
     *
     * Pre-condition: Neither {@code gameData} nor {@code world} should be null.
     * Post-condition: Game entities within the provided world are processed.
     *
     * @param gameData The game data object containing current game state information.
     * @param world The world object representing the game world containing entities to be processed.
     * @throws IllegalArgumentException if either gameData or world is null.
     */
    void process(GameData gameData, World world);
}
