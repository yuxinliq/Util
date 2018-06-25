package com.yu.util.file;

import jcifs.smb.SmbFile;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {

    public static String geNewName(String fileName) {
        String[] strings = {".HD", "HD-", "-1080p", "21bt.net-", "HD", "FHD", "Thz.la", "【高清共享】", "中文",
                "Prestige", "h.m.p", "Kirara Asuka", "明日花キララ ", "明日花キララ-",
                "[", "]", "()", "_", "- "};
        String[] regexs = {"[0-9]+_3xplanet_"};
        String removeStr = FileUtil.removeStr(fileName, strings);
        String removeRegex = FileUtil.removeRegex(removeStr == null ? fileName : removeStr, regexs);
        return removeRegex == null ? removeStr : removeRegex;
    }

    public static String regexNewName(String fileName) {
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        Pattern p = Pattern.compile("([A-Za-z]+)\\-?([0-9]+)(([A-Za-z]+[0-9]?)|(_[0-9]))?");
        Matcher m = p.matcher(fileName);
        if (m.find()) {
            String group = m.group(3) == null ? "" : m.group(3);
            return m.group(1).toLowerCase() + "-" + m.group(2) + group + suffix;
        }
        return fileName;
    }

    public static String removeStr(String fileName, String... str2clear) {
        String newName = fileName;
        for (String s : str2clear) {
            if (newName.contains(s)) {
                newName = newName.replace(s, "");
            }
        }
        if (newName.equals(fileName)) {
            return null;
        }
        return newName;
    }

    public static String removeRegex(String fileName, String... str2clear) {
        String newName = fileName;
        for (String s : str2clear) {
            Pattern p = Pattern.compile(s);
            Matcher m = p.matcher(newName);
            if (m.find()) {
                newName = newName.replaceAll(s, "");
            }
        }
        if (newName.equals(fileName)) {
            return null;
        }
        return newName;
    }
}
