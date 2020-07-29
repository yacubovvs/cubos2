package ru.cubos.server.system.events;

import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.views.View;

import java.util.List;

public class TouchTapEvent extends MouseEvent{

    public TouchTapEvent(int x, int y) {
        super(x, y);
        this.setType(Type.EVENT_TOUCH_TAP);
    }

    @Override
    public void executeViewsHandlers(List<View> viewList, App app) {
        if(
                y > app.getTopOffset()
                && y < app.getBottomOffset() + app.getWindowHeight()
                && x > app.getLeftOffset()
                && x < app.getRightOffset() + app.getWindowWidth()
        ){
            if(app.getBaseContainer().isVerticalScrollEnable()){
                //Check click on vertical scrollbar
                if(
                        x>app.getLeftOffset() + app.getWindowWidth() - app.getBaseContainer().getVerticalScroll().getActionWidth()
                        //&& y > app.getBaseContainer().getVerticalScroll().getTopOffset()
                        //&& y < app.getServer().display.getHeight() - app.getBaseContainer().getVerticalScroll().getBottomOffset()
                ){
                    // is on scrollbar click
                    app.getBaseContainer().getVerticalScroll().onClick(y);
                    return;
                }
            }else if(app.getBaseContainer().isHorizontalScrollEnable()){
                //Check click on horizontal scrollbar
                // TODO: need to write
            }

            // Check clicks on elements
            for (View view : viewList) {
                if (
                        view.getOnTouchTapListener() != null &&
                                view.isVisible() &&
                                view.getRenderX() <= x &&
                                view.getRenderY() <= y &&
                                view.getRenderX() + view.getWidth() > x &&
                                view.getRenderY() + view.getHeight() > y
                ) {
                    view.getOnTouchTapListener().run();
                }
            }
        }
    }


}
