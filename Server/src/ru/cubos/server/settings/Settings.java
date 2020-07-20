package ru.cubos.server.settings;

public class Settings {

    /*
     *  System
     * */

    private char systemCharWidth = 6;
    private char systemCharHeight = 7;


    /*
    *  STATUSBAR
    * */

    private boolean     statusBarEnable         = true;
    private char        statusBarHeight         = 32;
    private byte[]      statusBarColor          = new byte[]{100, 100, 100};
    private byte[]      statusTextColor         = new byte[]{-100, -100, -100};
    private char        statusTextSize          = 2;
    private char        statusLeftRightMargin   = 5;

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

    public char getStatusTextSize() {
        return statusTextSize;
    }

    public void setStatusTextSize(char statusTextSize) {
        this.statusTextSize = statusTextSize;
    }

    public byte[] getStatusTextColor() {
        return statusTextColor;
    }

    public void setStatusTextColor(byte[] statusTextColor) {
        this.statusTextColor = statusTextColor;
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

    public char getStatusLeftRightMargin() {
        return statusLeftRightMargin;
    }

    public void setStatusLeftRightMargin(char statusLeftRightMargin) {
        this.statusLeftRightMargin = statusLeftRightMargin;
    }
}
