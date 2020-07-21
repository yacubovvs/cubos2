package ru.cubos.server.system.views;

import ru.cubos.server.helpers.BinaryImage;

import java.util.ArrayList;
import java.util.List;

public class LinearContainer extends ContainerView {
    static final byte VERTICAL = 0;
    static final byte HORIZONTAL = 1;

    public LinearContainer(){

    }

    @Override
    public void draw() {
        List<BinaryImage> imageList = new ArrayList<>();
        int height = 0;

        for(View view: children){
            view.repaint();
            BinaryImage image = view.getRenderImage();
            imageList.add(image);
            height += view.getHeight();
        }

        renderImage = new BinaryImage(getWidth(), height);
        int height_position = 0;
        for(BinaryImage image: imageList){
            renderImage.drawImage(0, height_position, image);
            height_position += image.getHeight();
        }

        if(getAppParent()!=null) getAppParent().setRenderImage(renderImage);
    }

    private byte type = 0;

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    protected void updateChildrenWidth(){
        if(getType()==VERTICAL) {
            for (View view : children) {
                view.setWidth(getWidth());
            }
        }else{
            // TODO: Change in last
            for (View view : children) {
                view.setWidth(getWidth());
            }
        }
    }

    @Override
    public void add(View view){
        super.add(view);
        updateChildrenWidth();
    }

    @Override
    public void setWidth(int width){
        super.setWidth(width);
        updateChildrenWidth();
    }
}
