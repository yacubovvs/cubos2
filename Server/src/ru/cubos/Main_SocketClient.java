package ru.cubos;

import ru.cubos.connectors.ClientSessionSettings;
import ru.cubos.connectors.socketEmulatorClient.SocketClient;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import static ru.cubos.commonHelpers.StaticScreenSettings.screenHeight_init;
import static ru.cubos.commonHelpers.StaticScreenSettings.screenWidth_init;
import static ru.cubos.commonHelpers.StaticSocketSettings.cosketServerADdress;

public class Main_SocketClient {
    public static void main(String[] args){
        SocketClient socketClient = new SocketClient();
        socketClient.start(ClientSessionSettings.host_address, ClientSessionSettings.host_port);
    }


}
