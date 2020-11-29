package jp.mincra.mathclub.commands;

import discord4j.core.object.entity.Message;
import discord4j.rest.util.Color;
import jp.mincra.mathclub.objects.ScrapingForum;
import jp.mincra.mathclub.objects.beans.MCThread;

public class CommandScrapingForum {
    public static void CommandScrapingForum(Message message){
        String[] args = message.getContent().split(" ");
        switch (args[1]){
            case "get":
                commandGet(message);
                break;
        }
    }

    private static void commandGet(Message message){
        String[] args = message.getContent().split(" ");
        switch (args[2]){
            case "id":
                getThreadFromNumber(message,Integer.parseInt(args[3]));
        }
    }

    public static void getThreadFromNumber(Message message, int number){
        MCThread thread = new MCThread();
        for (MCThread mcThread : ScrapingForum.threadArrayList) {
            if (mcThread.getNumber() == number) {
                thread = mcThread;
                String subject = thread.getSubject();
                String text = thread.getText();
                String author = thread.getAuthor();
                String url = thread.getUrl();
                message.getChannel().block().createEmbed(embedCreateSpec -> embedCreateSpec
                        .setTitle(subject)
                        .setDescription(text)
                        .setAuthor(author,url,"https://avatars1.githubusercontent.com/u/14019495?s=460&u=fa60eaf25e3de57740a783a9f7541cbaeb6990b2&v=4")
                        .setColor(Color.DARK_GRAY)).block();
                break;
            } else {
                thread = null;
                message.getChannel().block().createEmbed(embedCreateSpec -> embedCreateSpec
                        .setTitle(number+"のスレッドは存在しません")
                        .setAuthor("","","https://avatars1.githubusercontent.com/u/14019495?s=460&u=fa60eaf25e3de57740a783a9f7541cbaeb6990b2&v=4")
                        .setColor(Color.DARK_GRAY)).block();
                break;
            }
        }


    }
}
