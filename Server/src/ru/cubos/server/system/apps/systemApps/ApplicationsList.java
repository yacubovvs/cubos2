package ru.cubos.server.system.apps.systemApps;

import ru.cubos.server.Server;
import ru.cubos.server.helpers.Colors;
import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.views.IconView;
import ru.cubos.server.system.views.TextView;
import ru.cubos.server.system.views.View;
import ru.cubos.server.system.views.containers.TabelContainer;
import ru.cubos.server.system.views.containers.VerticalContainer;

public class ApplicationsList extends App {
    TabelContainer tabelContainer;

    public ApplicationsList(Server server) {
        super(server);

        setWindowTitle("Main menu");

        getBaseContainer().setBackgroundColor(new byte[]{-96, -96, -96});

        setLeftOffset(30);
        setRightOffset(80);
        setTopOffset(120);
        setBottomOffset(30);

        tabelContainer = new TabelContainer(TabelContainer.TableType.FIXED_ROWS,5);
        tabelContainer.setMargin(15);

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

    public void addIcon(String name, String iconPath){
        IconView appIcon = new IconView(iconPath, Colors.COLOR_ALFA);
        appIcon.setHorizontalAlign(View.HorizontalAlign.ALIGN_HORIZONTAL_CENTER);

        TextView appName = new TextView(name);
        appName.setHorizontalAlign(View.HorizontalAlign.ALIGN_HORIZONTAL_CENTER);
        appName.setPadding(10);
        appName.setPaddingBottom(20);
        appName.setPaddingTop(10);


        VerticalContainer appContainer = new VerticalContainer();

        appContainer.setId(name);
        appContainer.setOnTouchTapListener(() -> {
            System.out.println("OnClick listener ID: " + name);
        }, this);

        appContainer.add(appIcon);
        appContainer.add(appName);
        tabelContainer.add(appContainer);
    }
}
