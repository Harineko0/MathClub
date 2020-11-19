package jp.mincra.mathclub;

import discord4j.core.object.entity.Message;
import jp.mincra.mathclub.commands.CommandHaruHaru;
import jp.mincra.mathclub.commands.CommandHelp;
import jp.mincra.mathclub.commands.CommandReload;
import jp.mincra.mathclub.commands.CommandSchedule;
import jp.mincra.mathclub.util.MathClubProperty;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class Event {

    public static void MessageCreate(Message message) {

        if (message.getAuthor().map(user -> !user.isBot()).orElse(false)) {

            switch (message.getContent()){
                case "はるはる！":
                    CommandHaruHaru.createMessage(message);
                    break;

                case "!help":
                    CommandHelp.CommandHelp(message);
                    break;

                case "!reload":
                    CommandReload.CommandReload(message);
                    break;

                case "!test":
                    CommandSchedule.CommandSchedule();
                    break;
            }
        }
    }
}
