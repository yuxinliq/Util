package com.yu.util.xml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class HtmlFileResolver extends AbstractHtmlResolver {
    private File file;

    public HtmlFileResolver(String fileName) throws Exception {
        file = new File(fileName);
        if (!file.exists()) {
            throw new RuntimeException(fileName + "not exists");
        }
        if (file.isDirectory()) {
            for (File sub : file.listFiles()) {
                if (sub.isFile() && sub.getName().endsWith(".html")) {
                    resolveSingleFile(sub);
                }
            }
        } else {
            resolveSingleFile(file);
        }
    }

    private void resolveSingleFile(File file) throws IOException {
        Document document = Jsoup.parse(file, "utf-8");
        Elements infos = document.select(".photo-info");
        for (Element info : infos) {
            String str = info.child(0).text().split(" / ")[0];
            int lastSplit = str.lastIndexOf(" ");
            if (lastSplit > 0) {
                String key = str.substring(lastSplit).trim().toLowerCase();
                String name = str.substring(0, lastSplit);
                name = nameResolve(name);
//                System.out.println(key + ":\t" + name);
                Video value = new Video();
                value.setKey(key.toUpperCase());
                value.setName(name);
                map.put(key, value);
            }
        }
    }

    protected String nameResolve(String name) {
        name = name.replace("高清", "");
        name = name.replace("字幕", "");
        name = name.replaceAll("\\s+", " ");
        return name;
    }

}
