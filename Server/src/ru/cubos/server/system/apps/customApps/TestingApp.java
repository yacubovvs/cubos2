package ru.cubos.server.system.apps.customApps;

import ru.cubos.server.Server;
import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.views.IconView;
import ru.cubos.server.system.views.TextView;
import ru.cubos.server.system.views.View;
import ru.cubos.server.system.views.containers.TabelContainer;
import ru.cubos.server.system.views.containers.VerticalContainer;

import java.io.IOException;

public class TestingApp extends App {
    public TestingApp(Server server){
        super(server);

        TabelContainer tabelContainer = new TabelContainer(TabelContainer.TableType.FIXED_ROWS,3);
        tabelContainer.setMarginTop(15);

        for(int i=0; i<8; i++){

            IconView appIcon = new IconView("images//icons//testicon.png");
            appIcon.setHorizontalAlign(View.HorizontalAlign.ALIGN_HORIZONTAL_CENTER);

            TextView appName = new TextView("Application name");
            appName.setHorizontalAlign(View.HorizontalAlign.ALIGN_HORIZONTAL_CENTER);
            appName.setPadding(10);
            appName.setPaddingBottom(20);
            appName.setPaddingTop(10);


            VerticalContainer appContainer = new VerticalContainer();



            appContainer.add(appIcon);
            appContainer.add(appName);
            tabelContainer.add(appContainer);
        }

        addView(tabelContainer);

        //IconView iconView = new IconView("images//icons//testicon.png");

    }
}
