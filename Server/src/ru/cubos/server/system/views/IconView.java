package ru.cubos.server.system.views;

import ru.cubos.commonHelpers.binaryImages.BinaryImage;
import ru.cubos.commonHelpers.binaryImages.BinaryImage_24bit;
import ru.cubos.commonHelpers.binaryImages.BinaryImage_24bit_alpha;

import java.io.IOException;

public class IconView extends View {
    private int scale = 1;
    BinaryImage icon;
    private byte alphaColor[];

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

    public IconView(String path, boolean isAlpha){
        super();
        try {
            loadImage(path, isAlpha);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server: Counldn't find icon path " + path);
        }
    }

    public IconView(String path,  byte[] alphaColor){
        super();
        try {
            loadImage(path, alphaColor);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server: Counldn't find icon path " + path);
        }
    }

    public void loadImage(String path) throws IOException {
        icon = new BinaryImage_24bit(path);
        return;
    }

    public void loadImage(String path, boolean isAlphaImage) throws IOException {
        if(isAlphaImage) icon = new BinaryImage_24bit_alpha(path);
        else icon = new BinaryImage_24bit(path);
        icon.getBufferedImageImage();
        return;
    }

    public void loadImage(String path, byte[] alfaColor) throws IOException {
        this.alphaColor = alfaColor;
        icon = new BinaryImage_24bit(path);
        return;
    }

    @Override
    public void draw() {
        if(!isRepaintPending()) return;

        if(icon.type== BinaryImage.Type.COLOR_24BIT) renderImage = new BinaryImage_24bit(getWidth(), icon.getHeight() + getPaddingTop() + getMarginTop() + getMarginBottom() + getPaddingBottom());
        else renderImage = new BinaryImage_24bit_alpha(getWidth(), icon.getHeight() + getPaddingTop() + getMarginTop() + getMarginBottom() + getPaddingBottom());

        drawBackGround();

        if(getHorizontalAlign()==View.HorizontalAlign.ALIGN_HORIZONTAL_LEFT) {
            renderImage.drawImage(getMarginLeft() + getPaddingLeft(), getMarginTop() + getPaddingTop(), icon, alphaColor);
        }else if(getHorizontalAlign()==View.HorizontalAlign.ALIGN_HORIZONTAL_RIGHT){
            renderImage.drawImage(getWidth() - getMarginLeft() - getPaddingLeft() - getIcon().getWidth(), getMarginTop() + getPaddingTop(), icon, alphaColor);
        }else if(getHorizontalAlign()==View.HorizontalAlign.ALIGN_HORIZONTAL_CENTER){
            renderImage.drawImage(getMarginLeft() + getPaddingLeft() + (getWidth() - getMarginLeft() - getPaddingLeft() - getMarginRight() - getPaddingRight() - getIcon().getWidth())/2, getMarginTop() + getPaddingTop(), icon, alphaColor);
        }

        super.onRender();
        setRepaintPending(false);
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
