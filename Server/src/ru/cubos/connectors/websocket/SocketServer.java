package ru.cubos.connectors.websocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static ru.cubos.connectors.Protocol.Command_CLOSE_CONNECTION;

public class SocketServer {

    private static Socket clientSocket; //сокет для общения
    private static ServerSocket server; // серверсокет
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет
    private int port;
    public List<byte[]> messagesToSend = new ArrayList<>();

    private Reader reader;
    private Writer writer;

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

                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                        reader = new Reader();
                        writer = new Writer();

                        reader.start();
                        writer.start();
                    } catch (Exception e) {
                        System.out.println("Server error #1");
                    } finally {
                        //clientSocket.close();
                        //in.close();
                        //out.close();
                    }
                } catch (Exception e) {

                    System.out.println("Server error #2");
                } finally {
                    System.out.println("Server closed!");
                    server.close();
                }
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }


    private class Reader extends Thread {
        @Override
        public void run() {

            String str;

                while (true) {
                    try {
                    str = in.readLine();
                    //if (str.equals("stop")) {
                    //    break;
                    //}

                    out.write("got string: " + str + "\n");
                    out.flush();

                    } catch (IOException e) {}
                }


        }
    }

    public class Writer extends Thread {

        @Override
        public void run() {
            while (true) {
                String userWord;
                try {
                    if (messagesToSend.size()>0){
                        byte string[] = messagesToSend.get(0);
                        //out.write(string);
                        //out.w
                        for(int i=0; i<string.length; i++){
                            out.write(string[i]);
                        }

                        out.flush();
                        messagesToSend.remove(string);
                    }

                    Thread.sleep(1000);

                } catch (IOException | InterruptedException e) {}

            }
        }
    }

}


