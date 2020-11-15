package jp.mincra.mathclub.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class MathClubProperty {


    private static final String INIT_FILE_PATH = new File(".").getAbsoluteFile().getParent()+"/MathClub/MathClub.properties";
    private static final Properties properties;
    private static final File dir = new File("./MathClub");
    private static final File file_properties = new File("./MathClub/MathClub.properties");
    private static final File file_json = new File("./MathClub/MathClub.json");
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
        String result = properties.getProperty(key, defaultValue);
        if (result.isEmpty()) {
            System.out.println(key+"が記述されていません");
        }
        return result;
    }

//    //Json読み込み
//    public static void getJson() {
//        ObjectMapper mapper = new ObjectMapper();
//
//        try {
//            JsonNode node = mapper.readTree(file_json);
//            JSONArray jsonArray_commands = new JSONArray(node.get("commands").toString());
//
//            for (int i = 0; i<jsonArray_commands.length(); i++) {
//
//                JSONObject jsonObject = jsonArray_commands.getJSONObject(i);
//                String key_command = jsonObject.getString("command");
//                System.out.println(key_command);
//                String key_description = jsonObject.getString("description");
//                System.out.println(key_description);
//
//                Data.command[i] = key_command;
//                Data.description[i] = key_description;
//
//                System.out.println(Data.command[i]);
//                System.out.println(Data.description[i]);
//            }
//
//        } catch (JsonProcessingException e){
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    //MathClubフォルダーの処理
    public static void setFiles() throws IOException {

        //MathClubフォルダー作成
        if (dir.exists()) {
        } else {
            dir.mkdir();
        }

        //MathClub.json作成
        if (file_json.exists()) {
        } else {
            Writer out = new PrintWriter("./MathClub/MathClub.json");
            JsonFactory jsonFactory = new JsonFactory();
            JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(out);
            jsonGenerator.flush();
        }

        //MathClub.properties作成
        if (file_properties.exists()) {
        } else {
            FileWriter fileWriter = new FileWriter(file_properties);
            file_properties.createNewFile();

            //文字コード指定
            PrintWriter pw = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    new FileOutputStream
                                            (file_properties),"UTF-8")));
            pw.write("#DiscordBOTのトークンを入力してください\ntoken=\n\n#はるはる！コマンドを実行するチャンネルIDを入力してください\nchannel_HaruHaru=");
            pw.close();
        }
    }
}
