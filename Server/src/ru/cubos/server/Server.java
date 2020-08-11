package ru.cubos.server;

import ru.cubos.commonHelpers.profiler.Profiler;
import ru.cubos.connectors.Connector;
import ru.cubos.server.helpers.binaryImages.BinaryImage_24bit;
import ru.cubos.server.helpers.ByteConverter;
import ru.cubos.server.helpers.Colors;
import ru.cubos.server.helpers.framebuffer.Display;
import ru.cubos.server.settings.Settings;
import ru.cubos.server.system.ButtonBar;
import ru.cubos.server.system.TimeWidgetView;
import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.apps.systemApps.ApplicationsList;
import ru.cubos.server.system.apps.systemApps.desktopWidgets.StatusBarDesktopWidget;
import ru.cubos.server.system.events.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.cubos.server.helpers.ByteConverter.uByte;
import static ru.cubos.connectors.Protocol.*;

public class Server {

    public Connector connector;
    public Display display;
    public Settings settings;
    public StatusBarDesktopWidget statusBar;
    public ButtonBar buttonBar;
    public TimeWidgetView timeWidgetView;
    public List<App> openedApps;

    private boolean repaintPending;

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
        //openedApps.add(new TestingApp(this));

        timeWidgetView = new TimeWidgetView();

        try {
            backGroundImage = new BinaryImage_24bit("images//bg_640x480.png");
        } catch (IOException e) {
            backGroundImage = null;
        }
    }


    public void start() {
        System.out.println("Server: Server started");
        drawApps();
        drawBars();

        sendFrameBufferCommands();
    }


    public void drawApps(){
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
            connector.OnDataGotFromServer(message);
            Profiler.addCount("Received data", message.length);
            Profiler.showCountAccumulators();

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

        Profiler.addCount("Transmit data", data.length);
        //Profiler.showCountAccumulators();

        //System.out.println("Server: received " + data.length + " bytes");

        char x_start, y_start;
        char x0, y0, x1, y1, r, g, b;
        int current_position = 0;

        while(current_position<data.length) {

            int restLength = data.length - current_position;
            switch (data[current_position]) {

                case _1_1_EVENT_TOUCH_TAP:
                    //System.out.println("Emulator client: drawing rectangle command");
                    if(restLength<5){
                        current_position += 5;
                        break;
                    }

                    x0 = ByteConverter.bytesToChar(uByte(data[current_position + 1]), uByte(data[current_position + 2]));
                    y0 = ByteConverter.bytesToChar(uByte(data[current_position + 3]), uByte(data[current_position + 4]));

                    //System.out.println("Server: on screen tap " + (int)x0 + ", " + (int)y0);
                    getActiveApp().execEvent(new TouchTapEvent(x0, y0));

                    current_position += 5;

                    break;
                case _1_2_EVENT_TOUCH_UP:
                    if(restLength<5){
                        current_position += 5;
                        break;
                    }

                    x0      = ByteConverter.bytesToChar(uByte(data[current_position + 1]),  uByte(data[current_position + 2]));
                    y0      = ByteConverter.bytesToChar(uByte(data[current_position + 3]),  uByte(data[current_position + 4]));
                    current_position += 5;

                    //System.out.println("Server: on screen mouse up");
                    getActiveApp().execEvent(new TouchUpEvent(x0, y0));
                    break;
                case _1_3_EVENT_TOUCH_DOWN:
                    if(restLength<5){
                        current_position += 5;
                        break;
                    }

                    x0      = ByteConverter.bytesToChar(uByte(data[current_position + 1]),  uByte(data[current_position + 2]));
                    y0      = ByteConverter.bytesToChar(uByte(data[current_position + 3]),  uByte(data[current_position + 4]));
                    current_position += 5;

                    //System.out.println("Server: on screen mouse down");
                    //System.out.println("Server: on screen down " + (int)x0 + ", " + (int)y0);
                    for (App app: openedApps) app.setMoving(false);
                    for (App app: openedApps) app.setResizing(false);
                    activateAppByCoordinates(x0, y0);
                    getActiveApp().execEvent(new TouchDownEvent(x0, y0));
                    break;
                case _1_4_EVENT_TOUCH_MOVE:
                    if(restLength<13){
                        current_position += 13;
                        break;
                    }

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
                case _1_5_EVENT_TOUCH_MOVE_FINISHED:
                    if(restLength<9){
                        current_position += 9;
                        break;
                    }

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
                    current_position += 1;
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
        // TODO: Remove this in future, should checking in loop
        //long start = System.currentTimeMillis();
        drawApps();
        //long timeConsumedMillis = System.currentTimeMillis() - start;
        //System.out.println("Repaint time: " + timeConsumedMillis + " ms");

        sendFrameBufferCommands();

    }

    public void cancelRepaintPending() {
        this.repaintPending = false;
    }
}