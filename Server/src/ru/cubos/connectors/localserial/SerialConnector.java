package ru.cubos.connectors.localserial;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import ru.cubos.connectors.Connectorable;
import ru.cubos.server.Server;
import ru.cubos.server.framebuffer.Display;

import static ru.cubos.commonHelpers.Protocol._0_MODE_OPTION;

public class SerialConnector implements Connectorable {

    static SerialPort serialPort;
    String serialPortName = "";
    private Server server;

    public SerialConnector(String serialPortName){
        this.serialPortName = serialPortName;
        serialPort = new SerialPort(serialPortName);
    }

    public void connect() throws SerialPortException {
        serialPort.openPort();
        serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        //serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
        //SerialPort.MASK_RXCHAR
        serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
    }

    private class PortReader implements SerialPortEventListener {

        public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR() && event.getEventValue() > 0){
                try {
                    byte data[] = serialPort.readBytes(event.getEventValue());
                    SerialConnector.this.decode(data);
                }
                catch (SerialPortException ex) {
                    System.out.println(ex);
                }
            }
        }
    }

    private void decode(byte[] data) {
        server.serverCommandsDecoder.decodeCommands(data, true, 0);
    }

    public void start(Server server) {
        this.server = server;
    }

    @Override
    public boolean OnDataGotFromServer(byte[] data) {
        return false;
    }

    @Override
    public int getScreenWidth() {
        return 0;
    }

    @Override
    public int getScreenHeight() {
        return 0;
    }

    @Override
    public boolean updateScreen(Display display) {
        return false;
    }

    @Override
    public void switchToMode(byte mode) {
        byte message[] = new byte[]{
                _0_MODE_OPTION,                         // Switch mode
                mode,                      // Switch to COMMON MODE 1
        };

        OnDataGotFromServer(message);
    }

}
