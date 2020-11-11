package jp.mincra.mathclub;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.command.CommandClient;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.io.IOException;

class Main {

    private static JDA jda;
    //ロード
    public static final String token = PropertyUtil.getProperty("token");
    public static final String clientid = PropertyUtil.getProperty("clientid");
    private static final String COMMAND_PREFIX = "!"; // コマンドの接頭辞

    public static void main(String args[]) throws IOException, LoginException {
        //MathClubフォルダー作成
        PropertyUtil.setFiles();

        // コマンドを扱うイベントリスナを生成
        CommandClient commandClient = new CommandClientBuilder()
                .setPrefix(COMMAND_PREFIX) // コマンドの接頭辞
                .setStatus(OnlineStatus.ONLINE) // オンラインステータスの設定
                .setActivity(Activity.playing("数研BOT")) // ステータスの設定（視聴中、プレイ中など）
                .setOwnerId(clientid)
                .build();

        jda = new JDABuilder(AccountType.BOT)
                .setToken(token) // トークンを設定
                .addEventListeners(commandClient) // commandClientを設定
                .build();
    }
}
