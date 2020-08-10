package ru.cubos.connectors.websocket;

import ru.cubos.connectors.Connector;
import ru.cubos.connectors.sockets.ServerSocket;
import ru.cubos.server.Server;

public class WebSocketConnector implements Connector {

    ServerSocket socketServer;
    public WebSocketConnector(){
    }

    @Override
    public boolean OnDataGotFromServer(byte[] data) {
        //String outstring = data.toString();
        socketServer.addMessage(data);

        return true;
    }

    @Override
    public int getScreenWidth() {
        return 1080/3;
    }

    @Override
    public int getScreenHeight() {
        return 2340/3;
    }

    public void start(Server server) {
        socketServer = new ServerSocket(8000, server);
        socketServer.start();
    }
}
