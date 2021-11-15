package xyz.vkislitsin.fullstack.enums;

import java.util.Random;

public enum SpinComponent {
    CHERRY,
    LEMON,
    ORANGE,
    WATERMELON;

    public static SpinComponent getRandom() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
