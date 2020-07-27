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

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int xPosition;
                int yPosition;

                xPosition = (char)e.getX();
                yPosition = (char)e.getY();

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

                System.out.println("Emulator client: Click mouse position: " + (int)xPosition + ", " + (int)yPosition);

                byte x_bytes[] = ByteConverter.char_to_bytes((char)(xPosition));
                byte y_bytes[] = ByteConverter.char_to_bytes((char)(yPosition));

                byte clickData[] = new byte[5];

                clickData[0] = Protocol.EVENT_CLICK_DOWN;
                clickData[1] = x_bytes[0];
                clickData[2] = x_bytes[1];
                clickData[3] = y_bytes[0];
                clickData[4] = y_bytes[1];

                emulator.getServer().transmitData(clickData);

            }
        });

    }
}
