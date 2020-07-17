package ru.cubos.connectors.emulator;

import ru.cubos.connectors.Connector;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Emulator extends JFrame implements Connector {
    private JPanel mainpanel;
    private JPanel imageWrapper;
    BufferedImage image;

    public int width = 320;
    public int height = 480;

    public Emulator(){
        setContentPane(mainpanel);
        setTitle("CubOS2 Emulator");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(new Dimension(320,480));
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        updateImage();
        setVisible(true);
    }

    public void updateImage(){
        ((ImagePanel)imageWrapper).setImage(image);
    }

    private void createUIComponents() {
        imageWrapper = new ImagePanel();
    }



    @Override
    public void transmitData(byte[] data) {

    }

    @Override
    public byte[] recieveData() {
        return new byte[0];
    }
}
