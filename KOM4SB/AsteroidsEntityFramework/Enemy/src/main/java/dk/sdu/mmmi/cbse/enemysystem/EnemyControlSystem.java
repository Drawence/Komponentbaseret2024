package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.bulletsystem.BulletControlSystem;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;

import java.util.Random;

public class EnemyControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {

        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart pos = enemy.getPart(PositionPart.class);
            MovingPart mov = enemy.getPart(MovingPart.class);
            LifePart life = enemy.getPart(LifePart.class);


            Random rand = new Random();
            int random = rand.nextInt(2);

            if (random == 0){
                mov.setUp(true);
                mov.setRight(false);
                mov.setLeft(false);
                BulletControlSystem bullet = new BulletControlSystem();
                world.addEntity(bullet.createBullet(enemy,gameData));
            } else if (random == 1) {
                mov.setUp(false);
                mov.setRight(true);
                mov.setLeft(false);
            } else if (random == 2) {
                mov.setUp(false);
                mov.setRight(false);
                mov.setLeft(true);
            }

            mov.process(gameData, enemy);
            pos.process(gameData, enemy);
            life.process(gameData, enemy);

            if(life.isIsHit() && life.getLife() <= 1){
                world.removeEntity(enemy);
            } else {
                life.setLife(life.getLife() - 1);
                life.setIsHit(false);
            }

            updateShape(enemy);
        }
    }

    private void updateShape(Entity entity) {
        float[] x1 = entity.getShapeX();
        float[] y1 = entity.getShapeY();

        PositionPart pos = entity.getPart(PositionPart.class);
        float x = pos.getX();
        float y = pos.getY();
        float radians = pos.getRadians();

        x1[0] = (float) (x + Math.cos(radians) * 9);
        y1[0] = (float) (y + Math.sin(radians) * 9);

        x1[1] = (float) (x + Math.cos(radians - 4 * 3.14f / 5) * 9);
        y1[1] = (float) (y + Math.sin(radians - 4 * 3.14f / 5) * 9);

        x1[2] = (float) (x + Math.cos(radians + 3.14f) * 9);
        y1[2] = (float) (y + Math.sin(radians + 3.14f) * 9);

        x1[3] = (float) (x + Math.cos(radians + 4 * 3.14f / 5) * 9);
        y1[3] = (float) (y + Math.sin(radians + 4 * 3.14f / 5) * 9);

        entity.setShapeX(x1);
        entity.setShapeY(y1);
    }
}

