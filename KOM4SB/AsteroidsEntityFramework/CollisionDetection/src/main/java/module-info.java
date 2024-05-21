import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
module CollisionDetection {
    exports dk.sdu.mmmi.cbse.collisiondetection;
    requires Common;
    requires CommonAsteroids;
    requires CommonEnemy;
    requires CommonBullet;
    requires CommonPlayer;
}