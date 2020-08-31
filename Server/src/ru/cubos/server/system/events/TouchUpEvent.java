package ru.cubos.server.system.events;

import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.views.View;

import java.util.List;

public class TouchUpEvent extends EventTouch {

    @Override
    public void runViewEvent(View view) {
        view.getTouchUpListener().onTouchUp(view, this);
    }

    public TouchUpEvent(int x, int y){
        super(x,y);
        this.setType(Type.EVENT_TOUCH_UP);
    }

    @Override
    public void executeViewsHandlers(List<View> viewList, App app) {
        if(!isInWindowEvent(app)) return;
        checkViewsForEvent(viewList);
    }

}
