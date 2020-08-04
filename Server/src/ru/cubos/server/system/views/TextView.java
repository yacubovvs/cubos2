package ru.cubos.server.system.views;

import ru.cubos.server.Server;
import ru.cubos.server.helpers.BinaryImage;
import ru.cubos.server.helpers.BinaryImage_24bit;
import ru.cubos.server.helpers.Colors;
import ru.cubos.server.helpers.Strings;

import java.util.ArrayList;
import java.util.List;

public class TextView extends View {
    private String text;
    private int fontSize = 1;
    private boolean multiString = true;
    private byte[] textColor = Colors.COLOR_WHITE;
    private int lineSpacing = 5; // TODO: Need to update on fontsize change

    public TextView(String text){
        super();
        this.text = text;
        setPadding(10);
    }

    public TextView(){
        super();
        setPadding(10);
    }

    @Override
    public void draw() {
        if(!isRepaintPending()) return;

        List<String> stringList = null;
        if(isMultiString()) {
            // Multi string
            List<String> words = Strings.splitTextOnWords(getText());
            stringList = Strings.createStrings(getServer(), words, getFontSize(), getWidth() - getMarginRight() - getMarginLeft() - getPaddingRight() - getPaddingLeft());

            renderImage = new BinaryImage_24bit(getWidth(), getMarginTop() + getPaddingTop() + getMarginBottom() + getPaddingBottom() + getServer().settings.getSystemCharHeight() * getFontSize() * stringList.size() + (stringList.size()-1)*getLineSpacing());
        }else{
            // Single string
            renderImage = new BinaryImage_24bit(getWidth(), getMarginTop() + getPaddingTop() + getMarginBottom() + getPaddingBottom() + getServer().settings.getSystemCharHeight() * getFontSize());
        }

        drawBackGround();
        /*
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
        }*/

        String string = "";
        if(isMultiString()){
            // Multi string
            for (int i=0; i<stringList.size(); i++){
                string = stringList.get(i);
                int topOffset = i*getServer().settings.getSystemCharHeight()*getFontSize() + i*getLineSpacing();
                int LeftOffset = getPaddingLeft() + getMarginLeft();
                int placeForContent = renderImage.getWidth() - getMarginRight() - getMarginLeft() - getPaddingRight() - getPaddingLeft();
                int string_size = Strings.getStringWidth(getServer(), string, getFontSize());

                if(getHorizontalAlign()==View.HorizontalAlign.ALIGN_HORIZONTAL_LEFT) {
                    renderImage.drawString(getMarginLeft() + getPaddingLeft(), getMarginTop() + getPaddingTop() + topOffset, string, getTextColor(), getFontSize());
                }else if(getHorizontalAlign()==View.HorizontalAlign.ALIGN_HORIZONTAL_RIGHT){
                    renderImage.drawString(renderImage.getWidth() - getMarginLeft() - getPaddingLeft() - string_size, getMarginTop() + getPaddingTop() + topOffset, string, getTextColor(), getFontSize());
                }else if(getHorizontalAlign()==View.HorizontalAlign.ALIGN_HORIZONTAL_CENTER){
                    renderImage.drawString( LeftOffset + (placeForContent - string_size)/2, getMarginTop() + getPaddingTop() + topOffset, string, getTextColor(), getFontSize());
                }
            }

        }else{
            // Single string

            for(int x=0; x<getText().length() ; x++){
                int string_size = Strings.getStringWidth(getServer(), string + getText().charAt(x), getFontSize());
                if(string_size>renderImage.getWidth() - getPaddingLeft() - getMarginLeft() - getPaddingRight() - getMarginRight()) break;
                string += getText().charAt(x);
            }

            int string_size = Strings.getStringWidth(getServer(), string, getFontSize());
            if(getHorizontalAlign()==View.HorizontalAlign.ALIGN_HORIZONTAL_LEFT) {
                renderImage.drawString(getMarginLeft() + getPaddingLeft(), getMarginTop() + getPaddingTop(), string, getTextColor(), getFontSize());
            }else if(getHorizontalAlign()==View.HorizontalAlign.ALIGN_HORIZONTAL_RIGHT){
                renderImage.drawString(renderImage.getWidth() - getMarginLeft() - getPaddingLeft() - string_size, getMarginTop() + getPaddingTop(), string, getTextColor(), getFontSize());
            }else if(getHorizontalAlign()==View.HorizontalAlign.ALIGN_HORIZONTAL_CENTER){
                renderImage.drawString((renderImage.getWidth() - getMarginLeft() - getPaddingLeft() - string_size)/2, getMarginTop() + getPaddingTop(), string, getTextColor(), getFontSize());
            }
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

    public byte[] getTextColor() {
        return textColor;
    }

    public void setTextColor(byte[] textColor) {
        this.textColor = textColor;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        if(fontSize<=0) fontSize = 1;
        this.fontSize = fontSize;
    }

    public int getLineSpacing() {
        return lineSpacing;
    }

    public void setLineSpacing(int lineSpacing) {
        this.lineSpacing = lineSpacing;
    }
}
