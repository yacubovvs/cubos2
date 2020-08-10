package ru.cubos;

import java.net.UnknownHostException;

public class Main_ClientAndServerSocket_bench {
    public static void main(String[] args) {
        Thread serverThread = new Thread(() -> Main_SocketServer.main(args));
        Thread clientThread = new Thread(() -> {
            try {
                Main_SocketClient.main(args);
            } catch (UnknownHostException e) {
                System.out.println("Client socket start error");
            }
        });

        serverThread.start();
        clientThread.start();
    }
}
