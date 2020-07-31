package ru.cubos.server.system.apps;

import ru.cubos.server.Server;
import ru.cubos.server.helpers.BinaryImage;
import ru.cubos.server.helpers.Colors;
import ru.cubos.server.system.events.Event;
import ru.cubos.server.system.views.IconView;
import ru.cubos.server.system.views.TextView;
import ru.cubos.server.system.views.containers.LinearContainer;
import ru.cubos.server.system.views.View;
import ru.cubos.server.system.views.viewElements.ScrollBar;
import ru.cubos.server.system.views.viewElements.WindowTitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class App {
    private LinearContainer baseContainer;
    private String appName = "";
    private boolean repaintPending = true;
    private Server server;
    protected BinaryImage contentRenderImage;
    protected BinaryImage renderImage;

    private int leftOffset;
    private int topOffset;
    private int rightOffset;
    private int bottomOffset;

    private boolean hasXScroll;
    private boolean hasYScroll;

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

    WindowTitleBar windowTitleBar;

    public void move(int x, int y){
        setLeftOffset( getLeftOffset() + x );
        setRightOffset( getRightOffset() - x );

        setTopOffset( getTopOffset() + y );
        setBottomOffset( getBottomOffset() - y );

        setRepaintIsPending();
    }

    public App(Server server){
        this.server = server;
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

        if(server.settings.isWindowMode()){
            windowTitleBar = new WindowTitleBar();
            windowTitleBar.setHeight(server.settings.getWindowTitleBarHeight());
            windowTitleBar.setHorizontalScrollDisable();
            windowTitleBar.setVerticalScrollDisable();



            IconView close_button       = new IconView("images//icons//close_button.png");
            close_button.setMargin((server.settings.getWindowTitleBarHeight() - close_button.getIcon().getHeight())/2);
            close_button.setWidth_source(View.SizeSource.SIZE_SOURCE_CONTENT);

            IconView fullscreen_button  = new IconView("images//icons//fullscreen_button.png");
            fullscreen_button.setMargin((server.settings.getWindowTitleBarHeight() - fullscreen_button.getIcon().getHeight())/2);
            fullscreen_button.setWidth_source(View.SizeSource.SIZE_SOURCE_CONTENT);

            IconView rollup_button      = new IconView("images//icons//rollup_button.png");
            rollup_button.setMargin((server.settings.getWindowTitleBarHeight() - rollup_button.getIcon().getHeight())/2);
            rollup_button.setWidth_source(View.SizeSource.SIZE_SOURCE_CONTENT);


            windowTitleBar.add(close_button);
            windowTitleBar.add(fullscreen_button);
            windowTitleBar.add(rollup_button);
            windowTitleBar.setBackgroundColor(server.settings.getWindowTitleColor());

            TextView title = new TextView("Window title");
            windowTitleBar.add(title);
            windowTitleBar.setAppParent(this);
        }

    }

    public Server getServer(){
        return this.server;
    }

    public void drawWindowBorders(BinaryImage image){
        for(int i=0; i<server.settings.getWindowBorderWidth(); i++) {
            image.drawRect(i,i, image.getWidth()-1-i, image.getHeight()-1-i, server.settings.getWindowBorderColor());
        }
        //image.drawRect(server.settings.getWindowBorderWidth(), server.settings.getWindowBorderWidth(), image.getWidth()-server.settings.getWindowBorderWidth(), server.settings.getWindowTitleHeight(), server.settings.getWindowTitleColor(), true);
    }

    public void repaint(){

        // TODO: remove in multi app server +
        getServer().display.drawRect(0, getServer().settings.getStatusBarHeight(), getServer().display.getWidth(), getServer().display.getHeight() - getServer().settings.getButtonBarHeight(), Colors.COLOR_BLACK, true);
        //-

        if(this.repaintPending){
            setRepaintPending(false);
            baseContainer.draw();

            renderImage = new BinaryImage(getWindowWidth() + 2*server.settings.getWindowBorderWidth(), getWindowHeight() + server.settings.getWindowBorderWidth()*2 + server.settings.getWindowTitleBarHeight());

            renderImage.drawImage(
                    server.settings.getWindowBorderWidth()-baseContainer.getScrollX(),
                    server.settings.getWindowBorderWidth() + server.settings.getWindowTitleBarHeight()-baseContainer.getScrollY(),
                    getWindowWidth() + baseContainer.getScrollX(),
                    getWindowHeight() + baseContainer.getScrollY(),
                    baseContainer.getScrollX(),
                    baseContainer.getScrollY(),
                    contentRenderImage, null);
            /*
            int renderSize[] = server.display.drawImage(
                    getLeftOffset()-baseContainer.getScrollX(),
                    getTopOffset()-baseContainer.getScrollY(),
                    getWindowWidth() + baseContainer.getScrollX(),
                    getWindowHeight() + baseContainer.getScrollY(),
                    0 + baseContainer.getScrollX(),
                    0 + baseContainer.getScrollY(),
                    contentRenderImage, null);
            // */

            setHasXScroll(contentRenderImage.getWidth()>getWindowWidth());
            setHasYScroll(contentRenderImage.getHeight()>getWindowHeight());

            //Drawing scrolls
            if(baseContainer.isHorizontalScrollEnable() && hasXScroll){
                baseContainer.getHorizontalScroll().draw(server.display, 0, getTopOffset(), 0, server.settings.getStatusBarHeight());
            }

            if(baseContainer.isVerticalScrollEnable() && hasYScroll) {
                ScrollBar scrollBar = baseContainer.getVerticalScroll();
                scrollBar.setVisibleContentlength(getWindowHeight());
                scrollBar.setTotalContentLength(contentRenderImage.getHeight());
                //scrollBar.draw(server.display, getLeftOffset(), getTopOffset(), getRightOffset(), getBottomOffset());
                scrollBar.draw(renderImage, server.settings.getWindowBorderWidth(), server.settings.getWindowBorderWidth() + server.settings.getWindowTitleBarHeight(), server.settings.getWindowBorderWidth(), server.settings.getWindowBorderWidth());
                //scrollBar.draw(renderImage, 0, 0, 0, 0);


                //int leftOffset, int topOffset, int rightOffset, int bottomOffset
            }

            //int
            /*
                        -baseContainer.getScrollX(),
                     + server.settings.getWindowTitleHeight()-baseContainer.getScrollY(),
                    getWindowWidth() + baseContainer.getScrollX(),
                    getWindowHeight() + baseContainer.getScrollY(),
                    baseContainer.getScrollX(),
                    baseContainer.getScrollY(),
                    contentRenderImage, null);
             */
            if(server.settings.isWindowMode()) {
                drawWindowBorders(renderImage);
                windowTitleBar.draw();
                renderImage.drawImage(getServer().settings.getWindowBorderWidth(), getServer().settings.getWindowBorderWidth(), windowTitleBar.getRenderImage());
            }


            int renderSize[] = server.display.drawImage(
                    getLeftOffset() - server.settings.getWindowBorderWidth(),
                    getTopOffset() - server.settings.getWindowBorderWidth() - server.settings.getWindowTitleBarHeight(),

                    renderImage, null);

            baseContainer.resetPositionsRenderImage();
            baseContainer.setPositionOnRenderImage(0, getTopOffset() - baseContainer.getScrollY());
            baseContainer.setSizeOnRenderImage(renderSize[0], renderSize[1] - getTopOffset());
            baseContainer.recountRenderPositions();

            baseContainer.setRepaintPending(false);
        }else{
            //server.display.drawImage(0, app_image_y, renderImage.getWidth(), app_image_height, renderImage);
            server.display.drawImage(getLeftOffset() - server.settings.getWindowBorderWidth(), getTopOffset() - server.settings.getWindowTitleBarHeight() - server.settings.getWindowBorderWidth(), renderImage);
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

    public void setContentRenderImage(BinaryImage image){
        contentRenderImage = image;
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

    public boolean isHasXScroll() {
        return hasXScroll;
    }

    public void setHasXScroll(boolean hasXScroll) {
        this.hasXScroll = hasXScroll;
    }

    public boolean isHasYScroll() {
        return hasYScroll;
    }

    public void setHasYScroll(boolean hasYScroll) {
        this.hasYScroll = hasYScroll;
    }

    public int getScrollX(){
        return getBaseContainer().getScrollX();
    }

    public int getScrollY(){
        return getBaseContainer().getScrollY();
    }

    public void setScrollX(int scroll){
        getBaseContainer().setScrollX(scroll);
    }

    public void setScrollY(int scroll){
        getBaseContainer().setScrollY(scroll);
    }
}
