package ru.cubos.server.system.views.containers;

import ru.cubos.server.helpers.BinaryImage;
import ru.cubos.server.system.views.View;
import ru.cubos.server.system.views.containers.ContainerView;

import java.util.ArrayList;
import java.util.List;

public class LinearContainer extends ContainerView {
    static public final byte VERTICAL = 0;
    static public final byte HORIZONTAL = 1;

    private byte type = 0;

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
            if(getType()==HORIZONTAL) size += view.getWidth();
            else size += view.getHeight();
        }

        if(getType()==HORIZONTAL) renderImage = new BinaryImage(size, getHeight());
        else renderImage = new BinaryImage(getWidth(), size);

        drawBackGround();

        int position = 0;
        for(BinaryImage image: imageList){
            if(getType()==HORIZONTAL){
                renderImage.drawImage(position, 0, image);
                position += image.getWidth();
            }else{
                renderImage.drawImage(0, position, image);
                position += image.getHeight();
            }
        }

        if(getAppParent()!=null) getAppParent().setRenderImage(renderImage);
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    /*
    protected void updateChildrenWidth(){
        if(getType()==VERTICAL) {
            for (View view : children) {
                view.setWidth(getWidth());
            }
        }else{
            // TODO: Change in past
            for (View view : children) {
                view.setWidth(getWidth());
            }
        }
    }
    */

    @Override
    public void add(View view){
        super.add(view);
        view.setParent(this);
        //updateChildrenWidth();
    }

    @Override
    public void setWidth(int width){
        super.setWidth(width);
        //updateChildrenWidth();
    }

    @Override
    public boolean isLinearContainer(){
        return true;
    }
}
