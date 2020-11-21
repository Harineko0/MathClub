package jp.mincra.mathclub.commands;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.channel.TextChannel;
import discord4j.rest.util.Color;
import jp.mincra.mathclub.MathClub;
import jp.mincra.mathclub.util.MathClubDate;
import jp.mincra.mathclub.util.MathClubProperty;
import jp.mincra.mathclub.util.holiday.JapaneseHolidayUtils;
import org.json.JSONArray;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class CommandSchedule {

    //起動時実行
    public static void CommandSchedule(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (calendar.get(Calendar.HOUR) < 7) {
            //今日の時間割をすぐに実行
            runTask(calendar);
        } else {
            //明日の時間割を16:00に実行
            String string0 = MathClubProperty.jsonNode.get("properties").get("timer").get("schedule").asText();
            String[] string1 = string0.split(":",0);
            calendar.set(Calendar.HOUR, Integer.parseInt(string1[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(string1[1]));
            calendar.set(Calendar.SECOND, Integer.parseInt(string1[2]));
            calendar.add(Calendar.DATE,1);
            runTask(calendar);
        }
    }

    private static void runTimer(Calendar calendar){

        calendar.add(calendar.DATE, 1);
        //時間指定
        String string0 = MathClubProperty.jsonNode.get("properties").get("timer").get("schedule").asText();
        String[] string1 = string0.split(":",0);
        calendar.set(Calendar.HOUR, Integer.parseInt(string1[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(string1[1]));
        calendar.set(Calendar.SECOND, Integer.parseInt(string1[2]));

        Date date = calendar.getTime();
        Timer timer = new Timer(true);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runTask(calendar);
                timer.cancel();
            }
        };

        timer.schedule(task, date);
    }

    private static void runTask(Calendar calendar) {
        calendar.add(Calendar.DATE,1);
        int intDay = calendar.get(Calendar.DAY_OF_WEEK);
        int intDate = calendar.get(Calendar.DATE);
        int intMonth = calendar.get(Calendar.MONTH);
        String strDay = MathClubDate.getDayOfWeek(calendar);

        if (intDay == 1 || intDay == 7 || JapaneseHolidayUtils.isNationalHoliday(calendar) == true) {

            Snowflake snowflake = Snowflake.of(MathClubProperty.jsonNode.get("properties").get("channel").get("schedule").get("1-2").asText());
            MathClub.client.getChannelById(snowflake).cast(TextChannel.class).flatMap(channel -> channel.createEmbed(embedCreateSpec -> embedCreateSpec
                    .setTitle(":calendar_spiral: **" + intMonth + "月" + intDate + "日" + strDay + "曜日は休日です。**")
                    .setColor(Color.GRAY))).block();

            runTimer(calendar);

        } else {
            //A週B週
            String ab;
            if (intDate % 2 == 0) {
                ab = "b";
            } else {
                ab = "a";
            }

            //日の時間割取得
            JSONArray jsonArray = new JSONArray(MathClubProperty.jsonNode.get("schedule").get(ab).get(intDay).toString());

            //時間割文字列生成
            String result = "";
            for (int i = 0; i < jsonArray.length(); i++) {
                int j = i + 1;
                result = result + j + "限: " + jsonArray.get(i) + "\n";
            }

            //メッセージ送信
            String finalResult = result;
            Snowflake snowflake = Snowflake.of(MathClubProperty.jsonNode.get("properties").get("channel").get("schedule").get("1-2").asText());
            MathClub.client.getChannelById(snowflake).cast(TextChannel.class).flatMap(channel -> channel.createEmbed(embedCreateSpec -> embedCreateSpec
                    .setTitle(":calendar_spiral: **" + intMonth + "月" + intDate + "日 " + strDay + "曜日の時間割**")
                    .setDescription(finalResult)
                    .setColor(Color.GRAY))).block();

            calendar.set(Calendar.HOUR,8);
            runTimer(calendar);

            System.out.println(MathClub.date + "[LOG] " + intMonth + "月" + intDate + "日" + strDay + "曜日の時間割を送信しました。");
        }
    }
}
