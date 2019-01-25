package no.artorp.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Bird {
	private static final float GRAVITY = -900;
	private static final float MOVEMENT = 100;

	private Vector3 position;
	private Vector3 velocity;
	private Texture texture;
	private Animation textureAnimation;
	private Rectangle bounds;
	private Sound flap;

	public Bird(float x, float y) {
		position = new Vector3(x, y, 0);
		velocity = new Vector3(0, 0, 0);
		texture = new Texture("birdanimation.png");
		textureAnimation = new Animation(texture, 3, 0.5f);
		bounds = new Rectangle(x, y, texture.getWidth() / 3f, texture.getHeight());
		flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
	}

	public void update(float dt) {
		textureAnimation.update(dt);
		if (position.y > 0) {
			velocity.add(0, GRAVITY * dt, 0);
			position.add(MOVEMENT * dt, velocity.y * dt, 0);
		} else {
			position.y = 0;
			velocity.y = 0;
		}
		bounds.setPosition(position.x, position.y);
	}

	public Vector3 getPosition() {
		return position;
	}

	public float getRotationInDegrees() {
		double adjacentEdgeLength = 200;
		double oppositeEdgeLength = velocity.y;
		double radians = Math.atan(oppositeEdgeLength / adjacentEdgeLength);
		return (float) Math.toDegrees(radians);
	}

	public TextureRegion getTexture() {
		return textureAnimation.getFrame();

	}

	public void jump() {
		velocity.y = 300;
		flap.play(0.5f);
	}

	public Rectangle getBounds() {
		return this.bounds;
	}

	public void dispose() {
		texture.dispose();
		flap.stop();
		flap.dispose();
	}
}
