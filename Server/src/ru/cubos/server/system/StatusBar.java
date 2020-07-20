package ru.cubos.server.system;

import ru.cubos.server.Server;
import ru.cubos.server.helpers.framebuffer.Display;

public class StatusBar {
    Server server;

    private boolean repaintPending = true;

    public StatusBar(Server server){
        this.server = server;
    }

    public void paint(){
        if (server.settings.isStatusBarEnable()){
            server.display.drawRect(0,0, server.display.getWidth(), server.settings.getStatusBarHeight(), server.settings.getStatusBarColor(), true);
            String timeString = server.time.getCurrentTime();

            //int time_x = server.display.getWidth() - server.settings.getStatusTextSize()*server.settings.getSystemCharWidth()*timeString.trim().length() - server.settings.getStatusLeftRightMargin();
            int time_x = server.settings.getStatusLeftRightMargin();
            int time_y = server.settings.getStatusBarHeight()/2 - server.settings.getStatusTextSize()*server.settings.getSystemCharHeight()/2;

            server.display.drawString(time_x, time_y, timeString, server.settings.getStatusTextColor(), server.settings.getStatusTextSize());
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
