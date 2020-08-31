package ru.cubos.server.system.views;

import ru.cubos.commonHelpers.Colors;
import ru.cubos.server.system.events.TouchTapEvent;
import ru.cubos.server.system.views.containers.VerticalContainer;
import ru.cubos.server.system.views.viewListeners.TouchTapListener;

public class DesktopIconView extends VerticalContainer {
    public DesktopIconView(String name, String iconPath){
        IconView appIcon = new IconView(iconPath, Colors.COLOR_ALFA);
        appIcon.setHorizontalAlign(View.HorizontalAlign.ALIGN_HORIZONTAL_CENTER);

        TextView appName = new TextView(name);
        appName.setHorizontalAlign(View.HorizontalAlign.ALIGN_HORIZONTAL_CENTER);
        appName.setPadding(7);
        appName.setPaddingBottom(7);
        appName.setPaddingTop(5);

        this.setId(name);

        appIcon.setMarginTop(5);

        this.add(appIcon);
        this.add(appName);

        this.setMargin(5);
    }
}
