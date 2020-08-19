package ru.cubos;

import ru.cubos.connectors.emulator.Emulator;
import ru.cubos.connectors.straightEmulator.StraightEmulator;
import ru.cubos.server.Server;

public class Main_Local {
    public static void main(String[] args) {
        StraightEmulator straightEmulator = new StraightEmulator(320, 640);
        Server server = new Server(straightEmulator);
        straightEmulator.setServer(server);
        server.setRepaintPending();
    }

}
