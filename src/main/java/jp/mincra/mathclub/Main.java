package jp.mincra.mathclub;

import net.dv8tion.jda.api.JDA;

import java.io.IOException;

class Main {

    private static JDA jda;

    //    private static final String TOKEN =
    public static void main(String[] args) throws IOException {

        //MathClubフォルダー作成
        PropertyUtil.setFiles();
        System.out.println(PropertyUtil.getProperty("token"));
    }
}