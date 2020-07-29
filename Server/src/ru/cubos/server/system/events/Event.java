package ru.cubos.server.system.events;

import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.views.View;

import java.util.List;

public abstract class Event{
    private Type type;

    public enum Type{
        EVENT_TOUCH_TAP,
        EVENT_TOUCH_UP,
        EVENT_TOUCH_DOWN,
        EVENT_TOUCH_MOVE,
        EVENT_TOUCH_MOVE_FINISHED,
        EVENT_TOUCH_ZOOM_IN,
        EVENT_TOUCH_ZOOM_OUT,
        EVENT_TOUCH_LONG,
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public abstract void executeViewsHandlers(List<View> viewList, App app);

}
