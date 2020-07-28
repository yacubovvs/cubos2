package ru.cubos.server.system.views.containers;

import ru.cubos.server.Server;
import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.views.View;
import ru.cubos.server.system.views.viewElements.ScrollBar;

import java.util.ArrayList;
import java.util.List;

public abstract class ContainerView extends View {

    private ScrollBar verticalScroll;
    protected ScrollBar horizontalScroll;
    private int scrollPosition = 0;

    protected List<View> children = new ArrayList<>();

    public ContainerView(){
        super();
    }
    public abstract void recountRenderPositions();

    public void setVerticalScrollEnable(App app){
        verticalScroll = new ScrollBar(ScrollBar.Type.VERTICAL, app);
    }

    public void setVerticalScrollDisable(){
        verticalScroll = null;
    }

    public boolean isVerticalScrollEnable(){
        return verticalScroll!=null;
    }

    public void setHorizontalScrollEnable(App app){
        horizontalScroll = new ScrollBar(ScrollBar.Type.HORIZONTAL, app);
    }

    public void setHorizontalScrollDisable(){
        horizontalScroll = null;
    }

    public ScrollBar getHorizontalScroll(){
        return horizontalScroll;
    }

    public ScrollBar getVerticalScroll(){
        return verticalScroll;
    }

    public boolean isHorizontalScrollEnable(){
        return horizontalScroll!=null;
    }

    public int getScrollX(){
        if (isHorizontalScrollEnable()) return getHorizontalScroll().getScreenScroll();
        else return 0;
    }

    public int getScrollY(){
        if (isVerticalScrollEnable()) return getVerticalScroll().getScreenScroll();
        else return 0;
    }

    public void setScrollX(int scroll){
        if (isHorizontalScrollEnable()) getHorizontalScroll().setScreenScroll(scroll);
    }

    public void setScrollY(int scroll){
        if (isVerticalScrollEnable()) getVerticalScroll().setScreenScroll(scroll);
    }

    public void add(View view){
        view.setServer(getServer());
        view.setParent(this);
        children.add(view);
    }

    @Override
    public void setServer(Server server) {
        super.setServer(server);

        for(View view: getChildren()){
            view.setServer(server);
        }
    }

    public List<View> getChildren() {
        return children;
    }

    public int getScrollPosition() {
        return scrollPosition;
    }

    public void setScrollPosition(int scrollPosition) {
        this.scrollPosition = scrollPosition;
    }
}
