package ru.cubos.connectors.emulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    private BufferedImage image;
    private JPanel imageWrapper;

    float image_k = 1;
    boolean vertivalOffset = false;
    int offsetSize = 0;

    public ImagePanel() {
    }

    public void loadImage(BufferedImage image){
        setLayout(new BorderLayout());
        setImage(image);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        if(image != null){

            float panel_k = (float)getWidth()/(float)getHeight();
            image_k = (float)image.getWidth()/(float)image.getHeight();

            if(panel_k>image_k){
                vertivalOffset = false;
                offsetSize = (getWidth() - (int)(getHeight()*image_k))/2;
                g.drawImage(image,  offsetSize, 0, (int)(getHeight()*image_k), getHeight(), null);
            }else{
                vertivalOffset = true;
                offsetSize = (getHeight() - (int)(getWidth()/image_k))/2;
                g.drawImage(image, 0, offsetSize, getWidth(),  (int)(getWidth()/image_k), null);
            }

        }

    }

}