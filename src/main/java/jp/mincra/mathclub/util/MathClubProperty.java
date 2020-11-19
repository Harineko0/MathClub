package jp.mincra.mathclub.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class MathClubProperty {

    //JSONロード
    static String filePath = "./MathClub/MathClub.json";
    static ObjectMapper mapper = new ObjectMapper();
    public static JsonNode jsonNode;
    static {
        try {
            jsonNode = mapper.readTree(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //リロード
    public static void reloadProperty() throws IOException {
        jsonNode = mapper.readTree(new File(filePath));
    }

    //ファイル設置
    public static void setPropertyFile() throws IOException {

        File dir = new File("./MathClub");
        File json = new File("./MathClub/MathClub.json");

        //ディレクトリ作成
        if (dir.exists()){
        } else {
            dir.mkdir();
        }

        //Json作成
        if (json.exists()) {
        } else {
            Writer writer = new PrintWriter(filePath);
            JsonFactory jsonFactory = new JsonFactory();
            JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(writer);

            jsonGenerator.writeStartObject();
            jsonGenerator.writeFieldName("properties");
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("token", "");
            jsonGenerator.writeFieldName("channel");
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("HaruHaru", "");
            jsonGenerator.writeEndObject();
            jsonGenerator.writeEndObject();
            jsonGenerator.writeEndObject();
            //アウトプット
            jsonGenerator.flush();
        }
    }
}
