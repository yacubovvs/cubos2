package ru.cubos;

import ru.cubos.commonHelpers.Colors;

public class Main_tester {
    public static void main(String[] args) {

        byte value = Colors.rgb_to_color_256((byte)(-64), (byte)(10), (byte)(64));
        byte rgb[] = Colors.color_256_to_rgb(value);

        return;
    }
}
