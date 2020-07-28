package ru.cubos.server.system.apps;

import ru.cubos.server.Server;
import ru.cubos.server.helpers.BinaryImage;
import ru.cubos.server.system.events.Event;
import ru.cubos.server.system.views.containers.LinearContainer;
import ru.cubos.server.system.views.View;
import ru.cubos.server.system.views.viewElements.ScrollBar;

import java.util.ArrayList;
import java.util.List;

public abstract class App {
    private LinearContainer baseContainer;
    private String appName = "";
    private boolean repaintPending = true;
    private Server server;
    protected BinaryImage renderImage;

    protected List<View> eventViews = new ArrayList<>();

    public void addEventView(View view){
        eventViews.add(view);
    }


    public App(Server server){
        baseContainer = new LinearContainer();
        baseContainer.setServer(server);
        baseContainer.setAppParent(this);

        //baseContainer.setHorizontalScrollEnable(this);
        baseContainer.setVerticalScrollEnable(this);

        baseContainer.setScrollY(300);
        //baseContainer.setScrollX(0);

        this.server = server;
    }

    public Server getServer(){
        return this.server;
    }

    public void repaint(){

        int app_image_y = server.settings.getStatusBarHeight();
        int app_image_height = server.display.getHeight() - server.settings.getStatusBarHeight() - server.settings.getButtonBarHeight();

        if(this.repaintPending){
            setRepaintPending(false);
            baseContainer.draw();

            int renderSize[] = server.display.drawImage( -baseContainer.getScrollX(), app_image_y - baseContainer.getScrollY(), renderImage.getWidth() + baseContainer.getScrollX(), app_image_height + baseContainer.getScrollY(), baseContainer.getScrollX(), baseContainer.getScrollY(), renderImage, null);

            //Drawing scrolls
            if(baseContainer.isHorizontalScrollEnable() && renderImage.getWidth()>server.display.getWidth()){
                baseContainer.getHorizontalScroll().draw(server.display, 0, app_image_y, 0, server.settings.getStatusBarHeight());
            }

            if(baseContainer.isVerticalScrollEnable() && renderImage.getHeight()>app_image_height) {
                ScrollBar scrollBar = baseContainer.getVerticalScroll();
                scrollBar.setVisibleContentlength(app_image_height);
                scrollBar.setTotalContentLength(renderImage.getHeight());
                scrollBar.draw(server.display, 0, app_image_y, 0, server.settings.getStatusBarHeight());
            }

            baseContainer.setPositionOnRenderImage(0, app_image_y);
            baseContainer.setSizeOnRenderImage(renderSize[0], renderSize[1] - app_image_y);
            baseContainer.recountRenderPositions();
        }else{
            //server.display.drawImage(0, app_image_y, renderImage.getWidth(), app_image_height, renderImage);
            server.display.drawImage(0, app_image_y, renderImage);
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

    public void execEvent(Event event ){
        event.executeViewsHandlers(eventViews, this);
    }
}
