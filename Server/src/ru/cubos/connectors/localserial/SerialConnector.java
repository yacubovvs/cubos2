package ru.cubos.connectors.localserial;

import ru.cubos.connectors.Connector;

public class SerialConnector implements Connector {
    @Override
    public boolean transmitData(byte[] data) {
        return false;
    }

    @Override
    public int getScreenWidth() {
        return 0;
    }

    @Override
    public int getScreenHeight() {
        return 0;
    }
}
