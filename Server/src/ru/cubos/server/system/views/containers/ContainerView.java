package ru.cubos.server.system.views.containers;

import ru.cubos.server.Server;
import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.views.View;

import java.util.ArrayList;
import java.util.List;

public abstract class ContainerView extends View {
    protected List<View> children = new ArrayList<>();

    public ContainerView(){
        super();
    }
    public abstract void recountRenderPositions();

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
