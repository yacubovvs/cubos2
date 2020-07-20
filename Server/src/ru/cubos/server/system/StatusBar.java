package ru.cubos.server.system;

import ru.cubos.server.Server;
import ru.cubos.server.helpers.BinaryImage;
import ru.cubos.server.helpers.Colors;

import java.io.IOException;

public class StatusBar {
    Server server;
    private BinaryImage batteryIndicator;

    private boolean repaintPending = true;

    public StatusBar(Server server){
        this.server = server;
        try {
            this.batteryIndicator = new BinaryImage("images//icons//battery_x2_100.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(){
        if (server.settings.isStatusBarEnable()){
            server.display.drawRect(0,0, server.display.getWidth(), server.settings.getStatusBarHeight(), server.settings.getStatusBarColor(), true);
            String timeString = server.time.getCurrentTime();

            // # # # # # # Time # # # # # #
            //int time_x = server.display.getWidth() - server.settings.getStatusTextSize()*server.settings.getSystemCharWidth()*timeString.trim().length() - server.settings.getStatusLeftRightMargin();
            int time_x = server.settings.getStatusBarLeftRightMargin();
            int time_y = server.settings.getStatusBarHeight()/2 - server.settings.getStatusBarTextSize()*server.settings.getSystemCharHeight()/2;
            server.display.drawString(time_x, time_y, timeString, server.settings.getStatusBarTextColor(), server.settings.getStatusBarTextSize());

            // # # # # # # Battery # # # # # #
            int battery_x = server.display.getWidth() - server.settings.getStatusBarLeftRightMargin() - batteryIndicator.getWidth();
            int battery_y = server.settings.getStatusBarHeight()/2 - batteryIndicator.getHeight()/2;

            server.display.drawImage(battery_x, battery_y, batteryIndicator, Colors.COLOR_WHITE);


        }

        setRepaintPending(false);
    }

    /////////////////////////////////////////////////////////////////

    public boolean isRepaintPending() {
        return repaintPending;
    }

    public void setRepaintPending(boolean repaintPending) {
        this.repaintPending = repaintPending;
    }
}
