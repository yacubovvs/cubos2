package ru.cubos.connectors.sockets;

import ru.cubos.connectors.ClientSessionSettings;
import ru.cubos.connectors.socketEmulatorClient.SocketEmulatorClientCommandDecoder;
import ru.cubos.server.helpers.ByteConverter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
    private SocketEmulatorClientCommandDecoder socketEmulatorClientCommandDecoder;
    //protected BufferedImage bitmap = clientSocket_updater.getBitmap();

    public void addMessage(byte[] message){
        messagesToSend.add(message);

        if(writer==null){
            writer = new Writer();
            writer.start();
        }
    }



    public ClientSocket(final String addr, final int port, ClientSocket_Updater clientSocket_updater, SocketEmulatorClientCommandDecoder socketEmulatorClientCommandDecoder){
        this.clientSocket_updater = clientSocket_updater;
        this.socketEmulatorClientCommandDecoder = socketEmulatorClientCommandDecoder;

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

            // Sending screen params
            byte[] screenWidth = ByteConverter.char_to_bytes((char)(ClientSessionSettings.screen_width));
            byte[] screenHeight = ByteConverter.char_to_bytes((char)(ClientSessionSettings.screen_height));

            byte message[] = new byte[]{
                    _0_MODE_OPTION,                         // Switch mode
                    _0_1_OPTIONS_MODE,                      // Switch to COMMON MODE 1

                    _1_SET_OPTION,                          // Command to set option
                    _1_6_OPTIONS_SCREEN,                    // Screen param
                    _1_6_1_OPTIONS_SETTINGS_WIDTH,          // Setting screen width
                    screenWidth[0],
                    screenWidth[1],

                    _1_SET_OPTION,                           // Command to set option
                    _1_6_OPTIONS_SCREEN,                     // Screen param
                    _1_6_2_OPTIONS_SETTINGS_HEIGHT,          // Setting screen height
                    screenHeight[0],
                    screenHeight[1],

                    _1_SET_OPTION,                           // Command to set option
                    _1_6_OPTIONS_SCREEN,                     // Screen param
                    _1_6_3_OPTIONS_SETTINGS_COLORS,          // Setting screen color
                    _1_6_3_2_SCREEN_COLORS_24BIT__8_8_8,

                    _1_SET_OPTION,                           // Command to set option
                    _1_4_SERVER_OPTION,
                    _1_4_1_START_SERVER
            };

            addMessage(message);
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

                    socketEmulatorClientCommandDecoder.decodeCommands(rest_bytes);

                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

            }
        }


        private byte[] decodeCommands(byte data[]){
            return socketEmulatorClientCommandDecoder.decodeCommands(data, false, 8);
        }


        /*
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
                    case _1_DRAWING_PIXEL:
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
                    case _2_DRAWING_RECT:
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
                    case _4_DRAWING_RECTS_ARRAY:
                        //System.out.println("Emulator client: drawing rectangle array");
                        break;
                    case _3_DRAWING_PIXELS_ARRAY:
                        //System.out.println("Emulator client: drawing pixels array");
                        break;

                    //case UPDATE_SCREEN:
                        //System.out.println("Emulator client: update screen");
                        //updateImage();
                        //System.out.printf("Update image");
                    //    break;
                    default:
                        //System.out.println("Emulator client: unknown protocol command");
                        current_position++;
                        //return false;
                }


            }
            return null;
        }*/
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
