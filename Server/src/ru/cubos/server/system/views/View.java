package ru.cubos.server.system.views;

import java.util.List;

public abstract class View {
    protected String id;
    protected boolean visible = true;
    protected View parent = null;
    protected int marginLeft = 0;
    protected int marginRight = 0;
    protected int marginTop = 0;
    protected int marginBottom = 0;

    public View(){ }
}
