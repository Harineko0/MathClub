package jp.mincra.mathclub;

import discord4j.core.object.entity.Message;
import jp.mincra.mathclub.commands.CommandHaruHaru;
import jp.mincra.mathclub.commands.CommandHelp;
import jp.mincra.mathclub.commands.CommandReload;

public class MathClubEvent {

    public static void MessageCreate(Message message) {

        if (message.getAuthor().map(user -> !user.isBot()).orElse(false)) {

            switch (message.getContent()){
                case "はるはる！":
                    CommandHaruHaru.CommandHaruHaru(message);
                    break;

                case "!help":
                    CommandHelp.CommandHelp(message);
                    break;

                case "!reload":
                    CommandReload.CommandReload(message);
                    break;
            }
        }
    }
}
