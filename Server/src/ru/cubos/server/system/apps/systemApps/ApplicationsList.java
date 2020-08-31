package ru.cubos.server.system.apps.systemApps;

import ru.cubos.server.Server;
import ru.cubos.commonHelpers.Colors;
import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.events.TouchTapEvent;
import ru.cubos.server.system.views.DesktopIconView;
import ru.cubos.server.system.views.IconView;
import ru.cubos.server.system.views.TextView;
import ru.cubos.server.system.views.View;
import ru.cubos.server.system.views.containers.TabelContainer;
import ru.cubos.server.system.views.containers.VerticalContainer;
import ru.cubos.server.system.views.viewListeners.ActivationListener;
import ru.cubos.server.system.views.viewListeners.TouchTapListener;

public class ApplicationsList extends App {
    TabelContainer tabelContainer;
    ActivationListener activationListener;
    TouchTapListener touchTapListener;

    public ApplicationsList(Server server) {
        super(server);

        activationListener = new ActivationListener() {
            @Override
            public void activate(View view) {
                view.setBackgroundColor(new byte[]{87-128, 0-128, 112-128});
            }

            @Override
            public void deactivate(View view) {
                view.setBackgroundColor(null);
            }
        };

        touchTapListener = new TouchTapListener() {
            @Override
            public void onTouchTap(View view, TouchTapEvent touchTapEvent) {
                ApplicationsList.this.focus(view);
            }
        };

        setWindowTitle("Main menu");

        getBaseContainer().setBackgroundColor(new byte[]{-96, -96, -96});

        setLeftOffset(0);
        setRightOffset(0);
        setTopOffset(getServer().settings.getStatusBarHeight());
        setBottomOffset(0);

        tabelContainer = new TabelContainer(TabelContainer.TableType.FIXED_ROWS,3);
        tabelContainer.setMargin(4);
        tabelContainer.setMarginRight(12);
        tabelContainer.setMarginLeft(6);

        addIcon("Settings" , "images//icons//apps//settings.png");
        addIcon("Network" , "images//icons//apps//network.png");
        addIcon("File browser" , "images//icons//apps//filebrowser.png");
        addIcon("Text editor" , "images//icons//apps//texteditor.png");
        addIcon("Paint" , "images//icons//apps//paint.png");
        addIcon("Server terminal" , "images//icons//apps//terminal.png");
        addIcon("Screen calibration" , "images//icons//apps//calibration.png");

        for(int i=0; i<30; i++){
            addIcon("Application " + i, "images//icons//testicon.png");
        }

        addView(tabelContainer);

        //IconView iconView = new IconView("images//icons//testicon.png");

    }

    private void addIcon(String name, String iconPath){
        DesktopIconView desktopIconView = new DesktopIconView(name , iconPath);
        desktopIconView.setActivationListener(activationListener);
        desktopIconView.setOnTouchTapListener(this, touchTapListener);

        tabelContainer.add(desktopIconView);
    }

}
