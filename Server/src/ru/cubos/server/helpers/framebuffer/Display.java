package ru.cubos.server.helpers.framebuffer;

import ru.cubos.server.helpers.binaryImages.BinaryImage_24bit;

import java.util.ArrayList;
import java.util.List;

import static ru.cubos.server.helpers.ByteConverter.char_to_bytes;
import static ru.cubos.server.helpers.ByteConverter.uByte;
import static ru.cubos.connectors.Protocol._1_DRAWING_PIXEL;

public class Display extends BinaryImage_24bit {
    private BinaryImage_24bit last_frame;
    private List<DisplayCommand> displayCommands;

    public void resetLastFrame(){
        last_frame   = new BinaryImage_24bit(getWidth(), getHeight());
    }

    public Display(int width, int height){
        super(width, height);
        last_frame   = new BinaryImage_24bit(width, height);
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

        for(char x=0; x<getWidth(); x++){
            for(char y=0; y<getHeight(); y++){

                byte[] newPixel = getColorPixel(x,y);
                byte[] oldPixel = ((BinaryImage_24bit)last_frame).getColorPixel(x,y);

                if(newPixel[0]!=oldPixel[0] || newPixel[1]!=oldPixel[1] || newPixel[2]!=oldPixel[2]){
                    //System.out.println("New pixel");
                    DisplayCommand displayCommand = new DisplayCommand();

                    byte x_bytes[] = char_to_bytes((char) x);
                    byte y_bytes[] = char_to_bytes((char) y);

                    displayCommand.type = _1_DRAWING_PIXEL;
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

        System.arraycopy(this.data, 0, last_frame.data, 0, this.data.length);

        return displayCommands;
    }

    public class DisplayCommand{
        public byte type;
        public byte params[];
    }

}
