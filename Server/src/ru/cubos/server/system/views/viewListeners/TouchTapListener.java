package ru.cubos.server.system.views.viewListeners;

import ru.cubos.server.system.events.TouchDownEvent;
import ru.cubos.server.system.events.TouchTapEvent;
import ru.cubos.server.system.views.View;

public abstract class TouchTapListener extends viewListener {
    public abstract void onTouchTap(View view, TouchTapEvent touchTapEvent);
}
