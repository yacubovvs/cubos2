package ru.cubos.server;

import ru.cubos.commonHelpers.CommandDecoder;
import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.events.Event;
import ru.cubos.server.system.events.EventTouch;

public class ServerCommandsDecoder extends CommandDecoder {
    Server server;
    public ServerCommandsDecoder(Server server) {
        this.server = server;
    }

    @Override
    protected void execTouchEvent(EventTouch event){
        if(event.getType()== Event.Type.EVENT_TOUCH_DOWN){
            for (App app: server.openedApps) app.setMoving(false);
            for (App app: server.openedApps) app.setResizing(false);
            server.activateAppByCoordinates(event.getX(), event.getY());
        }


    }
}
