package ru.cubos.server.system.apps.systemApps.desktopWidgets;

import ru.cubos.server.Server;
import ru.cubos.server.helpers.Colors;
import ru.cubos.server.system.views.IconView;
import ru.cubos.server.system.views.TextView;
import ru.cubos.server.system.views.View;
import ru.cubos.server.system.views.containers.HorizontalContainer;
import ru.cubos.server.system.views.containers.LinearContainer;

public class MainMenuDesktopWidget extends DesktopWidget {
    public MainMenuDesktopWidget(Server server) {
        super(server);
        setWindowMode(false);

        getBaseContainer().setBackgroundColor(Colors.COLOR_DARK_GRAY);

        IconView iconView = new IconView("images//icons//apps_24//calibration.png", Colors.COLOR_ALFA);
        iconView.setPadding(6);
        iconView.setWidth(iconView.getPaddingLeft() + iconView.getIcon().getWidth());
        TextView textView = new TextView("Calibration");
        textView.setHorizontalAlign(View.HorizontalAlign.ALIGN_HORIZONTAL_LEFT);

        HorizontalContainer appContainer = new HorizontalContainer();
        appContainer.add(iconView);
        appContainer.add(textView);

        addView(appContainer);
        /*
        IconView iconView2 = new IconView("images//icons//filebrowser.png", Colors.COLOR_ALFA);

        IconView iconView3 = new IconView("images//icons//network.png", Colors.COLOR_ALFA);
        IconView iconView4 = new IconView("images//icons//paint.png", Colors.COLOR_ALFA);
        IconView iconView5 = new IconView("images//icons//settings.png", Colors.COLOR_ALFA);

        IconView iconView6 = new IconView("images//icons//terminal.png", Colors.COLOR_ALFA);
        IconView iconView7 = new IconView("images//icons//texteditor.png", Colors.COLOR_ALFA);
        */

        iconView.setOnTouchDownListener(new Runnable() {
            @Override
            public void run() {
                System.out.println("On menu click");
            }
        }, this);

        setTopOffset(getSettings().getStatusBarHeight());
        setBottomOffset(100);
        setLeftOffset(0);
        setRightOffset(getDisplayWidth() - 200);

    }

    /*
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
    }*/


}
