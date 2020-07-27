package ru.cubos.server.system.events;

import ru.cubos.server.system.views.View;

import java.util.List;

public class ClickEvent extends Event{
    private int x = 0;
    private int y = 0;

    public ClickEvent(int x, int y){
        super();
        this.setType(Type.CLICK);
        setX(x);
        setY(y);
    }

    @Override
    public void executeViewsHandlers(List<View> viewList) {

        for(View view: viewList){
            if(
                    view.getOnClickListener()!=null &&
                    view.isVisible() &&
                    view.getRenderX() <= x &&
                    view.getRenderY() <= y &&
                    view.getRenderX() + view.getWidth() > x &&
                    view.getRenderY() + view.getHeight() > y
            ){
                view.getOnClickListener().run();
            }
        }
        return;
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
