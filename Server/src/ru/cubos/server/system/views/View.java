package ru.cubos.server.system.views;

import ru.cubos.server.Server;
import ru.cubos.server.helpers.BinaryImage;

import java.util.List;

public abstract class View {
    static final byte SIZE_SOURCE_CONTENT = 0;
    static final byte SIZE_SOURCE_FIXED = 1;
    static final byte SIZE_SOURCE_PERCENT = 2;

    private String id;
    private boolean visible = true;
    private View parent = null;
    private int marginLeft = 0;
    private int marginRight = 0;
    private int marginTop = 0;
    private int marginBottom = 0;
    private boolean repaintPending = true;
    private int height;
    private int width;
    private Server server;

    private byte height_source = SIZE_SOURCE_CONTENT;
    private byte width_source = SIZE_SOURCE_CONTENT;

    protected BinaryImage renderImage;

    public View(){

    }

    public void repaint(){
        if(this.repaintPending){
            draw();
            setRepaintPending(false);
        }
    }

    public abstract void draw();

    protected void onRender(){
        if(height_source==SIZE_SOURCE_CONTENT){
            height = renderImage.getHeight();
        }
        if(width_source==SIZE_SOURCE_CONTENT){
            width = renderImage.getWidth();
        }
    }
    /*
     * # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
     * # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
     * # #                                                                                             # #
     * # #                                      GETTERS-N-SETTER                                       # #
     * # #                                                                                             # #
     * # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
     * # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
     * */

    public BinaryImage getRenderImage(){
        return renderImage;
    }

    public boolean isRepaintPending() {
        return repaintPending;
    }

    public void setRepaintPending(boolean repaintPending) {
        this.repaintPending = repaintPending;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public View getParent() {
        return parent;
    }

    public void setParent(View parent) {
        this.parent = parent;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    public int getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(int marginRight) {
        this.marginRight = marginRight;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public int getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setMarging(int marging){
        setMarginBottom(marging);
        setMarginTop(marging);
        setMarginLeft(marging);
        setMarginRight(marging);
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public byte getHeight_source() {
        return height_source;
    }

    public void setHeight_source(byte height_source) {
        this.height_source = height_source;
    }

    public byte getWidth_source() {
        return width_source;
    }

    public void setWidth_source(byte width_source) {
        this.width_source = width_source;
    }
}
