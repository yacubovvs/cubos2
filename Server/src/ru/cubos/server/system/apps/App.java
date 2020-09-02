package ru.cubos.server.system.apps;

import ru.cubos.server.Server;
import ru.cubos.commonHelpers.binaryImages.BinaryImage;
import ru.cubos.commonHelpers.binaryImages.BinaryImage_24bit;
import ru.cubos.server.settings.Settings;
import ru.cubos.server.system.events.Event;
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

    private boolean windowMode;
    private int leftOffset;
    private int topOffset;
    private int rightOffset;
    private int bottomOffset;

    private boolean hasXScroll;
    private boolean hasYScroll;

    private boolean movingEnable = true;
    private boolean resizingEnable = true;

    private View activeView;

    public void onButtonPressed(char code, char buttonChar){
        return;
    }

    protected void focus(View view){
        if(activeView!=null && activeView.getActivationListener()!=null){
            activeView.getActivationListener().deactivate(activeView);
        }

        activeView = view;
        if(view!=null) activeView.getActivationListener().activate(activeView);
    }

    private HashMap<Event.Type, List<View>> eventViewLists = new HashMap<>();
    public List<View> getEventList(Event.Type type){
        return eventViewLists.get(type);
    }

    public Settings getSettings(){
        return getServer().settings;
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

        server.setRepaintPending();
    }

    private boolean isMoving = false;
    private boolean isResizing = false;

    public int[] getActiveCoordinates(){
        // get enable to click area on screen
        int[] result = new int[]{
            getLeftOffset() - getWindowBorderActiveOffsetWidth(),
            getTopOffset() - getWindowBorderActiveOffsetWidth() - getWindowTitleBarHeight(),
            getDisplayWidth() - getRightOffset() + getWindowBorderActiveOffsetWidth(),
            getDisplayHeight() - getBottomOffset()  + getWindowBorderActiveOffsetWidth()
        };

        return result;
    }

    public int getWindowBorderWidth(){
        if(windowMode) return server.settings.getWindowBorderWidth();
        else return 0;
    }

    public int getWindowBorderActiveOffsetWidth(){
        if(windowMode) return server.settings.getWindowBorderActiveOffsetWidth();
        else return 0;
    }

    public int getWindowTitleBarHeight(){
        if(windowMode) return server.settings.getWindowTitleBarHeight();
        else return 0;
    }

    public boolean coordinatesInActiveArea(int x, int y){
        int appCoordinates[] = getActiveCoordinates();
        int x_min = appCoordinates[0];
        int y_min = appCoordinates[1];
        int x_max = appCoordinates[2];
        int y_max = appCoordinates[3];

        if(x_min<=x && x_max>=x && y_min<=y && y_max>=y) return true;
        else return false;
    }

    private String windowTitle = "New window";

    public App(Server server){
        this.server = server;
        setWindowMode(getSettings().isWindowMode());
        baseContainer = new LinearContainer();
        baseContainer.setServer(server);
        baseContainer.setAppParent(this);

        //baseContainer.setHorizontalScrollEnable(this);
        baseContainer.setVerticalScrollEnable(this);

        baseContainer.setScrollY(0);
        //baseContainer.setScrollX(0);

        // TODO: on Zero (window mode) in offsets, window is not showing
        setLeftOffset(0);
        setRightOffset(0);
        setTopOffset(0);
        setBottomOffset(0);

        if(isWindowMode()){
            windowTitleBar = new WindowTitleBar(this);
        }

    }

    public Server getServer(){
        return this.server;
    }

    public void drawWindowBorders(BinaryImage image){
        for(int i=0; i<getWindowBorderWidth(); i++) {
            image.drawRect(i,i, image.getWidth()-1-i, image.getHeight()-1-i, getSettings().getWindowBorderColor());
        }
    }

    public void draw(){

        if(this.repaintPending){

            baseContainer.draw();

            renderImage = new BinaryImage_24bit(getWindowWidth() + 2*getWindowBorderWidth(), getWindowHeight() + getWindowBorderWidth()*2 + getWindowTitleBarHeight());
            contentRenderImage = baseContainer.getRenderImage();

            renderImage.drawImage(
                    getWindowBorderWidth()-baseContainer.getScrollX(),
                    getWindowBorderWidth() + getWindowTitleBarHeight()-baseContainer.getScrollY(),
                    getWindowWidth() + baseContainer.getScrollX(),
                    getWindowHeight() + baseContainer.getScrollY(),
                    baseContainer.getScrollX(),
                    baseContainer.getScrollY(),
                    (BinaryImage_24bit)contentRenderImage, null);

            setHasXScroll(contentRenderImage.getWidth()>getWindowWidth());
            setHasYScroll(contentRenderImage.getHeight()>getWindowHeight());

            //Drawing scrolls
            if(baseContainer.isHorizontalScrollEnable() && hasXScroll){
                baseContainer.getHorizontalScroll().draw(server.display, 0, getTopOffset(), 0, getSettings().getStatusBarHeight());
            }

            if(baseContainer.isVerticalScrollEnable() && hasYScroll) {
                ScrollBar scrollBar = baseContainer.getVerticalScroll();
                scrollBar.setVisibleContentlength(getWindowHeight());
                scrollBar.setTotalContentLength(contentRenderImage.getHeight());
                scrollBar.draw(renderImage, getWindowBorderWidth(), getWindowBorderWidth() + getWindowTitleBarHeight(), getWindowBorderWidth(), getWindowBorderWidth());
            }

            if(isWindowMode()) {
                drawWindowBorders(renderImage);
                windowTitleBar.draw();
                renderImage.drawImage(getWindowBorderWidth(), getWindowBorderWidth(), (BinaryImage_24bit)windowTitleBar.getRenderImage());
            }


            int renderSize[] = drawRenderedImage();

            resetingRenderSizes(renderSize);
            cancelRepaintPending();

            //System.out.println("Rerendering painting");
        }else{
            //server.display.drawImage(0, app_image_y, renderImage.getWidth(), app_image_height, renderImage);
            //System.out.println("No rerendering painting");
            int renderSize[] = drawRenderedImage();
            resetingRenderSizes(renderSize);
        }
        return;
    }

    public int[] drawRenderedImage(){
        int renderSize[] = getServer().display.drawImage(
                getLeftOffset() - getWindowBorderWidth(),
                getTopOffset() - getWindowTitleBarHeight() - getWindowBorderWidth(),
                getDisplayWidth(),
                getDisplayHeight() + 1 - getTopOffset() + getWindowTitleBarHeight() + getWindowBorderWidth() - getSettings().getButtonBarHeight(),
                0,
                - getTopOffset() + getWindowTitleBarHeight() + getWindowBorderWidth() + getSettings().getStatusBarHeight(),
                (BinaryImage_24bit)renderImage, null);

        return renderSize;
    }

    public void resetingRenderSizes(int renderSize[]){
        baseContainer.resetPositionsRenderImage();
        baseContainer.setPositionOnRenderImage(getLeftOffset() - baseContainer.getScrollX(), getTopOffset() - baseContainer.getScrollY());
        baseContainer.setSizeOnRenderImage(renderSize[0] - getLeftOffset(), renderSize[1] - getTopOffset());
        baseContainer.recountRenderPositions();

        baseContainer.setRepaintPending(false);
    }

    public boolean onFocusLose(){
        //return  false; // for prevent focus losing
        return true;
    }

    public void onFocusGot(){
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

    protected void cancelRepaintPending() {
        repaintPending = false;
    }

    public void setRepaintPending() {
        this.repaintPending = true;
        getServer().setRepaintPending();
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
        if(leftOffset>this.leftOffset && getWindowWidth()<getSettings().getScrollbarWidth() + getSettings().getWindowTitleBarHeight()) return;
        this.leftOffset = leftOffset;
    }

    public int getTopOffset() {
        return topOffset;
    }

    public void setTopOffset(int topOffset) {
        if(topOffset>this.topOffset && getWindowHeight()<getSettings().getWindowTitleBarHeight()) return;
        this.topOffset = topOffset;
    }

    public int getRightOffset() {
        return rightOffset;
    }

    public void setRightOffset(int rightOffset) {
        if(rightOffset>this.rightOffset && getWindowWidth()<getSettings().getScrollbarWidth() + getSettings().getWindowTitleBarHeight()) return;
        this.rightOffset = rightOffset;
    }

    public int getBottomOffset() {
        return bottomOffset;
    }

    public void setBottomOffset(int bottomOffset) {
        if(bottomOffset>this.bottomOffset && getWindowHeight()<getSettings().getWindowTitleBarHeight()) return;
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
        setRepaintPending();
    }

    public void setScrollY(int scroll){
        getBaseContainer().setScrollY(scroll);
        setRepaintPending();
    }

    public int getDisplayHeight(){
        return server.display.getHeight();
    }

    public int getDisplayWidth(){
        return server.display.getWidth();
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        if(isMovingEnable()) isMoving = moving;
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
    }

    public boolean isResizing() {
        return isResizing;
    }

    public void setResizing(boolean resizing) {
        if(isResizingEnable()) isResizing = resizing;
    }

    public void setRepaintPending(boolean repaintPending, boolean anything) {
        if(anything){
            getBaseContainer().setRepaintPending(repaintPending, true);
            windowTitleBar.setRepaintPending(repaintPending, true);
        }

        this.setRepaintPending();
    }

    public boolean isMovingEnable() {
        return movingEnable;
    }

    public void setMovingEnable(boolean movingEnable) {
        this.movingEnable = movingEnable;
    }

    public boolean isResizingEnable() {
        return resizingEnable;
    }

    public void setResizingEnable(boolean resizingEnable) {
        this.resizingEnable = resizingEnable;
    }

    public boolean isWindowMode() {
        return windowMode;
    }

    public void setWindowMode(boolean windowMode) {
        this.windowMode = windowMode;
    }

}
