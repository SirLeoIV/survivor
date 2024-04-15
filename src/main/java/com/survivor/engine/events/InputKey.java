package com.survivor.engine.events;

public enum InputKey {
    KEY_UP,
    KEY_DOWN,
    KEY_LEFT,
    KEY_RIGHT,
    KEY_SPACE,
    KEY_ENTER,
    KEY_ESCAPE,

    MOUSE_PRIMARY,
    MOUSE_SECONDARY,
    MOUSE_MIDDLE,
    MOUSE_SCROLL_UP,
    MOUSE_SCROLL_DOWN,

    KEY_A,
    KEY_B,
    KEY_C,
    KEY_D,
    KEY_E,
    KEY_F,
    KEY_G,
    KEY_H,
    KEY_I,
    KEY_J,
    KEY_K,
    KEY_L,
    KEY_M,
    KEY_N,
    KEY_O,
    KEY_P,
    KEY_Q,
    KEY_R,
    KEY_S,
    KEY_T,
    KEY_U,
    KEY_V,
    KEY_W,
    KEY_X,
    KEY_Y,
    KEY_Z,

    KEY_0,
    KEY_1,
    KEY_2,
    KEY_3,
    KEY_4,
    KEY_5,
    KEY_6,
    KEY_7,
    KEY_8,
    KEY_9,

    UNDEFINED;

    public static InputKey getKey(String code) {
        return switch (code) {
            case "Up" -> KEY_UP;
            case "Down" -> KEY_DOWN;
            case "Left" -> KEY_LEFT;
            case "Right" -> KEY_RIGHT;
            case "Space" -> KEY_SPACE;
            case "Enter" -> KEY_ENTER;
            case "Escape" -> KEY_ESCAPE;
            case "PRIMARY" -> MOUSE_PRIMARY;
            case "SECONDARY" -> MOUSE_SECONDARY;
            case "MIDDLE" -> MOUSE_MIDDLE;
            case "Scroll_up" -> MOUSE_SCROLL_UP;
            case "Scroll_down" -> MOUSE_SCROLL_DOWN;
            case "A" -> KEY_A;
            case "B" -> KEY_B;
            case "C" -> KEY_C;
            case "D" -> KEY_D;
            case "E" -> KEY_E;
            case "F" -> KEY_F;
            case "G" -> KEY_G;
            case "H" -> KEY_H;
            case "I" -> KEY_I;
            case "J" -> KEY_J;
            case "K" -> KEY_K;
            case "L" -> KEY_L;
            case "M" -> KEY_M;
            case "N" -> KEY_N;
            case "O" -> KEY_O;
            case "P" -> KEY_P;
            case "Q" -> KEY_Q;
            case "R" -> KEY_R;
            case "S" -> KEY_S;
            case "T" -> KEY_T;
            case "U" -> KEY_U;
            case "V" -> KEY_V;
            case "W" -> KEY_W;
            case "X" -> KEY_X;
            case "Y" -> KEY_Y;
            case "Z" -> KEY_Z;
            case "0" -> KEY_0;
            case "1" -> KEY_1;
            case "2" -> KEY_2;
            case "3" -> KEY_3;
            case "4" -> KEY_4;
            case "5" -> KEY_5;
            case "6" -> KEY_6;
            case "7" -> KEY_7;
            case "8" -> KEY_8;
            case "9" -> KEY_9;
            default -> UNDEFINED;
        };
    }
}
