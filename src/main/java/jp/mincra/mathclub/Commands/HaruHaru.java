package jp.mincra.mathclub.Commands;

import java.util.Random;

public class HaruHaru {

    public static String getString() {

        String result = "";
        Random random = new Random();
        String[] Chara = { "は", "る" };

        for (int i = 0; i < 4; i++) {
            result = result + Chara[random.nextInt(2)];
        }

        return result;
    }
}
