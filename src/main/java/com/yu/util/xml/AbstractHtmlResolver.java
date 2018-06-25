package com.yu.util.xml;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AbstractHtmlResolver {
    protected Map<String, Video> map = new HashMap<>();

    public String getName(String fileName) {
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        Matcher m = getMatcher(fileName);
        if (!m.find()) {
            return null;
        }
        String key = getKey(fileName);
        Video value = map.get(key.toUpperCase());
        if (value == null) {
            return null;
        }
        String sub = m.group(3) == null ? "" : m.group(3);
        return key + sub + " " + value.getActorsStr() + value.getNameStr() + suffix;
    }

    public List<String> checkNotExistIn(List<String> fileNames) {
        Map<String, String> nameMap = new HashMap<>();
        for (String fileName : fileNames) {
            String key = getKey(fileName);
            if (key != null) {
                nameMap.put(key, fileName);
            }
        }
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Video> entry : map.entrySet()) {
            if (nameMap.get(entry.getKey()) == null) {
                result.add(entry.getKey() + " " + entry.getValue().getName());
            }
        }
        Collections.sort(result);
        return result;
    }

    public Map<String, Video> getMap() {
        return map;
    }

    public static class Video {
        private String key;
        private String name;
        private List<String> actors;

        private String getNameStr() {
            int endIndex = 60;
            if (name.trim().length() > endIndex) {
                return name.trim().substring(0, endIndex);
            }
            return name.trim();
        }

        private String getActorsStr() {
            if (actors == null || actors.size() <= 1) {
                return "";
            }
            List<String> list = new ArrayList<>();
            for (String actor : actors) {
                list.add(actor);
                if (list.size() >= 5) {
                    break;
                }
            }
            return list.toString().replaceAll("\\s+", "");
        }


        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getActors() {
            return actors;
        }

        public void setActors(List<String> actors) {
            this.actors = actors;
        }

        @Override
        public String toString() {
            return "Video{" +
                    "key='" + key + '\'' +
                    ", name='" + name + '\'' +
                    ", actors=" + actors +
                    '}';
        }
    }

    public static String getKey(String fileName) {
        Matcher m = getMatcher(fileName);
        if (!m.find()) {
            return null;
        }
        return m.group(1).toLowerCase() + "-" + m.group(2);
    }

    public static Matcher getMatcher(String fileName) {
        Pattern p = Pattern.compile("([A-Za-z]+)\\-?([0-9]+)([^\\.^\\s^\\)]+)?");
        return p.matcher(fileName);
    }
}
