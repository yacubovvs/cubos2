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
    protected void setPixel(int x, int y, int rgb){
        bitmap.setRGB(x, y, rgb);
    }

}
