package jp.mincra.mathclub.objects;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.TextChannel;
import discord4j.rest.util.Color;
import jp.mincra.mathclub.MathClub;
import jp.mincra.mathclub.objects.beans.MCThread;
import jp.mincra.mathclub.util.BinaryTreeUtil;
import jp.mincra.mathclub.util.PropertyUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class ScrapingForum {
    public static List<MCThread> threadArrayList = new ArrayList<>();
    public static BinaryTreeUtil threadBinaryTree = new BinaryTreeUtil();
    static Snowflake snowflake = Snowflake.of(PropertyUtil.jsonNode.get("properties").get("channel").get("forum").asText());

    public static void ScrapingForum(int page) {
        for (int i=1; i<page*10-8; i+=10){
            String url = PropertyUtil.jsonNode.get("properties").get("forum_url").asText()+"page="+i;
            System.out.println("----- Order Scraping from "+url+" -----");
            //discordログ
            int finalI = 1+(i-1)/10;
            MathClub.client.getChannelById(snowflake).cast(TextChannel.class).flatMap(channel -> channel.createMessage(finalI +"ページのロードを開始します...\n"+url)).block();

            if (isExistForum(url)){
                runScraping(url);
            } else {
                System.out.println("----- [ERROR] "+url+" doesn't exist -----");
                MathClub.client.getChannelById(snowflake).cast(TextChannel.class).flatMap(channel -> channel.createMessage("[ERROR] "+finalI +"ページは存在しません\n")).block();

                break;
            }
        }
    }

    public static void ScrapingForum(int page, Message message){
        String url = PropertyUtil.jsonNode.get("properties").get("forum_url").asText()+"page="+page;
        if (isExistForum(url)){
            ScrapingForum(page);
        } else {
//            message.getChannel().block().createMessage(page+"ページは存在しません").block();
        }
    }

    //スレが存在するかどうか
    public static boolean isExistForum(String urlStr){
        Document document = null;
        try {
            document = Jsoup.connect(urlStr).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (document.getElementsByTag("blockquote").select("font").isEmpty()){
            return false;
        } else {
            return true;
        }

    }

    public static void runScraping(String forumUrl){
        System.out.println("----- Run Scraping from "+forumUrl+" -----");
        MathClub.client.getChannelById(snowflake).cast(TextChannel.class).flatMap(channel -> channel.createMessage("掲示板のロードを実行します...\n")).block();
        Document document = null;
        try {
            document = Jsoup.connect(forumUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements fontElements = document.getElementsByTag("blockquote").select("font");

        int parent = 0;
        String url = null;
        for (Element element : fontElements) {
            int number = getNumber(element.text());

            //二分木に存在していたら追加
            if (threadBinaryTree.contains(number)){
                System.out.println("[ERROR] "+number+"番のスレは既にリストに存在しています。");
//                MathClub.client.getChannelById(snowflake).cast(TextChannel.class).flatMap(channel -> channel.createMessage("[ERROR] "+number+"番のスレは既にリストに存在しています。")).block();
            } else {
                MCThread thread = new MCThread();

                thread.setNumber(number);
                threadBinaryTree.add(number);

                if (element.select("a").attr("href").isEmpty()) {
                    //返信であるとき
                    thread.setParent(parent);
                } else {
                    //元スレであるとき
                    parent = getNumber(element.text());
                    url = element.select("a").attr("href");
                }
                thread.setUrl(url);
                thread.setSubject(getSubject(element.text()));
                thread.setText(getText(thread.getNumber(), thread.getUrl()));
                thread.setAuthor(getAuthor(element.text()));
                thread.setDate(getDate(element.text()));

                System.out.println("\n" + element.text()
                        + "\nURL: " + thread.getUrl()
                        + "\nnumber: " + thread.getNumber()
                        + "\nparent: " + thread.getParent()
                        + "\ndate: " + thread.getDate().toString()
                        + "\nauthor: " + thread.getAuthor()
                        + "\nsubject: " + thread.getSubject()
                        + "\ntext: " + thread.getText());

                //リストに追加
                threadArrayList.add(thread);
            }
        }
        System.out.println("----- Finish Scraping from "+forumUrl+" -----");
        MathClub.client.getChannelById(snowflake).cast(TextChannel.class).flatMap(channel -> channel.createMessage("掲示板のロードが完了しました。\n")).block();
    }

    //スレ番号取得
    public static int getNumber(String str){
        String number = new String();

        //string型の数字を作る
        if (str.charAt(0) == '[') {

            for (int i = 1; i < str.length(); i++) {
                char c = str.charAt(i);
                //[なら繰り返しを抜ける
                if (c == ']') {
                    break;
                }
                number = number + c;
            }
            return Integer.parseInt(number);
        } else {
            return 0;
        }
    }

    //日付取得
    public static Date getDate(String str){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 (E) HH時mm分");
        String stringDate = new String();

        for(int i=str.length()-2; i>0; i--){
            char c = str.charAt(i);
            //[なら繰り返しを抜ける
            if(c == '['){
                break;
            }
            stringDate = c + stringDate;
        }
        Date date = new Date();
        try {
            date = dateFormat.parse(stringDate);
        } catch (ParseException e){
            e.printStackTrace();
        }
        return date;
    }

    //著者を取得
    public static String getAuthor(String str){
        String author = new String();
        for(int i=str.length()-25; i>0; i--){
            String dash = new String();

            //-----なら繰り返しを抜ける
            for(int j=-1; j>-6; j--){
                dash = str.charAt(i+j) + dash;
            }
            if(dash.equals("-----")){
                break;
            }
            author = str.charAt(i)+author;
        }
        return author;
    }

    //タイトルを取得
    public static String getSubject(String str){
        String subject = new String();
        String number = String.valueOf(getNumber(str));
        int numberLength = number.length();

        //タイトルの前にスペースが入っている場合
        int p = 0;
        int q = 0;
        if (str.charAt(4+numberLength+1) == ' '){
            p = 1;
        } else {
            q = 1;
        }

        //"[] ▼+スレ番号"の文字数
        for(int i=4+numberLength; i<str.length(); i++){

            String dash = "";
            //-----なら繰り返しを抜ける
            for(int j=2; j<7; j++){
                dash = dash + str.charAt(i+j-q);
            }
            if(dash.equals("-----")){
                break;
            }
            subject = subject + str.charAt(i+p);
        }
        return subject;
    }

    //内容を取得
    public static String getText(int number, String url){
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements centerElements = document.getElementsByTag("center");
        for (Element element : centerElements) {
            if (getNumber(element.text()) == number){
                return element.select("blockquote").text();
            }
        }
        return null;
    }
}