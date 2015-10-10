package com.jsouptest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by chenzhilei on 15/9/26.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("http://tuan.airchina.com/index.htm")
                .data("query", "Java")
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(3000)
                .post();
        String title = doc.title();


        System.out.println(title);

        Elements elements = doc.getElementById("products").getElementsByClass("infor");

        for (Element element : elements) {
            Elements citys = element.getElementsByClass("city");
            for (Element city : citys) {
                Elements cityNames = city.getElementsByClass("cityname");
                Elements lables = city.getElementsByClass("icon2");
                Elements imgs = null;
                if (lables.size() > 0) {
                    imgs = lables.get(0).getElementsByTag("img");
                } else {
                    imgs = city.getElementsByTag("img");
                    imgs.remove(0);
                }
                String sem = "";
                for (int i = 0; i < cityNames.size(); i++) {
                    Element cityName = cityNames.get(i);
                    if ("d_icon.gif".indexOf(imgs.get(i % 2).attr("src")) != -1) {
                        sem += cityName.text() + "===>";
                    } else {
                        sem += cityName.text() + "<==>";
                    }


                }
                for (Element cityName : cityNames) {

                }
                System.out.println(sem.substring(0, sem.length() - 4));
            }
        }
    }
}
