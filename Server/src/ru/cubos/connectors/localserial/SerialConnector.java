package ru.cubos.connectors.localserial;

import ru.cubos.connectors.Connectorable;
import ru.cubos.server.framebuffer.Display;

import static ru.cubos.commonHelpers.Protocol._0_MODE_OPTION;

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

    @Override
    public void switchToMode(byte mode) {
        byte message[] = new byte[]{
                _0_MODE_OPTION,                         // Switch mode
                mode,                      // Switch to COMMON MODE 1
        };

        OnDataGotFromServer(message);
    }

}
