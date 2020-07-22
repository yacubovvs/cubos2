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

        TextView textView = new TextView("Long long multi line string for few lines testing with any font size");
        textView.setLineSpacing(5);
        textView.setFontSize(1);
        textView.setMargin(10);
        textView.setTextColor(Colors.COLOR_WHITE);
        textView.setMultiString(true);
        textView.setHorizontalAlign(View.ALIGN_HORIZONTAL_LEFT);

        TextView textView2 = new TextView("Testing long long string for multi string for TextView with many words");
        textView2.setPadding(0);
        textView2.setMargin(10);
        textView2.setTextColor(Colors.COLOR_WHITE);
        textView2.setMultiString(true);
        textView2.setHorizontalAlign(View.ALIGN_HORIZONTAL_RIGHT);

        TextView textView3 = new TextView("Testing long long string for multi string for TextView with many words");
        textView3.setPadding(0);
        textView3.setMargin(10);
        textView3.setTextColor(Colors.COLOR_WHITE);
        textView3.setMultiString(true);
        textView3.setHorizontalAlign(View.ALIGN_HORIZONTAL_CENTER);

        TextView textView4 = new TextView("Testing long long string for multi string for TextView with many words");
        textView4.setPadding(0);
        textView4.setMargin(10);
        textView4.setTextColor(Colors.COLOR_WHITE);
        textView4.setMultiString(true);
        textView4.setHorizontalAlign(View.ALIGN_HORIZONTAL_LEFT);



        TextView textView5 = new TextView("Testing not long string");
        textView2.setPadding(0);
        textView2.setMargin(10);
        textView2.setTextColor(Colors.COLOR_WHITE);
        textView2.setMultiString(true);
        textView2.setHorizontalAlign(View.ALIGN_HORIZONTAL_RIGHT);

        TextView textView6 = new TextView("Testing not long string");
        textView3.setPadding(0);
        textView3.setMargin(10);
        textView3.setTextColor(Colors.COLOR_WHITE);
        textView3.setMultiString(true);
        textView3.setHorizontalAlign(View.ALIGN_HORIZONTAL_CENTER);

        TextView textView7 = new TextView("Testing not long string");
        textView4.setPadding(0);
        textView4.setMargin(10);
        textView4.setTextColor(Colors.COLOR_WHITE);
        textView4.setMultiString(true);
        textView4.setHorizontalAlign(View.ALIGN_HORIZONTAL_LEFT);

        addView(textView);
        //addView(textView2);
        //addView(textView3);
        //addView(textView4);
        //addView(textView5);
        //addView(textView6);
        //addView(textView7);
    }
}
