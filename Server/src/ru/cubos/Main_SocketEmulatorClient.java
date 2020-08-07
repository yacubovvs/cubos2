package ru.cubos;

import ru.cubos.connectors.emulator.Emulator;
import ru.cubos.connectors.socketEmulatorClient.SocketEmulatorClient;
import ru.cubos.server.Server;

public class Main_SocketEmulatorClient {
    public static void main(String[] args) {
        SocketEmulatorClient socketEmulatorClient = new SocketEmulatorClient(320, 640);
        //Server server = new Server(socketEmulatorClient);
        //socketEmulatorClient.start("10.0.0.153", 8000);
        //server.setRepaintPending();
    }

}
