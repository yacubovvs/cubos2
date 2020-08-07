package ru.cubos;

import ru.cubos.connectors.socketEmulatorClient.SocketClient;

public class Main_SocketClient {
    public static void main(String[] args) {
        SocketClient socketClient = new SocketClient(1080/3, 2340/3);
        socketClient.start("192.168.1.38", 8000);
        //Server server = new Server(socketClient);
        //socketClient.start("10.0.0.153", 8000);
        //server.setRepaintPending();
    }


}
