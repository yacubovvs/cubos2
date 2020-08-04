package ru.cubos.server.system.views.viewListeners;

import ru.cubos.server.system.events.TouchTapEvent;
import ru.cubos.server.system.events.TouchUpEvent;

public abstract class TouchUpListener extends viewListener {
    public abstract void onTouchUp(TouchUpEvent touchUpEvent);
}
