package jp.mincra.mathclub.commands;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

public class CommandHelp {
    public static void CommandHelp(Message message) {
        message.getChannel().block().createMessage("").block();
    }
}
