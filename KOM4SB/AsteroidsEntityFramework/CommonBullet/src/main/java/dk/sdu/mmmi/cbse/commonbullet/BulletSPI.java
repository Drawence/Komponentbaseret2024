package dk.sdu.mmmi.cbse.commonbullet;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Entity;

public interface BulletSPI {
    Entity createBullet(Entity shooter, GameData gameData);
}
