package ru.cubos.connectors;

public interface Connector {
    public void transmitData(byte[] data);
    public byte[] recieveData();
}
