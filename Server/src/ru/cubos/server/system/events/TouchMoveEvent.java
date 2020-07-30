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

    public TouchMoveEvent(int x, int y, int x_last, int y_last, int x_start, int y_start){
        super(x,y);
        this.setType(Type.EVENT_TOUCH_TAP);

        this.x_last     = x_last;
        this.y_last     = y_last;
        this.x_start    = x_start;
        this.y_start    = y_start;
    }

    @Override
    public void executeViewsHandlers(List<View> viewList, App app) {

        if(!isInWindowEvent(app)) return;

        // # # # # # # # # # # # # # # # # # # # SCROLLBARS EVENTS # # # # # # # # # # # # # # # # # # # # # # #
        if(app.getServer().settings.isDragScollBarEnable()) {
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
