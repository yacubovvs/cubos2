package ru.cubos.server;

import ru.cubos.connectors.Connector;
import ru.cubos.connectors.emulator.Emulator;
import ru.cubos.server.helpers.BinaryImage;
import ru.cubos.server.helpers.BinaryImage_24bit;
import ru.cubos.server.helpers.ByteConverter;
import ru.cubos.server.helpers.Colors;
import ru.cubos.server.helpers.framebuffer.Display;
import ru.cubos.server.settings.Settings;
import ru.cubos.server.system.ButtonBar;
import ru.cubos.server.system.TimeWidgetView;
import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.apps.customApps.TestingApp;
import ru.cubos.server.system.apps.systemApps.ApplicationsList;
import ru.cubos.server.system.apps.systemApps.desktopWidgets.StatusBarDesktopWidget;
import ru.cubos.server.system.events.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.cubos.server.helpers.ByteConverter.uByte;
import static ru.cubos.server.helpers.Protocol.*;

public class Server {

    public Connector connector;
    public Display display;
    public Settings settings;
    public StatusBarDesktopWidget statusBar;
    public ButtonBar buttonBar;
    public TimeWidgetView timeWidgetView;
    public List<App> openedApps;

    private boolean repaintPending;

    public static void main(String[] args) {
        startServerEmulator();
    }

    public static void startServerEmulator() {
        Emulator emulator = new Emulator(640, 480);
        Server server = new Server(emulator);
        emulator.setServer(server);

        server.start();
    }

    public App getActiveApp(){
        return openedApps.get(openedApps.size()-1);
    }

    private BinaryImage_24bit backGroundImage;



    public Server(Connector connector) {
        this.connector = connector;
        display = new Display(connector.getScreenWidth(), connector.getScreenHeight());
        settings = new Settings();
        statusBar = new StatusBarDesktopWidget(this);
        buttonBar = new ButtonBar(this);
        openedApps = new ArrayList<>();

        openedApps.add(statusBar);
        openedApps.add(new ApplicationsList(this));
        openedApps.add(new TestingApp(this));

        timeWidgetView = new TimeWidgetView();

        try {
            backGroundImage = new BinaryImage_24bit("images//bg_640x480.png");
        } catch (IOException e) {
            backGroundImage = null;
        }

        //display.drawLine(0,0,100,100, Colors.COLOR_RED);
    }

