package ru.cubos.commonHelpers;

public class Buttons {
    public enum ButtonType{
        ARROW_LEFT,
        ARROW_RIGHT,
        ENTER
    }

    public static ButtonType getButtonType(char code, char buttonChar){
        switch (code){
            case 65535:
                switch (buttonChar){
                    case 39:
                        return ButtonType.ARROW_RIGHT;
                    case 37:
                        return ButtonType.ARROW_LEFT;
                    case 10:
                        return ButtonType.ENTER;
                    default:
                        return null;
                }
            default:
                return null;
        }
    }
}
