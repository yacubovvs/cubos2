package ru.cubos.server.system.views.viewListeners;

import ru.cubos.server.system.events.TouchMoveEvent;
import ru.cubos.server.system.events.TouchTapEvent;

public abstract class TouchMoveListener extends viewListener {
    public abstract void onTouchMove(TouchMoveEvent touchMoveEvent);
}
