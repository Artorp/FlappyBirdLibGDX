package no.artorp.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import no.artorp.game.FlappyBirdGame;
import no.artorp.game.sprites.Bird;
import no.artorp.game.sprites.Tube;

public class PlayState extends State {
	private static final int TUBE_SPACING = 175;
	private static final int TUBE_COUNT = 4;
	private static final int TUBE_INIT_DISTANCE = 200;
	private static final int GROUND_Y_OFFSET = -50;

	private Bird bird;
	private Texture bg;
	private Texture ground;
	private Vector2 groundPos1, groundPos2;

	private Array<Tube> tubes;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		bird = new Bird(50, 300);
		cam.setToOrtho(false,
				(float) FlappyBirdGame.WIDTH / 2,
				(float) FlappyBirdGame.HEIGHT / 2);
		bg = new Texture("bg.png");
		ground = new Texture("ground.png");
		groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
		groundPos2 = new Vector2(cam.position.x - cam.viewportWidth / 2 + ground.getWidth(), GROUND_Y_OFFSET);

		tubes = new Array<>(TUBE_COUNT);
		for (int i = 0; i < TUBE_COUNT; i++) {
			tubes.add(new Tube(TUBE_INIT_DISTANCE + i * TUBE_SPACING));
		}
	}

	@Override
	public void handleInput() {
		updateGround();
		if (Gdx.input.justTouched()) {
			bird.jump();
		}
	}

	@Override
	public void update(float dt) {
		this.handleInput();
		bird.update(dt);
		cam.position.x = bird.getPosition().x + 80;

		for (Tube tube : tubes) {
			float tubeFarRightEdge = tube.getPosTopTube().x + tube.getTopTube().getWidth();
			float camFarLeftEdge = cam.position.x - (cam.viewportWidth / 2);
			if (tubeFarRightEdge < camFarLeftEdge) {
				tube.reposition((int)(tube.getPosTopTube().x + TUBE_SPACING * TUBE_COUNT));
			}
		}

		if (bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
			gameover();
		}

		cam.update();
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
		sb.draw(bird.getTexture(),
				bird.getPosition().x,
				bird.getPosition().y,
				bird.getTexture().getRegionWidth() / 2f,
				bird.getTexture().getRegionHeight() / 2f - 5f,
				bird.getTexture().getRegionWidth(),
				bird.getTexture().getRegionHeight(),
				1, 1,
				bird.getRotationInDegrees());
		for (Tube tube : tubes) {
			sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
			sb.draw(tube.getBottomTube(), tube.getPosBottomTube().x, tube.getPosBottomTube().y);

			if (tube.collides(bird.getBounds())) {
				gameover();
				break;
			}
		}
		sb.draw(ground, groundPos1.x, groundPos2.y);
		sb.draw(ground, groundPos2.x, groundPos2.y);
		sb.end();
	}

	@Override
	public void dispose() {
		bg.dispose();
		bird.dispose();
		ground.dispose();
		for (Tube tube : tubes) {
			tube.dispose();
		}
	}

	private void updateGround() {
		float camLeftEdge = cam.position.x - (cam.viewportWidth / 2);
		float ground1RightEdge = groundPos1.x + ground.getWidth();
		float ground2RightEdge = groundPos2.x + ground.getWidth();
		if (ground1RightEdge < camLeftEdge) {
			groundPos1.add(ground.getWidth() * 2, 0);
		}
		if (ground2RightEdge < camLeftEdge) {
			groundPos2.add(ground.getWidth() * 2, 0);
		}
	}

	private void gameover() {
		gsm.set(new PlayState(gsm));
	}
}
