package ru.cubos.connectors.emulator;

import ru.cubos.profiler.Profiler;
import ru.cubos.server.helpers.ByteConverter;
import ru.cubos.connectors.Protocol;

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

                byte eventData[] = new byte[5];

                eventData[0] = Protocol.EVENT_TOUCH_DOWN;
                eventData[1] = x_bytes[0];
                eventData[2] = x_bytes[1];
                eventData[3] = y_bytes[0];
                eventData[4] = y_bytes[1];

                startPositionCoords = mousePosition;

                startPosition = new byte[4];
                startPosition[0] = x_bytes[0];
                startPosition[1] = x_bytes[1];
                startPosition[2] = y_bytes[0];
                startPosition[3] = y_bytes[1];

                emulator.sendToServer(eventData);

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

                byte eventData[];

                if(Math.abs(mousePosition[0] - startPositionCoords[0])<minClickPositionDiff && Math.abs(mousePosition[1] - startPositionCoords[1])<minClickPositionDiff){
                    eventData = new byte[5];
                    eventData[0] = Protocol.EVENT_TOUCH_TAP;
                    eventData[1] = x_bytes[0];
                    eventData[2] = x_bytes[1];
                    eventData[3] = y_bytes[0];
                    eventData[4] = y_bytes[1];

                    emulator.sendToServer(eventData);
                }else{
                    eventData = new byte[9];
                    eventData[0] = Protocol.EVENT_TOUCH_MOVE_FINISHED;
                    eventData[1] = x_bytes[0];
                    eventData[2] = x_bytes[1];
                    eventData[3] = y_bytes[0];
                    eventData[4] = y_bytes[1];
                    eventData[5] = startPosition[0];
                    eventData[6] = startPosition[1];
                    eventData[7] = startPosition[2];
                    eventData[8] = startPosition[3];

                    emulator.sendToServer(eventData);
                }

                eventData = new byte[5];
                eventData[0] = Protocol.EVENT_TOUCH_UP;
                eventData[1] = x_bytes[0];
                eventData[2] = x_bytes[1];
                eventData[3] = y_bytes[0];
                eventData[4] = y_bytes[1];

                emulator.sendToServer(eventData);

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

                byte eventData[] = new byte[13];

                if(lastPosition==null) lastPosition = startPosition;

                eventData[0]  = Protocol.EVENT_TOUCH_MOVE;
                eventData[1]  = x_bytes[0];
                eventData[2]  = x_bytes[1];
                eventData[3]  = y_bytes[0];
                eventData[4]  = y_bytes[1];
                eventData[5]  = lastPosition[0];
                eventData[6]  = lastPosition[1];
                eventData[7]  = lastPosition[2];
                eventData[8]  = lastPosition[3];
                eventData[9]  = startPosition[0];
                eventData[10] = startPosition[1];
                eventData[11] = startPosition[2];
                eventData[12] = startPosition[3];

                lastPosition = new byte[4];
                lastPosition[0] = x_bytes[0];
                lastPosition[1] = x_bytes[1];
                lastPosition[2] = y_bytes[0];
                lastPosition[3] = y_bytes[1];

                emulator.sendToServer(eventData);
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
