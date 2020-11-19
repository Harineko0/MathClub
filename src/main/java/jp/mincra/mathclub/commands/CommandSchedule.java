package jp.mincra.mathclub.commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class CommandSchedule {
    public static void CommandSchedule(){
        //定期実行
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Timer timer = new Timer(false);

        Date date = new Date();
        System.out.println(sdf.format(date));

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                System.out.println("てすと");
                timer.cancel();
            }
        };
        try {
            timer.schedule(task, sdf.parse("2020/11/19 22:17:30"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
