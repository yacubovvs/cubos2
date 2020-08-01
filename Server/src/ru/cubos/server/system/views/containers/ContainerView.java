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

    protected List<View> children = new ArrayList<>();

    @Override
    public void recountPositionOnRenderImage(int x, int y){
        super.recountPositionOnRenderImage(x,y);
        recountRenderPositions();
    }

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
        if(scroll<0) scroll = 0;
        if(scroll>getMaxScrollX()) scroll = getMaxScrollX();
        getHorizontalScroll().setScreenScroll(scroll);
    }

    public void setScrollY(int scroll){
        if(scroll<0) scroll = 0;
        if(scroll>getMaxScrollY()) scroll = getMaxScrollY();
        getVerticalScroll().setScreenScroll(scroll);
    }

    public int getMaxScrollX(){
        if(getRenderImage()!=null) return getRenderWidth() - getApp().getWindowWidth();
        else return 0;
    }

    public int getMaxScrollY(){
        if(getRenderImage()!=null) return getRenderImage().getHeight() - getApp().getWindowHeight();
        else return 0;
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

    @Override
    public void resetPositionsRenderImage(){
        super.resetPositionsRenderImage();
        for(View view: children){
            view.resetPositionsRenderImage();
        }
    }

}
