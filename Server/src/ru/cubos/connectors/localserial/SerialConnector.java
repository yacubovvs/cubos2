package ru.cubos.connectors.localserial;

import ru.cubos.connectors.Connector;
import ru.cubos.server.Server;

public class SerialConnector implements Connector {
    @Override
    public boolean OnDataGotFromServer(byte[] data) {
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
