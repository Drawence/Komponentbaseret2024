package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonbullet.BulletSPI;
import dk.sdu.mmmi.cbse.commonbullet.Bullet;


public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity bullet : world.getEntities(Bullet.class)) {
            PositionPart positionPart = bullet.getPart(PositionPart.class);
            MovingPart movingPart = bullet.getPart(MovingPart.class);
            LifePart lifePart = bullet.getPart(LifePart.class);
            lifePart.reduceExpiration(gameData.getDelta());

            movingPart.setUp(true);

            if (lifePart.getExpiration() <= 0 ) {
                world.removeEntity(bullet);
            }

            movingPart.process(gameData, bullet);
            positionPart.process(gameData, bullet);
            lifePart.process(gameData, bullet);

            updateShape(bullet);
        }
    }
    public Entity createBullet(Entity shooter, GameData gameData) {
        PositionPart playerPos = shooter.getPart(PositionPart.class);

        float slow = 10;
        float speed = 500;
        float maxSpeed = 500;
        float rota = 5;
        float x = playerPos.getX();
        float y = playerPos.getY();
        float radians = playerPos.getRadians();

        Entity bullet = new Bullet();
        bullet.add(new MovingPart(slow, speed, maxSpeed, rota));
        bullet.add(new PositionPart(x, y, radians));
        bullet.add(new LifePart(1,1));


        return bullet;
    }

    private void updateShape(Entity entity) {
        float[] x1 = entity.getShapeX();
        float[] y1 = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        x1[0] = (float) (x + Math.cos(radians) * 3);
        y1[0] = (float) (y + Math.sin(radians) * 3);

        x1[1] = (float) (x + Math.cos(radians - 4 * 3.14f / 5) * 3);
        y1[1] = (float) (y + Math.sin(radians - 4 * 3.14f / 5) * 3);

        x1[2] = (float) (x + Math.cos(radians + 3.14f) * 3);
        y1[2] = (float) (y + Math.sin(radians + 3.14f) * 3);

        x1[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 3);
        y1[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 3);

        entity.setShapeX(x1);
        entity.setShapeY(y1);
    }
}
