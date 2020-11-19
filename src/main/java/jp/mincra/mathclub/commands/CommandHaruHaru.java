package jp.mincra.mathclub.commands;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import jp.mincra.mathclub.util.MathClubProperty;

import java.util.Random;

public class CommandHaruHaru {

    public static void createMessage(Message message) {

        MessageChannel channel = message.getChannel().block();

        //HaruHaruチャンネルのID読み込み
        String HaruHaru = MathClubProperty.jsonNode.get("properties").get("channel").get("HaruHaru").asText();

        if (message.getChannelId().toString().equals("Snowflake{"+ HaruHaru +"}")) {
            channel.createMessage(getString()+"！").block();
        }
    }

    public static String getString() {

        String result = "";
        Random random = new Random();
        String[] Chara = { "は", "る" };

        for (int i = 0; i < 4; i++) {
            result = result + Chara[random.nextInt(2)];
        }

        return result;
    }

}
