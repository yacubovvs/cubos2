package ru.cubos;

import ru.cubos.commonHelpers.profiler.Profiler;

import java.net.UnknownHostException;

public class Main_ClientAndServerSocket_bench {
    public static void main(String[] args) {
        Thread serverThread = new Thread(() -> Main_SocketServer_Bench.main(args));
        Thread clientThread = new Thread(() -> {
            Main_SocketClient.main(args);
        });

        serverThread.start();
        clientThread.start();
    }
}
