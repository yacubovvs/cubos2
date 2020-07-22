package ru.cubos.server.system.views;

import ru.cubos.server.helpers.BinaryImage;

import java.io.IOException;

public class IconView extends View {
    private int scale = 1;
    BinaryImage icon;
    byte alfaColor[];

    public IconView(){
        super();
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
        renderImage = new BinaryImage(getWidth(), icon.getHeight() + getMarginTop() + getMarginBottom());

        renderImage.drawImage(getMarginLeft(), getMarginTop(), icon, alfaColor);
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
}
