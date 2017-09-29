package com.ankushrayabhari.nebuloid.client;

import com.ankushrayabhari.nebuloid.core.Constants;
import com.ankushrayabhari.nebuloid.core.network.packets.InputPacket;
import com.ankushrayabhari.nebuloid.core.network.NetworkManager;
import com.ankushrayabhari.nebuloid.core.network.packets.NewEntityPacket;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;

/**
 * Created by ankushrayabhari on 9/28/17.
 */

public class ClientNetworkManager extends NetworkManager {
    private static final ClientNetworkManager instance = new ClientNetworkManager();

    public static ClientNetworkManager getInstance() {
        return instance;
    }

    private Client client;

    public ClientNetworkManager() {
        Log.set(Log.LEVEL_NONE);
        client = new Client();
        client.start();
        ClientNetworkManager.registerClasses(client.getKryo());
    }

    public void connect() {
        try {
            client.connect(Constants.CONNECTION_TIMEOUT, "localhost", Constants.TCP_PORT, Constants.UDP_PORT);
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    public void addListener(Listener listener) {
        client.addListener(listener);
    }

    public void requestSpawnEntity(Constants.EntityCode code) {
        NewEntityPacket newPlayerPacket = new NewEntityPacket();
        newPlayerPacket.entityCode = code.ordinal();
        newPlayerPacket.uuid = "";
        client.sendTCP(newPlayerPacket);
    }

    public InputPacket sendInputPacket(KeyboardController inputController, Camera camera) {
        InputPacket inputPacket = new InputPacket();
        inputPacket.fire1 = inputController.isFire1();
        inputPacket.fire2 = inputController.isFire2();
        inputPacket.moveUp = inputController.isMoveUp();
        inputPacket.moveDown = inputController.isMoveDown();
        inputPacket.moveRight = inputController.isMoveRight();
        inputPacket.moveLeft = inputController.isMoveLeft();
        Vector3 worldPos = camera.unproject(new Vector3(inputController.getMouseCoordinates(), 0));
        inputPacket.mouseCoordinatesX = worldPos.x;
        inputPacket.mouseCoordinatesY = worldPos.y;
        client.sendTCP(inputPacket);
        return inputPacket;
    }
}
