package ru.cubos.server.system.views.viewElements;

import ru.cubos.server.helpers.BinaryImage;

public class ScrollBar {
    private Type type;

    public ScrollBar(Type type){
        this.type = type;
    }
    public enum Type{
        VERTICAL,
        HORIZONTAL
    }
    private boolean isVisible = true;
    private boolean allwaysShow = false;
    private int contentlength = 0;

    public boolean isEnabled(){
        if(isVisible){
            return true;
        }else{
            return false;
        }
    }

    public boolean isAllwaysShow() {
        return allwaysShow;
    }

    public void setAllwaysShow(boolean allwaysShow) {
        this.allwaysShow = allwaysShow;
    }

    public int getContentlength() {
        return contentlength;
    }

    public void setContentlength(int contentlength) {
        this.contentlength = contentlength;
    }

    public void draw(BinaryImage image){

    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
