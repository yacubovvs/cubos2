package ru.cubos.server;

import ru.cubos.connectors.Connectorable;
import ru.cubos.commonHelpers.binaryImages.BinaryImage_24bit;
import ru.cubos.commonHelpers.Colors;
import ru.cubos.server.framebuffer.Display;
import ru.cubos.server.settings.Settings;
import ru.cubos.server.system.ButtonBar;
import ru.cubos.server.system.TimeWidgetView;
import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.apps.customApps.TestingApp;
import ru.cubos.server.system.apps.systemApps.ApplicationsList;
import ru.cubos.server.system.apps.systemApps.desktopWidgets.StatusBarDesktopWidget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.cubos.commonHelpers.Protocol.*;

public class Server {

    public Connectorable connector;
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
    public ServerCommandsDecoder serverCommandsDecoder;

    public Server(Connectorable connector, Settings initing_settings) {
        this.connector = connector;
        serverCommandsDecoder = new ServerCommandsDecoder(this);
        if(initing_settings==null){
            settings = new Settings();
        } else {
            settings = initing_settings;
        }

        // TODO: delete this string later:
        display = new Display(settings.getSystemScreenWidth(), settings.getSystemScreenHeight());

        if(settings.isStatusBarEnable()) statusBar = new StatusBarDesktopWidget(this);
        buttonBar = new ButtonBar(this);
        openedApps = new ArrayList<>();

        if(settings.isStatusBarEnable()) openedApps.add(statusBar);
        //openedApps.add(new ApplicationsList(this));
        //openedApps.add(new TestingApp(this));

        timeWidgetView = new TimeWidgetView();

        try {
            backGroundImage = new BinaryImage_24bit("images//bg_640x480.png");
        } catch (IOException e) {
            backGroundImage = null;
        }

        Thread displayThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    updateAndSendFrameBuffer();
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        displayThread.start();
    }

    public Server(Connectorable connector) {
        this(connector, null);
    }


    public void start() {
        display = new Display(settings.getSystemScreenWidth(), settings.getSystemScreenHeight());
        display.setColorScheme(settings.getSystemScreenColorScheme());
        System.out.println("Server: Server started");
        drawApps();
        drawBars();

        connector.switchToMode(_0_2_DRAW_MODE);

        sendFrameBuffer();
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
        if (settings.isStatusBarEnable() && statusBar.isRepaintPending()) statusBar.draw();
        if (buttonBar.isRepaintPending()) buttonBar.paint();
    }

    public void sendFrameBuffer() {
        connector.updateScreen(display);
    }

    /*
    public boolean transmitData(byte[] data) {
        if(data.length==0){
            return true; // No data
        }

        //Profiler.addCount("Transmit data", data.length);
        //Profiler.showCountAccumulators();

        //System.out.println("Server: received " + data.length + " bytes");

        serverCommandsDecoder.decodeCommands(data, true, 0);

        return true;
    }
    */

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
                if(settings.isStatusBarEnable()) activateApp(statusBar);
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
        app.draw();
        openedApps.add(app);
        app.onFocusGot();
        setRepaintPending();
    }

    public void setRepaintPending() {
        this.repaintPending = true;
        //updateAndSendFrameBuffer();
    }

    public void updateAndSendFrameBuffer(){

        if(this.repaintPending) {
            //long start = System.currentTimeMillis();
            drawApps();
            //long timeConsumedMillis = System.currentTimeMillis() - start;
            //System.out.println("Repaint time: " + timeConsumedMillis + " ms");

            //Profiler.start("sendFrameBufferCommands");
            sendFrameBuffer();
            //Profiler.point("sendFrameBufferCommands");
            //Profiler.showSumTimers();
            this.repaintPending = false;
        }
    }

    public void cancelRepaintPending() {
        this.repaintPending = false;
    }
}