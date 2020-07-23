package ru.cubos.server.system.views;

import ru.cubos.server.Server;
import ru.cubos.server.helpers.BinaryImage;
import ru.cubos.server.helpers.Colors;
import ru.cubos.server.system.views.containers.LinearContainer;

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
    private float width_k;
    private float height_k;
    private Server server;

    private byte[] backgroundColor = null;

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

    protected int getContentHeight(){
        return renderImage.getHeight();
    }

    public int getHeight() {
        if(getHeight_source()==SIZE_SOURCE_CONTENT){
            return getContentHeight();
        } else{
            return height;
        }
    }

    public void setHeight(int height) {
        setHeight_source(SIZE_SOURCE_FIXED);
        this.height = height;
    }

    public int getWidth() {
        // SIZE_SOURCE_CONTENT = 0;
        // SIZE_SOURCE_FIXED = 1;
        // SIZE_SOURCE_PERCENT = 2;
        // SIZE_SOURCE_PARENT = 3;

        if (getWidth_source()==SIZE_SOURCE_FIXED){
            return width;
        }else if (getWidth_source()==SIZE_SOURCE_PERCENT){
            if(getParent()!=null) return (int)(getParent().getWidth()* width_k);
            else return (int)(server.display.getWidth()* width_k);
        }else if (getWidth_source()==SIZE_SOURCE_PARENT){
            if(getParent()!=null){
                if(getParent().isLinearContainer()){
                    if(((LinearContainer)getParent()).getType()==LinearContainer.HORIZONTAL){
                        return (getParent().getWidth()) / (((LinearContainer) getParent()).getChildren().size());
                    }
                }
                return (int)(getParent().getWidth());
            }
            else return (int)(server.display.getWidth());
        //}else if (getWidth_source()==SIZE_SOURCE_CONTENT){ //TODO: Make it later, width by content size
        }else{
            return getParent().getWidth();
        }

    }

    public void setWidth(int width) {
        setWidth_source(SIZE_SOURCE_FIXED);
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

    protected byte getHeight_source() {
        return height_source;
    }

    protected void setHeight_source(byte height_source) {
        this.height_source = height_source;
    }

    protected byte getWidth_source() {
        return width_source;
    }

    protected void setWidth_source(byte width_source) {
        this.width_source = width_source;
    }

    public byte[] getBackgroundColor() {
        if(backgroundColor==null){
            if(getParent()==null) return Colors.COLOR_BLACK;
            else return getParent().getBackgroundColor();
        } else return backgroundColor;
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

    public float getWidth_k() {
        return width_k;
    }

    public void setWidth_k(double width_k) {
        setWidth_k((float)width_k);
    }

    public void setWidth_k(float width_k) {
        setWidth_source(SIZE_SOURCE_PERCENT);
        this.width_k = width_k;
    }

    public float getHeight_k() {
        setHeight_source(SIZE_SOURCE_PERCENT);
        return height_k;
    }

    public void setHeight_k(float height_k) {
        this.height_k = height_k;
    }

    public boolean isLinearContainer(){
        return false;
    }

    protected void drawBackGround(){
        if(getMarginTop()==0 && getMarginLeft()==0 && getMarginBottom()==0 && getMarginLeft()==0){
            renderImage.drawRect(0, 0, renderImage.getWidth(), renderImage.getHeight(), getBackgroundColor(), true);
        }else {
            byte parentBackgroundColor[];
            if (getParent() != null) {
                parentBackgroundColor = getParent().getBackgroundColor();
            } else {
                parentBackgroundColor = Colors.COLOR_BLACK;
            }

            renderImage.drawRect(0, 0, renderImage.getWidth(), renderImage.getHeight(), parentBackgroundColor, true);
            renderImage.drawRect(getMarginLeft(), getMarginTop(), renderImage.getWidth() - getMarginRight(), renderImage.getHeight() - getMarginBottom(), getBackgroundColor(), true);
        }
    }
}
