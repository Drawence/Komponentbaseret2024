package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonbullet.Bullet;

public class BulletPlugin implements IGamePluginService {
    private Entity bullet;

    public void bulletPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

    }


    @Override
    public void stop(GameData gameData, World world) {
        for (Entity bullet : world.getEntities()){
            if (bullet.getClass() == Bullet.class){
                world.removeEntity(bullet);
            }
        }
    }
}
