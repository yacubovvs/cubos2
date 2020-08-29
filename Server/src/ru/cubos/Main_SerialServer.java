package ru.cubos;

import jssc.SerialPortException;
import ru.cubos.connectors.localserial.SerialConnector;
import ru.cubos.server.Server;

public class Main_SerialServer {
    public static void main(String[] args) {
        //while(true) {
            SerialConnector connector = new SerialConnector("/dev/cu.usbmodem14201");
            Server server = new Server(connector);
            connector.start(server);

        //}
    }

}