    public void start() {
        System.out.println("Server: Server started");
        Thread serverThread = new Thread(() -> {
            //while(true) {
            drawApps();
            drawBars();
            sendFrameBufferCommands();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        serverThread.start();
    }


    void drawApps(){
        if(backGroundImage==null) display.drawRect(0, settings.getStatusBarHeight(), display.getWidth(), display.getHeight() - settings.getButtonBarHeight(), Colors.COLOR_BLACK, true);
        else display.drawImage(
                0, 0,
                display.getWidth(), display.getHeight() - settings.getButtonBarHeight(),
                0, settings.getStatusBarHeight(),
                backGroundImage, null);

        for( App app: openedApps){
            app.draw();
        }

    }

    void drawBars() {
        if (statusBar.isRepaintPending()) statusBar.draw();
        if (buttonBar.isRepaintPending()) buttonBar.paint();

    }

    public void sendFrameBufferCommands() {
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

            //System.out.println("Server: sending " + message.length + " bytes");
            connector.transmitData(message);
        } else {
            //System.out.println("Server: no frame change");
        }

        //long usedBytes = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        //System.out.println("Using RAM: " + usedBytes/1048576 + " mb");
    }


    public boolean transmitData(byte[] data) {
        if(data.length==0){
            return true; // No data
        }

        //System.out.println("Server: received " + data.length + " bytes");

        char x_start, y_start;
        char x0, y0, x1, y1, r, g, b;
        int current_position = 0;

        while(current_position<data.length) {

            switch (data[current_position]) {

                case EVENT_TOUCH_TAP:
                    //System.out.println("Emulator client: drawing rectangle command");
                    x0 = ByteConverter.bytesToChar(uByte(data[current_position + 1]), uByte(data[current_position + 2]));
                    y0 = ByteConverter.bytesToChar(uByte(data[current_position + 3]), uByte(data[current_position + 4]));

                    //System.out.println("Server: on screen click " + (int)x0 + ", " + (int)y0);
                    getActiveApp().execEvent(new TouchTapEvent(x0, y0));

                    current_position += 5;

                    break;
                case EVENT_TOUCH_UP:
                    x0      = ByteConverter.bytesToChar(uByte(data[current_position + 1]),  uByte(data[current_position + 2]));
                    y0      = ByteConverter.bytesToChar(uByte(data[current_position + 3]),  uByte(data[current_position + 4]));
                    current_position += 5;

                    //System.out.println("Server: on screen mouse up");
                    getActiveApp().execEvent(new TouchUpEvent(x0, y0));
                    break;
                case EVENT_TOUCH_DOWN:
                    x0      = ByteConverter.bytesToChar(uByte(data[current_position + 1]),  uByte(data[current_position + 2]));
                    y0      = ByteConverter.bytesToChar(uByte(data[current_position + 3]),  uByte(data[current_position + 4]));
                    current_position += 5;

                    //System.out.println("Server: on screen mouse down");
                    for (App app: openedApps) app.setMoving(false);
                    for (App app: openedApps) app.setResizing(false);
                    activateAppByCoordinates(x0, y0);
                    getActiveApp().execEvent(new TouchDownEvent(x0, y0));
                    break;
                case EVENT_TOUCH_MOVE:
                    x0      = ByteConverter.bytesToChar(uByte(data[current_position + 1]),  uByte(data[current_position + 2]));
                    y0      = ByteConverter.bytesToChar(uByte(data[current_position + 3]),  uByte(data[current_position + 4]));
                    x1      = ByteConverter.bytesToChar(uByte(data[current_position + 5]),  uByte(data[current_position + 6]));
                    y1      = ByteConverter.bytesToChar(uByte(data[current_position + 7]),  uByte(data[current_position + 8]));
                    x_start = ByteConverter.bytesToChar(uByte(data[current_position + 9]),  uByte(data[current_position + 10]));
                    y_start = ByteConverter.bytesToChar(uByte(data[current_position + 11]), uByte(data[current_position + 12]));
                    current_position += 13;

                    //System.out.println("Server: on screen move");
                    getActiveApp().execEvent(new TouchMoveEvent(x0, y0, x1, y1, x_start, y_start));
                    break;
                case EVENT_TOUCH_MOVE_FINISHED:
                    x0      = ByteConverter.bytesToChar(uByte(data[current_position + 1]),  uByte(data[current_position + 2]));
                    y0      = ByteConverter.bytesToChar(uByte(data[current_position + 3]),  uByte(data[current_position + 4]));
                    x_start = ByteConverter.bytesToChar(uByte(data[current_position + 5]),  uByte(data[current_position + 6]));
                    y_start = ByteConverter.bytesToChar(uByte(data[current_position + 7]),  uByte(data[current_position + 8]));
                    current_position += 9;

                    //System.out.println("Server: on move finished");
                    getActiveApp().execEvent(new TouchMoveFinishedEvent(x0, y0, x_start, y_start));
                    break;
                default:
                    System.out.println("Server: unknown protocol command recieved");
                    return false;
            }

        }

        return true;
    }

    public boolean isRepaintPending() {
        return repaintPending;
    }

    public void activateAppByCoordinates(int x, int y){
        //for (App app: openedApps){
        if(!getActiveApp().coordinatesInActiveArea(x,y)) {
            if(getActiveApp().onFocusLose()) {
                for (int i = openedApps.size() - 1; i >= 0; i--) {
                    App app = openedApps.get(i);

                    if (app.coordinatesInActiveArea(x, y)) {
                        if (getActiveApp() != app) {
                            activateApp(app);
                        }
                        return;
                    }
                }
                activateApp(statusBar);
            }
        }
    }

    public void activateApp(App app){
        openedApps.remove(app);
        openedApps.add(app);
        app.onFocusGot();
        // TODO: call redrawing app after order change
        setRepaintPending();
    }

    public void closeApp(App app){
        openedApps.remove(app);
        app = null;
    }

    public void openApp(App app){
        openedApps.add(app);
        app.onFocusGot();
        setRepaintPending();
    }

    public void setRepaintPending() {
        this.repaintPending = true;

        // TODO: Remove this in future
        long start = System.currentTimeMillis();
        drawApps();
        long finish = System.currentTimeMillis();
        long timeConsumedMillis = finish - start;
        //System.out.println("Repaint time: " + timeConsumedMillis + " ms");

        sendFrameBufferCommands();
    }

    public void cancelRepaintPending() {
        this.repaintPending = false;
    }
}