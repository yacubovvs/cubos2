package ru.cubos.server.helpers.framebuffer;

import java.util.ArrayList;
import java.util.List;

import static ru.cubos.server.helpers.ByteConverter.char_to_bytes;
import static ru.cubos.server.helpers.ByteConverter.uByte;
import static ru.cubos.server.helpers.Protocol.DRWING_PIXEL;

public class Display extends BinaryImage{
    private BinaryImage last_frame;
    private List<DisplayCommand> displayCommands;

    public Display(int width, int height){
        super(width, height);
        last_frame   = new BinaryImage(width, height);
    }

    /*
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * *                                                                                                             * *
     * *                                                    FRAMES                                                   * *
     * *                                                                                                             * *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     */

    public List<DisplayCommand> getFrame(){
        displayCommands = new ArrayList<>();

        for(int x=0; x<getWidth(); x++){
            for(int y=0; y<getHeight(); y++){
                byte[] newPixel = getColorPixel(x,y);
                byte[] oldPixel = last_frame.getColorPixel(x,y);
                if(newPixel[0]!=oldPixel[0] || newPixel[1]!=oldPixel[1] || newPixel[2]!=oldPixel[2]){
                    //System.out.println("New pixel");
                    DisplayCommand displayCommand = new DisplayCommand();

                    byte x_bytes[] = char_to_bytes((char) x);
                    byte y_bytes[] = char_to_bytes((char) y);

                    displayCommand.type = DRWING_PIXEL;
                    displayCommand.params = new byte[]{
                        uByte(x_bytes[0]),   // X0
                        uByte(x_bytes[1]),   // X0
                        uByte(y_bytes[0]),   // Y0
                        uByte(y_bytes[1]),   // Y0
                        newPixel[0],   // R
                        newPixel[1],   // G
                        newPixel[2],   // B
                    };

                    displayCommands.add(displayCommand);
                }
            }
        }

        for(int x=0; x<getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                last_frame.setColorPixel(x,y, getColorPixel(x,y));
            }
        }

        return displayCommands;
    }

    public class DisplayCommand{
        public byte type;
        public byte params[];
    }

}
