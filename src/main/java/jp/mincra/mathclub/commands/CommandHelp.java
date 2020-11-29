package jp.mincra.mathclub.commands;

import discord4j.core.object.entity.Message;
import discord4j.rest.util.Color;
import jp.mincra.mathclub.util.PropertyUtil;
import org.json.JSONArray;
import org.json.JSONObject;

public class CommandHelp {

    public static void CommandHelp(Message message) {

        JSONArray jsonArray = new JSONArray(PropertyUtil.jsonNode.get("commands").toString());

        String[] command = new String[jsonArray.length()];
        String[] description = new String[jsonArray.length()];
        String result = new String();

        for (int i = 0; i< jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            command[i] = jsonObject.getString("command");
            description[i] = jsonObject.getString("description");

            result = result + command[i] + ": " + description[i] + "\n";
        }

        String finalResult = result;
        message.getChannel().block().createEmbed(embedCreateSpec -> embedCreateSpec
        .setTitle(":question: **HELP**")
                .setDescription(finalResult)
                .setColor(Color.RED)).block();
    }
}
