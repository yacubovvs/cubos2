package ru.cubos.server;

import ru.cubos.connectors.websocket.SocketServer;

public class Test {

    public static void main(String[] args) {
        SocketServer socketServer = new SocketServer(8000);
        socketServer.start();
    }

}