package dk.sdu.mmmi.cbse.collisiondetection;

import dk.sdu.mmmi.cbse.commonasteroids.Asteroids;
import dk.sdu.mmmi.cbse.commonbullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;
import dk.sdu.mmmi.cbse.commonplayer.Player;

import java.util.List;

    public class Collision implements IPostEntityProcessingService {

        @Override
        public void process(GameData gameData, World world) {
            for (Entity entity : world.getEntities()) {
                LifePart entityLife = entity.getPart(LifePart.class);
                if (entityLife.getExpiration() <= 0) {
                    world.removeEntity(entity);
                    continue;
                }

                for (Entity otherEntity : world.getEntities()) {
                    if (entity.equals(otherEntity)) {
                        continue;
                    }

                    LifePart otherLife = otherEntity.getPart(LifePart.class);
                    if (otherLife.getExpiration() <= 0) {
                        world.removeEntity(otherEntity);
                        continue;
                    }

                    if (collides(entity, otherEntity)) {
                        handleCollision(world, entity, entityLife);
                    }
                }
            }
        }

        private void handleCollision(World world, Entity entity, LifePart entityLife) {
            if (entityLife.getLife() > 0) {
                entityLife.setLife(entityLife.getLife() - 1);
                if (entityLife.getLife() <= 0) {
                    world.removeEntity(entity);
                }
            }
        }

        public boolean collides(Entity entity1, Entity entity2) {
            PositionPart pos1 = entity1.getPart(PositionPart.class);
            PositionPart pos2 = entity2.getPart(PositionPart.class);
            float dx = pos1.getX() - pos2.getX();
            float dy = pos1.getY() - pos2.getY();
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            return distance < (entity1.getRadius() + entity2.getRadius());
        }
    }
