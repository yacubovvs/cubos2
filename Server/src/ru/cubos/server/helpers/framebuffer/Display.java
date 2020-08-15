package ru.cubos.server.helpers.framebuffer;

import ru.cubos.commonHelpers.Colors;
import ru.cubos.server.helpers.binaryImages.BinaryImage_24bit;

import java.util.ArrayList;
import java.util.List;

import static ru.cubos.connectors.Protocol.*;
import static ru.cubos.server.helpers.ByteConverter.char_to_bytes;
import static ru.cubos.server.helpers.ByteConverter.uByte;
import static ru.cubos.connectors.Protocol._1_DRAW_PIXEL;

public class Display extends BinaryImage_24bit {
    private BinaryImage_24bit last_frame;
    private List<DisplayCommand> displayCommands;
    private byte colorScheme = _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8;

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

    boolean pointsEqual(byte a[], byte b[]){
        return a[0]==b[0] && a[1]==b[1] && a[2]==b[2];
    }

    public List<DisplayCommand> getFrame(){

        displayCommands = new ArrayList<>();

        for(char y=0; y<getHeight(); y++){
            for(char x=0; x<getWidth(); x++){

                byte[] newPixel = getColorPixel(x,y);
                byte[] oldPixel = last_frame.getColorPixel(x,y);

                if(!pointsEqual(newPixel, oldPixel)){

                    // Check for lines
                    int lineLength = 0;
                    for(int i=x; i<getWidth(); i++){
                        byte[] newLinePixel = getColorPixel(i,y);
                        byte[] oldLinePixel = last_frame.getColorPixel(i,y);

                        if(pointsEqual(newPixel, newLinePixel) && !pointsEqual(newLinePixel, oldLinePixel)){
                            lineLength = i-x;
                        }else{
                            //System.out.println("Line length " + i);
                            lineLength = i-x;
                            break;
                        }
                    }

                    if(lineLength>1){
                        x+= lineLength;
                        //continue;
                    }

                    // Check point
                    DisplayCommand displayCommand = new DisplayCommand();

                    byte x_bytes[] = char_to_bytes((char) x);
                    byte y_bytes[] = char_to_bytes((char) y);

                    displayCommand.type = _1_DRAW_PIXEL;
                    if(colorScheme == _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8){
                        displayCommand.params = new byte[]{
                            uByte(x_bytes[0]),   // X0
                            uByte(x_bytes[1]),   // X0
                            uByte(y_bytes[0]),   // Y0
                            uByte(y_bytes[1]),   // Y0
                            newPixel[0],   // R
                            newPixel[1],   // G
                            newPixel[2],   // B
                        };
                    }else if(colorScheme == _1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS){
                        displayCommand.params = new byte[]{
                            uByte(x_bytes[0]),   // X0
                            uByte(x_bytes[1]),   // X0
                            uByte(y_bytes[0]),   // Y0
                            uByte(y_bytes[1]),   // Y0
                            Colors.rgb_to_color_256(newPixel)
                        };
                    }

                    displayCommands.add(displayCommand);

                }
            }
        }

        System.arraycopy(this.data, 0, last_frame.data, 0, this.data.length);

        return displayCommands;
    }

    public byte getColorScheme() {
        return colorScheme;
    }

    public void setColorScheme(byte colorScheme) {
        this.colorScheme = colorScheme;
    }

    public class DisplayCommand{
        public byte type;
        public byte params[];
    }

}
