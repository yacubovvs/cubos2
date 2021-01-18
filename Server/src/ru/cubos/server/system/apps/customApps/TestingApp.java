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

        if(server.settings.isWindowMode()) {
            setLeftOffset(30);
            setRightOffset(30);
            setTopOffset(80);
            setBottomOffset(40);
        }else{
            setLeftOffset(0);
            setRightOffset(0);
            setTopOffset(getServer().settings.getStatusBarHeight());
            setBottomOffset(0);
        }

        getBaseContainer().setBackgroundColor(Colors.COLOR_DARK_GRAY);
        //getBaseContainer().setBackgroundColor(Colors.COLOR_GREEN);

        TextView textView = new TextView("Testing Window");
        //IconView iconView = new IconView("images//tricon.png", true);

        addView(textView);
        //addView(iconView);

    }

}
