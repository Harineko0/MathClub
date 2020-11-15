package jp.mincra.mathclub;

import discord4j.core.object.entity.Message;
import jp.mincra.mathclub.commands.CommandHaruHaru;
import jp.mincra.mathclub.commands.CommandHelp;

public class Event {

    public static void MessageCreate(Message message) {

        if (message.getAuthor().map(user -> !user.isBot()).orElse(false)) {

            switch (message.getContent()){

                case "はるはる！":
                    CommandHaruHaru.createMessage(message);

                case "help":
                    CommandHelp.CommandHelp(message);
            }
        }
    }
}
