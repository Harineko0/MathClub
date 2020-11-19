package jp.mincra.mathclub.commands;

import discord4j.core.object.entity.Message;
import jp.mincra.mathclub.util.MathClubProperty;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class CommandReload {

    public static void CommandReload(Message message) {

        JSONArray jsonArray = new JSONArray(MathClubProperty.jsonNode.get("commands").toString());
        String success = new String();
        String failure = new String();
        
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.get("command").equals("!reload")) {
                JSONObject jsonObject1 = jsonObject.getJSONObject("message");
                success = jsonObject1.getString("success");
                failure = jsonObject1.getString("failure");
            }
        }

        try {
            MathClubProperty.reloadProperty();
            message.getChannel().block().createMessage(success).block();
        } catch (IOException e) {
            e.printStackTrace();
            message.getChannel().block().createMessage(failure).block();
        }
    }
}
