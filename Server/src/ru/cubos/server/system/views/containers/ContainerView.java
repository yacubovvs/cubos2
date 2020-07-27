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

    public ContainerView(){
        super();
    }
    public abstract void recountRenderPositions();

    public void setVerticalScrollEnable(){
        verticalScroll = new ScrollBar(ScrollBar.Type.VERTICAL);
    }

    public void setVerticalScrollDisable(){
        verticalScroll = null;
    }

    public boolean isVerticalScrollEnable(){
        return verticalScroll!=null;
    }

    public void setHorizontalScrollEnable(){
        horizontalScroll = new ScrollBar(ScrollBar.Type.HORIZONTAL);
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

}
