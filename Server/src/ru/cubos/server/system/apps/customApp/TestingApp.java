package ru.cubos.server.system.apps.customApp;

import ru.cubos.server.Server;
import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.views.TextView;

public class TestingApp extends App {
    public TestingApp(Server server){
        super(server);

        TextView textView = new TextView("Test");
        TextView textView2 = new TextView("Testing string 2");
        addView(textView);
        addView(textView2);
    }
}
