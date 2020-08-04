package ru.cubos.server.system.views.viewListeners;

import ru.cubos.server.system.events.TouchDownEvent;
import ru.cubos.server.system.events.TouchTapEvent;

public abstract class TouchDownListener extends viewListener {
    public abstract void onTouchDown(TouchDownEvent touchDownEvent);
}
