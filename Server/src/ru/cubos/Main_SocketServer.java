package ru.cubos;

import ru.cubos.connectors.websocket.SocketConnector;
import ru.cubos.server.Server;

public class Main_SocketServer {
    public static void main(String[] args) {
        while(true) {
            SocketConnector connector = new SocketConnector();
            Server server = new Server(connector);
            connector.start(server);
            //server.start();
        }
    }

}
