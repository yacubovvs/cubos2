package ru.cubos.server;

import ru.cubos.connectors.Connector;
import ru.cubos.connectors.emulator.Emulator;

import static ru.cubos.server.helpers.ByteConverter.uByte;
import static ru.cubos.server.helpers.Protocol.*;

public class Server {

    Connector connector;

    public static void main(String[] args) {
        startServerEmulator();
    }

    public static void startServerEmulator() {
        Server server = new Server(new Emulator(32,48));
        server.start();
    }

    public Server(Connector connector){
        this.connector = connector;
    }

    public void start(){
        System.out.println("Server: Server started");
        Thread serverThread = new Thread(()->{
            while(true) {
                System.out.println("Server: Server loop");
                try {
                    connector.transmitData(new byte[]{
                            DRWING_RECT,
                            uByte(0x00),    // X0
                            uByte(0x00),    // X0
                            uByte(0x00),    // Y0
                            uByte(0x00),    // Y0
                            uByte(0x00),    // X1
                            uByte(0x02),    // X1
                            uByte(0x00),    // Y1
                            uByte(0x02),    // Y1
                            uByte(0xFF),    // R
                            uByte(0x00),    // G
                            uByte(0x00)     // B
                    });

                    connector.transmitData(new byte[]{
                            DRWING_RECT,
                            uByte(0x00),    // X0
                            uByte(0x03),    // X0
                            uByte(0x00),    // Y0
                            uByte(0x03),    // Y0
                            uByte(0x00),    // X1
                            uByte(0x05),    // X1
                            uByte(0x00),    // Y1
                            uByte(0x05),    // Y1
                            uByte(0x00),    // R
                            uByte(0xFF),    // G
                            uByte(0x00)     // B
                    });

                    connector.transmitData(new byte[]{
                            DRWING_RECT,
                            uByte(0x00),    // X0
                            uByte(0x09),    // X0
                            uByte(0x00),    // Y0
                            uByte(0x09),    // Y0
                            uByte(0x00),    // X1
                            uByte(0x06),    // X1
                            uByte(0x00),    // Y1
                            uByte(0x06),    // Y1
                            uByte(0x00),    // R
                            uByte(0x00),    // G
                            uByte(0xFF)     // B
                    });

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        serverThread.start();
    }


}
