package ru.cubos.server.system.views;

import ru.cubos.server.system.apps.App;

import java.util.ArrayList;
import java.util.List;

public abstract class ContainerView extends View {
    protected List<View> children = new ArrayList<>();
    private App appParent;

    public ContainerView(){
        super();
    }

    public void add(View view){
        view.setServer(getServer());
        children.add(view);
    }

    public List<View> getChildren() {
        return children;
    }

    public App getAppParent() {
        return appParent;
    }

    public void setAppParent(App appParent) {
        this.appParent = appParent;
    }
}
