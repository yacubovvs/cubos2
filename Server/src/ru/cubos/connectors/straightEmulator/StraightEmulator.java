package ru.cubos.connectors.straightEmulator;

import ru.cubos.connectors.Connector;
import ru.cubos.connectors.emulator.Emulator;
import ru.cubos.server.Server;

public class StraightEmulator extends Emulator implements Connector {
    public StraightEmulator(int width, int height) {
        super(width, height);
    }

    public void start(Server server) {
        setServer(server);
    }
}
