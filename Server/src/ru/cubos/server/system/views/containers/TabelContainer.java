package ru.cubos.server.system.views.containers;

import ru.cubos.server.system.views.View;

import java.util.ArrayList;
import java.util.List;

public class TabelContainer extends LinearContainer {

    public List<View> ElementsList = new ArrayList<>();

    public enum TableType {
        FIXED_ROWS,
        FIXED_COLS
    }

    TableType tableType;
    int fixed_num = 0;
    LinearContainer baseLinearContainer;
    List<LinearContainer> secondaryLinerContainers;
    //int currentSecondaryPosition = 0;

    public TabelContainer(TableType tableType, int fixed_num){
        super();
        this.tableType = tableType;
        this.fixed_num = fixed_num;

        if(tableType == TableType.FIXED_ROWS){
            this.setType(Type.VERTICAL);
            super.add(new HorizontalContainer());
        }else{
            this.setType(Type.HORIZONTAL);
            super.add(new VerticalContainer());
        }
    }

    @Override
    public void add(View view) {
        ElementsList.add(view);
        view.setWidth_k(((float)1)/fixed_num);
        if (((LinearContainer)getChildren().get(getChildren().size() - 1)).getChildren().size() < fixed_num){
            ((LinearContainer)getChildren().get(getChildren().size() - 1)).add(view);
        }else{
            if(tableType == TableType.FIXED_ROWS){
                super.add(new HorizontalContainer());
            }else{
                super.add(new VerticalContainer());
            }
            ((LinearContainer) getChildren().get(getChildren().size() - 1)).add(view);
        }
    }
}
