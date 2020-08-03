package ru.cubos.server.system.apps.systemApps.desktopWidgets;

import ru.cubos.server.Server;
import ru.cubos.server.system.apps.App;

public abstract class DesktopWidget extends App {
    public DesktopWidget(Server server) {
        super(server);

        setResizingEnable(false);
        setMovingEnable(false);
    }

}
