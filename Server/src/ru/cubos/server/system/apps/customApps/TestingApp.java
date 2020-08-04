package ru.cubos.server.system.apps.customApps;

import ru.cubos.server.Server;
import ru.cubos.server.helpers.Colors;
import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.views.IconView;
import ru.cubos.server.system.views.TextView;
import ru.cubos.server.system.views.View;
import ru.cubos.server.system.views.containers.TabelContainer;
import ru.cubos.server.system.views.containers.VerticalContainer;

public class TestingApp extends App {
    TabelContainer tabelContainer;

    public TestingApp(Server server){
        super(server);

        setWindowTitle("Testing app");

        setLeftOffset(50);
        setRightOffset(50);
        setTopOffset(100);
        setBottomOffset(50);

        getBaseContainer().setBackgroundColor(Colors.COLOR_DARK_GRAY);
        //getBaseContainer().setBackgroundColor(Colors.COLOR_GREEN);

        TextView textView = new TextView("Testing Window");
        //IconView iconView = new IconView("images//tricon.png", true);

        addView(textView);
        //addView(iconView);

    }

}
