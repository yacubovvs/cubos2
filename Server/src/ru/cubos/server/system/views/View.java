package ru.cubos.server.system.views;

import ru.cubos.server.Server;
import ru.cubos.server.helpers.BinaryImage;
import ru.cubos.server.helpers.Colors;

public abstract class View {
    public static final byte SIZE_SOURCE_CONTENT = 0;
    public static final byte SIZE_SOURCE_FIXED = 1;
    public static final byte SIZE_SOURCE_PERCENT = 2;
    public static final byte SIZE_SOURCE_PARENT = 3;

    public static final byte ALIGN_VERTICAL_TOP = 0;
    public static final byte ALIGN_VERTICAL_BOTTOM = 1;
    public static final byte ALIGN_VERTICAL_CENTER = 2;
    public static final byte ALIGN_HORIZONTAL_LEFT = 3;
    public static final byte ALIGN_HORIZONTAL_RIGHT = 4;
    public static final byte ALIGN_HORIZONTAL_CENTER = 5;

    private byte verticalAlign = ALIGN_VERTICAL_TOP;
    private byte horizontalAlign = ALIGN_HORIZONTAL_LEFT;

    private String id;
    private boolean visible = true;
    private View parent = null;

    private int marginLeft = 0;
    private int marginRight = 0;
    private int marginTop = 0;
    private int marginBottom = 0;
    private int paddingLeft = 0;
    private int paddingRight = 0;
    private int paddingTop = 0;
    private int paddingBottom = 0;

    private boolean repaintPending = true;
    private int height;
    private int width;
    private Server server;

    private byte[] backgroundColor = Colors.COLOR_BLACK;

    private byte height_source = SIZE_SOURCE_CONTENT;
    private byte width_source = SIZE_SOURCE_PARENT;

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

    public void setMargin(int marging){
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

    public byte[] getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(byte[] backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public void setPadding(int padding){
        setPaddingBottom(padding);
        setPaddingTop(padding);
        setPaddingLeft(padding);
        setPaddingRight(padding);
    }

    public byte getVerticalAlign() {
        return verticalAlign;
    }

    public void setVerticalAlign(byte verticalAlign) {
        this.verticalAlign = verticalAlign;
    }

    public byte getHorizontalAlign() {
        return horizontalAlign;
    }

    public void setHorizontalAlign(byte horizontalAlign) {
        this.horizontalAlign = horizontalAlign;
    }
}
