package no.artorp.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State {
	protected OrthographicCamera cam;
	protected GameStateManager gsm;

	protected State(GameStateManager gsm) {
		this.gsm = gsm;
		this.cam = new OrthographicCamera();
	}

	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render(SpriteBatch sb);
	public abstract void dispose();
}
