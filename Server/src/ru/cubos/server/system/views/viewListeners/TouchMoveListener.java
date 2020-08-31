package ru.cubos.server.system.views.viewListeners;

import ru.cubos.server.system.events.TouchMoveEvent;
import ru.cubos.server.system.events.TouchTapEvent;
import ru.cubos.server.system.views.View;

public abstract class TouchMoveListener extends viewListener {
    public abstract void onTouchMove(View view, TouchMoveEvent touchMoveEvent);
}
