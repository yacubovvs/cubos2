package ru.cubos.server.system.events;

import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.views.View;

import java.util.List;

public class TouchDownEvent extends EventTouch {

    @Override
    public void runViewEvent(View view) {
        view.getOnTouchDownListener().run();
    }

    public TouchDownEvent(int x, int y){
        super(x,y);
        this.setType(Type.EVENT_TOUCH_DOWN);
    }

    @Override
    public void executeViewsHandlers(List<View> viewList, App app) {

        if (isOnTitleBarEvent(app)) app.setMoving(true);

        if(!isInWindowEvent(app)){
            if(app.getServer().settings.isWindowMode() && app.coordinatesInActiveArea(x, y)){
                app.setResizing(true);
                return;
            }
            return;
        }else{
            //System.out.println("On form touch down");
        }

        // # # # # # # # # # # # # # # # # # # # SCROLLBARS EVENTS # # # # # # # # # # # # # # # # # # # # # # #

        if(app.getServer().settings.isDragScrollBarEnable()){
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
