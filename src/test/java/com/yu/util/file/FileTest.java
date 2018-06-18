package com.yu.util.file;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
public class FileTest {
    @Test
    public void test() {
        FileResolver fileResolver = new FileResolver("E:\\media\\君島みお\\1");
        fileResolver.showAllFileNames();
    }

    @Test
    public void test2() {
        FileResolver fileResolver = new FileResolver("E:\\media\\君島みお\\1");
        fileResolver.doEvent4All((file) -> {
            String fileName = file.getName();
            String newName = geNewName(file);
            if (newName == null) {
                return;
            }
            System.out.println(fileName + "->" + newName);
            file.renameTo(FileResolver.newSameLevelFile(file, newName));
        });
    }

    private String geNewName(File file) {
        String str2clear = "-";
        String fileName = file.getName();
        if (!fileName.contains(str2clear)) {
            return null;
        }
        return fileName.replace(str2clear, "");
    }

    public static void main(String[] args) {
        FileResolver fileResolver = new FileResolver("E:\\media\\君島みお\\1");
        fileResolver.addSuffix4All();
    }
}
