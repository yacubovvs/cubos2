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

            byte[] startDragPosition = null;
            byte[] lastPosition = null;

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

                clickData[0] = Protocol.EVENT_TOUCH_TAP;
                clickData[1] = x_bytes[0];
                clickData[2] = x_bytes[1];
                clickData[3] = y_bytes[0];
                clickData[4] = y_bytes[1];

                startDragPosition = new byte[4];
                startDragPosition[0] = x_bytes[0];
                startDragPosition[1] = x_bytes[1];
                startDragPosition[2] = y_bytes[0];
                startDragPosition[3] = y_bytes[1];

                emulator.getServer().transmitData(clickData);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                startDragPosition = null;
                lastPosition = null;

                int xPosition;
                int yPosition;

                xPosition = (char)e.getX();
                yPosition = (char)e.getY();

                int mousePosition[] = getPositionOnScreen(xPosition, yPosition);

                byte x_bytes[] = ByteConverter.char_to_bytes((char)(mousePosition[0]));
                byte y_bytes[] = ByteConverter.char_to_bytes((char)(mousePosition[1]));

                byte clickData[] = new byte[5];

                clickData[0] = Protocol.EVENT_TOUCH_UP;
                clickData[1] = x_bytes[0];
                clickData[2] = x_bytes[1];
                clickData[3] = y_bytes[0];
                clickData[4] = y_bytes[1];

                emulator.getServer().transmitData(clickData);
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

                if(lastPosition==null) lastPosition = startDragPosition;

                clickData[0]  = Protocol.EVENT_TOUCH_MOVE;
                clickData[1]  = x_bytes[0];
                clickData[2]  = x_bytes[1];
                clickData[3]  = y_bytes[0];
                clickData[4]  = y_bytes[1];
                clickData[5]  = lastPosition[0];
                clickData[6]  = lastPosition[1];
                clickData[7]  = lastPosition[2];
                clickData[8]  = lastPosition[3];
                clickData[9]  = startDragPosition[0];
                clickData[10] = startDragPosition[1];
                clickData[11] = startDragPosition[2];
                clickData[12] = startDragPosition[3];

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
