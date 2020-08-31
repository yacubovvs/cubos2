package ru.cubos.server.system.views.viewListeners;

import ru.cubos.server.system.events.TouchTapEvent;
import ru.cubos.server.system.events.TouchUpEvent;
import ru.cubos.server.system.views.View;

public abstract class TouchUpListener extends viewListener {
    public abstract void onTouchUp(View view, TouchUpEvent touchUpEvent);
}
