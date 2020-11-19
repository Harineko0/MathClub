package jp.mincra.mathclub;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import jp.mincra.mathclub.util.MathClubProperty;

import java.io.IOException;

public class MathClub {

    public static void main(String args[]) {

        //JSONロード
        try{
            MathClubProperty.setPropertyFile();
            MathClubProperty.reloadProperty();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //tokenロード
        String token = MathClubProperty.jsonNode.get("properties").get("token").asText();

        //client作成
        GatewayDiscordClient client = DiscordClientBuilder.create(token).build().login().block();

        //ログイン時のイベント
        client.getEventDispatcher().on(ReadyEvent.class)
                .subscribe(event -> {
                    User self = event.getSelf();
                    System.out.println(String.format("Logged in as %s#%s", self.getUsername(), self.getDiscriminator()));
                });

        //メッセージ送信時ののイベント
        client.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            Event.MessageCreate(message);
        });

        client.onDisconnect().block();
    }
}
