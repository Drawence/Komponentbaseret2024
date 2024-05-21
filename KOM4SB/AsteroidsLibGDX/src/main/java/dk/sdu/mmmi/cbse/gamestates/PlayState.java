package dk.sdu.mmmi.cbse.gamestates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.entities.Bullet;
import dk.sdu.mmmi.cbse.entities.Enemy;
import dk.sdu.mmmi.cbse.entities.Player;
import dk.sdu.mmmi.cbse.managers.GameKeys;
import dk.sdu.mmmi.cbse.managers.GameStateManager;

import java.util.ArrayList;
import java.util.Random;

public class PlayState extends GameState {
	
	private ShapeRenderer sr;
	
	private Player player;

	private Enemy enemy;

	private ArrayList<Bullet> bullets;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		sr = new ShapeRenderer();
		
		player = new Player();

		bullets = new ArrayList<>();

		enemy = new Enemy(bullets);

	}
	
	public void update(float dt) {

		handleInput();
		enemyInput();

		// Update player
		player.update(dt);

		// update enemy
		enemy.update(dt);

		// Update enemy bullets
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).update(dt);
			if(bullets.get(i).shouldRemoveBullet()){
				bullets.remove(i);
				i--;
			}
		}
		
	}
	
	public void draw() {
		// draw player
		player.draw(sr);

		// draw enemy
		enemy.draw(sr);

		//draw enemy bullets
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(sr);
		}
	}
	
	public void handleInput() {
		player.setLeft(GameKeys.isDown(GameKeys.LEFT));
		player.setRight(GameKeys.isDown(GameKeys.RIGHT));
		player.setUp(GameKeys.isDown(GameKeys.UP));
	}

	public void enemyInput() {
		Random rand = new Random();
		int random = rand.nextInt(5);

		if (random == 0) {
			enemy.setUp(true);
			enemy.setRight(false);
			enemy.setLeft(false);
		} else if (random == 1) {
			enemy.setUp(false);
			enemy.setRight(true);
			enemy.setLeft(false);
		} else if (random == 2) {
			enemy.setUp(false);
			enemy.setRight(false);
			enemy.setLeft(true);
		} else if (random == 4) {
			enemy.shoot();
		}
	}
	
	public void dispose() {}
	
}









