package com.yu.util.file;

import java.io.File;
import java.util.*;

public class FileResolver extends AbstractFileResolver {
    private String baseDir;
    private LinkedHashMap<String, File> filesMap = new LinkedHashMap<>();
    private boolean isTest;

    public FileResolver(String baseDir) {
        this.baseDir = baseDir + (baseDir.endsWith("\\") ? "" : "\\");
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

    public List<String> getAllFileName() {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, File> entry : filesMap.entrySet()) {
//            System.out.println(entry.getKey());
            result.add(entry.getValue().getName());
        }
        return result;
    }

    public void doEvent4All(FileEvent event) {
        for (File file : filesMap.values()) {
            executor.execute(() -> {
                try {
                    event.doWork(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            if (isTest) {
                break;
            }
        }
    }

    public interface FileEvent {
        void doWork(File file) throws Exception;
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

    public void moveAll2BaseDir() {
        this.doEvent4All((file) -> {
            String newFileName = baseDir + file.getName();
            if (!file.getPath().equals(newFileName)) {
                File dest = new File(newFileName);
                if (dest.exists() && !dest.isFile()) {
                    File tmpDir = new File(baseDir + "tmp/");
                    if (!tmpDir.exists()) {
                        tmpDir.mkdirs();
                    }
                    dest = new File(baseDir + "tmp/" + file.getName());
                }
                System.out.println(file.getPath() + "\t->\t" + dest.getPath());
                file.renameTo(dest);
            }
        });
    }
}
