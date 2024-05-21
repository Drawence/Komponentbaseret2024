package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;

public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;

    public EnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

        enemy = asteroid(gameData);
        world.addEntity(enemy);
    }

    private Entity asteroid(GameData gameData) {

        float slow = 10;
        float speed = 200;
        float maxSpeed = 350;
        float rota = 5;
        float x = (float) ((Math.random() * (gameData.getDisplayWidth() - 0)) + 0);
        float y = (float) ((Math.random() * (gameData.getDisplayHeight() - 0)) + 0);
        float radians = 3.14f / 2;

        Entity enemyShip = new Enemy();
        enemyShip.add(new MovingPart(slow, speed, maxSpeed, rota));
        enemyShip.add(new PositionPart(x, y, radians));
        enemyShip.add(new LifePart(1,0));


        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }
}
