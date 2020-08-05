package ru.cubos.connectors.websocket;

import ru.cubos.connectors.Connector;

public class WebSocketConnector implements Connector {
    @Override
    public boolean transmitData(byte[] data) {
        return false;
    }

    @Override
    public int getScreenWidth() {
        return 320;
    }

    @Override
    public int getScreenHeight() {
        return 240;
    }
}
