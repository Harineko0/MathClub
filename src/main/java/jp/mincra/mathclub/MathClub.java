package jp.mincra.mathclub;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import jp.mincra.mathclub.objects.ScrapingForum;
import jp.mincra.mathclub.util.PropertyUtil;

import java.io.IOException;
import java.util.Date;

public class MathClub {

    public static GatewayDiscordClient client;
    public static Date date = new Date();

    public static void main(String args[]) {

        ScrapingForum.ScrapingForum();

        //JSONロード
        try{
            PropertyUtil.setPropertyFile();
            PropertyUtil.reloadProperty();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //tokenロード
        String token = PropertyUtil.jsonNode.get("properties").get("token").asText();

        //client作成
        client = DiscordClientBuilder.create(token).build().login().block();

        //時差9時間
        date.setHours(date.getHours()+ PropertyUtil.jsonNode.get("properties").get("time_difference").asInt());
        //時間割
//        CommandSchedule.CommandSchedule(date);

        //ログイン時のイベント
        client.getEventDispatcher().on(ReadyEvent.class)
                .subscribe(event -> {
                    User self = event.getSelf();
                    System.out.println(String.format("Logged in as %s#%s", self.getUsername(), self.getDiscriminator()));
                });

        //メッセージ送信時ののイベント
        client.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            MathClubEvent.MessageCreate(message);
        });

        client.onDisconnect().block();

    }
}
