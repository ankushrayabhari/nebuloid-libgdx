package com.ankushrayabhari.nebuloid.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.scenes.scene2d.utils.FocusListener.FocusEvent.Type.scroll;

/**
 * Created by ankushrayabhari on 8/6/17.
 */

public class KeyboardController extends InputAdapter {

    private boolean moveUp, moveDown, moveLeft, moveRight, fire1, fire2, rotateLeft, rotateRight;
    private Vector2 mouseCoordinates;

    public KeyboardController() {
        mouseCoordinates = new Vector2(0,0);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.W:
                moveUp = true;
                break;
            case Keys.S:
                moveDown = true;
                break;
            case Keys.D:
                moveRight = true;
                break;
            case Keys.A:
                moveLeft = true;
                break;
            case Keys.Z:
                rotateLeft = true;
                break;
            case Keys.C:
                rotateRight = true;
                break;
            case Keys.SPACE:
                fire2 = true;
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.W:
                moveUp = false;
                break;
            case Keys.S:
                moveDown = false;
                break;
            case Keys.D:
                moveRight = false;
                break;
            case Keys.A:
                moveLeft = false;
                break;
            case Keys.Z:
                rotateLeft = false;
                break;
            case Keys.C:
                rotateRight = false;
                break;
            case Keys.SPACE:
                fire2 = false;
                break;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        switch (button) {
            case Input.Buttons.LEFT:
                fire1 = true;
                break;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        switch (button) {
            case Input.Buttons.LEFT:
                fire1 = false;
                break;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        mouseCoordinates.set(screenX, Gdx.graphics.getHeight()-screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseCoordinates.set(screenX, Gdx.graphics.getHeight()-screenY);
        return false;
    }

    public boolean isMoveUp() { return moveUp; }

    public boolean isMoveDown() { return moveDown; }

    public boolean isMoveRight() { return moveRight; }

    public boolean isMoveLeft() { return moveLeft; }

    public boolean isRotateLeft() { return rotateLeft; }

    public boolean isRotateRight() { return rotateRight; }

    public boolean isFire1() { return fire1; }

    public boolean isFire2() { return fire2; }

    public Vector2 getMouseCoordinates() { return mouseCoordinates; }
}
