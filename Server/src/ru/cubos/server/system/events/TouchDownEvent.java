package ru.cubos.server.system.events;

import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.views.View;

import java.util.List;

public class TouchDownEvent extends EventTouch {

    @Override
    public void runViewEvent(View view) {
        view.getOnTouchDownListener();
    }

    public TouchDownEvent(int x, int y){
        super(x,y);
        this.setType(Type.EVENT_TOUCH_TAP);
    }

    @Override
    public void executeViewsHandlers(List<View> viewList, App app) {

        if(!isInWindowEvent(app)) return;

        // # # # # # # # # # # # # # # # # # # # SCROLLBARS EVENTS # # # # # # # # # # # # # # # # # # # # # # #

        if(app.getServer().settings.isDragScollBarEnable()){
            if(app.getBaseContainer().isVerticalScrollEnable()){
                //Check touch on vertical scrollbar
                if(x>app.getLeftOffset() + app.getWindowWidth() - app.getBaseContainer().getVerticalScroll().getActionWidth()){
                    app.getBaseContainer().getVerticalScroll().onClick(y);
                    setPreventEvent(true);
                    return;
                }
            }
            if(app.getBaseContainer().isHorizontalScrollEnable()){
                //Check touch on horizontal scrollbar
                // TODO: need to write
            }
        }

        // # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #

        checkViewsForEvent(viewList);


    }

}
