package ru.cubos.server.system.apps;

import ru.cubos.server.Server;
import ru.cubos.server.helpers.BinaryImage;
import ru.cubos.server.system.events.Event;
import ru.cubos.server.system.views.containers.LinearContainer;
import ru.cubos.server.system.views.View;

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
        this.server = server;
    }

    public void repaint(){

        if(this.repaintPending){
            baseContainer.draw();
            setRepaintPending(false);

            int app_image_y = server.settings.getStatusBarHeight();
            // TODO: add crop of app renderImage size
            int renderSize[] = server.display.drawImage(0, app_image_y, renderImage);
            baseContainer.setPositionOnRenderImage(0, app_image_y);
            baseContainer.setSizeOnRenderImage(renderSize[0], renderSize[1] - app_image_y);
            baseContainer.recountRenderPositions();
        }else{
            int app_image_y = server.settings.getStatusBarHeight();
            // TODO: add crop of app renderImage size
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

    public void setRepaintPending(boolean repaintPending) {
        this.repaintPending = repaintPending;
    }

    public void execEvent(Event event ){
        event.executeViewsHandlers(eventViews);
    }
}
