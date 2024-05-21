package dk.sdu.mmmi.cbse.asteroidssystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonasteroids.Asteroids;

import java.util.Random;

    public class AsteroidsControlSystem implements IEntityProcessingService {

        @Override
        public void process(GameData gameData, World world) {

            List<Entity> asteroids = world.getEntities(Asteroids.class);
            Random random = new Random();
            AsteroidsPlugin asteroidPlugin = new AsteroidsPlugin();

            for (Entity asteroid : asteroids) {
                PositionPart position = asteroid.getPart(PositionPart.class);
                MovingPart movement = asteroid.getPart(MovingPart.class);
                LifePart life = asteroid.getPart(LifePart.class);

                int direction = random.nextInt(20);
                updateMovement(direction, movement);

                movement.process(gameData, asteroid);
                position.process(gameData, asteroid);
                life.process(gameData, asteroid);

                if (life.isIsHit()) {
                    asteroidPlugin.createMiniAsteroid(asteroid, gameData, world);
                }
                updateShape(asteroid);
            }
        }

        private void updateMovement(int direction, MovingPart movement) {
            switch (direction) {
                case 0:
                    movement.setUp(true);
                    movement.setRight(false);
                    movement.setLeft(false);
                    break;
                case 1:
                    movement.setUp(false);
                    movement.setRight(true);
                    movement.setLeft(false);
                    break;
                case 2:
                    movement.setUp(false);
                    movement.setRight(false);
                    movement.setLeft(true);
                    break;
                default:
                    movement.setUp(false);
                    movement.setRight(false);
                    movement.setLeft(false);
                    break;
            }
        }


    public void updateShape(Entity entity) {
        float[] x0 = entity.getShapeX();
        float[] y0 = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        float radius = entity.getRadius();

        x0[0] = (float) (x + Math.cos(radians) * radius);
        y0[0] = (float) (y + Math.sin(radians) * radius);

        x0[1] = (float) (x + Math.cos(radians - 3.14f / 5) * radius);
        y0[1] = (float) (y + Math.sin(radians - 3.14f / 5) * radius);

        x0[2] = (float) (x + Math.cos(radians + 3.14f) * radius);
        y0[2] = (float) (y + Math.sin(radians + 3.14f) * radius);

        x0[3] = (float) (x + Math.cos(radians + 4 * 3.14f / 5) * radius);
        y0[3] = (float) (y + Math.sin(radians + 4 * 3.14f / 5) * radius);

        entity.setShapeX(x0);
        entity.setShapeY(y0);


    }

}
