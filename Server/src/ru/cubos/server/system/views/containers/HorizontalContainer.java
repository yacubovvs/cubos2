package ru.cubos.server.system.views.containers;

import ru.cubos.server.system.views.View;

public class HorizontalContainer extends LinearContainer {
    public HorizontalContainer(){
        super();
        setType(LinearContainer.Type.HORIZONTAL);
    }

    @Override
    protected int getContentHeight(){
        int maxContentHeight = 0;
        for (View view: getChildren()){
            maxContentHeight = Math.max(maxContentHeight, view.getHeight());
        }
        return maxContentHeight;
    }
}
