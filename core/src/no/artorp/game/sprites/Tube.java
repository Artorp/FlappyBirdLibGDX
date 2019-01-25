package no.artorp.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Tube {
	private static final int FLUCTUATION = 130;
	private static final int TUBE_GAP = 100;
	private static final int LOWEST_OPENING = 120;
	private Texture topTube, bottomTube;
	private Vector2 posTopTube, posBottomTube;
	private Rectangle boundsTop, boundsBot;
	private Random rand;

	public Tube(float x) {
		// TODO: use static textures, instead of loading new texture for each sprite
		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		rand = new Random();
		posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
		posBottomTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

		boundsTop = new Rectangle(posTopTube.x, posTopTube.y,
				topTube.getWidth(), topTube.getHeight());
		boundsBot = new Rectangle(posBottomTube.x, posBottomTube.y,
				bottomTube.getWidth(), bottomTube.getHeight());
	}

	public Texture getTopTube() {
		return topTube;
	}

	public Texture getBottomTube() {
		return bottomTube;
	}

	public Vector2 getPosTopTube() {
		return posTopTube;
	}

	public Vector2 getPosBottomTube() {
		return posBottomTube;
	}

	public void reposition(int x) {
		posTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
		posBottomTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
		boundsTop.setPosition(posTopTube.x, posTopTube.y);
		boundsBot.setPosition(posBottomTube.x, posBottomTube.y);
	}

	public boolean collides(Rectangle player) {
		return player.overlaps(boundsTop) || player.overlaps(boundsBot);
	}

	public void dispose() {
		topTube.dispose();
		bottomTube.dispose();
	}
}
