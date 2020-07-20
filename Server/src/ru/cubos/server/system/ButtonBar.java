package ru.cubos.server.system;

import ru.cubos.server.Server;
import ru.cubos.server.helpers.BinaryImage;
import ru.cubos.server.helpers.Colors;

import java.io.IOException;

public class ButtonBar {
    Server server;

    private boolean repaintPending = true;

    public ButtonBar(Server server){
        this.server = server;
    }

    public void paint(){
        if (server.settings.isStatusBarEnable()){
            server.display.drawRect(0,server.display.getHeight(), server.display.getWidth(), server.display.getHeight() - server.settings.getStatusBarHeight(), server.settings.getStatusBarColor(), true);
            String timeString = server.time.getCurrentTime();

            // # # # # # # Time # # # # # #

            // Central button
            int x_button2 = server.display.getWidth()/2 - server.settings.getButtonBarButtonSize()/2;
            int y_button2 = server.display.getHeight() - server.settings.getButtonBarHeight() + (server.settings.getButtonBarHeight()-server.settings.getButtonBarButtonSize())/2;

            // Left button
            int x_button1 = server.display.getWidth()/2 - server.settings.getButtonBarButtonSize()/2 - server.settings.getButtonBarButtonMargin();
            int y_button1 = server.display.getHeight() - server.settings.getButtonBarHeight() + (server.settings.getButtonBarHeight()-server.settings.getButtonBarButtonSize())/2;

            // Right button
            int x_button3 = server.display.getWidth()/2 - server.settings.getButtonBarButtonSize()/2 + server.settings.getButtonBarButtonMargin();
            int y_button3 = server.display.getHeight() - server.settings.getButtonBarHeight() + (server.settings.getButtonBarHeight()-server.settings.getButtonBarButtonSize())/2;

            server.display.drawRect_custom(x_button1, y_button1 + server.settings.getButtonBarButtonSize()/2, x_button1, y_button1 + server.settings.getButtonBarButtonSize()/2, x_button1 + server.settings.getButtonBarButtonSize(), y_button1,x_button1 + server.settings.getButtonBarButtonSize(), y_button1 + server.settings.getButtonBarButtonSize(), server.settings.getButtonBarButtonsColor(),true);
            server.display.drawRect(x_button2, y_button2, x_button2 + server.settings.getButtonBarButtonSize(), y_button2 + server.settings.getButtonBarButtonSize(), server.settings.getButtonBarButtonsColor(),true);
            server.display.drawRect_custom(x_button3, y_button3, x_button3, y_button3, x_button3, y_button3 + server.settings.getButtonBarButtonSize(),x_button3 + server.settings.getButtonBarButtonSize(), y_button3 + server.settings.getButtonBarButtonSize()/2, server.settings.getButtonBarButtonsColor(),true);



            //server.display.drawString(time_x, time_y, timeString, server.settings.getStatusBarTextColor(), server.settings.getStatusBarTextSize());

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
