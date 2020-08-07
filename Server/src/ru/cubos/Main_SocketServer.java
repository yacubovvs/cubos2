package ru.cubos;

import ru.cubos.connectors.websocket.WebSocketConnector;
import ru.cubos.server.Server;

public class Main_SocketServer {
    public static void main(String[] args) {
        while(true) {
            WebSocketConnector connector = new WebSocketConnector();
            Server server = new Server(connector);
            connector.start(server);
            server.start();
        }
    }

}
