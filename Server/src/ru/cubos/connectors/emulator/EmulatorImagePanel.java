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
                char xPosition;
                char yPosition;

                xPosition = (char)e.getX();
                yPosition = (char)e.getY();
                System.out.println("Emulator client: Click mouse position: " + (int)xPosition + ", " + (int)yPosition);

                byte x_bytes[] = ByteConverter.char_to_bytes(xPosition);
                byte y_bytes[] = ByteConverter.char_to_bytes(yPosition);

                byte clickData[] = new byte[5];

                clickData[0] = Protocol.EVENT_CLICK;
                clickData[1] = x_bytes[0];
                clickData[2] = x_bytes[1];
                clickData[3] = y_bytes[0];
                clickData[4] = y_bytes[1];

                emulator.getServer().transmitData(clickData);

            }
        });

    }
}
