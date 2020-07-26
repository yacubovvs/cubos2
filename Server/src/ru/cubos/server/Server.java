package ru.cubos.server;

import ru.cubos.connectors.Connector;
import ru.cubos.connectors.emulator.Emulator;
import ru.cubos.server.helpers.ByteConverter;
import ru.cubos.server.helpers.framebuffer.Display;
import ru.cubos.server.settings.Settings;
import ru.cubos.server.system.ButtonBar;
import ru.cubos.server.system.StatusBar;
import ru.cubos.server.system.Time;
import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.apps.customApps.TestingApp;

import java.awt.*;
import java.util.List;

import static ru.cubos.server.helpers.ByteConverter.uByte;
import static ru.cubos.server.helpers.Protocol.*;

public class Server {

    public Connector connector;
    public Display display;
    public Settings settings;
    public StatusBar statusBar;
    public ButtonBar buttonBar;
    public Time time;
    public App currentApp;

    public static void main(String[] args) {
        startServerEmulator();
    }

    public static void startServerEmulator() {
        Emulator emulator = new Emulator(320, 480);
        Server server = new Server(emulator);
        emulator.setServer(server);

        server.start();
    }

    public Server(Connector connector) {
        this.connector = connector;
        display = new Display(connector.getScreenWidth(), connector.getScreenHeight());
        settings = new Settings();
        statusBar = new StatusBar(this);
        buttonBar = new ButtonBar(this);
        currentApp = new TestingApp(this);
        time = new Time();

        //display.drawLine(0,0,100,100, Colors.COLOR_RED);
    }

    public void start() {
        System.out.println("Server: Server started");
        Thread serverThread = new Thread(() -> {
            //while(true) {
            paint();
            currentApp.repaint();
            sendFrameBufferCommands();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //}
        });
        serverThread.start();
    }

    void paint() {
        if (statusBar.isRepaintPending()) statusBar.paint();
        if (buttonBar.isRepaintPending()) buttonBar.paint();

    }

    void sendFrameBufferCommands() {
        List<Display.DisplayCommand> commands = display.getFrame();
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

            connector.transmitData(message);
        } else {
            System.out.println("Server: no frame change");
        }
    }


    public boolean transmitData(byte[] data) {
        if(data.length==0){
            return true; // No data
        }

        System.out.println("Server: received " + data.length + " bytes");

        char x0, y0, x1, y1, r, g, b;
        int current_position = 0;

        while(current_position<data.length) {

            switch (data[current_position]) {

                case EVENT_CLICK:
                    //System.out.println("Emulator client: drawing rectangle command");
                    x0 = ByteConverter.bytesToChar(uByte(data[current_position + 1]), uByte(data[current_position + 2]));
                    y0 = ByteConverter.bytesToChar(uByte(data[current_position + 3]), uByte(data[current_position + 4]));

                    System.out.println("Server: on screen click " + (int)x0 + ", " + (int)y0);

                    current_position += 5;
                default:
                    System.out.println("Server: unknown protocol command recieved");
                    return false;
            }

        }

        return true;
    }
}