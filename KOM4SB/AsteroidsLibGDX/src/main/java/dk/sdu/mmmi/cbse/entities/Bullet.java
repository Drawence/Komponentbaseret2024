package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Bullet extends SpaceObject{
    private float lifetime;
    private float lifetimer;

    private boolean remove;

    public Bullet(float x, float y, float radians) {
        this.x = x;
        this.y = y;
        this.radians = radians;

        float speed = 350;
        dx = MathUtils.cos(radians) * speed;
        dy = MathUtils.sin(radians) * speed;

        width = 2;
        height = 2;

        lifetimer = 0;
        lifetime = 2;

    }

    public boolean shouldRemoveBullet(){
        return remove;
    }

    public void update(float dt){
        x += dx * dt;
        y += dy * dt;

        wrap();

        lifetimer += dt;
        if (lifetimer > lifetime){
            remove = true;
        }
    }

    public void  draw(ShapeRenderer sr){
        sr.setColor(1, 1, 1, 1);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.circle(this.x - this.width / 2, this.y - this.height / 2, this.width / 2);
        sr.end();
    }
}
