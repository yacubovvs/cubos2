package ru.cubos.server.system.views;

import ru.cubos.server.Server;
import ru.cubos.server.helpers.BinaryImage;
import ru.cubos.server.helpers.Colors;
import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.views.containers.LinearContainer;

public abstract class View {

    private App appParent;

    public App getAppParent() {
        return appParent;
    }

    public void setAppParent(App appParent) {
        this.appParent = appParent;
    }

    public Runnable getOnTouchTapListener() {
        return onTouchTapListener;
    }

    public void setOnClickListener(Runnable onClickListener, App app) {
        this.onTouchTapListener = onClickListener;
        app.addEventView(this);
    }

    public Runnable getOnTouchUpListener() {
        return onTouchUpListener;
    }

    public void setOnTouchUpListener(Runnable onTouchUpListener) {
        this.onTouchUpListener = onTouchUpListener;
    }

    public Runnable getOnTouchMoveListener() {
        return onTouchMoveListener;
    }

    public void setOnTouchMoveListener(Runnable onTouchMoveListener) {
        this.onTouchMoveListener = onTouchMoveListener;
    }

    public Runnable getOnTouchMoveFinishedListener() {
        return onTouchMoveFinishedListener;
    }

    public void setOnTouchMoveFinishedListener(Runnable onTouchMoveFinishedListener) {
        this.onTouchMoveFinishedListener = onTouchMoveFinishedListener;
    }

    public Runnable getOnTouchZoomInListener() {
        return onTouchZoomInListener;
    }

    public void setOnTouchZoomInListener(Runnable onTouchZoomInListener) {
        this.onTouchZoomInListener = onTouchZoomInListener;
    }

    public Runnable getOnTouchZoomOutListener() {
        return onTouchZoomOutListener;
    }

    public void setOnTouchZoomOutListener(Runnable onTouchZoomOutListener) {
        this.onTouchZoomOutListener = onTouchZoomOutListener;
    }

    public Runnable getOnTouchZoomFinishedListener() {
        return onTouchZoomFinishedListener;
    }

    public void setOnTouchZoomFinishedListener(Runnable onTouchZoomFinishedListener) {
        this.onTouchZoomFinishedListener = onTouchZoomFinishedListener;
    }

    public Runnable getOnTouchDownListener() {
        return onTouchDownListener;
    }

    public void setOnTouchDownListener(Runnable onTouchDownListener) {
        this.onTouchDownListener = onTouchDownListener;
    }

    public Runnable getOnTouchZoomLongListener() {
        return onTouchZoomLongListener;
    }

    public void setOnTouchZoomLongListener(Runnable onTouchZoomLongListener) {
        this.onTouchZoomLongListener = onTouchZoomLongListener;
    }

    public enum SizeSource {
        SIZE_SOURCE_CONTENT,
        SIZE_SOURCE_FIXED,
        SIZE_SOURCE_K,
        SIZE_SOURCE_PARENT
    }

    public enum VerticalAlign {
        ALIGN_VERTICAL_TOP,
        ALIGN_VERTICAL_BOTTOM,
        ALIGN_VERTICAL_CENTER
    }

    public enum HorizontalAlign {
        ALIGN_HORIZONTAL_LEFT,
        ALIGN_HORIZONTAL_RIGHT,
        ALIGN_HORIZONTAL_CENTER
    }

    private int render_x_def; // Position on rendered image, used for listeners
    private int render_y_def; // Position on rendered image, used for listeners
    protected int render_x; // Position on rendered image, used for listeners
    protected int render_y; // Position on rendered image, used for listeners
    int render_height; // Size on rendered image, used for listeners
    int render_width; // Size on rendered image, used for listeners

    public int getRenderX() {
        return render_x;
    }

    public int getRenderY() {
        return render_y;
    }

    public int getRenderHeight() {
        return render_height;
    }

    public int getRenderWidth() {
        return render_width;
    }

    public void recountPositionOnRenderImage(int x, int y) {
        this.render_x += x;
        this.render_y += y;

        //if(getId()!=null) System.out.println("Recount " + getId() + ": " + this.content_x + ", " + this.content_y + "    -    " + content_width + ", " + content_height);
    }

    public void resetPositionsRenderImage(){
        this.render_x = render_x_def;
        this.render_y = render_y_def;
    }

    public void setPositionOnRenderImage(int x, int y) {
        this.render_x_def = x;
        this.render_y_def = y;
        this.render_x = x;
        this.render_y = y;
    }

    public void setSizeOnRenderImage(int width, int height) {
        this.render_width = width;
        this.render_height = height;
    }

    private View.VerticalAlign verticalAlign = View.VerticalAlign.ALIGN_VERTICAL_TOP;
    private View.HorizontalAlign horizontalAlign = View.HorizontalAlign.ALIGN_HORIZONTAL_LEFT;

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

    private SizeSource height_source = View.SizeSource.SIZE_SOURCE_CONTENT;
    private SizeSource width_source = View.SizeSource.SIZE_SOURCE_PARENT;

    protected BinaryImage renderImage;

