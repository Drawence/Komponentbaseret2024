package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * Interface representing a post-entity processing service.
 * Provides methods to perform post-processing on game entities within the game world.
 * This post-processing typically occurs after standard entity processing.
 *
 * @author jcs
 */
public interface IPostEntityProcessingService {

        /**
         * Post-processes game entities based on the provided game data and world state.
         * This might involve final adjustments, cleanup, or other tasks that need to occur
         * after the main entity processing phase.
         *
         * Pre-condition: Neither {@code gameData} nor {@code world} should be null.
         * Post-condition: Game entities within the provided world have undergone post-processing.
         *
         * @param gameData The game data object containing current game state information.
         * @param world The world object representing the game world containing entities to be post-processed.
         * @throws IllegalArgumentException if either gameData or world is null.
         */
        void process(GameData gameData, World world);
}
