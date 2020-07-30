package ru.cubos.server.system.events;

import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.views.View;

import java.util.List;

public abstract class EventTouch extends Event {
    protected int x = 0;
    protected int y = 0;

    public boolean isInWindowEvent(App app){
        if(
                y > app.getTopOffset()
                        && y < app.getBottomOffset() + app.getWindowHeight()
                        && x > app.getLeftOffset()
                        && x < app.getRightOffset() + app.getWindowWidth()
        ) return true;
        else return false;
    }

    public void checkViewsForEvent(List<View> viewList){
        for(View view: viewList){
            if(
                    view.getOnTouchTapListener()!=null &&
                            view.isVisible() &&
                            view.getRenderX() <= x &&
                            view.getRenderY() <= y &&
                            view.getRenderX() + view.getWidth() > x &&
                            view.getRenderY() + view.getHeight() > y
            ){
                if(isPreventEvent()) break;
                runViewEvent(view);
            }
        }
    }

    public abstract void runViewEvent(View view);

    public EventTouch(int x, int y){
        super();
        setX(x);
        setY(y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
