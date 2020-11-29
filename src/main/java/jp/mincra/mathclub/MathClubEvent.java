package jp.mincra.mathclub;

import discord4j.core.object.entity.Message;
import jp.mincra.mathclub.commands.CommandHaruHaru;
import jp.mincra.mathclub.commands.CommandHelp;
import jp.mincra.mathclub.commands.CommandReload;
import jp.mincra.mathclub.commands.CommandScrapingForum;

public class MathClubEvent {

    public static void MessageCreate(Message message) {

        if (message.getAuthor().map(user -> !user.isBot()).orElse(false)) {

            String[] args = message.getContent().split(" ");

            switch (args[0]){
                case "はるはる！":
                    CommandHaruHaru.CommandHaruHaru(message);
                    break;

                case "!help":
                    CommandHelp.CommandHelp(message);
                    break;

                case "!reload":
                    CommandReload.CommandReload(message);
                    break;

                case "!forum":
                    CommandScrapingForum.CommandScrapingForum(message);
                    break;
            }
        }
    }
}
