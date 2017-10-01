package com.ankushrayabhari.nebuloid.client.screens;

import com.ankushrayabhari.nebuloid.client.ClientGameEngine;
import com.ankushrayabhari.nebuloid.client.Window;
import com.badlogic.gdx.Screen;

/**
 * Created by ankushrayabhari on 8/6/17.
 */

public class GameScreen implements Screen {
    private Window window;
    private ClientGameEngine gameEngine;

    public GameScreen(Window window) {
        gameEngine = new ClientGameEngine();
        this.window = window;
    }

    @Override
    public void show() {
        gameEngine.init();
    }

    @Override
    public void render(float delta) {
        gameEngine.render(delta);
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
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
