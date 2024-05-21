package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.LEFT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.RIGHT;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.UP;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonplayer.Player;

/**
 *
 * @author jcs
 */
public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            LifePart lifePart = player.getPart(LifePart.class);

            movingPart.setLeft(gameData.getKeys().isDown(LEFT));
            movingPart.setRight(gameData.getKeys().isDown(RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(UP));
            
            
            movingPart.process(gameData, player);
            positionPart.process(gameData, player);

            if(lifePart.isIsHit() && lifePart.getLife() <= 1){
                world.removeEntity(player);
            } else {
                lifePart.setLife(lifePart.getLife() - 1);
                lifePart.setIsHit(false);
            }

            updateShape(player);
        }
    }

    private void updateShape(Entity entity) {
        float[] x1 = entity.getShapeX();
        float[] y1 = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        x1[0] = (float) (x + Math.cos(radians) * 10);
        y1[0] = (float) (y + Math.sin(radians) * 10);

        x1[1] = (float) (x + Math.cos(radians - 4 * 3.14f / 5) * 10);
        y1[1] = (float) (y + Math.sin(radians - 4 * 3.14f / 5) * 10);

        x1[2] = (float) (x + Math.cos(radians + 3.14f) * 6);
        y1[2] = (float) (y + Math.sin(radians + 3.14f) * 6);

        x1[3] = (float) (x + Math.cos(radians + 4 * 3.14f / 5) * 10);
        y1[3] = (float) (y + Math.sin(radians + 4 * 3.14f / 5) * 10);

        entity.setShapeX(x1);
        entity.setShapeY(y1);
    }

}
