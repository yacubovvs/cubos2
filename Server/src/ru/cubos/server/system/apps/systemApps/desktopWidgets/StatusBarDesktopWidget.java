package ru.cubos.server.system.apps.systemApps.desktopWidgets;

import ru.cubos.server.Server;
import ru.cubos.server.helpers.Colors;
import ru.cubos.server.system.views.IconView;
import ru.cubos.server.system.views.TextView;
import ru.cubos.server.system.views.containers.LinearContainer;

public class StatusBarDesktopWidget extends DesktopWidget {
    public StatusBarDesktopWidget(Server server) {
        super(server);
        setWindowMode(false);

        getBaseContainer().setBackgroundColor(Colors.COLOR_LIGHT_GRAY);
        getBaseContainer().setType(LinearContainer.Type.HORIZONTAL);
        getBaseContainer().setHeight(getSettings().getStatusBarHeight());
        IconView iconView = new IconView("images//icons//startButton.png", Colors.COLOR_ALFA);
        iconView.setMargin((getSettings().getStatusBarHeight() - iconView.getIcon().getHeight())/2);

        iconView.setOnTouchDownListener(new Runnable() {
            @Override
            public void run() {
                System.out.println("On menu click");
            }
        }, this);

        setTopOffset(0);
        setBottomOffset(getDisplayHeight() - getSettings().getStatusBarHeight());
        setLeftOffset(0);
        setRightOffset(0);

        addView(iconView);
    }

    public int[] drawRenderedImage(){
        int renderSize[] = getServer().display.drawImage(
                getLeftOffset() - getWindowBorderWidth(),
                getTopOffset() - getWindowTitleBarHeight() - getWindowBorderWidth(),
                getDisplayWidth(),
                getServer().settings.getStatusBarHeight(),//getDisplayHeight() + 1 - getTopOffset() + getWindowTitleBarHeight() + getWindowBorderWidth() - getSettings().getButtonBarHeight(),
                0,
                0,
                renderImage, null);

        return renderSize;
    }

    /*
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
            String timeString = server.timeWidgetView.getCurrentTime();

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
    * */
}
