package ru.cubos.connectors.websocket;

import ru.cubos.connectors.Connector;
import ru.cubos.connectors.sockets.ServerSocket;
import ru.cubos.server.Server;

import static ru.cubos.commonHelpers.StaticScreenSettings.screenHeight_init;
import static ru.cubos.commonHelpers.StaticScreenSettings.screenWidth_init;

public class SocketConnector implements Connector {

    ServerSocket socketServer;
    public SocketConnector(){
    }

    @Override
    public boolean OnDataGotFromServer(byte[] data) {
        //String outstring = data.toString();
        socketServer.addMessage(data);

        return true;
    }

    @Override
    public int getScreenWidth() {
        return screenWidth_init;
    }

    @Override
    public int getScreenHeight() {
        return screenHeight_init;
    }

    public void start(Server server) {
        //socketServer = new ServerSocket(8000, server);
        socketServer = new ServerSocket(8000, server);
        socketServer.start();
    }
}
