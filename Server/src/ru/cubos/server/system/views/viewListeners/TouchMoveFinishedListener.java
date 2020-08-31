package ru.cubos.server.system.views.viewListeners;

import ru.cubos.server.system.events.TouchMoveEvent;
import ru.cubos.server.system.events.TouchMoveFinishedEvent;
import ru.cubos.server.system.events.TouchTapEvent;
import ru.cubos.server.system.views.View;

public abstract class TouchMoveFinishedListener extends viewListener {
    public abstract void onTouchMoveFinished(View view, TouchMoveFinishedEvent touchMoveEvent);
}
