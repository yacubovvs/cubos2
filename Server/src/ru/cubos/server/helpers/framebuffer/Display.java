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
                    char lineLength = 0;
                    for(char i=x; i<getWidth(); i++){
                        byte[] newLinePixel = getColorPixel(i,y);
                        byte[] oldLinePixel = last_frame.getColorPixel(i,y);

                        lineLength = (char)(i-x);
                        if(pointsEqual(newPixel, newLinePixel) && !pointsEqual(newLinePixel, oldLinePixel)){
                        }else{
                            break;
                        }
                    }

                    if(lineLength>1){
                        DisplayCommand displayCommand = new DisplayCommand();

                        if(x>255 || y>255) {

                            byte x_bytes[] = char_to_bytes((char) x);
                            byte y_bytes[] = char_to_bytes((char) y);

                            if(lineLength>255) {
                                byte lineLength_bytes[] = char_to_bytes((char) lineLength);

                                displayCommand.type = _5_DRAW_LINE_VERTICAL_COORDINATES_MORE_255;
                                if (colorScheme == _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8) {
                                    displayCommand.params = new byte[]{
                                            uByte(x_bytes[0]),   // X0
                                            uByte(x_bytes[1]),   // X0
                                            uByte(y_bytes[0]),   // Y0
                                            uByte(y_bytes[1]),   // Y0
                                            uByte(lineLength_bytes[0]), // Length
                                            uByte(lineLength_bytes[1]), // Length
                                            newPixel[0],   // R
                                            newPixel[1],   // G
                                            newPixel[2],   // B
                                    };
                                } else if (colorScheme == _1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS) {
                                    displayCommand.params = new byte[]{
                                            uByte(x_bytes[0]),   // X0
                                            uByte(x_bytes[1]),   // X0
                                            uByte(y_bytes[0]),   // Y0
                                            uByte(y_bytes[1]),   // Y0
                                            uByte(lineLength_bytes[0]), // Length
                                            uByte(lineLength_bytes[1]), // Length
                                            Colors.rgb_to_color_256(newPixel)
                                    };
                                }
                            }else{
                                displayCommand.type = _7_DRAW_LINE_VERTICAL_COORDINATES_MORE_255_LENGTH_LESS_255;
                                if (colorScheme == _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8) {
                                    displayCommand.params = new byte[]{
                                            uByte(x_bytes[0]),   // X0
                                            uByte(x_bytes[1]),   // X0
                                            uByte(y_bytes[0]),   // Y0
                                            uByte(y_bytes[1]),   // Y0
                                            uByte(lineLength), // LENGTH
                                            newPixel[0],   // R
                                            newPixel[1],   // G
                                            newPixel[2],   // B
                                    };
                                } else if (colorScheme == _1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS) {
                                    displayCommand.params = new byte[]{
                                            uByte(x_bytes[0]),   // X0
                                            uByte(x_bytes[1]),   // X0
                                            uByte(y_bytes[0]),   // Y0
                                            uByte(y_bytes[1]),   // Y0
                                            uByte(lineLength), // LENGTH
                                            Colors.rgb_to_color_256(newPixel)
                                    };
                                }
                            }

                        }else{

                            byte x_bytes[] = char_to_bytes((char) x);
                            byte y_bytes[] = char_to_bytes((char) y);

                            if(lineLength>255) {
                                byte lineLength_bytes[] = char_to_bytes((char) lineLength);

                                displayCommand.type = _6_DRAW_LINE_VERTICAL_COORDINATES_LESS_255;
                                if (colorScheme == _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8) {
                                    displayCommand.params = new byte[]{
                                            uByte(x),   // X0
                                            uByte(y),   // Y0
                                            uByte(lineLength_bytes[0]), // Length
                                            uByte(lineLength_bytes[1]), // Length
                                            newPixel[0],   // R
                                            newPixel[1],   // G
                                            newPixel[2],   // B
                                    };
                                } else if (colorScheme == _1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS) {
                                    displayCommand.params = new byte[]{
                                            uByte(x),   // X0
                                            uByte(y),   // Y0
                                            uByte(lineLength_bytes[0]), // Length
                                            uByte(lineLength_bytes[1]), // Length
                                            Colors.rgb_to_color_256(newPixel)
                                    };
                                }
                            }else{
                                displayCommand.type = _8_DRAW_LINE_VERTICAL_COORDINATES_LESS_255_LENGTH_LESS_255;
                                if (colorScheme == _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8) {
                                    displayCommand.params = new byte[]{
                                            uByte(x),   // X0
                                            uByte(y),   // Y0
                                            uByte(lineLength), // LENGTH
                                            newPixel[0],   // R
                                            newPixel[1],   // G
                                            newPixel[2],   // B
                                    };
                                } else if (colorScheme == _1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS) {
                                    displayCommand.params = new byte[]{
                                            uByte(x),   // X0
                                            uByte(y),   // Y0
                                            uByte(lineLength), // LENGTH
                                            Colors.rgb_to_color_256(newPixel)
                                    };
                                }
                            }

                        }

                        displayCommands.add(displayCommand);
                        x+= lineLength-1;
                        continue;
                    }

                    // Check point
                    DisplayCommand displayCommand = new DisplayCommand();

                    if(x>255 || y>255) {
                        byte x_bytes[] = char_to_bytes((char) x);
                        byte y_bytes[] = char_to_bytes((char) y);

                        displayCommand.type = _1_DRAW_PIXEL;
                        if (colorScheme == _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8) {
                            displayCommand.params = new byte[]{
                                    uByte(x_bytes[0]),   // X0
                                    uByte(x_bytes[1]),   // X0
                                    uByte(y_bytes[0]),   // Y0
                                    uByte(y_bytes[1]),   // Y0
                                    newPixel[0],   // R
                                    newPixel[1],   // G
                                    newPixel[2],   // B
                            };
                        } else if (colorScheme == _1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS) {
                            displayCommand.params = new byte[]{
                                    uByte(x_bytes[0]),   // X0
                                    uByte(x_bytes[1]),   // X0
                                    uByte(y_bytes[0]),   // Y0
                                    uByte(y_bytes[1]),   // Y0
                                    Colors.rgb_to_color_256(newPixel)
                            };
                        }
                    }else{
                        displayCommand.type = _2_DRAW_PIXEL_COORDINATES_LESS_255;
                        if (colorScheme == _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8) {
                            displayCommand.params = new byte[]{
                                    uByte(x),   // X0
                                    uByte(y),   // Y0
                                    newPixel[0],   // R
                                    newPixel[1],   // G
                                    newPixel[2],   // B
                            };
                        } else if (colorScheme == _1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS) {
                            displayCommand.params = new byte[]{
                                    uByte(x-128),   // X0
                                    uByte(y-128),   // Y0
                                    Colors.rgb_to_color_256(newPixel)
                            };
                        }
                    }

                    displayCommands.add(displayCommand);

                }
            }
        }

        if(displayCommands.size()!=0) {
            DisplayCommand updateScreenCommand = new DisplayCommand();
            updateScreenCommand.type = _3_UPDATE_SCREEN;
            displayCommands.add(updateScreenCommand);
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
        public byte params[] = new byte[]{};
    }

}
