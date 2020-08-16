package ru.cubos;

import ru.cubos.connectors.websocket.SocketConnector;
import ru.cubos.commonHelpers.profiler.Profiler;
import ru.cubos.server.Server;

public class Main_SocketServer_Bench {
    public static void main(String[] args) {
        SocketConnector connector = new SocketConnector();
        Server server = new Server(connector);
        connector.start(server);
        //server.start();

        server.display.resetLastFrame();
        server.drawApps();

        for (int i=0; i<20; i++) {
            benchFoo(server);
        }

        Profiler.start("test");
        for (int i=0; i<3000; i++) {
            benchFoo(server);
            Profiler.point("test");
        }

        //Profiler.showSumTimers();
    }

    static public void benchFoo(Server server){
        server.drawApps();
        //server.display.getFrame();

        /*
        server.display.resetLastFrame();
        server.drawApps();
        server.sendFrameBufferCommands();
        */
    }



}
