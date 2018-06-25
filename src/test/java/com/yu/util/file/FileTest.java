package com.yu.util.file;

import com.yu.util.xml.HtmlFileResolver;
import com.yu.util.xml.SingleHtmlResolver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
public class FileTest {
    @Test
    public void test() {
        FileResolver fileResolver = new FileResolver("E:\\media\\君島みお\\1");
        fileResolver.getAllFileName();
    }

    @Test
    public void test2() {
        FileResolver fileResolver = new FileResolver("E:\\media\\20180623\\RION");
        fileResolver.doEvent4All((file) -> {
            String fileName = file.getName();
            String newName = FileUtil.geNewName(file.getName());
            if (newName == null) {
                return;
            }
            System.out.println(fileName + "->" + newName);
            file.renameTo(FileResolver.newSameLevelFile(file, newName));
        });
    }


    @Test
    public void testRenameHtml() throws Exception {
        HtmlFileResolver htmlFileResolver = new HtmlFileResolver("E:\\media\\renameHtml\\RION");
        FileResolver fileResolver = new FileResolver("E:\\media\\20180623\\RION");
        fileResolver.doEvent4All((file) -> {
            String fileName = file.getName();
            String newName = htmlFileResolver.getName(fileName);
            if (newName == null) {
                return;
            }
            System.out.println(fileName + "->" + newName);
            file.renameTo(FileResolver.newSameLevelFile(file, newName));
        });
    }

    @Test
    public void testSingleRenameHtml() throws Exception {
        FileResolver fileResolver = new FileResolver("F:\\20180625");
        fileResolver.doEvent4All((file) -> {
            String fileName = file.getName();
            String newName = new SingleHtmlResolver(fileName).getName(fileName);
            if (newName == null) {
                return;
            }
            System.out.println(fileName + "->\n" + newName);
            file.renameTo(FileResolver.newSameLevelFile(file, newName));
        });
    }

    @Test
    public void testMove() {
        FileResolver fileResolver = new FileResolver("E:\\media\\20180623\\RION");
        fileResolver.moveAll2BaseDir();
    }

    @Test
    public void testNotExist() throws Exception {
        FileResolver fileResolver = new FileResolver("E:\\media\\20180623\\RION");
        HtmlFileResolver htmlFileResolver = new HtmlFileResolver("E:\\media\\renameHtml\\RION\\RION - 女優 - 影片.html");
        for (String name : htmlFileResolver.checkNotExistIn(fileResolver.getAllFileName())) {
            System.out.println(name);
        }
    }

    public static void main(String[] args) {
        FileResolver fileResolver = new FileResolver("E:\\media\\君島みお\\1");
        fileResolver.addSuffix4All();
    }
}
