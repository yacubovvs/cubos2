package ru.cubos;

import ru.cubos.connectors.socketEmulatorClient.SocketClient;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class Main_SocketClient {
    public static void main(String[] args) throws UnknownHostException {
        SocketClient socketClient = new SocketClient(1080/3, 2340/3);
        //socketClient.start(Inet4Address.getLocalHost().getHostAddress(), 8000);
        socketClient.start("192.168.1.38", 8000);
    }


}
