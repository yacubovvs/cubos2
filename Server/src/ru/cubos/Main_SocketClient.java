package ru.cubos;

import ru.cubos.connectors.socketEmulatorClient.SocketClient;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import static ru.cubos.commonHelpers.StaticScreenSettings.screenHeight_init;
import static ru.cubos.commonHelpers.StaticScreenSettings.screenWidth_init;
import static ru.cubos.commonHelpers.StaticSocketSettings.cosketServerADdress;

public class Main_SocketClient {
    public static void main(String[] args) throws UnknownHostException {
        SocketClient socketClient = new SocketClient(screenWidth_init, screenHeight_init);
        socketClient.start(cosketServerADdress, 8000);
        //socketClient.start("192.168.1.38", 8000);
    }


}
