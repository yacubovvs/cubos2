package ru.cubos.connectors.socketEmulatorClient;

import ru.cubos.connectors.Connector;
import ru.cubos.connectors.emulator.Emulator;
import ru.cubos.connectors.sockets.ClientSocket;
import ru.cubos.connectors.sockets.ClientSocket_Updater;

import java.awt.image.BufferedImage;

public class SocketClient extends Emulator implements Connector, ClientSocket_Updater {
    ClientSocket clientSocket;

    public SocketClient(int width, int height) {
        super(width, height);
    }

    @Override
    public boolean sendToServer(byte[] data) {
        //clientSocket.messagesToSend.add(data);
        clientSocket.addMessage(data);
        return true;
    }

    @Override
    public BufferedImage getBitmap() {
        return getImage();
    }

    public void start(String host, int port){
        clientSocket = new ClientSocket(host, port, this);
    }
}
