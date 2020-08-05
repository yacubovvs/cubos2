package ru.cubos.connectors.websocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static ru.cubos.connectors.Protocol.Command_CLOSE_CONNECTION;

public class SocketServer {

    private static Socket clientSocket; //сокет для общения
    private static ServerSocket server; // серверсокет
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет
    private int port;

    public SocketServer(int port){
        this.port = port;
    }

    public void start(){
        while (true) {
            try {
                try {
                    server = new ServerSocket(port);
                    System.out.println("Socket server started at port " + port);

                    clientSocket = server.accept();

                    try {
                        String dataString = "";
                        while (!dataString.equals(Command_CLOSE_CONNECTION)) {
                            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                            if (in.lines().count() > 0) dataString = in.readLine();
                            else out.write("No data");

                            Thread.sleep(1000);
                            //System.out.println(dataString);

                            out.write("Reply: " + dataString + "\n");
                            out.flush();
                        }
                    } catch (Exception e) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        System.out.println("Server error #1");
                    } finally {
                        clientSocket.close();
                        in.close();
                        out.close();
                    }
                } catch (Exception e) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    System.out.println("Server error #2");
                } finally {
                    System.out.println("Server closed!");
                    server.close();
                }
            } catch (IOException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.err.println(e);
            }
        }
    }


    private class ReadMsg extends Thread {
        @Override
        public void run() {

            String str;
            try {
                while (true) {
                    str = in.readLine(); // ждем сообщения с сервера
                    if (str.equals("stop")) {

                        break; // выходим из цикла если пришло "stop"
                    }
                }
            } catch (IOException e) {}
        }
    }

    public class WriteMsg extends Thread {

        @Override
        public void run() {
            while (true) {
                String userWord;
                try {
                    out.write("stop" + "\n");
                    out.flush();
                    Thread.sleep(1000);

                } catch (IOException | InterruptedException e) {}

            }
        }
    }

}


