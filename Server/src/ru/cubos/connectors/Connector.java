package ru.cubos.connectors;

import ru.cubos.server.Server;

public interface Connector {
    public boolean OnDataGotFromServer(byte[] data);
    public int getScreenWidth();
    public int getScreenHeight();
}
