package com.example.androidcubosclient.connectors;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.example.androidcubosclient.CanvasScreen;
import com.example.androidcubosclient.helpers.ByteConverter;
import com.example.androidcubosclient.helpers.ClientSessionSettings;
import com.example.androidcubosclient.helpers.CommandDecoder;
import com.example.androidcubosclient.helpers.Profiler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.example.androidcubosclient.helpers.Protocol.*;
import static com.example.androidcubosclient.helpers.StaticSocketSettings.clientBufferSize;
import static com.example.androidcubosclient.helpers.StaticSocketSettings.clientBufferSize_max;


public class ClientSocket{

    private static Socket clientSocket;
    private static InputStream in;
    private static OutputStream out;
    private int port;
    private String addr;
    private List<byte[]> messagesToSend = new ArrayList<>();
    private Reader reader;
    private Writer writer;
    CommandDecoder commandDecoder;

    Bitmap bitmap;

    //protected BufferedImage bitmap = clientSocket_updater.getBitmap();

    public void addMessage(byte[] message){
        messagesToSend.add(message);

        if(writer==null){
            writer = new Writer();
            writer.start();
        }
    }



    public ClientSocket(final String addr, final int port, Bitmap bitmap, final CommandDecoder commandDecoder){
        this.bitmap = bitmap;
        this.commandDecoder = commandDecoder;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
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

                    byte colorScheme = _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8;
                    //byte colorScheme = _1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS;
                    commandDecoder.currentColorScheme = colorScheme;

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
                            colorScheme,

                            _1_SET_OPTION,                           // Command to set option
                            _1_4_SERVER_OPTION,
                            _1_4_1_START_SERVER,

                            _0_MODE_OPTION,                          // Switch mode
                            _0_3_EVENT_MODE,                         // Switch to COMMON MODE 1
                    };

                    addMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error starting socket client");
                    return;
                }
            }
        });

        thread.start();
    }

    private class Reader extends Thread {

        @Override
        public void run() {

            while (true) {
                Profiler.start("Socket read");
                int count;
                byte bytes[] = new byte[clientBufferSize];

                try {
                    byte rest_bytes[] = null;
                    while ((count = in.read(bytes)) > 0) {
                        if (rest_bytes != null) {
                            byte sum_bytes[] = new byte[count + rest_bytes.length];

                            System.arraycopy(rest_bytes, 0, sum_bytes, 0, rest_bytes.length);
                            System.arraycopy(bytes, 0, sum_bytes, rest_bytes.length, count);

                            rest_bytes = decodeCommands(sum_bytes);

                        } else {
                            byte sum_bytes[] = new byte[count];
                            System.arraycopy(bytes, 0, sum_bytes, 0, count);
                            rest_bytes = decodeCommands(sum_bytes);
                        }
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
                            commandDecoder.decodeCommands(rest_bytes, true, 0);
                            //clientSocket_updater.updateImage();
                            Profiler.point("Socket read");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }


        private byte[] decodeCommands(byte data[]){
            return commandDecoder.decodeCommands(data, false, 8);
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
