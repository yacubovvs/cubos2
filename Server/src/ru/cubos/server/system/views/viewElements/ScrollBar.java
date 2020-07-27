package ru.cubos.server.system.views.viewElements;

import ru.cubos.server.Server;
import ru.cubos.server.helpers.BinaryImage;
import ru.cubos.server.helpers.framebuffer.Display;

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

    public void draw(Server server, BinaryImage image, int leftOffset, int topOffset, int rightOffset, int bottomOffset){
        final int scrollSize = server.settings.getScrollbarWidth();
        final Display display = server.display;
        final byte[] scrollColor = server.settings.getScrollbarColor();

        if(type==Type.HORIZONTAL){
            image.drawLine(scrollSize/2 + leftOffset, display.getHeight()-scrollSize/2 - topOffset, display.getWidth()-scrollSize/2 - rightOffset, display.getHeight()-scrollSize/2 - bottomOffset, scrollColor);
        }else{
            image.drawLine(display.getWidth()-scrollSize/2 - leftOffset, scrollSize/2 + topOffset, display.getWidth()-scrollSize/2 - rightOffset, display.getHeight()-scrollSize/2 - bottomOffset, scrollColor);
        }
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
