package dk.sdu.mmmi.cbse.asteroidssystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonasteroids.Asteroids;

import java.util.Random;

public class AsteroidsPlugin implements IGamePluginService {
    private Entity asteroids;

    public AsteroidsPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        Random rando = new Random();
        int random = rando.nextInt(4);

        asteroids = createAsteroid(gameData,25);
        world.addEntity(asteroids);

        for (int i = 0; i < random; i++) {
            asteroids = createAsteroid(gameData,25);
            world.addEntity(asteroids);
        }

    }

    private Entity createAsteroid(GameData gameData, int radius) {

        float slow = 10;
        float speed = 200;
        float maxSpeed = 300;
        float rotate = 5;
        float x = (float) ((Math.random() * (gameData.getDisplayWidth() - 0)) + 0);
        float y = (float) ((Math.random() * (gameData.getDisplayHeight() - 0)) + 0);
        float radians = 3.1415f / 2;

        Entity asteroid = new Asteroids();
        asteroid.add(new MovingPart(slow, speed, maxSpeed, rotate));
        asteroid.add(new PositionPart(x, y, radians));
        asteroid.add(new LifePart(3,0));

        asteroid.setRadius(25);
        return asteroid;
    }


    public void createMiniAsteroid(Entity asteroidHit, GameData gameData, World world){
        PositionPart newPos = asteroidHit.getPart(PositionPart.class);
        LifePart newLife = asteroidHit.getPart(LifePart.class);

        float radians = newPos.getRadians();
        int radius = 0;
        float speed = 5;
        int life = newLife.getLife() - 1;
        if (life == 1) {
            radius = 12;
            speed = (float) Math.random() * 20f + 60f;
        } else if (life <= 0) {
            world.removeEntity(asteroidHit);
        }

        Entity asteroid1 = createAsteroid(gameData,radius);

        Entity asteroid2 = createAsteroid(gameData,radius);

        world.removeEntity(asteroidHit);
        world.addEntity(asteroid1);
        world.addEntity(asteroid2);

    }



    @Override
    public void stop(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroids.class)) {
            world.removeEntity(asteroid);
        }
    }
}
