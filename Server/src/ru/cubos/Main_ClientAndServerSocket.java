package ru.cubos;

public class Main_ClientAndServerSocket {
    public static void main(String[] args) {
        Thread serverThread = new Thread(() -> Main_SocketServer.main(args));
        Thread clientThread = new Thread(() -> Main_SocketClient.main(args));

        serverThread.start();
        clientThread.start();
    }
}
