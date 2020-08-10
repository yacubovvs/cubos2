package ru.cubos.commonHelpers;

public class StaticSocketSettings {
    public static int clientBufferSize = 8 * 1024 * 1024;
    public static int serverBufferSize = 16 * 1024 * 1024;

    public static int clientBufferSize_max = 16 * 1024 * 1024;
    public static int serverBufferSize_max = 16 * 1024 * 1024;

    public static String cosketServerADdress = "192.168.1.38"; // Inet4Address.getLocalHost().getHostAddress()
}
