package jp.mincra.mathclub.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WebUtil {
    //【Java】指定したURLの存在確認
    //https://qiita.com/nbkn/items/4a081f2893a4ab844340
    public static boolean isExistURL(String urlStr) {
        URL url;
        int status = 0;
        try {
            url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.connect();
            status = conn.getResponseCode();
            conn.disconnect();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (status == HttpURLConnection.HTTP_OK) {
            return true;
        } else {
            return false;
        }
    }
}
