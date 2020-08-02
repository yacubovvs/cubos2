package ru.cubos.server.system.views.viewElements;

import ru.cubos.server.Server;
import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.views.IconView;
import ru.cubos.server.system.views.TextView;
import ru.cubos.server.system.views.View;
import ru.cubos.server.system.views.containers.HorizontalContainer;

public class WindowTitleBar extends HorizontalContainer {
    private App app;

    public WindowTitleBar(App app){
        super();

        this.app = app;
        Server server = getApp().getServer();

        setHeight(server.settings.getWindowTitleBarHeight());
        setHorizontalScrollDisable();
        setVerticalScrollDisable();

        IconView close_button       = new IconView("images//icons//close_button.png");
        close_button.setMargin((server.settings.getWindowTitleBarHeight() - close_button.getIcon().getHeight())/2);
        close_button.setWidth_source(View.SizeSource.SIZE_SOURCE_CONTENT);

        IconView fullscreen_button  = new IconView("images//icons//fullscreen_button.png");
        fullscreen_button.setMargin((server.settings.getWindowTitleBarHeight() - fullscreen_button.getIcon().getHeight())/2);
        fullscreen_button.setWidth_source(View.SizeSource.SIZE_SOURCE_CONTENT);

        IconView rollup_button      = new IconView("images//icons//rollup_button.png");
        rollup_button.setMargin((server.settings.getWindowTitleBarHeight() - rollup_button.getIcon().getHeight())/2);
        rollup_button.setWidth_source(View.SizeSource.SIZE_SOURCE_CONTENT);


        add(close_button);
        add(fullscreen_button);
        add(rollup_button);
        setBackgroundColor(server.settings.getWindowTitleColor());

        TextView title = new TextView(app.getWindowTitle());
        add(title);
        setAppParent(app);
    }

    @Override
    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }
}
