package ru.cubos.server.system.apps;

import ru.cubos.server.Server;
import ru.cubos.server.helpers.BinaryImage;
import ru.cubos.server.system.events.Event;
import ru.cubos.server.system.views.containers.LinearContainer;
import ru.cubos.server.system.views.View;
import ru.cubos.server.system.views.viewElements.ScrollBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class App {
    private LinearContainer baseContainer;
    private String appName = "";
    private boolean repaintPending = true;
    private Server server;
    protected BinaryImage renderImage;

    private int leftOffset;
    private int topOffset;
    private int rightOffset;
    private int bottomOffset;

    private HashMap<Event.Type, List<View>> eventViewLists = new HashMap<>();
    public List<View> getEventList(Event.Type type){
        return eventViewLists.get(type);
    }

    public void addEventView(View view, Event.Type type){
        List<View> list = getEventList(type);
        if(list==null){
            list = new ArrayList<>();
            eventViewLists.put(type, list);
        }

        list.add(view);
    }

    public App(Server server){
        baseContainer = new LinearContainer();
        baseContainer.setServer(server);
        baseContainer.setAppParent(this);

        //baseContainer.setHorizontalScrollEnable(this);
        baseContainer.setVerticalScrollEnable(this);

        baseContainer.setScrollY(0);
        //baseContainer.setScrollX(0);

        setLeftOffset(0);
        setRightOffset(0);
        setTopOffset(server.settings.getStatusBarHeight());
        setBottomOffset(server.settings.getButtonBarHeight());

        this.server = server;
    }

    public Server getServer(){
        return this.server;
    }

    public void repaint(){

        if(this.repaintPending){
            setRepaintPending(false);
            baseContainer.draw();

            int renderSize[] = server.display.drawImage(
                    getLeftOffset()-baseContainer.getScrollX(),
                    getTopOffset() - baseContainer.getScrollY(),
                    renderImage.getWidth() + baseContainer.getScrollX(),
                    getWindowHeight() + baseContainer.getScrollY(),
                    0 + baseContainer.getScrollX(),
                    0 + baseContainer.getScrollY(),
                    renderImage, null);

            //Drawing scrolls
            if(baseContainer.isHorizontalScrollEnable() && renderImage.getWidth()>server.display.getWidth()){
                baseContainer.getHorizontalScroll().draw(server.display, 0, getTopOffset(), 0, server.settings.getStatusBarHeight());
            }

            if(baseContainer.isVerticalScrollEnable() && renderImage.getHeight()>getWindowHeight()) {
                ScrollBar scrollBar = baseContainer.getVerticalScroll();
                scrollBar.setVisibleContentlength(getWindowHeight());
                scrollBar.setTotalContentLength(renderImage.getHeight());
                scrollBar.draw(server.display, getLeftOffset(), getTopOffset(), getRightOffset(), getBottomOffset());

                //int leftOffset, int topOffset, int rightOffset, int bottomOffset
            }


            baseContainer.resetPositionsRenderImage();
            baseContainer.setPositionOnRenderImage(0, getTopOffset() - baseContainer.getScrollY());
            baseContainer.setSizeOnRenderImage(renderSize[0], renderSize[1] - getTopOffset());
            baseContainer.recountRenderPositions();

            baseContainer.setRepaintPending(false);
        }else{
            //server.display.drawImage(0, app_image_y, renderImage.getWidth(), app_image_height, renderImage);
            server.display.drawImage(0, getTopOffset(), renderImage);
        }

        return;
    }


    /*
     * # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
     * # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
     * # #                                                                                             # #
     * # #                                      GETTERS-N-SETTERS                                      # #
     * # #                                                                                             # #
     * # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
     * # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
     * */

    public void setRenderImage(BinaryImage image){
        renderImage = image;
    }

    public void addView(View view){
        this.baseContainer.add(view);
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public LinearContainer getBaseContainer() {
        return baseContainer;
    }

    public void setBaseContainer(LinearContainer baseContainer) {
        this.baseContainer = baseContainer;
    }

    public boolean isRepaintPending() {
        return repaintPending;
    }

    protected void setRepaintPending(boolean repaintPending) {
        this.repaintPending = repaintPending;
    }

    public void setRepaintIsPending() {
        this.repaintPending = true;
        this.baseContainer.setRepaintPending(true);
        // TODO: Remove this in future
        this.repaint();
        getServer().sendFrameBufferCommands();
    }

    public void onScrollXListener(int coord_new, int coord_old){
        System.out.println("On scroll X listener");
    }

    public void onScrollYListener(int coord_new, int coord_old){
        System.out.println("On scroll Y listener");
    }

    public void execEvent(Event event){
        event.executeViewsHandlers(getEventList(event.getType()), this);
    }

    public int getLeftOffset() {
        return leftOffset;
    }

    public void setLeftOffset(int leftOffset) {
        this.leftOffset = leftOffset;
    }

    public int getTopOffset() {
        return topOffset;
    }

    public void setTopOffset(int topOffset) {
        this.topOffset = topOffset;
    }

    public int getRightOffset() {
        return rightOffset;
    }

    public void setRightOffset(int rightOffset) {
        this.rightOffset = rightOffset;
    }

    public int getBottomOffset() {
        return bottomOffset;
    }

    public void setBottomOffset(int bottomOffset) {
        this.bottomOffset = bottomOffset;
    }

    public int getWindowWidth(){
        return getServer().display.getWidth() - getLeftOffset() - getRightOffset();
    }

    public int getWindowHeight(){
        return getServer().display.getHeight() - getTopOffset() - getBottomOffset();
    }
}
