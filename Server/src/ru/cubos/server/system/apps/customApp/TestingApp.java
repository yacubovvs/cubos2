package ru.cubos.server.system.apps.customApp;

import ru.cubos.server.Server;
import ru.cubos.server.helpers.Colors;
import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.views.IconView;
import ru.cubos.server.system.views.TextView;
import ru.cubos.server.system.views.View;

import java.io.IOException;

public class TestingApp extends App {
    public TestingApp(Server server){
        super(server);

        TextView textView = new TextView("Testing long long string for multi string for TextView with many words");
        textView.setFontSize(7);
        textView.setMargin(10);
        textView.setTextColor(Colors.COLOR_WHITE);
        textView.setMultiString(false);
        textView.setHorizontalAlign(View.ALIGN_HORIZONTAL_LEFT);

        TextView textView2 = new TextView("Testing not long string");
        textView2.setPadding(0);
        textView2.setMargin(0);
        textView2.setTextColor(Colors.COLOR_WHITE);
        textView2.setMultiString(false);
        textView2.setHorizontalAlign(View.ALIGN_HORIZONTAL_RIGHT);

        TextView textView3 = new TextView("Testing not long string");
        textView3.setPadding(0);
        textView3.setMargin(0);
        textView3.setTextColor(Colors.COLOR_WHITE);
        textView3.setMultiString(false);
        textView3.setHorizontalAlign(View.ALIGN_HORIZONTAL_CENTER);

        TextView textView4 = new TextView("Testing not long string");
        textView4.setPadding(0);
        textView4.setMargin(0);
        textView4.setTextColor(Colors.COLOR_WHITE);
        textView4.setMultiString(false);
        textView4.setHorizontalAlign(View.ALIGN_HORIZONTAL_LEFT);

        addView(textView);
        addView(textView2);
        addView(textView3);
        addView(textView4);
    }
}
