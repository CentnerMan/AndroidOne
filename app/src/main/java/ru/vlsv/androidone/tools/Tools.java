package ru.vlsv.androidone.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.vlsv.androidone.entities.City;
import ru.vlsv.androidone.entities.HistoryData;

public class Tools {
    public static int randomInt(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public static List<HistoryData> historyList = new ArrayList<>();

    public static String getLines(BufferedReader reader) {
        StringBuilder rawData = new StringBuilder(1024);
        String tempVariable;

        while (true) {
            try {
                tempVariable = reader.readLine();
                if (tempVariable == null) break;
                rawData.append(tempVariable).append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rawData.toString();
    }

//    Для версии Андрюшки с 24
//    public static String getLines(BufferedReader in) {
//        return in.lines().collect(Collectors.joining("\n"));
//    }
}
