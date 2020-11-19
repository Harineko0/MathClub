package jp.mincra.mathclub;

import discord4j.core.object.entity.Message;
import jp.mincra.mathclub.commands.CommandHaruHaru;
import jp.mincra.mathclub.commands.CommandHelp;
import jp.mincra.mathclub.util.MathClubProperty;

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
                    try {
                        MathClubProperty.reloadProperty();
                        message.getChannel().block().createMessage("設定の再読み込みに成功しました。").block();
                    } catch (IOException e) {
                        e.printStackTrace();
                        message.getChannel().block().createMessage("設定の再読み込みに失敗しました。").block();
                    }
                    break;
            }
        }
    }
}
