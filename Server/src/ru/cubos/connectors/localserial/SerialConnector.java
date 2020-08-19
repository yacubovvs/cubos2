package ru.cubos.connectors.localserial;

import ru.cubos.connectors.Connectorable;
import ru.cubos.server.framebuffer.Display;

public class SerialConnector implements Connectorable {
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

    @Override
    public boolean updateScreen(Display display) {
        return false;
    }

}
