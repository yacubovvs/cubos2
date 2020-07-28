package ru.cubos.server.system.views.viewElements;

import ru.cubos.server.Server;
import ru.cubos.server.helpers.BinaryImage;
import ru.cubos.server.helpers.framebuffer.Display;
import ru.cubos.server.system.apps.App;

public class ScrollBar {
    private Type type;
    private boolean isVisible           = true;
    private boolean allwaysShow         = false;
    private int visibleContentlength    = 0;
    private int totalContentlength      = 0;
    private int screenScrool            = 0;
    private App app;

    public ScrollBar(Type type, App app){
        this.type = type;
        setApp(app);
    }

    public int getTotalContentlength() {
        return totalContentlength;
    }

    public void setTotalContentlength(int totalContentlength) {
        this.totalContentlength = totalContentlength;
    }

    public int getScreenScroll() {
        return screenScrool;
    }

    public void setScreenScrool(int screenScrool) {
        this.screenScrool = screenScrool;
    }

    public int getActionWidth() {
        return getApp().getServer().settings.getScrollbarPointWidth()/2 + getApp().getServer().settings.getScrollbarPointWidth()-1;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public int getBottomOffset() {
        return bottomOffset;
    }

    public int getRightOffset() {
        return rightOffset;
    }

    public int getScollBarLength() {
        return scollBarLength;
    }

    public int getLeftOffset() {
        return leftOffset;
    }

    public int getTopOffset() {
        return topOffset;
    }

    public enum Type{
        VERTICAL,
        HORIZONTAL
    }

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

    public int getVisibleContentlength() {
        return visibleContentlength;
    }

    public void setVisibleContentlength(int visibleContentlength) {
        this.visibleContentlength = visibleContentlength;
    }

    private int leftOffset;
    private int topOffset;
    private int rightOffset;
    private int bottomOffset;
    private int scollBarLength;

    public void onClick(int screenPosition) {
        if(type==Type.HORIZONTAL){
            // Horizontal - later
        }else{
            // Vertical
            int scollBarPosition    = screenPosition - app.getServer().settings.getScrollbarWidth() - topOffset;
            //int scollBarLength      =
            if(scollBarPosition<0) scollBarPosition = 0;
            if(scollBarPosition>scollBarLength) scollBarPosition = scollBarLength;
            System.out.println("Click on scrollbar " + scollBarPosition + " of " + scollBarLength);

            app.setRepaintIsPending();
            return;
        }
    }

    public void draw(BinaryImage image, int leftOffset, int topOffset, int rightOffset, int bottomOffset){

        Server server = app.getServer();
        this.leftOffset     = leftOffset;
        this.topOffset      = topOffset;
        this.rightOffset    = rightOffset;
        this.bottomOffset   = bottomOffset;

        final int scrollSize = server.settings.getScrollbarWidth();
        final Display display = server.display;
        final byte[] scrollColor = server.settings.getScrollbarColor();

        if(type==Type.HORIZONTAL){
            // Horizontal
            image.drawLine(scrollSize + leftOffset, display.getHeight()-scrollSize - topOffset, display.getWidth()-scrollSize - rightOffset, display.getHeight()-scrollSize/2 - bottomOffset, scrollColor);

        }else{
            // Vertical
            final int x0 = display.getWidth()-scrollSize - leftOffset;
            final int y0 = scrollSize + topOffset;
            final int x1 = display.getWidth()-scrollSize - rightOffset;
            final int y1 = display.getHeight()-scrollSize - bottomOffset;

            scollBarLength = y1 - y0;
            image.drawLine( x0, y0, x1, y1, scrollColor);

            final int pointWidth  = server.settings.getScrollbarPointWidth();
            final int pointHeight = 30;
            final int pointOffset = 50;

            image.drawRect(x0 - pointWidth/2, y0 + pointOffset, x0 + pointWidth/2, y0 + pointOffset + pointHeight, scrollColor, true);
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
