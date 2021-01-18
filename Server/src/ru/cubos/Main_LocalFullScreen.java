package ru.cubos;

import ru.cubos.connectors.straightEmulator.LocalEmulator;
import ru.cubos.server.Server;
import ru.cubos.server.settings.Settings;
import ru.cubos.server.system.apps.customApps.TestingApp;
import ru.cubos.server.system.apps.systemApps.ApplicationsList;

import java.awt.*;

public class Main_LocalFullScreen {
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        LocalEmulator straightEmulator = new LocalEmulator(screenSize.width, screenSize.height);

        Settings settings = new Settings();
        settings.setWindowMode(true);

        Server server = new Server(straightEmulator, settings);

        straightEmulator.setServer(server);
        straightEmulator.start();
        //server.setRepaintPending();

        //server.openApp(new ApplicationsList(server));
        server.openApp(new ApplicationsList(server));
        server.openApp(new TestingApp(server));
    }

}
