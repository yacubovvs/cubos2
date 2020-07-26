package ru.cubos.connectors;

public interface Connector {
    public boolean transmitData(byte[] data);
    public int getScreenWidth();
    public int getScreenHeight();
}
