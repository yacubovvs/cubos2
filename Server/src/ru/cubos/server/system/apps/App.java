package ru.cubos.server.system.apps;

import ru.cubos.server.Server;
import ru.cubos.server.helpers.BinaryImage;
import ru.cubos.server.system.views.containers.LinearContainer;
import ru.cubos.server.system.views.View;

public abstract class App {
    private LinearContainer baseContainer;
    private String appName = "";
    private boolean repaintPending = true;
    private Server server;
    protected BinaryImage renderImage;

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
        }

        int app_image_y = server.settings.getStatusBarHeight();
        // TODO: add crop of app size
        server.display.drawImage(0, app_image_y, renderImage);

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
}
