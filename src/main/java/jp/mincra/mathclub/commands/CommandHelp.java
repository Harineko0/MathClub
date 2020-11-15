package jp.mincra.mathclub.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import discord4j.core.object.entity.Message;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class CommandHelp {
    public static void CommandHelp(Message message) {
        try {
            File file = new File("./MathClub/MathClub.json");
            JSONArray jsonArray = new JSONArray(new ObjectMapper().readTree(file).get("commands").toString());
            String[] command = new String[jsonArray.length()];
            String[] description = new String[jsonArray.length()];
            String result = new String();

            for (int i = 0; i<jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                command[i] = jsonObject.getString("command");
                description[i] = jsonObject.getString("description");

                result = result + command[i] + ": " + description[i] + "\n";
            }
            message.getChannel().block().createMessage("```"+result+"```").block();
        } catch (JsonProcessingException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
