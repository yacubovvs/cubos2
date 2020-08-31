package ru.cubos.server.system.views.viewListeners;

import ru.cubos.server.system.events.TouchUpEvent;
import ru.cubos.server.system.views.View;

public abstract class ActivationListener extends viewListener {
    public abstract void activate(View view);
    public abstract void deactivate(View view);
}
