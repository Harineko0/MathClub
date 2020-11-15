package jp.mincra.mathclub.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Properties;

public class MathClubConfig {


    private static final String INIT_FILE_PATH = new File(".").getAbsoluteFile().getParent()+"/MathClub.properties";
    private static final Properties properties;
    private static final File dir = new File(".");
    private static final File file = new File("./MathClub.properties");
    private static final String charset = "UTF-8";

    static {
        properties = new Properties();
        try {
            properties.load(Files.newBufferedReader(Paths.get(INIT_FILE_PATH), StandardCharsets.UTF_8));
        } catch (IOException e) {
            //ファイル読み込み失敗
            System.out.println(String.format("ファイルの読み込みに失敗しました。ファイル名:%s", INIT_FILE_PATH));
        }
    }

    /**
     * プロパティ値を取得する
     *
     * @param key キー
     * @return 値
     */
    public static String getConfig(final String key){
        return getConfig(key, "");
    }

    /**
     * プロパティ値を取得する
     *
     * @param key キー
     * @param defaultValue デフォルト値
     * @return キーが存在しない場合、デフォルト値
     *          存在する場合、値
     */
    public static String getConfig(final String key, final String defaultValue) {

        String result = properties.getProperty(key, defaultValue);

        if (result.isEmpty()) {
            System.out.println(key+"が記述されていません");
        }

        return result;
    }

    //MathClubフォルダーの処理
    public static void setFiles() throws IOException {

        //MathClubフォルダー作成
        if (dir.exists()) {
        } else {
            dir.mkdir();
        }

        //config.properties作成
        if (file.exists()) {
        } else {
            FileWriter fileWriter = new FileWriter(file);

            file.createNewFile();

            //文字コード指定
            PrintWriter pw = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    new FileOutputStream
                                            (file),"UTF-8")));
            pw.write("#DiscordBOTのトークンを入力してください\ntoken=\n\n#はるはる！コマンドを実行するチャンネルIDを入力してください\nchannel_HaruHaru=");
            pw.close();
        }
    }
}
