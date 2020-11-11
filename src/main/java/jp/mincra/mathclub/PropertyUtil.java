package jp.mincra.mathclub;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Properties;

public class PropertyUtil {

    public static final int test = 10;

    private static final String INIT_FILE_PATH = new File(".").getAbsoluteFile().getParent()+"/MathClub/config.properties";
    private static Properties properties;

    private PropertyUtil() throws  Exception {
    }

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
    public static String getProperty(final String key){
        return getProperty(key, "");
    }

    /**
     * プロパティ値を取得する
     *
     * @param key キー
     * @param defaultValue デフォルト値
     * @return キーが存在しない場合、デフォルト値
     *          存在する場合、値
     */
    public static String getProperty(final String key, final String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    //MathClubフォルダーの処理
    public static void setFiles() throws IOException {
        File dir = new File("./MathClub");
        File file = new File("./MathClub/config.properties");
        String charset = "UTF-8";

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
            pw.write("#DiscordBOTのトークンを入力してください\ntoken=\n\n#DiscordAPPのCLIENT IDを入力してください\nclientid=");
            pw.close();
        }
    }
}
