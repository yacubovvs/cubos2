package ru.cubos.server.system.events;

import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.views.View;

import java.util.List;

public class TouchMoveEvent extends EventTouch {

    int x_last;
    int y_last;
    int x_start;
    int y_start;

    @Override
    public void runViewEvent(View view) {
        view.getOnTouchDownListener();
    }

    public boolean isOnTitleBarEvent(App app){
        if(
                app.getServer().settings.isWindowMode()
                && y_last <= app.getTopOffset()
                && y_last > app.getTopOffset() - app.getServer().settings.getWindowTitleBarHeight()
                && x_last > app.getLeftOffset()
                && x_last < app.getDisplayWidth() - app.getRightOffset()
        ) return true;
        else return false;

        /*
        app.getServer().settings.isWindowMode()
        && y_last <= app.getTopOffset()
        && y_last > app.getDisplayHeight() - app.getBottomOffset()
        && x_last > app.getLeftOffset()
        && x_last < app.getDisplayWidth() - app.getRightOffset()
        * */

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

    public TouchMoveEvent(int x, int y, int x_last, int y_last, int x_start, int y_start){
        super(x,y);
        this.setType(Type.EVENT_TOUCH_MOVE);

        this.x_last     = x_last;
        this.y_last     = y_last;
        this.x_start    = x_start;
        this.y_start    = y_start;
    }

    @Override
    public void executeViewsHandlers(List<View> viewList, App app) {

        if(app.isMoving() && app.getServer().settings.isWindowMode() && isOnTitleBarEvent(app)){
            if(y>app.getServer().settings.getStatusBarHeight() && y<app.getDisplayHeight() - app.getServer().settings.getButtonBarHeight()){
                app.move(x - x_last,y - y_last);
            }

            return;
        }

        if(app.isMoving()) return;

        if (!isInWindowEvent(app)){
            return;
        }

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

        if(isPreventEvent()) return;
        // # # # # # # # # # # # # # # # # # # # CONTENT SCROLLING # # # # # # # # # # # # # # # # # # # # # # #
        if(app.getServer().settings.isScrollingByContentDrag()) {
            //System.out.println("Checking content scrolling");
            if(app.isHasXScroll()){
                // TODO: need to write
            }
            if(app.isHasYScroll() && (y_last - y)!=0){
                //System.out.println("Y-scrolling");
                app.setScrollY( app.getScrollY() + (y_last - y));
            }
        }
        // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #

    }

}
