package com.yu.util.file;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class FileResolver {
    private String baseDir;
    private LinkedHashMap<String, File> filesMap = new LinkedHashMap<>();
    private boolean isTest;

    public FileResolver(String baseDir) {
        this.baseDir = baseDir;
        File dir = new File(baseDir);
        if (dir.isDirectory() && dir.exists()) {
            doScan(dir);
        } else {
            throw new RuntimeException(dir + "is not a directory or not exist!");
        }
    }

    public FileResolver(String baseDir, boolean isTest) {
        this(baseDir);
        this.isTest = isTest;
    }

    private void doScan(File dir) {
        if (dir == null || !dir.isDirectory()) {
            return;
        }
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                doScan(file);
            } else {
                filesMap.put(getName(file), file);
            }
        }
    }

    private String getName(File file) {
        return file.getPath();
    }

    public void showAllFileNames() {
        for (Map.Entry<String, File> entry : filesMap.entrySet()) {
            System.out.println(entry.getKey());
        }
    }

    public void doEvent4All(FileEvent event) {
        for (File file : filesMap.values()) {
            try {
                event.doWork(file);
                if (isTest) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface FileEvent {
        void doWork(File file);
    }

    public void addSuffix4All() {
        this.doEvent4All((file) -> {
            if (file == null) {
                return;
            }
            Scanner sc = new Scanner(System.in);
            String fileName = file.getName();
            String dot = ".";
            if (fileName.contains(dot)) {
                String filePre = fileName.substring(0, fileName.indexOf(dot));
                System.out.println(filePre);
                String next = sc.next();
                if ("0".equals(next)) {
                    return;
                }
                String fileSuff = fileName.substring(fileName.indexOf(dot), fileName.length());
                String newFileName = filePre + "-" + next + fileSuff;
                System.out.println(fileName + "->" + newFileName);
                file.renameTo(FileResolver.newSameLevelFile(file, newFileName));
            }
        });
    }

    public static File newSameLevelFile(File file, String newName) {
        File newFile = new File(file.getParent(), newName);
        if (newFile.exists()) {
            throw new RuntimeException("file:" + newFile.getPath() + " exists");
        } else {
            return newFile;
        }
    }
}