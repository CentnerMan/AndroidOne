package ru.vlsv.androidone.tools;

public class Tools {
    public static int randomInt(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
}
