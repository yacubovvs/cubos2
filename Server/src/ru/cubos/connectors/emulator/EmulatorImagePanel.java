package ru.cubos.connectors.emulator;

import ru.cubos.server.helpers.ByteConverter;
import ru.cubos.server.helpers.Protocol;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EmulatorImagePanel extends ImagePanel {
    Emulator emulator;

    public EmulatorImagePanel(Emulator emulator){
        super();
        this.emulator = emulator;

        MouseAdapter mouseAdapter = new MouseAdapter() {

            byte[] startPosition = null;
            byte[] lastPosition = null;
            int[] startPositionCoords = null;

            final char minClickPositionDiff = 5; // If position between touch down and touch up less then N, it is tab, else drag

            @Override
            public void mousePressed(MouseEvent e) {
                int xPosition;
                int yPosition;

                xPosition = (char)e.getX();
                yPosition = (char)e.getY();

                int mousePosition[] = getPositionOnScreen(xPosition, yPosition);

                byte x_bytes[] = ByteConverter.char_to_bytes((char)(mousePosition[0]));
                byte y_bytes[] = ByteConverter.char_to_bytes((char)(mousePosition[1]));

                byte clickData[] = new byte[5];

                clickData[0] = Protocol.EVENT_TOUCH_DOWN;
                clickData[1] = x_bytes[0];
                clickData[2] = x_bytes[1];
                clickData[3] = y_bytes[0];
                clickData[4] = y_bytes[1];

                startPositionCoords = mousePosition;

                startPosition = new byte[4];
                startPosition[0] = x_bytes[0];
                startPosition[1] = x_bytes[1];
                startPosition[2] = y_bytes[0];
                startPosition[3] = y_bytes[1];

                emulator.getServer().transmitData(clickData);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                int xPosition;
                int yPosition;

                xPosition = (char)e.getX();
                yPosition = (char)e.getY();

                int mousePosition[] = getPositionOnScreen(xPosition, yPosition);

                byte x_bytes[] = ByteConverter.char_to_bytes((char)(mousePosition[0]));
                byte y_bytes[] = ByteConverter.char_to_bytes((char)(mousePosition[1]));

                byte clickData[];

                if(Math.abs(mousePosition[0] - startPositionCoords[0])<minClickPositionDiff && Math.abs(mousePosition[1] - startPositionCoords[1])<minClickPositionDiff){
                    clickData = new byte[5];
                    clickData[0] = Protocol.EVENT_TOUCH_TAP;
                    clickData[1] = x_bytes[0];
                    clickData[2] = x_bytes[1];
                    clickData[3] = y_bytes[0];
                    clickData[4] = y_bytes[1];

                    emulator.getServer().transmitData(clickData);
                }else{
                    clickData = new byte[9];
                    clickData[0] = Protocol.EVENT_TOUCH_MOVE_FINISHED;
                    clickData[1] = x_bytes[0];
                    clickData[2] = x_bytes[1];
                    clickData[3] = y_bytes[0];
                    clickData[4] = y_bytes[1];
                    clickData[5] = startPosition[0];
                    clickData[6] = startPosition[1];
                    clickData[7] = startPosition[2];
                    clickData[8] = startPosition[3];

                    emulator.getServer().transmitData(clickData);
                }

                clickData = new byte[5];
                clickData[0] = Protocol.EVENT_TOUCH_UP;
                clickData[1] = x_bytes[0];
                clickData[2] = x_bytes[1];
                clickData[3] = y_bytes[0];
                clickData[4] = y_bytes[1];

                emulator.getServer().transmitData(clickData);

                startPosition = null;
                lastPosition = null;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int xPosition;
                int yPosition;

                xPosition = (char)e.getX();
                yPosition = (char)e.getY();

                int mousePosition[] = getPositionOnScreen(xPosition, yPosition);

                byte x_bytes[] = ByteConverter.char_to_bytes((char)(mousePosition[0]));
                byte y_bytes[] = ByteConverter.char_to_bytes((char)(mousePosition[1]));

                byte clickData[] = new byte[13];

                if(lastPosition==null) lastPosition = startPosition;

                clickData[0]  = Protocol.EVENT_TOUCH_MOVE;
                clickData[1]  = x_bytes[0];
                clickData[2]  = x_bytes[1];
                clickData[3]  = y_bytes[0];
                clickData[4]  = y_bytes[1];
                clickData[5]  = lastPosition[0];
                clickData[6]  = lastPosition[1];
                clickData[7]  = lastPosition[2];
                clickData[8]  = lastPosition[3];
                clickData[9]  = startPosition[0];
                clickData[10] = startPosition[1];
                clickData[11] = startPosition[2];
                clickData[12] = startPosition[3];

                lastPosition = new byte[4];
                lastPosition[0] = x_bytes[0];
                lastPosition[1] = x_bytes[1];
                lastPosition[2] = y_bytes[0];
                lastPosition[3] = y_bytes[1];

                emulator.getServer().transmitData(clickData);
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        addMouseWheelListener(mouseAdapter);
    }

    public int[] getPositionOnScreen(int xPosition, int yPosition){
        if(verticalOffset){
            yPosition -= offsetSize;
        }else{
            xPosition -= offsetSize;
        }

        if(xPosition<0) xPosition = 0;
        if(yPosition<0) yPosition = 0;

        xPosition /= scale_k;
        yPosition /= scale_k;

        if(xPosition>image.getWidth()) xPosition = image.getWidth();
        if(yPosition>image.getHeight()) yPosition = image.getHeight();

        //System.out.println("Emulator client: Mouse move position: " + (int)xPosition + ", " + (int)yPosition);

        return new int[]{xPosition, yPosition};
    }
}
