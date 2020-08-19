package ru.cubos.connectors.sockets;

import ru.cubos.commonHelpers.Colors;
import ru.cubos.connectors.Connectorable;
import ru.cubos.connectors.sockets.ServerSocket;
import ru.cubos.server.Server;
import ru.cubos.server.framebuffer.Display;

import java.util.ArrayList;
import java.util.List;

import static ru.cubos.commonHelpers.ByteConverter.char_to_bytes;
import static ru.cubos.commonHelpers.ByteConverter.uByte;
import static ru.cubos.commonHelpers.Protocol.*;
import static ru.cubos.commonHelpers.StaticScreenSettings.screenHeight_init;
import static ru.cubos.commonHelpers.StaticScreenSettings.screenWidth_init;

public class SocketConnector implements Connectorable {
    private List<Display.DisplayCommand> displayCommands;

    ServerSocket socketServer;
    public SocketConnector(){
    }

    @Override
    public boolean OnDataGotFromServer(byte[] data) {
        //String outstring = data.toString();
        socketServer.addMessage(data);

        return true;
    }

    @Override
    public int getScreenWidth() {
        return screenWidth_init;
    }

    @Override
    public int getScreenHeight() {
        return screenHeight_init;
    }

    @Override
    public boolean updateScreen(Display display) {
        //Profiler.start("get frame");
        List<Display.DisplayCommand> commands = display.getFrame();
        //Profiler.stop("get frame");
        //System.out.println("Server: Server loop");

        if (commands.size() != 0) {
            int messageLength = 0;
            for (Display.DisplayCommand command : commands) {
                messageLength += command.params.length;
                messageLength += 1; // Command type
            }

            byte message[] = new byte[messageLength];
            int messagePosition = 0;

            for (Display.DisplayCommand command : commands) {
                message[messagePosition] = command.type;
                messagePosition++;
                for (char p = 0; p < command.params.length; p++) {
                    message[messagePosition] = command.params[p];
                    messagePosition++;
                }
            }

            //System.out.println("Server: sending " + message.length + " bytes");
            OnDataGotFromServer(message);
            //Profiler.addCount("Received data", message.length);
            //Profiler.addCount("Total frames", 1);
            //Profiler.showCountAccumulators();
            return true;
        } else {
            //System.out.println("Server: no frame change");
            return false;
        }

        //long usedBytes = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        //System.out.println("Using RAM: " + usedBytes/1048576 + " mb");
    }

    public void start(Server server) {
        //socketServer = new ServerSocket(8000, server);
        socketServer = new ServerSocket(8000, server);
        socketServer.start();
    }


}
