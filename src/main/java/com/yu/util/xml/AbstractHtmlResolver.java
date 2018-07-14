package com.yu.util.xml;

import com.baidu.translate.demo.TransApi;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AbstractHtmlResolver {
    protected Map<String, Video> map = new HashMap<>();

    public String getName(String fileName) {
        return getName(fileName, true, true);
    }

    public String getName(String fileName, boolean showSingleActors, boolean trans) {
        NameKey nameKey = new NameKey(fileName);
        Video value = map.get(nameKey.key);
        if (value == null) {
            return null;
        }
        String nameStr = trans ? TransApi.getTransResult(value.getNameStr()).toString() : value.getNameStr();
        String result = nameKey.key + nameKey.splitIndex + " " + value.getActorsStr() + nameStr;
        if (showSingleActors && value.getActors().size() == 1 && !value.getActors().get(0).isEmpty()) {
            result = "[" + value.getActors().get(0) + "]" + result;
        }
        int endIndex = 80;
        if (result.length() > endIndex) {
            result = result.substring(0, endIndex);
        }
        return result + nameKey.suffix;
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
            String result = name.trim();
            return result.replace(":", "").replace("/", "");
        }

        private String getActorsStr() {
            if (actors == null || actors.size() <= 1) {
                return "";
            }
            List<String> list = new ArrayList<>();
            for (String actor : actors) {
                if (list.size() > 5) {
                    list.add("..");
                    break;
                }
                list.add(actor);
            }
            String result = list.toString().replaceAll("\\s+", "");
            if ("[]".equals(result)) {
                return "";
            }
            return result;
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
        Pattern p = Pattern.compile("([A-Za-z]{3,})\\-?([0-9]{3,})([A-Za-z0-9_]+)?");
        return p.matcher(fileName);
    }

    public static Matcher parse(String fileName) {
        Pattern p = Pattern.compile("([A-Za-z]{3,})\\-?([0-9]{3,})([A-Za-z0-9_]+)?");
        return p.matcher(fileName);
    }

    public static class NameKey {
        public String key;
        public String splitIndex = "";
        public String suffix;

        public NameKey(String fileName) {
            if (!parseRider(fileName) && !parseInfantry(fileName)) {
                throw new RuntimeException("not match:" + fileName);
            }
            this.suffix = fileName.substring(fileName.lastIndexOf("."));
        }

        private boolean parseRider(String fileName) {
            Pattern p = Pattern.compile("([A-Za-z2]{2,})\\-?([A-Za-z]?[0-9]{2,})([A-Za-z0-9_\\-]+)?");
            Matcher m = p.matcher(fileName);
            if (m.find()) {
                String corpCode = m.group(1).toUpperCase();
                if (!"RED".equalsIgnoreCase(corpCode)) corpCode += "-";
                String numCode = m.group(2);
                if ((!"SOE".equalsIgnoreCase(corpCode) || !"PPSD".equalsIgnoreCase(corpCode)) && numCode.length() > 3)
                    numCode = numCode.substring(numCode.length() - 3, numCode.length());
                this.key = corpCode + numCode;
                this.splitIndex = m.group(3) == null ? "" : m.group(3);
                return true;
            }
            return false;
        }

        private boolean parseInfantry(String fileName) {
            Matcher m = Pattern.compile("([0-9]{6})(.)?([0-9]{3})").matcher(fileName);
            if (m.find()) {
                this.key = m.group(1) + ((m.group(2) == null) ? "_" : m.group(2)) + m.group(3);
                return true;
            }
            return false;
        }

        private String getNum(String num) {
            if (!num.startsWith("0")) {
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
    }

}
