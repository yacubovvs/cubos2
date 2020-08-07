package ru.cubos.connectors.sockets;

import ru.cubos.server.Server;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSocket {

    private Server server;
    private Socket clientSocket; //сокет для общения
    private java.net.ServerSocket socketServer; // серверсокет
    private InputStream in; // поток чтения из сокета
    private OutputStream out; // поток записи в сокет
    private int port;
    public List<byte[]> messagesToSend = new ArrayList<>();

    private Reader reader;
    private Writer writer;

    public ServerSocket(int port, Server server){
        this.server = server;
        this.port = port;
    }

    public void start(){
        //while (true) {
            try {
                try {
                    socketServer = new java.net.ServerSocket(port);
                    System.out.println("Socket server started at port " + port);

                    clientSocket = socketServer.accept();

                    try {
                        String dataString = "";

                        in = clientSocket.getInputStream();
                        out = clientSocket.getOutputStream();

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
                    socketServer.close();
                }
            } catch (IOException e) {
                System.err.println(e);
            }

        //}
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }


    private class Reader extends Thread {
        @Override
        public void run() {
            int count;
            byte bytes[] = new byte[32];

                byte rest_bytes[] = null;
                try{
                    while ((count = in.read(bytes)) > 0) {

                        System.out.println("Recieved " + bytes.length + " bytes, with count " + count );
                        byte recievedData[] = new byte[count];
                        for(int i=0; i<count; i++){
                            recievedData[i] = bytes[i];
                        }
                        server.transmitData(recievedData);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    private class Writer extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    if (messagesToSend.size()>0){
                        byte message[] = messagesToSend.get(0);


                        int position = 0;
                        int package_size = 16*1024*1024;
                        while (position<message.length) {
                            int message_size = (position+package_size>message.length?message.length-position:package_size);
                            byte slice_message[] = new byte[message_size];
                            for(int i=0; i<message_size; i++) slice_message[i] = message[i + position];

                            out.write(slice_message, 0, message_size);
                            position += message_size;
                        }

                        //for(int i=0; i<string.length; i++){
                        //    out.write(string[i]);
                        //}

                        out.flush();
                        messagesToSend.remove(message);
                    }

                    Thread.sleep(1000);

                } catch (IOException | InterruptedException e) {}

            }
        }
    }

}

