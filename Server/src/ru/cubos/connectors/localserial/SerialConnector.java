package ru.cubos.connectors.localserial;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import ru.cubos.connectors.Connectorable;
import ru.cubos.server.Server;
import ru.cubos.server.framebuffer.Display;

import java.io.IOException;
import java.util.List;

import static ru.cubos.commonHelpers.Protocol.*;
import static ru.cubos.commonHelpers.StaticSocketSettings.restBytesSize;
import static ru.cubos.commonHelpers.StaticSocketSettings.serverBufferSize;

public class SerialConnector implements Connectorable {

    static SerialPort serialPort;
    String serialPortName = "";
    private Server server;

    public SerialConnector(String serialPortName){
        this.serialPortName = serialPortName;
        serialPort = new SerialPort(serialPortName);
    }

    private class PortReader implements SerialPortEventListener {

        public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR() && event.getEventValue() > 0){
                try {
                    byte data[] = serialPort.readBytes(event.getEventValue());
                    /*
                    for(int i=0; i<data.length; i++){
                        if(data[i]<0) data[i]+=128;
                        else data[i]-=128;
                    }*/
                    SerialConnector.this.decode(data);
                }
                catch (SerialPortException ex) {
                    System.out.println(ex);
                }
            }
        }
    }

    byte[] rest_bytes;
    private void decode(byte[] bytes) {
        //server.serverCommandsDecoder.decodeCommands(data, true, 0);

        int count = bytes.length;
        //byte bytes[] = new byte[serverBufferSize];

        //try {
        //    byte rest_bytes[] = null;
        //    while ((count = in.read(bytes)) > 0) {
                byte sum_bytes[];
                if (rest_bytes != null) {
                    sum_bytes = new byte[count + rest_bytes.length];
                    System.arraycopy(rest_bytes, 0, sum_bytes, 0, rest_bytes.length);
                    System.arraycopy(bytes, 0, sum_bytes, rest_bytes.length, count);
                } else {
                    sum_bytes = new byte[count];
                    System.arraycopy(bytes, 0, sum_bytes, 0, count);
                }

                rest_bytes = server.serverCommandsDecoder.decodeCommands(sum_bytes, false, restBytesSize);
                //System.out.println("Read " + count + " bytes");
                //System.out.println("Rest bytes " + rest_bytes.length + " bytes");
                if(
                        bytes[count-1] == _FINISH_BYTES
                                && bytes[count-2] == _FINISH_BYTES
                                && bytes[count-3] == _FINISH_BYTES
                                && bytes[count-4] == _FINISH_BYTES
                                && bytes[count-5] == _FINISH_BYTES
                                && bytes[count-6] == _FINISH_BYTES
                                && bytes[count-7] == _FINISH_BYTES
                                && bytes[count-8] == _FINISH_BYTES
                ){
                    server.serverCommandsDecoder.decodeCommands(rest_bytes, true, 0);
                    //Profiler.start("Message");
                }
            //}
        //} catch (IOException e) {
        //    e.printStackTrace();
        //    return;
        //}
    }

    public void connect() throws SerialPortException {
        serialPort.openPort();
        //serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        //serialPort.setParams(SerialPort.BAUDRATE_256000, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        serialPort.setParams(2048000, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        //serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
        //SerialPort.MASK_RXCHAR

        //n103
        serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);

    }

    public void start(Server server) {
        this.server = server;

        try {
            connect();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean OnDataGotFromServer(byte[] data) {
        try {

            for(int i=0; i<data.length; i++){
                serialPort.writeByte((byte)(data[i]+128));
                if(i%64==0 || i==data.length)
                try {
                    Thread.sleep(130);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
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
        List<Display.DisplayCommand> commands = display.getFrame();
        //Profiler.stop("get frame");
        //System.out.println("Server: Server loop");

        if (commands.size() != 0) {
            int messageLength = 0;
            for (Display.DisplayCommand command : commands) {
                messageLength += command.params.length;
                messageLength += 1; // Command type
            }

            byte message[] = new byte[messageLength];
            int messagePosition = 0;

            for (Display.DisplayCommand command : commands) {
                message[messagePosition] = command.type;
                messagePosition++;
                for (char p = 0; p < command.params.length; p++) {
                    message[messagePosition] = command.params[p];
                    messagePosition++;
                }
            }

            //System.out.println("Server: sending " + message.length + " bytes");
            OnDataGotFromServer(message);
            //Profiler.addCount("Received data", message.length);
            //Profiler.addCount("Total frames", 1);
            //Profiler.showCountAccumulators();
            return true;
        } else {
            //System.out.println("Server: no frame change");
            return false;
        }

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
