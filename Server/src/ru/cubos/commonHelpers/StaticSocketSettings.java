package ru.cubos.commonHelpers;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class StaticSocketSettings {
    public static int clientBufferSize = 8 * 1024 * 1024;
    public static int serverBufferSize = 16 * 1024 * 1024;

    public static int clientBufferSize_max = 16 * 1024 * 1024;
    public static int serverBufferSize_max = 16 * 1024 * 1024;

    //public static String cosketServerADdress = "192.168.1.38";
    public static String cosketServerADdress;

    static {
        try {
            cosketServerADdress = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            cosketServerADdress = "192.168.1.38";
        }
    }
}
