package ru.cubos.server;

import ru.cubos.connectors.Connector;
import ru.cubos.connectors.emulator.Emulator;
import ru.cubos.server.helpers.Colors;
import ru.cubos.server.helpers.framebuffer.FrameBuffer;

import java.util.List;

import static ru.cubos.server.helpers.ByteConverter.uByte;
import static ru.cubos.server.helpers.Protocol.*;

public class Server {

    Connector connector;
    FrameBuffer display;

    public static void main(String[] args) {
        startServerEmulator();
    }

    public static void startServerEmulator() {
        Server server = new Server(new Emulator(320,480));
        server.start();
    }

    public Server(Connector connector){
        this.connector = connector;
        display = new FrameBuffer(connector.getScreenWidth(), connector.getScreenHeight());
        display.drawRect(0,0,2,2, Colors.COLOR_RED, true);
        display.drawRect(3,3,5,5, Colors.COLOR_GREEN, true);
        display.drawRect(6,6,9,9, Colors.COLOR_BLUE, true);
    }

    public void start(){
        System.out.println("Server: Server started");
        Thread serverThread = new Thread(()->{
            while(true) {
                updateFrameBuffer();


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        serverThread.start();
    }

    void updateFrameBuffer(){
        List<FrameBuffer.DisplayCommand> commands =  display.getFrame();
        //System.out.println("Server: Server loop");

        if(commands.size()!=0){
            int messageLength = 0;
            for(FrameBuffer.DisplayCommand command: commands){
                messageLength += command.params.length;
                messageLength += 1; // Command type
            }

            byte message[] = new byte[messageLength];
            int messagePosition = 0;

            for(FrameBuffer.DisplayCommand command: commands){
                message[messagePosition] = command.type;
                messagePosition++;
                for(char p=0; p<command.params.length; p++){
                    message[messagePosition] = command.params[p];
                    messagePosition++;
                }
            }

            connector.transmitData(message);
        }else{
            System.out.println("Server: no frame change");
        }
    }


}
