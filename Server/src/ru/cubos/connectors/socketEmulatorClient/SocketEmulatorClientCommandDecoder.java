package ru.cubos.connectors.socketEmulatorClient;

import ru.cubos.commonHelpers.CommandDecoder;

import java.awt.image.BufferedImage;

public class SocketEmulatorClientCommandDecoder extends CommandDecoder {
    private SocketClient socketClient;
    private BufferedImage bitmap;

    public SocketEmulatorClientCommandDecoder(SocketClient socketClient) {
        this.socketClient = socketClient;
        this.bitmap = socketClient.getBitmap();
    }

    @Override
    protected void setPixel(int x, int y, byte rgb[]){
        java.awt.Color color = new java.awt.Color(rgb[0]+128,rgb[1]+128,rgb[2]+128);
        bitmap.setRGB(x, y, color.getRGB());
    }

}
