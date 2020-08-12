package ru.cubos.server;

import ru.cubos.commonHelpers.profiler.Profiler;
import ru.cubos.connectors.Connector;
import ru.cubos.server.helpers.binaryImages.BinaryImage_24bit;
import ru.cubos.server.helpers.Colors;
import ru.cubos.server.helpers.framebuffer.Display;
import ru.cubos.server.settings.Settings;
import ru.cubos.server.system.ButtonBar;
import ru.cubos.server.system.TimeWidgetView;
import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.apps.systemApps.ApplicationsList;
import ru.cubos.server.system.apps.systemApps.desktopWidgets.StatusBarDesktopWidget;

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
    public byte currentMode = _0_1_OPTIONS_MODE;

    private boolean repaintPending;

    public App getActiveApp(){
        return openedApps.get(openedApps.size()-1);
    }

    private BinaryImage_24bit backGroundImage;
    private ServerCommandsDecoder serverCommandsDecoder;


    public Server(Connector connector) {
        this.connector = connector;
        serverCommandsDecoder = new ServerCommandsDecoder(this);
        settings = new Settings();

        // TODO: delete this string later:
        display = new Display(settings.getSystemScreenWidth(), settings.getSystemScreenHeight());

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
        display = new Display(settings.getSystemScreenWidth(), settings.getSystemScreenHeight());
        System.out.println("Server: Server started");
        drawApps();
        drawBars();

        byte message[] = new byte[]{
                _0_MODE_OPTION,                         // Switch mode
                _0_2_DRAW_MODE,                      // Switch to COMMON MODE 1
        };

        connector.OnDataGotFromServer(message);

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

        serverCommandsDecoder.decodeCommands(data);

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
                        // TODO: Do not making deactivation main menu in not window mode
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