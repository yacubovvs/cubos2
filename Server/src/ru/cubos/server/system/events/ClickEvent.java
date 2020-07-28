package ru.cubos.server.system.events;

import ru.cubos.server.system.apps.App;
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
    public void executeViewsHandlers(List<View> viewList, App app) {

        if(app.getBaseContainer().isVerticalScrollEnable()){
            //Check click on vertical scrollbar
            if(
                    x>app.getServer().display.getWidth() - app.getBaseContainer().getVerticalScroll().getActionWidth()
                    && y > app.getBaseContainer().getVerticalScroll().getTopOffset()
                    && y < app.getServer().display.getHeight() - app.getBaseContainer().getVerticalScroll().getBottomOffset()
            ){
                // is on scrollbar click
                app.getBaseContainer().getVerticalScroll().onClick(y);
                return;
            }
        }else if(app.getBaseContainer().isHorizontalScrollEnable()){
            //Check click on horizontal scrollbar
            if(
                    y>app.getServer().display.getHeight() - app.getBaseContainer().getVerticalScroll().getActionWidth()
                    && x > app.getBaseContainer().getVerticalScroll().getLeftOffset()
                    && x < app.getServer().display.getWidth() - app.getBaseContainer().getVerticalScroll().getRightOffset()
            ){
                // is on scrollbar click
                app.getBaseContainer().getHorizontalScroll().onClick(x);
                return;
            }
        }

        // Check clicks on elements
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
