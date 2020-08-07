package ru.cubos.connectors.socketEmulatorClient;

import ru.cubos.connectors.Connector;
import ru.cubos.connectors.emulator.Emulator;
import ru.cubos.server.Server;

public class SocketEmulatorClient extends Emulator implements Connector {
    public SocketEmulatorClient(int width, int height) {
        super(width, height);
    }

    @Override
    public boolean sendToServer(byte[] data) {
        return false;
    }


}
