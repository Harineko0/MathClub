package jp.mincra.mathclub.commands;

import discord4j.core.object.entity.Message;
import jp.mincra.mathclub.util.MathClubProperty;
import org.json.JSONArray;
import org.json.JSONObject;

public class CommandHelp {

    public static void CommandHelp(Message message) {

        JSONArray jsonArray = new JSONArray(MathClubProperty.jsonNode.get("commands").toString());

        String[] command = new String[jsonArray.length()];
        String[] description = new String[jsonArray.length()];
        String result = new String();

        for (int i = 0; i< jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            command[i] = jsonObject.getString("command");
            description[i] = jsonObject.getString("description");

            result = result + command[i] + ": " + description[i] + "\n";
        }

        message.getChannel().block().createMessage("```"+result+"```").block();

    }
}
