package ru.cubos.connectors.sockets;

import ru.cubos.commonHelpers.profiler.Profiler;
import ru.cubos.server.Server;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static ru.cubos.commonHelpers.StaticSocketSettings.*;
import static ru.cubos.connectors.Protocol._FINISH_BYTES;

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

    public void addMessage(byte[] message){
        //Profiler.point("Message");

        messagesToSend.add(message);

        if(writer==null){
            writer = new Writer();
            writer.start();
        }
    }

    public ServerSocket(int port, Server server){
        this.server = server;
        this.port = port;
    }

    public void start(){
        //while (true) {
            try {
                try {
                    //socketServer = new java.net.ServerSocket(port);
                    socketServer = new java.net.ServerSocket();
                    socketServer.setReceiveBufferSize(serverBufferSize_max);
                    socketServer.bind(new InetSocketAddress( (InetAddress) null, port));
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
                    if(socketServer!=null)socketServer.close();
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
            //byte bytes[] = new byte[16 * 1024 * 1024];
            byte bytes[] = new byte[serverBufferSize];

            try {
                byte rest_bytes[] = null;
                while ((count = in.read(bytes)) > 0) {
                    byte sum_bytes[];
                    if (rest_bytes != null) {
                        sum_bytes = new byte[count + rest_bytes.length];
                        System.arraycopy(rest_bytes, 0, sum_bytes, 0, rest_bytes.length);
                        System.arraycopy(bytes, 0, sum_bytes, rest_bytes.length, count);
                    } else {
                        sum_bytes = new byte[count];
                        System.arraycopy(bytes, 0, sum_bytes, 0, count);
                    }

                    rest_bytes = server.serverCommandsDecoder.decodeCommands(sum_bytes, false, restBytesSize);
                    //System.out.println("Read " + count + " bytes");
                    //System.out.println("Rest bytes " + rest_bytes.length + " bytes");
                    if(
                            bytes[count-1] == _FINISH_BYTES
                                    && bytes[count-2] == _FINISH_BYTES
                                    && bytes[count-3] == _FINISH_BYTES
                                    && bytes[count-4] == _FINISH_BYTES
                                    && bytes[count-5] == _FINISH_BYTES
                                    && bytes[count-6] == _FINISH_BYTES
                                    && bytes[count-7] == _FINISH_BYTES
                                    && bytes[count-8] == _FINISH_BYTES
                    ){
                        server.serverCommandsDecoder.decodeCommands(rest_bytes, true, 0);
                        //Profiler.start("Message");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
                /*
                try{
                    byte rest_bytes[] = null;
                    while ((count = in.read(bytes)) > 0) {
                        //System.out.println("Recieved " + bytes.length + " bytes, with count " + count );

                        byte recievedData[] = new byte[count];
                        for(int i=0; i<count; i++){
                            recievedData[i] = bytes[i];
                        }
                        server.transmitData(recievedData);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
        }
    }

    public class Writer extends Thread {

        @Override
        public void run() {
            while (messagesToSend.size()>0) {

                try {
                    byte data[] = messagesToSend.get(0);
                    out.write(data);
                    out.write(new byte[]{_FINISH_BYTES, _FINISH_BYTES, _FINISH_BYTES, _FINISH_BYTES, _FINISH_BYTES, _FINISH_BYTES, _FINISH_BYTES, _FINISH_BYTES});
                    out.flush();
                    messagesToSend.remove(data);
                } catch (IOException e) {}

            }

            writer = null;
        }
    }

}


