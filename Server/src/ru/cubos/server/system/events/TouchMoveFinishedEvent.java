package ru.cubos.server.system.events;

import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.views.View;

import java.util.List;

public class TouchMoveFinishedEvent extends EventTouch {

    int x_start;
    int y_start;

    @Override
    public void runViewEvent(View view) {
        view.getOnTouchDownListener();
    }

    @Override
    public boolean isInWindowEvent(App app){
        if(
            y_start > app.getTopOffset()
            && y_start < app.getDisplayHeight() - app.getBottomOffset()
            && x_start > app.getLeftOffset()
            && x_start < app.getDisplayWidth() - app.getRightOffset()
        ) return true;
        else return false;
    }

    public void checkViewsForEvent(List<View> viewList){
        if(viewList==null) return;
        for(View view: viewList){
            if(
                    view.getOnTouchTapListener()!=null &&
                            view.isVisible() &&
                            view.getRenderX() <= x_start &&
                            view.getRenderY() <= y_start &&
                            view.getRenderX() + view.getWidth() > x_start &&
                            view.getRenderY() + view.getHeight() > y_start
            ){
                if(isPreventEvent()) break;
                runViewEvent(view);
            }
        }
    }

    public TouchMoveFinishedEvent(int x, int y, int x_start, int y_start){
        super(x,y);
        this.setType(Type.EVENT_TOUCH_MOVE_FINISHED);

        this.x_start    = x_start;
        this.y_start    = y_start;
    }

    @Override
    public void executeViewsHandlers(List<View> viewList, App app) {

        if(!isInWindowEvent(app)) return;

        // # # # # # # # # # # # # # # # # # # # SCROLLBARS EVENTS # # # # # # # # # # # # # # # # # # # # # # #
        if(app.getServer().settings.isDragScrollBarEnable()) {
            if (app.getBaseContainer().isVerticalScrollEnable()) {
                //Check touch on vertical scrollbar
                if (x_start > app.getLeftOffset() + app.getWindowWidth() - app.getBaseContainer().getVerticalScroll().getActionWidth()) {
                    app.getBaseContainer().getVerticalScroll().onClick(y);
                    setPreventEvent(true);
                    return;
                }
            }
            if (app.getBaseContainer().isHorizontalScrollEnable()) {
                //Check touch on horizontal scrollbar
                // TODO: need to write
            }
        }
        // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #

        checkViewsForEvent(viewList);

    }

}
