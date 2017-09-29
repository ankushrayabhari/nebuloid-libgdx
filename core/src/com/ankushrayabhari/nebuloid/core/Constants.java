package com.ankushrayabhari.nebuloid.core;

/**
 * Created by ankushrayabhari on 8/6/17.
 */

public class Constants {
    public static final short CATEGORY_WORLD = 1;
    public static final short CATEGORY_ALLY = 2;
    public static final short CATEGORY_ALLY_PROJECTILE = 4;
    public static final short CATEGORY_ENEMY = 8;
    public static final short CATEGORY_ENEMY_PROJECTILE = 16;
    public static final short CATEGORY_MESSAGE = 32;
    public static final short CATEGORY_LOOTBAG = 64;

    public static final short MASK_WORLD = -1;
    public static final short MASK_ALLY = CATEGORY_WORLD | CATEGORY_ENEMY_PROJECTILE | CATEGORY_LOOTBAG;
    public static final short MASK_ALLY_PROJECTILE =  CATEGORY_WORLD | CATEGORY_ENEMY;
    public static final short MASK_ENEMY =  CATEGORY_WORLD | CATEGORY_ALLY_PROJECTILE;
    public static final short MASK_ENEMY_PROJECTILE = CATEGORY_WORLD | CATEGORY_ALLY;
    public static final short MASK_MESSAGE = 0;
    public static final short MASK_LOOTBAG = CATEGORY_ALLY;

    public static enum PhysicalEntityTypes {ALLY_PROJECTILE, ENEMY_PROJECTILE, ALLY, ENEMY, MESSAGE, LOOTBAG}

    public static final float TIME_STEP = 1/60f;
    public static final int VELOCITY_ITERATIONS = 8;
    public static final int POSITION_ITERATIONS = 3;

    public static final int TCP_PORT = 5000;
    public static final int UDP_PORT = 6000;
    public static final int CONNECTION_TIMEOUT = 5000;

    public static enum EntityCode {PLAYER}
}
