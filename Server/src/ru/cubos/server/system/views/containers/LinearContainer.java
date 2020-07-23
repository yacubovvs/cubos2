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
    public void draw() {
        List<BinaryImage> imageList = new ArrayList<>();
        int size = 0;

        for(View view: children){
            view.repaint();
            BinaryImage image = view.getRenderImage();
            imageList.add(image);
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
        for(BinaryImage image: imageList){
            if(getType()==LinearContainer.Type.HORIZONTAL){
                renderImage.drawImage(position, 0, image);
                position += image.getWidth();
            }else{
                renderImage.drawImage(getMarginLeft() + getPaddingLeft(), position, image);
                position += image.getHeight();
            }
        }

        if(getAppParent()!=null) getAppParent().setRenderImage(renderImage);
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
