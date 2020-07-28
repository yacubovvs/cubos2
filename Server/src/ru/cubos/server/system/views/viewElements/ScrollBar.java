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
    private int totalContentLength = 0;
    private int screenScroll            = 0;
    private App app;

    public ScrollBar(Type type, App app){
        this.type = type;
        setApp(app);
    }

    public int getTotalContentLength() {
        return totalContentLength;
    }

    public void setTotalContentLength(int totalContentLength) {
        this.totalContentLength = totalContentLength;
    }

    public int getScreenScroll() {
        return screenScroll;
    }

    public void setScreenScroll(int screenScroll) {
        this.screenScroll = screenScroll;
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
    private int pointOffsetHeight; //max height of drawind scrollbar

    public void onClick(int screenPosition) {
        if(type==Type.HORIZONTAL){
            // Horizontal - later
        }else{
            // Vertical
            int scollBarPosition    = screenPosition - app.getServer().settings.getScrollbarWidth() - topOffset;
            if(scollBarPosition<0) scollBarPosition = 0;
            if(scollBarPosition>scollBarLength) scollBarPosition = scollBarLength;
            System.out.println("Click on scrollbar " + scollBarPosition + " of " + scollBarLength);

            int scoll = (int)((float)scollBarPosition/(float)scollBarLength*(float)(getTotalContentLength() - visibleContentlength));

            app.getBaseContainer().setScrollY(scoll);
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
            final int pointHeight = (int)((float)scollBarLength * ((float)visibleContentlength/ (float)totalContentLength));
            pointOffsetHeight = scollBarLength - pointHeight;
            final int pointOffset = (int)((float)pointOffsetHeight * (float)getScreenScroll()/ (float)(getTotalContentLength() - visibleContentlength));

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
