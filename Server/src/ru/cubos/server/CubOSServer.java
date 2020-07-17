package ru.cubos.server;

import ru.cubos.connectors.Connector;
import ru.cubos.connectors.emulator.Emulator;

public class CubOSServer {

    Connector connector;

    public static void main(String[] args) {
        emulator();
    }

    public static void emulator() {
        CubOSServer cubOSServer = new CubOSServer(new Emulator());
        cubOSServer.start();
    }

    public CubOSServer(Connector connector){
        this.connector = connector;
    }

    public void start(){
        System.out.println("Server started");
    }
}
