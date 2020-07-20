package ru.cubos.server.system.views;

import java.util.ArrayList;
import java.util.List;

public abstract class ViewContainer extends View {
    protected List<View> childs = new ArrayList<>();

    public ViewContainer(){
        super();
    }

    public void add(View view){
        childs.add(view);
    }
}
