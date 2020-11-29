package jp.mincra.mathclub.objects.beans;

import java.util.Date;

//クラス型変数の定義
public class Thread {
    private int number;
    private int parent;
    private String subject;
    private String text;
    private String author;
    private Date date;
    private String url;

    public int getNumber(){
        return number;
    }
    public void setNumber(int number){
        this.number = number;
    }

    public int getParent(){
        return parent;
    }
    public void setParent(int parent){
        this.parent = parent;
    }

    public String getSubject(){
        return subject;
    }
    public void setSubject(String subject){
        this.subject = subject;
    }

    public String getText(){
        return text;
    }
    public void setText(String text){
        this.text = text;
    }

    public String getAuthor(){
        return author;
    }
    public void setAuthor(String author){
        this.author = author;
    }

    public Date getDate(){
        return date;
    }
    public void setDate(Date date){
        this.date = date;
    }

    public String getUrl(){
        return url;
    }
    public void setUrl(String url){
        this.url = url;
    }
}