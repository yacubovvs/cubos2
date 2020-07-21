package ru.cubos.server.system.views;

import ru.cubos.server.helpers.BinaryImage;
import ru.cubos.server.helpers.Colors;

public class TextView extends View {
    private String text;
    private boolean multiString = true;

    public TextView(String text){
        super();
        this.text = text;
        setMarging(10);
    }

    public TextView(){
        super();
        setMarging(10);
    }

    @Override
    public void draw() {
        renderImage = new BinaryImage(getWidth(), getMarginTop() + getMarginBottom() + getServer().settings.getSystemCharHeight());
        renderImage.drawString(getMarginLeft(), getMarginTop(), getText(), Colors.COLOR_BLUE, 1);
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isMultiString() {
        return multiString;
    }

    public void setMultiString(boolean multiString) {
        this.multiString = multiString;
    }
}
