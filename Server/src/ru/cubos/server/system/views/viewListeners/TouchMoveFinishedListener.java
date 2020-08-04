package ru.cubos.server.system.views.viewListeners;

import ru.cubos.server.system.events.TouchMoveEvent;
import ru.cubos.server.system.events.TouchMoveFinishedEvent;
import ru.cubos.server.system.events.TouchTapEvent;

public abstract class TouchMoveFinishedListener extends viewListener {
    public abstract void onTouchMoveFinished(TouchMoveFinishedEvent touchMoveEvent);
}
