package ru.cubos.connectors.socketEmulatorClient;

import ru.cubos.connectors.ClientSessionSettings;
import ru.cubos.connectors.Connectorable;
import ru.cubos.connectors.emulator.Emulator;
import ru.cubos.connectors.sockets.ClientSocket;
import ru.cubos.connectors.sockets.ClientSocket_Updater;
import ru.cubos.server.framebuffer.Display;

import java.awt.image.BufferedImage;

public class SocketClient extends Emulator implements ClientSocket_Updater {
    ClientSocket clientSocket;
    SocketEmulatorClientCommandDecoder socketEmulatorClientCommandDecoder;

    public SocketClient() {
        super(ClientSessionSettings.screen_width, ClientSessionSettings.screen_height);
        socketEmulatorClientCommandDecoder = new SocketEmulatorClientCommandDecoder(this);
    }

    public boolean sendToServer(byte[] data) {
        clientSocket.addMessage(data);
        return true;
    }

    public BufferedImage getBitmap() {
        return getImage();
    }

    public void start(String host, int port){
        clientSocket = new ClientSocket(host, port, this, socketEmulatorClientCommandDecoder);
    }

}
