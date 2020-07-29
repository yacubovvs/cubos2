package ru.cubos.server.system.events;

public abstract class MouseEvent extends Event {
    protected int x = 0;
    protected int y = 0;

    public MouseEvent(int x, int y){
        super();

        setX(x);
        setY(y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
