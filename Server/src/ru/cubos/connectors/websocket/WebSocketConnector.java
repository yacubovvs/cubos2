package ru.cubos.connectors.websocket;

import ru.cubos.connectors.Connector;
import ru.cubos.server.Server;

public class WebSocketConnector implements Connector {

    SocketServer socketServer;
    public WebSocketConnector(){

        /*
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                socketServer.start();
            }
        });

        thread.start();
        */

        //socketServer.start();
    }

    @Override
    public boolean OnDataGotFromServer(byte[] data) {
        //String outstring = data.toString();
        socketServer.messagesToSend.add(data);
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
        socketServer = new SocketServer(8000, server);
        socketServer.start();
    }
}
