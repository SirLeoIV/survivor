package com.survivor.engine.events;

import java.util.HashMap;

public class KeyStates {

    public static final HashMap<InputKey, InputType> keyStates =
            new HashMap<>() {{
                put(InputKey.KEY_0, InputType.KEY_RELEASED);
                put(InputKey.KEY_1, InputType.KEY_RELEASED);
                put(InputKey.KEY_2, InputType.KEY_RELEASED);
                put(InputKey.KEY_3, InputType.KEY_RELEASED);
                put(InputKey.KEY_4, InputType.KEY_RELEASED);
                put(InputKey.KEY_5, InputType.KEY_RELEASED);
                put(InputKey.KEY_6, InputType.KEY_RELEASED);
                put(InputKey.KEY_7, InputType.KEY_RELEASED);
                put(InputKey.KEY_8, InputType.KEY_RELEASED);
                put(InputKey.KEY_9, InputType.KEY_RELEASED);
                put(InputKey.KEY_A, InputType.KEY_RELEASED);
                put(InputKey.KEY_B, InputType.KEY_RELEASED);
                put(InputKey.KEY_C, InputType.KEY_RELEASED);
                put(InputKey.KEY_D, InputType.KEY_RELEASED);
                put(InputKey.KEY_E, InputType.KEY_RELEASED);
                put(InputKey.KEY_F, InputType.KEY_RELEASED);
                put(InputKey.KEY_G, InputType.KEY_RELEASED);
                put(InputKey.KEY_H, InputType.KEY_RELEASED);
                put(InputKey.KEY_I, InputType.KEY_RELEASED);
                put(InputKey.KEY_J, InputType.KEY_RELEASED);
                put(InputKey.KEY_K, InputType.KEY_RELEASED);
                put(InputKey.KEY_L, InputType.KEY_RELEASED);
                put(InputKey.KEY_M, InputType.KEY_RELEASED);
                put(InputKey.KEY_N, InputType.KEY_RELEASED);
                put(InputKey.KEY_O, InputType.KEY_RELEASED);
                put(InputKey.KEY_P, InputType.KEY_RELEASED);
                put(InputKey.KEY_Q, InputType.KEY_RELEASED);
                put(InputKey.KEY_R, InputType.KEY_RELEASED);
                put(InputKey.KEY_S, InputType.KEY_RELEASED);
                put(InputKey.KEY_T, InputType.KEY_RELEASED);
                put(InputKey.KEY_U, InputType.KEY_RELEASED);
                put(InputKey.KEY_V, InputType.KEY_RELEASED);
                put(InputKey.KEY_W, InputType.KEY_RELEASED);
                put(InputKey.KEY_X, InputType.KEY_RELEASED);
                put(InputKey.KEY_Y, InputType.KEY_RELEASED);
                put(InputKey.KEY_Z, InputType.KEY_RELEASED);

                put(InputKey.KEY_UP, InputType.KEY_RELEASED);
                put(InputKey.KEY_DOWN, InputType.KEY_RELEASED);
                put(InputKey.KEY_LEFT, InputType.KEY_RELEASED);
                put(InputKey.KEY_RIGHT, InputType.KEY_RELEASED);

                put(InputKey.KEY_SPACE, InputType.KEY_RELEASED);
                put(InputKey.KEY_ENTER, InputType.KEY_RELEASED);
                put(InputKey.KEY_ESCAPE, InputType.KEY_RELEASED);

                put(InputKey.MOUSE_PRIMARY, InputType.MOUSE_RELEASED);
                put(InputKey.MOUSE_SECONDARY, InputType.MOUSE_RELEASED);
                put(InputKey.MOUSE_MIDDLE, InputType.MOUSE_RELEASED);
            }};
}
