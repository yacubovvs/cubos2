package ru.cubos.server.settings;

import ru.cubos.server.helpers.Colors;

public class Settings {

    /*
     *
     *  SYSTEM
     *
     * */

    private char systemCharWidth = 6;
    private char systemCharHeight = 7;


    /*
    *
    *  STATUSBAR
    *
    * */

    private boolean     statusBarEnable             = true;
    private char        statusBarHeight             = 32;
    private byte[]      statusBarColor              = Colors.COLOR_LIGHT_GRAY;
    private byte[]      statusBarTextColor          = Colors.COLOR_DARK_GRAY;
    private char        statusBarTextSize           = 2;
    private char        statusBarLeftRightMargin    = 10;

    /*
     *
     *  BUTTONBAR
     *
     * */

    private boolean     buttonBarEnable         = true;
    private char        buttonBarHeight         = 32;
    private byte[]      buttonBarColor          = Colors.COLOR_LIGHT_GRAY;
    private byte[]      buttonBarButtonsColor   = Colors.COLOR_DARK_GRAY;
    private char        buttonBarButtonSize     = 12;
    private char        buttonBarButtonMargin   = 70;

    /*
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * *                                                                                                             * *
     * *                                             GETTERS AND SETTERS                                             * *
     * *                                                                                                             * *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     */

    public boolean isStatusBarEnable() {
        return statusBarEnable;
    }

    public void setStatusBarEnable(boolean statusBarEnable) {
        this.statusBarEnable = statusBarEnable;
    }

    public char getStatusBarHeight() {
        return statusBarHeight;
    }

    public void setStatusBarHeight(char statusBarHeight) {
        this.statusBarHeight = statusBarHeight;
    }

    public byte[] getStatusBarColor() {
        return statusBarColor;
    }

    public void setStatusBarColor(byte[] statusBarColor) {
        this.statusBarColor = statusBarColor;
    }

    public char getStatusBarTextSize() {
        return statusBarTextSize;
    }

    public void setStatusBarTextSize(char statusBarTextSize) {
        this.statusBarTextSize = statusBarTextSize;
    }

    public byte[] getStatusBarTextColor() {
        return statusBarTextColor;
    }

    public void setStatusBarTextColor(byte[] statusBarTextColor) {
        this.statusBarTextColor = statusBarTextColor;
    }

    public char getSystemCharWidth() {
        return systemCharWidth;
    }

    public void setSystemCharWidth(char systemCharWidth) {
        this.systemCharWidth = systemCharWidth;
    }

    public char getSystemCharHeight() {
        return systemCharHeight;
    }

    public void setSystemCharHeight(char systemCharHeight) {
        this.systemCharHeight = systemCharHeight;
    }

    public char getStatusBarLeftRightMargin() {
        return statusBarLeftRightMargin;
    }

    public void setStatusBarLeftRightMargin(char statusBarLeftRightMargin) {
        this.statusBarLeftRightMargin = statusBarLeftRightMargin;
    }

    public boolean isButtonBarEnable() {
        return buttonBarEnable;
    }

    public void setButtonBarEnable(boolean buttonBarEnable) {
        this.buttonBarEnable = buttonBarEnable;
    }

    public char getButtonBarHeight() {
        return buttonBarHeight;
    }

    public void setButtonBarHeight(char buttonBarHeight) {
        this.buttonBarHeight = buttonBarHeight;
    }

    public byte[] getButtonBarColor() {
        return buttonBarColor;
    }

    public void setButtonBarColor(byte[] buttonBarColor) {
        this.buttonBarColor = buttonBarColor;
    }

    public byte[] getButtonBarButtonsColor() {
        return buttonBarButtonsColor;
    }

    public void setButtonBarButtonsColor(byte[] buttonBarButtonsColor) {
        this.buttonBarButtonsColor = buttonBarButtonsColor;
    }

    public char getButtonBarButtonSize() {
        return buttonBarButtonSize;
    }

    public void setButtonBarButtonSize(char buttonBarButtonSize) {
        this.buttonBarButtonSize = buttonBarButtonSize;
    }

    public char getButtonBarButtonMargin() {
        return buttonBarButtonMargin;
    }

    public void setButtonBarButtonMargin(char buttonBarButtonMargin) {
        this.buttonBarButtonMargin = buttonBarButtonMargin;
    }
}
