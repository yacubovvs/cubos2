package ru.cubos.server;

import ru.cubos.connectors.Connector;
import ru.cubos.connectors.emulator.Emulator;
import ru.cubos.server.helpers.*;
import ru.cubos.server.helpers.framebuffer.Display;
import ru.cubos.server.settings.Settings;
import ru.cubos.server.system.ButtonBar;
import ru.cubos.server.system.TimeWidgetView;
import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.apps.customApps.TestingApp;
import ru.cubos.server.system.apps.systemApps.ApplicationsList;
import ru.cubos.server.system.apps.systemApps.desktopWidgets.StatusBarDesktopWidget;
import ru.cubos.server.system.events.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.cubos.server.helpers.ByteConverter.uByte;
import static ru.cubos.server.helpers.Protocol.*;

public class Test {

    public static void main(String[] args) {
    }

}