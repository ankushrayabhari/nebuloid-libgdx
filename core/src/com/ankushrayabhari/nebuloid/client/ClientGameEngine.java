package com.ankushrayabhari.nebuloid.client;

import com.ankushrayabhari.nebuloid.core.Constants;
import com.ankushrayabhari.nebuloid.core.network.NetworkManager;
import com.ankushrayabhari.nebuloid.core.network.InputPacket;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import java.io.IOException;
/**
 * Created by ankushrayabhari on 8/6/17.
 */

public class ClientGameEngine {
    private Client client;

    public ClientGameEngine() {
        client = new Client();
        client.start();
        NetworkManager.registerClasses(client.getKryo());

        try {
            client.connect(Constants.CONNECTION_TIMEOUT, "localhost", Constants.TCP_PORT, Constants.UDP_PORT);
        } catch (IOException err) {
            err.printStackTrace();
        }

        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {
            }
        });

        InputPacket temp = new InputPacket();
        temp.moveUp = true;
        temp.moveLeft = true;


        while (true) {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }

            client.sendTCP(temp);
        }
    }
}
