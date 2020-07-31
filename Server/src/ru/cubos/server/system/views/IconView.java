package ru.cubos.server.system.views;

import ru.cubos.server.helpers.BinaryImage;

import java.io.IOException;

public class IconView extends View {
    private int scale = 1;
    BinaryImage icon;
    private byte alfaColor[];

    public IconView(){
        super();
    }

    public IconView(String path){
        super();
        try {
            loadImage(path);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server: Counldn't find icon path " + path);
        }
    }

    public IconView(String path,  byte[] alfaColor){
        super();
        try {
            loadImage(path, alfaColor);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server: Counldn't find icon path " + path);
        }
    }

    public void loadImage(String path) throws IOException {
        icon = new BinaryImage(path);
    }

    public void loadImage(String path, byte[] alfaColor) throws IOException {
        this.alfaColor = alfaColor;
        icon = new BinaryImage(path);
    }

    @Override
    public void draw() {
        renderImage = new BinaryImage(getWidth(), icon.getHeight() + getPaddingTop() + getMarginTop() + getMarginBottom() + getPaddingBottom());

        drawBackGround();

        if(getHorizontalAlign()==View.HorizontalAlign.ALIGN_HORIZONTAL_LEFT) {
            renderImage.drawImage(getMarginLeft() + getPaddingLeft(), getMarginTop() + getPaddingTop(), icon, alfaColor);
        }else if(getHorizontalAlign()==View.HorizontalAlign.ALIGN_HORIZONTAL_RIGHT){
            renderImage.drawImage(getWidth() - getMarginLeft() - getPaddingLeft() - getIcon().getWidth(), getMarginTop() + getPaddingTop(), icon, alfaColor);
        }else if(getHorizontalAlign()==View.HorizontalAlign.ALIGN_HORIZONTAL_CENTER){
            renderImage.drawImage(getMarginLeft() + getPaddingLeft() + (getWidth() - getMarginLeft() - getPaddingLeft() - getMarginRight() - getPaddingRight() - getIcon().getWidth())/2, getMarginTop() + getPaddingTop(), icon, alfaColor);
        }

        super.onRender();
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

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public BinaryImage getIcon(){
        return icon;
    }

    @Override
    public int getWidth() {
        if (getWidth_source() == SizeSource.SIZE_SOURCE_CONTENT) {
            if(icon!=null) return icon.getWidth() + getPaddingLeftRight() + getMarginLeftRight();
            else return icon.getWidth() + getPaddingLeftRight() + getMarginLeftRight();
        }else return super.getWidth();
    }
}
