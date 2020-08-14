package ru.cubos.server.system.apps.customApps;

import ru.cubos.server.Server;
import ru.cubos.commonHelpers.Colors;
import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.views.TextView;
import ru.cubos.server.system.views.containers.TabelContainer;

public class TestingApp extends App {
    TabelContainer tabelContainer;

    public TestingApp(Server server){
        super(server);

        setWindowTitle("Testing app");

        setLeftOffset(30);
        setRightOffset(30);
        setTopOffset(40);
        setBottomOffset(40);

        getBaseContainer().setBackgroundColor(Colors.COLOR_DARK_GRAY);
        //getBaseContainer().setBackgroundColor(Colors.COLOR_GREEN);

        TextView textView = new TextView("Testing Window");
        //IconView iconView = new IconView("images//tricon.png", true);

        addView(textView);
        //addView(iconView);

    }

}
