package ru.cubos.connectors.sockets;

import javafx.scene.paint.Color;
import ru.cubos.profiler.Profiler;
import ru.cubos.server.helpers.ByteConverter;
import ru.cubos.server.helpers.binaryImages.BinaryImage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static ru.cubos.commonHelpers.StaticSocketSettings.clientBufferSize;
import static ru.cubos.commonHelpers.StaticSocketSettings.clientBufferSize_max;
import static ru.cubos.connectors.Protocol.*;

public class ClientSocket{

    private static Socket clientSocket; //сокет для общения
    private static InputStream in; // поток чтения из сокета
    private static OutputStream out; // поток записи в сокет
    private int port;
    private String addr;
    private List<byte[]> messagesToSend = new ArrayList<>();
    private ClientSocket_Updater clientSocket_updater;
    private Reader reader;
    private Writer writer;

    public void addMessage(byte[] message){
        messagesToSend.add(message);

        if(writer==null){
            writer = new Writer();
            writer.start();
        }
    }

    public ClientSocket(final String addr, final int port, ClientSocket_Updater clientSocket_updater){
        this.clientSocket_updater = clientSocket_updater;
        try {
            //clientSocket = new Socket(addr, port);
            clientSocket = new Socket();

            InetAddress addr_obj = InetAddress.getByName(addr);
            clientSocket.connect(new InetSocketAddress( addr_obj, port));
            clientSocket.setReceiveBufferSize(clientBufferSize_max);

            ClientSocket.this.addr = addr;
            ClientSocket.this.port = port;

            in = clientSocket.getInputStream();
            out = clientSocket.getOutputStream();

            reader = new Reader();
            writer = new Writer();

            reader.start();
            writer.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error starting socket client");
            return;
        }

    }

    private class Reader extends Thread {

        @Override
        public void run() {

            while (true) {
                int count;
                //byte bytes[] = new byte[16 * 1024 * 1024];
                byte bytes[] = new byte[clientBufferSize];

                try {
                    byte rest_bytes[] = null;
                    while ((count = in.read(bytes)) > 0) {
                        if (rest_bytes != null) {
                            byte sum_bytes[] = new byte[count + rest_bytes.length];
                            for (int i = 0; i < rest_bytes.length; i++) sum_bytes[i] = rest_bytes[i];
                            for (int i = rest_bytes.length; i < count + rest_bytes.length; i++)
                                sum_bytes[i] = bytes[i - rest_bytes.length];

                            rest_bytes = decodeCommands(sum_bytes);
                            clientSocket_updater.updateImage();
                        } else {
                            rest_bytes = decodeCommands(bytes);
                        }
                        //System.out.println("Read " + count + " bytes");
                        //System.out.println("Rest bytes " + rest_bytes.length + " bytes");
                    }

                    decodeCommands(rest_bytes);

                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

            }
        }


        private byte[] decodeCommands(byte data[]){
            return decodeCommands(data, false);
        }

        private byte[] decodeCommands(byte data[], boolean lastMessage){
            char x0, y0, x1, y1;
            int current_position = 0;
            final int rest_count_max = 8; //64
            byte r, g, b;

            BufferedImage bitmap = clientSocket_updater.getBitmap();

            while (current_position < data.length) {

                if(data.length-current_position<=rest_count_max && !lastMessage){
                    byte rest_data[] = new byte[data.length-current_position];
                    for(int i=0; i<data.length-current_position; i++){
                        rest_data[i] = data[current_position + i];
                    }
                    return  rest_data;
                }

                switch (data[current_position]) {
                    case DRAWING_PIXEL:
                        //System.out.println("Emulator client: drawing pixel command");

                        x0 = ByteConverter.bytesToChar(data[current_position + 1], data[current_position + 2]);
                        y0 = ByteConverter.bytesToChar(data[current_position + 3], data[current_position + 4]);
                        r = data[current_position + 5];
                        g = data[current_position + 6];
                        b = data[current_position + 7];

                        //drawPixel(x0, y0, new Color(r, g, b));
                        //System.out.printf("Drawing pixel");
                        //bitmap.setColorPixel(x0, y0, r, g, b);

                        //bitmap.setColorPixel(x0, y0, r, g, b);
                        java.awt.Color color = new java.awt.Color(r+128,g+128,b+128);
                        bitmap.setRGB(x0,y0, color.getRGB() );

                        current_position += 8;

                        break;
                    case DRAWING_RECT:
                        //System.out.println("Emulator client: drawing rectangle command");
                        x0 = ByteConverter.bytesToChar(data[current_position + 1], data[current_position + 2]);
                        y0 = ByteConverter.bytesToChar(data[current_position + 3], data[current_position + 4]);
                        x1 = ByteConverter.bytesToChar(data[current_position + 5], data[current_position + 6]);
                        y1 = ByteConverter.bytesToChar(data[current_position + 7], data[current_position + 8]);
                        r = data[current_position + 9];
                        g = data[current_position + 10];
                        b = data[current_position + 11];

                        current_position += 12;
                        //drawRect(x0, y0, x1, y1, new Color(r, g, b));
                        //System.out.printf("Drawing rectangle");
                        break;
                    case DRAWING_RECTS_ARRAY:
                        //System.out.println("Emulator client: drawing rectangle array");
                        break;
                    case DRAWING_PIXELS_ARRAY:
                        //System.out.println("Emulator client: drawing pixels array");
                        break;
                    case UPDATE_SCREEN:
                        //System.out.println("Emulator client: update screen");
                        //updateImage();
                        //System.out.printf("Update image");
                        break;
                    default:
                        //System.out.println("Emulator client: unknown protocol command");
                        current_position++;
                        //return false;
                }


            }
            return null;
        }
    }


    public class Writer extends Thread {

        @Override
        public void run() {
            while (messagesToSend.size()>0) {

                try {
                        byte data[] = messagesToSend.get(0);
                        out.write(data);
                        out.flush();
                        messagesToSend.remove(data);

                } catch (IOException e) {}

            }

            writer = null;
        }
    }
}
