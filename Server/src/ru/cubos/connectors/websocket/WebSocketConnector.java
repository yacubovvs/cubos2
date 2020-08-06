package ru.cubos.connectors.websocket;

import ru.cubos.connectors.Connector;
import ru.cubos.server.Server;

public class WebSocketConnector implements Connector {

    SocketServer socketServer;
    public WebSocketConnector(){
        socketServer = new SocketServer(8000);
        socketServer.start();
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
    public boolean transmitData(byte[] data) {
        //String outstring = data.toString();
        socketServer.messagesToSend.add(data);
        return true;
    }

    @Override
    public int getScreenWidth() {
        return 1080/2;
    }

    @Override
    public int getScreenHeight() {
        return 1920/2;
    }
}
