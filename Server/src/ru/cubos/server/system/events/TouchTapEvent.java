package ru.cubos.server.system.events;

import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.views.View;

import java.util.List;

public class TouchTapEvent extends EventTouch {

    @Override
    public void runViewEvent(View view) {
        view.getTouchTapListener().onTouchTap(this);
    }

    public TouchTapEvent(int x, int y) {
        super(x, y);
        this.setType(Type.EVENT_TOUCH_TAP);
    }


    @Override
    public void executeViewsHandlers(List<View> viewList, App app) {

        if (!isInWindowEvent(app)){
            if(app.getServer().settings.isWindowMode() && isOnTitleBarEvent(app)){
                //System.out.println("On title bar touch");
                return;
            }else{
                //System.out.println("Out of form click");
                return;
            }
        }

        //System.out.println("On form click");

        if(app.getServer().settings.isDragScrollBarEnable()) {
            if (app.getBaseContainer().isVerticalScrollEnable() || app.getBaseContainer().isHorizontalScrollEnable()) {
                //Check touch on vertical scrollbar
                if (x > app.getLeftOffset() + app.getWindowWidth() - app.getBaseContainer().getVerticalScroll().getActionWidth()) {
                    setPreventEvent(true);
                    return;
                }

                // TODO: add check for horizontal scrollbar
            }
        }
        checkViewsForEvent(viewList);
    }

}
