package ru.cubos;

import ru.cubos.connectors.websocket.SocketConnector;
import ru.cubos.profiler.Profiler;
import ru.cubos.server.Server;

public class Main_SocketServer_Bench {
    public static void main(String[] args) {
        //while(true) {
            SocketConnector connector = new SocketConnector();
            Server server = new Server(connector);
            connector.start(server);
            server.start();


            for (int i=0; i<50; i++) {
                server.display.resetLastFrame();
                server.drawApps();
                server.sendFrameBufferCommands();
            }

            Profiler.start("test");
            for (int i=0; i<100; i++) {
                server.display.resetLastFrame();
                server.drawApps();
                server.sendFrameBufferCommands();
                Profiler.point("test");
            }
        //}
    }

}
