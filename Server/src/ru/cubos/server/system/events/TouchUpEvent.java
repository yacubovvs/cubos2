package ru.cubos.server.system.events;

import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.views.View;

import java.util.List;

public class TouchUpEvent extends Event{
    private int x = 0;
    private int y = 0;

    public TouchUpEvent(int x, int y){
        super();
        this.setType(Type.EVENT_TOUCH_DOWN);
        setX(x);
        setY(y);
    }

    @Override
    public void executeViewsHandlers(List<View> viewList, App app) {

        // Check clicks on elements
        for(View view: viewList){
            if(
                    view.getOnTouchDownListener()!=null &&
                            view.isVisible() &&
                            view.getRenderX() <= x &&
                            view.getRenderY() <= y &&
                            view.getRenderX() + view.getWidth() > x &&
                            view.getRenderY() + view.getHeight() > y
            ){
                //view.getOnClickListener().run();
            }
        }


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
