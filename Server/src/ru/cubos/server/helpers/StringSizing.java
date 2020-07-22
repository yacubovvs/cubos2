package ru.cubos.server.helpers;

import ru.cubos.server.Server;

public class StringSizing {
    static public int getStringWidth(Server server, String string, int fontSize){
        return server.settings.getSystemCharWidth() * string.length() * fontSize;
    }
}
