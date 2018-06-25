package com.yu.util.xml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingleHtmlResolver extends AbstractHtmlResolver {
    public static final String BASE_SITE = "https://www.javbus8.pw/";

    public SingleHtmlResolver(String fileName) throws Exception {
        Pattern p = Pattern.compile("([A-Za-z]+)\\-?([0-9]+)");
        Matcher m = p.matcher(fileName);
        if (!m.find()) {
            throw new RuntimeException("unMatchedFileName:" + fileName);
        }
        Video video = new Video();
        video.setKey(m.group(1).toUpperCase() + "-" + getNum(m.group(2)));
        URL url = new URL(BASE_SITE + video.getKey());
        Document document = Jsoup.parse(url, 10000);
        Elements infos = document.select(".container h3");
        String originName = infos.text();
        video.setName(originName.startsWith(video.getKey()) ? originName.replace(video.getKey(), "").trim() : originName);
        Elements actors = document.select(".container ul li");
        video.setActors(Arrays.asList(actors.text().split("\\s+")));
        this.map.put(video.getKey(), video);
    }

    private String getNum(String num) {
        if (num.length() <= 3 || !num.startsWith("0")) {
            return num;
        }
        String result = num.substring(num.lastIndexOf("0") + 1, num.length());
        if (result.length() >= 3) {
            return result;
        }
        for (int i = 0; i < 3 - result.length(); i++) {
            result += "0";
        }
        return result;
    }

    @Override
    public String toString() {
        return "SingleHtmlResolver{" +
                "map=" + map +
                '}';
    }

    public static void main(String[] args) throws Exception {
        String fileName = "HUNTA-460 「ズ.mp4";
        SingleHtmlResolver singleHtmlResolver = new SingleHtmlResolver(fileName);
        String name = singleHtmlResolver.getName(fileName);
        System.out.println(singleHtmlResolver);
        System.out.println(name);
    }

}