    private Runnable onTouchTapListener;
    private Runnable onTouchUpListener;
    private Runnable onTouchDownListener;
    private Runnable onTouchMoveListener;
    private Runnable onTouchMoveFinishedListener;
    private Runnable onTouchZoomInListener;
    private Runnable onTouchZoomOutListener;
    private Runnable onTouchZoomFinishedListener;
    private Runnable onTouchZoomLongListener;

    public View() {

    }

    public App getApp() {
        if (getAppParent() == null) {
            if (getParent() == null) return null;
            return getParent().getAppParent();
        } else return getAppParent();
    }

    public void repaint() {
        if (this.repaintPending) {
            draw();
            setRepaintPending(false);
        }
    }

    public abstract void draw();

    protected void onRender() {
        if (height_source == View.SizeSource.SIZE_SOURCE_CONTENT) {
            height = renderImage.getHeight();
        }
        if (width_source == View.SizeSource.SIZE_SOURCE_CONTENT) {
            width = renderImage.getWidth();
        }
    }

    public BinaryImage getRenderImage() {
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

    protected int getContentHeight() {
        return renderImage.getHeight();
    }

    public int getHeight() {
        if (getHeight_source() == View.SizeSource.SIZE_SOURCE_CONTENT) {
            return getContentHeight();
        } else {
            return height;
        }
    }

    public void setHeight(int height) {
        setHeight_source(View.SizeSource.SIZE_SOURCE_FIXED);
        this.height = height;
    }

    public int getWidth() {
        // SIZE_SOURCE_CONTENT = 0;
        // SIZE_SOURCE_FIXED = 1;
        // SIZE_SOURCE_PERCENT = 2;
        // SIZE_SOURCE_PARENT = 3;

        if (getWidth_source() == View.SizeSource.SIZE_SOURCE_FIXED) {
            return width;
        } else if (getWidth_source() == View.SizeSource.SIZE_SOURCE_K) {
            if (getParent() != null) return (int) (getParent().getWidth() * width_k);
            //else return (int) (server.display.getWidth() * width_k);
            else return (int) (getApp().getWindowWidth() * width_k);
        } else if (getWidth_source() == View.SizeSource.SIZE_SOURCE_PARENT) {
            if (getParent() != null) {
                if (getParent().isLinearContainer()) {
                    if (((LinearContainer) getParent()).getType() == LinearContainer.Type.HORIZONTAL) {
                        return (getParent().getWidth()) / (((LinearContainer) getParent()).getChildren().size());
                    }
                }
                return (int) (getParent().getWidth());
            //} else return (int) (server.display.getWidth());
            } else return (int) (getApp().getWindowWidth());
            //}else if (getWidth_source()==SIZE_SOURCE_CONTENT){ //TODO: Make it later, width by content size
        } else {
            return getParent().getWidth();
        }

    }

    public void setWidth(int width) {
        setWidth_source(View.SizeSource.SIZE_SOURCE_FIXED);
        this.width = width;
    }

    public void setMargin(int marging) {
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

    protected View.SizeSource getHeight_source() {
        return height_source;
    }

    protected void setHeight_source(View.SizeSource height_source) {
        this.height_source = height_source;
    }

    protected View.SizeSource getWidth_source() {
        return width_source;
    }

    protected void setWidth_source(View.SizeSource width_source) {
        this.width_source = width_source;
    }

    public byte[] getBackgroundColor() {
        if (backgroundColor == null) {
            if (getParent() == null) return Colors.COLOR_BLACK;
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

    public void setPadding(int padding) {
        setPaddingBottom(padding);
        setPaddingTop(padding);
        setPaddingLeft(padding);
        setPaddingRight(padding);
    }

    public View.VerticalAlign getVerticalAlign() {
        return verticalAlign;
    }

    public void setVerticalAlign(View.VerticalAlign verticalAlign) {
        this.verticalAlign = verticalAlign;
    }

    public View.HorizontalAlign getHorizontalAlign() {
        return horizontalAlign;
    }

    public void setHorizontalAlign(View.HorizontalAlign horizontalAlign) {
        this.horizontalAlign = horizontalAlign;
    }

    public float getWidth_k() {
        return width_k;
    }

    public void setWidth_k(double width_k) {
        setWidth_k((float) width_k);
    }

    public void setWidth_k(float width_k) {
        setWidth_source(View.SizeSource.SIZE_SOURCE_K);
        this.width_k = width_k;
    }

    public float getHeight_k() {
        setHeight_source(View.SizeSource.SIZE_SOURCE_K);
        return height_k;
    }

    public void setHeight_k(float height_k) {
        this.height_k = height_k;
    }

    public boolean isLinearContainer() {
        return false;
    }

    protected void drawBackGround() {
        if (getMarginTop() == 0 && getMarginLeft() == 0 && getMarginBottom() == 0 && getMarginRight() == 0) {
            renderImage.drawRect(0, 0, renderImage.getWidth(), renderImage.getHeight(), getBackgroundColor(), true);
        } else {
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

    protected int getPaddingTopBottom() {
        return getPaddingBottom() + getPaddingTop();
    }

    protected int getMarginTopBottom() {
        return getMarginBottom() + getMarginTop();
    }
}
