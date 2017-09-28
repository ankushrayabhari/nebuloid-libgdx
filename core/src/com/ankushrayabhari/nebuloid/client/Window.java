package com.ankushrayabhari.nebuloid.client;

import com.ankushrayabhari.nebuloid.client.screens.GameScreen;
import com.badlogic.gdx.Game;

public class Window extends Game {
    public GameScreen gameScreen;

    public Window() {
        gameScreen = new GameScreen(this);
    }

    @Override
    public void create () {
        setScreen(gameScreen);
    }
}
