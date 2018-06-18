package com.yu.util.file;

import jcifs.smb.SmbFile;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class SmbFileResolver {
    private String baseDir;
    private LinkedHashMap<String, SmbFile> smbFilesMap = new LinkedHashMap<>();
    private boolean isTest;

    private String host = "192.168.31.21";//远程服务器的地址
    private String username = "admin";//用户名
    private String password = "18219ooo";//密码

    public SmbFileResolver(String baseDir) throws Exception {
        this.baseDir = "smb://" + username + ":" + password + "@" + host + baseDir + (baseDir.endsWith("/") ? "" : "/");
        SmbFile dir = new SmbFile(this.baseDir);
        if (dir.isDirectory() && dir.exists()) {
            doScan(dir);
        } else {
            throw new RuntimeException(dir + "is not a directory or not exist!");
        }
    }

    public SmbFileResolver(String baseDir, boolean isTest) throws Exception {
        this(baseDir);
        this.isTest = isTest;
    }

    private void doScan(SmbFile dir) throws Exception {
        if (dir == null || !dir.isDirectory()) {
            return;
        }
        for (SmbFile SmbFile : dir.listFiles()) {
            if (SmbFile.isDirectory()) {
                doScan(SmbFile);
            } else {
                smbFilesMap.put(getName(SmbFile), SmbFile);
            }
        }
    }

    private String getName(SmbFile SmbFile) {
        return SmbFile.getPath();
    }

    public void showAllSmbFileNames() {
        for (Map.Entry<String, SmbFile> entry : smbFilesMap.entrySet()) {
            System.out.println(entry.getKey());
        }
    }

    public void doEvent4All(SmbFileEvent event) {
        for (SmbFile SmbFile : smbFilesMap.values()) {
            try {
                event.doWork(SmbFile);
                if (isTest) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface SmbFileEvent {
        void doWork(SmbFile SmbFile) throws Exception;
    }

    public void addSuffix4All() {
        this.doEvent4All((SmbFile) -> {
            if (SmbFile == null) {
                return;
            }
            Scanner sc = new Scanner(System.in);
            String SmbFileName = SmbFile.getName();
            String dot = ".";
            if (SmbFileName.contains(dot)) {
                String SmbFilePre = SmbFileName.substring(0, SmbFileName.indexOf(dot));
                System.out.println(SmbFilePre);
                String next = sc.next();
                if ("0".equals(next)) {
                    return;
                }
                String SmbFileSuff = SmbFileName.substring(SmbFileName.indexOf(dot), SmbFileName.length());
                String newSmbFileName = SmbFilePre + "-" + next + SmbFileSuff;
                System.out.println(SmbFileName + "->" + newSmbFileName);
                SmbFile.renameTo(SmbFileResolver.newSameLevelSmbFile(SmbFile, newSmbFileName));
            }
        });
    }

    public void moveAll2BaseDir() {
        this.doEvent4All((file) -> {
            String newFileName = baseDir + file.getName();
            if (!file.getPath().equals(newFileName)) {
                System.out.println(file.getPath() + "\t->\t" + newFileName);
                file.renameTo(new SmbFile(newFileName));
            }
        });
    }

    public static SmbFile newSameLevelSmbFile(SmbFile SmbFile, String newName) throws Exception {
        SmbFile newSmbFile = new SmbFile(SmbFile.getParent(), newName);
        if (newSmbFile.exists()) {
            throw new RuntimeException("SmbFile:" + newSmbFile.getPath() + " exists");
        } else {
            return newSmbFile;
        }
    }
}
