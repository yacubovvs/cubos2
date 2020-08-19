package ru.cubos;

import ru.cubos.connectors.straightEmulator.LocalEmulator;
import ru.cubos.server.Server;

import java.awt.*;

public class Main_LocalFullScreen {
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize ();

        LocalEmulator straightEmulator = new LocalEmulator(screenSize.width, screenSize.height);
        Server server = new Server(straightEmulator);
        straightEmulator.setServer(server);
        straightEmulator.start();
        //server.setRepaintPending();
    }

}
