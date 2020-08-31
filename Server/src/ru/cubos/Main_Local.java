package ru.cubos;

import ru.cubos.connectors.straightEmulator.LocalEmulator;
import ru.cubos.server.Server;

public class Main_Local {
    public static void main(String[] args) {
        LocalEmulator straightEmulator = new LocalEmulator(280, 160);
        Server server = new Server(straightEmulator);
        straightEmulator.setServer(server);
        straightEmulator.start();
        //server.setRepaintPending();
    }

}
