package ru.cubos.server.settings;

import ru.cubos.commonHelpers.Colors;

import static ru.cubos.connectors.Protocol.*;

public class Settings {

    /*
     *
     *  SCREEN
     *
     * */

    private char systemScreenWidth = 240;
    private char systemScreenHeight = 240;
    private byte systemScreenColorScheme = _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8;
    /*
     *
     *  FONTS
     *
     * */

    private char systemCharWidth = 6;
    private char systemCharHeight = 8;

    /*
     *
     *  SCROLLBAR
     *
     * */

    private int     scrollbarWidth              = 6;
    private int     scrollbarPointWidth         = 6;
    private int     scrollbarTouchActiveArea    = scrollbarWidth + scrollbarPointWidth + 10;
    private byte[]  scrollbarColor              = Colors.COLOR_LIGHT_GRAY;
    private boolean dragScrollBarEnable         = true;
    private boolean scrollingByContentDrag      = true;

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

    private boolean     buttonBarEnable         = false;
    private char        buttonBarHeight         = 32;
    private byte[]      buttonBarColor          = Colors.COLOR_LIGHT_GRAY;
    private byte[]      buttonBarButtonsColor   = Colors.COLOR_DARK_GRAY;
    private char        buttonBarButtonSize     = 12;
    private char        buttonBarButtonMargin   = 70;


    /*
     *
     *  WINDOW MODE
     *
     * */

    private boolean     windowMode                      = false;
    private int         cornerWindowResizeSize          = 12;
    private int         windowBorderWidth               = 2;
    private int         windowBorderActiveOffsetWidth   = 8; // Active area out of window's borders to get click on border
    private int         windowTitleBarHeight            = 30;
    private byte[]      windowBorderColor               = Colors.COLOR_DARK_GRAY;
    private byte[]      windowTitleColor                = Colors.COLOR_DARK_BLUE;


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
        if(!isStatusBarEnable()) return 0; else return statusBarHeight;
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
        if(isButtonBarEnable()) return buttonBarHeight;
        else return 0;
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
        if(isButtonBarEnable()) return buttonBarButtonSize;
        else return 0;
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

    public int getScrollbarWidth() {
        return scrollbarWidth;
    }

    public void setScrollbarWidth(int scrollbarWidth) {
        this.scrollbarWidth = scrollbarWidth;
    }

    public byte[] getScrollbarColor() {
        return scrollbarColor;
    }

    public void setScrollbarColor(byte[] scrollbarColor) {
        this.scrollbarColor = scrollbarColor;
    }

    public int getScrollbarPointWidth() {
        return scrollbarPointWidth;
    }

    public void setScrollbarPointWidth(int scrollbarPointWidth) {
        this.scrollbarPointWidth = scrollbarPointWidth;
    }

    public boolean isDragScrollBarEnable() {
        return dragScrollBarEnable;
    }

    public void setDragScrollBarEnable(boolean dragScrollBarEnable) {
        this.dragScrollBarEnable = dragScrollBarEnable;
    }

    public int getScrollbarTouchActiveArea() {
        return scrollbarTouchActiveArea;
    }

    public void setScrollbarTouchActiveArea(int scrollbarTouchActiveArea) {
        this.scrollbarTouchActiveArea = scrollbarTouchActiveArea;
    }

    public boolean isScrollingByContentDrag() {
        return scrollingByContentDrag;
    }

    public void setScrollingByContentDrag(boolean scrollingByContentDrag) {
        this.scrollingByContentDrag = scrollingByContentDrag;
    }

    public boolean isWindowMode() {
        return windowMode;
    }

    public void setWindowMode(boolean windowMode) {
        this.windowMode = windowMode;
    }

    public int getWindowBorderWidth() {
        if(isWindowMode()) return windowBorderWidth;
        else return 0;
    }

    public void setWindowBorderWidth(int windowBorderWidth) {
        this.windowBorderWidth = windowBorderWidth;
    }

    public int getWindowTitleBarHeight() {
        if(isWindowMode()) return windowTitleBarHeight;
        else return 0;

    }

    public void setWindowTitleBarHeight(int windowTitleBarHeight) {
        this.windowTitleBarHeight = windowTitleBarHeight;
    }

    public byte[] getWindowBorderColor() {
        return windowBorderColor;
    }

    public void setWindowBorderColor(byte[] windowBorderColor) {
        this.windowBorderColor = windowBorderColor;
    }

    public byte[] getWindowTitleColor() {
        return windowTitleColor;
    }

    public void setWindowTitleColor(byte[] windowTitleColor) {
        this.windowTitleColor = windowTitleColor;
    }

    public int getWindowBorderActiveOffsetWidth() {
        return windowBorderActiveOffsetWidth;
    }

    public void setWindowBorderActiveOffsetWidth(int windowBorderActiveOffsetWidth) {
        this.windowBorderActiveOffsetWidth = windowBorderActiveOffsetWidth;
    }

    public int getCornerWindowResizeSize() {
        return cornerWindowResizeSize;
    }

    public void setCornerWindowResizeSize(int cornerWindowResizeSize) {
        this.cornerWindowResizeSize = cornerWindowResizeSize;
    }

    public char getSystemScreenWidth() {
        return systemScreenWidth;
    }

    public void setSystemScreenWidth(char systemScreenWidth) {
        this.systemScreenWidth = systemScreenWidth;
    }

    public char getSystemScreenHeight() {
        return systemScreenHeight;
    }

    public void setSystemScreenHeight(char systemScreenHeight) {
        this.systemScreenHeight = systemScreenHeight;
    }

    public byte getSystemScreenColorScheme() {
        return systemScreenColorScheme;
    }

    public void setSystemScreenColorScheme(byte systemScreenColorScheme) {
        this.systemScreenColorScheme = systemScreenColorScheme;
    }
}
