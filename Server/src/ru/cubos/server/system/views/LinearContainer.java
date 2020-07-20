package ru.cubos.server.system.views;

public class LinearContainer extends ViewContainer {
    static final byte VERTICAL = 0;
    static final byte HORIZONTAL = 1;

    public LinearContainer(){

    }

    private byte type = 0;

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}
