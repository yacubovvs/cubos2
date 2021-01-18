package ru.cubos;

import ru.cubos.connectors.straightEmulator.LocalEmulator;
import ru.cubos.server.Server;
import ru.cubos.server.settings.Settings;
import ru.cubos.server.system.apps.customApps.TestingApp;
import ru.cubos.server.system.apps.systemApps.ApplicationsList;

public class Main_Local_windowMode {
    public static void main(String[] args) {
        LocalEmulator straightEmulator = new LocalEmulator(480, 320);

        //LocalEmulator straightEmulator = new LocalEmulator(280, 240);
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
