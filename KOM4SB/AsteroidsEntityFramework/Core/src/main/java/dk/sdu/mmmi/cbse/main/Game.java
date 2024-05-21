package dk.sdu.mmmi.cbse.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.bulletsystem.BulletPlugin;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.managers.GameInputProcessor;

import dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
import dk.sdu.mmmi.cbse.playersystem.PlayerPlugin;
import dk.sdu.mmmi.cbse.enemysystem.EnemyControlSystem;
import dk.sdu.mmmi.cbse.enemysystem.EnemyPlugin;
import dk.sdu.mmmi.cbse.bulletsystem.BulletControlSystem;
import dk.sdu.mmmi.cbse.asteroidssystem.AsteroidsControlSystem;
import dk.sdu.mmmi.cbse.asteroidssystem.AsteroidsPlugin;
import dk.sdu.mmmi.cbse.collisiondetection.CollisionDetection;

import java.util.ArrayList;
import java.util.List;

public class Game implements ApplicationListener {

    private static OrthographicCamera camera;
    private ShapeRenderer shapeRender;

    private final GameData gameData = new GameData();
    private List<IEntityProcessingService> entityProcessors = new ArrayList<>();
    private List<IGamePluginService> entityPlugins = new ArrayList<>();
    private List<IPostEntityProcessingService> postEntityProcessors = new ArrayList<>();

    private World world = new World();

    @Override
    public void create() {

        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        camera = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        camera.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        camera.update();

        shapeRender = new ShapeRenderer();

        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );

        IGamePluginService playerPlugin = new PlayerPlugin();
        IEntityProcessingService playerProcess = new PlayerControlSystem();
        entityPlugins.add(playerPlugin);
        entityProcessors.add(playerProcess);

        IGamePluginService enemyPlugin = new EnemyPlugin();
        IEntityProcessingService enemyProcess = new EnemyControlSystem();
        entityPlugins.add(enemyPlugin);
        entityProcessors.add(enemyProcess);

        IGamePluginService bulletPlugin = new BulletPlugin();
        IEntityProcessingService bulletProcess = new BulletControlSystem();
        entityPlugins.add(bulletPlugin);
        entityProcessors.add(bulletProcess);

        IGamePluginService asteroidsPlugin = new AsteroidsPlugin();
        IEntityProcessingService asteroidsProcess = new AsteroidsControlSystem();
        entityPlugins.add(asteroidsPlugin);
        entityProcessors.add(asteroidsProcess);

        IPostEntityProcessingService collisionDetection = new CollisionDetection();
        postEntityProcessors.add(collisionDetection);


        for (IGamePluginService iGamePlugin : entityPlugins) {
            iGamePlugin.start(gameData, world);
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        update();
        draw();

        gameData.getKeys().update();
    }

    private void update() {
        for (IEntityProcessingService entityProcessorService : entityProcessors) {
            entityProcessorService.process(gameData, world);
        }
        for (IPostEntityProcessingService postProcessor : postEntityProcessors) {
            postProcessor.process(gameData, world);
        }
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {

            shapeRender.setColor(1, 1, 1, 1);

            shapeRender.begin(ShapeRenderer.ShapeType.Line);

            float[] xShape = entity.getShapeX();
            float[] yShape = entity.getShapeY();

            for (int i = 0, j = xShape.length - 1;
                    i < xShape.length;
                    j = i++) {

                shapeRender.line(xShape[i], yShape[i], xShape[j], yShape[j]);
            }

            shapeRender.end();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
