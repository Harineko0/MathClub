package jp.mincra.mathclub;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;

import java.io.IOException;

class Main {

    //ロード
    public static final String token = PropertyUtil.getProperty("token");
    public static final String clientid = PropertyUtil.getProperty("clientid");

    public static void main(String args[]) throws IOException {

        //MathClubフォルダー作成
        PropertyUtil.setFiles();

        GatewayDiscordClient client = DiscordClientBuilder.create(token).build().login().block();

        client.getEventDispatcher().on(ReadyEvent.class)
                .subscribe(event -> {
                    User self = event.getSelf();
                    System.out.println(String.format("Logged in as %s#%s", self.getUsername(), self.getDiscriminator()));
                });

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("はるはる！"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("はるはる！"))
                .subscribe();

        client.onDisconnect().block();
    }
}
