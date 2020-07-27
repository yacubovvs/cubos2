package ru.cubos.server.system.events;

import ru.cubos.server.system.views.View;

import java.util.List;

public abstract class Event{
    private Type type;

    public enum Type{
        CLICK
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public abstract void executeViewsHandlers(List<View> viewList);

}
