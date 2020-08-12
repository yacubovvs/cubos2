package ru.cubos.connectors;


import java.net.Inet4Address;
import java.net.UnknownHostException;

public class ClientSessionSettings {
    static public String host_address;

    static {
        try {
            host_address = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            host_address = "10.0.0.153";
        }
    }

    ; //"10.0.0.153";
    static public int host_port = 8000;
    static public byte image_scale = 3;
    static public int screen_width = 1080/image_scale;
    static public int screen_height = 2320/image_scale;
}
