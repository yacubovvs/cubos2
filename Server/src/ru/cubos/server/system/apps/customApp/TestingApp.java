package ru.cubos.server.system.apps.customApp;

import ru.cubos.server.Server;
import ru.cubos.server.helpers.Colors;
import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.views.IconView;
import ru.cubos.server.system.views.TextView;
import ru.cubos.server.system.views.View;
import ru.cubos.server.system.views.containers.HorizontalContainer;
import ru.cubos.server.system.views.containers.VerticalContainer;

import java.io.IOException;

public class TestingApp extends App {
    public TestingApp(Server server){
        super(server);

        HorizontalContainer container = new HorizontalContainer();
        VerticalContainer container2 = new VerticalContainer();

        TextView textView = new TextView("Long long multi line string for few lines testing and any font size");
        textView.setBackgroundColor(Colors.COLOR_BLUE);
        textView.setMargin(5);
        TextView textView2 = new TextView("Long long multi line string for few lines testing and any font size");
        TextView textView3 = new TextView("Long long multi line string for few lines testing and any font size");

        TextView textView4 = new TextView("Long long multi line string for few lines testing and any font size");
        TextView textView5 = new TextView("Long long multi line string for few lines testing and any font size");

        container.add(textView);
        container.setBackgroundColor(Colors.COLOR_DARK_RED);
        container.add(textView2);
        container.add(textView3);

        container2.add(textView4);
        container2.add(textView5);

        addView(container);
        //addView(container2);
    }
}
