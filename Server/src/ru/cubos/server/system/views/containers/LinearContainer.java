package ru.cubos.server.system.views.containers;

import ru.cubos.server.helpers.BinaryImage;
import ru.cubos.server.system.views.View;

import java.util.ArrayList;
import java.util.List;

public class LinearContainer extends ContainerView {

    public enum Type{
        VERTICAL,
        HORIZONTAL
    }

    private Type type = Type.VERTICAL;

    public LinearContainer(){
        super();
    }

    @Override
    public void recountRenderPositions(){
        // Recount elements positions
        for(View view: children){
            view.recountPositionOnRenderImage(this.render_x, this.render_y);
        }
    }

    @Override
    public void recountPositionOnRenderImage(int x, int y){
        super.recountPositionOnRenderImage(x,y);
        recountRenderPositions();
    }

    @Override
    public void draw() {
        int size = 0;

        for(View view: children){
            view.repaint();
            if(getType()==LinearContainer.Type.HORIZONTAL) size += view.getWidth();
            else size += view.getHeight();
        }

        if(getType()==LinearContainer.Type.HORIZONTAL){
            renderImage = new BinaryImage(Math.max(size, getWidth()), getHeight() + getPaddingTopBottom() + getMarginTopBottom());
        } else {
            renderImage = new BinaryImage(getWidth(), size + getPaddingTopBottom() + getMarginTopBottom());
        }

        drawBackGround();

        int position = 0;

        if(getType()== Type.VERTICAL){
            position += getMarginTop() + getPaddingTop();
        }

        for(View view: children){
            int draw_x;
            int draw_y;
            int renderSizes[];

            if(getType()==LinearContainer.Type.HORIZONTAL){
                draw_x = position;
                draw_y = 0;
                position += view.getRenderImage().getWidth();
            }else{
                draw_x = getMarginLeft() + getPaddingLeft();
                draw_y = position;
                position += view.getRenderImage().getHeight();
            }

            renderSizes = renderImage.drawImage(draw_x, draw_y, view.getRenderImage());
            view.setPositionOnRenderImage(draw_x, draw_y);
            view.setSizeOnRenderImage(renderSizes[0], renderSizes[1]);
        }

        if(getAppParent()!=null){
            getAppParent().setRenderImage(renderImage);
        }

    }

    public LinearContainer.Type getType() {
        return type;
    }

    public void setType(LinearContainer.Type type) {
        this.type = type;
    }

    @Override
    public boolean isLinearContainer(){
        return true;
    }
}
