package ru.cubos.connectors.straightEmulator;

import ru.cubos.connectors.Connector;
import ru.cubos.connectors.emulator.Emulator;
import ru.cubos.server.Server;

public class StraightEmulator extends Emulator implements Connector {
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
}
