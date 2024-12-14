package com.project.demo;

import java.net.HttpURLConnection;
import java.net.URL;


public class Test {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://api.stackexchange.com/2.3/questions?order=desc&sort=votes&tagged=java&site=stackoverflow&key=rl_zyHKGUaHDXG8ezSdnxX2xfgsm");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);  // 5 秒超时
            conn.setReadTimeout(5000);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Java Application)");

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
