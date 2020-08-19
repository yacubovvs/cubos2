package ru.cubos.connectors;

import ru.cubos.server.Server;
import ru.cubos.server.framebuffer.Display;

public interface Connectorable {
    public boolean OnDataGotFromServer(byte[] data);
    public int getScreenWidth();
    public int getScreenHeight();

    public boolean updateScreen(Display display);
}
