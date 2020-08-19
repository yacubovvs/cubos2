package ru.cubos.connectors.straightEmulator;

import ru.cubos.connectors.Connectorable;
import ru.cubos.connectors.emulator.Emulator;
import ru.cubos.server.Server;
import ru.cubos.server.framebuffer.Display;

import static ru.cubos.commonHelpers.Protocol._0_3_EVENT_MODE;
import static ru.cubos.commonHelpers.Protocol._1_6_3_7_SCREEN_COLORS_24BIT__8_8_8;

public class LocalEmulator extends Emulator implements Connectorable {
    public LocalEmulator(int width, int height) {
        super(width, height);
    }

    private Server server;

    @Override
    public boolean sendToServer(byte[] data) {
        server.serverCommandsDecoder.current_mode = _0_3_EVENT_MODE;
        server.serverCommandsDecoder.decodeCommands(data, true, 0);
        return false;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public boolean updateScreen(Display display) {
        updateImage(display.getBufferedImageImage());
        return true;
    }

    @Override
    public void switchToMode(byte mode) {

    }

    public void start() {
        server.serverCommandsDecoder.setColorScheme(_1_6_3_7_SCREEN_COLORS_24BIT__8_8_8);
        server.serverCommandsDecoder.setScreenWidth((char)getWidth());
        server.serverCommandsDecoder.setScreenHeight((char)getHeight());
        server.serverCommandsDecoder.startServer();
    }
}
