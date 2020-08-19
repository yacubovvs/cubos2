package ru.cubos.connectors.straightEmulator;

import ru.cubos.connectors.Connectorable;
import ru.cubos.connectors.emulator.Emulator;
import ru.cubos.server.Server;
import ru.cubos.server.framebuffer.Display;

public class StraightEmulator extends Emulator implements Connectorable {
    public StraightEmulator(int width, int height) {
        super(width, height);
    }

    private Server server;

    @Override
    public boolean sendToServer(byte[] data) {
        getServer().transmitData(data);
        return true;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public boolean updateScreen(Display display) {
        return false;
    }
}
