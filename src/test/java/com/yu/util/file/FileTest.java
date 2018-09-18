package com.yu.util.file;

import com.yu.util.xml.HtmlFileResolver;
import com.yu.util.xml.SingleHtmlResolver;
import org.jsoup.HttpStatusException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
public class FileTest {
    @Test
    public void test() {
        FileResolver fileResolver = new FileResolver("E:\\media\\");
        List<String> allFileName = fileResolver.getAllFileName();
        for (String s : allFileName) {
            System.out.println(s);
        }
    }

    @Test
    public void test2() {
        FileResolver fileResolver = new FileResolver("E:\\media\\小澤マリア");
        fileResolver.doEvent4All((file) -> {
            String fileName = file.getName();
            String newName = FileUtil.excludeTag(file.getName());
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
    public void testSingleRenameHtml() {
        FileResolver fileResolver = new FileResolver("E:\\media\\20180902");
        fileResolver.doEvent4All((file) -> {
            try {
                String fileName = file.getName();
                String newName = new SingleHtmlResolver(fileName).getName(fileName, true, true);
                if (newName == null) {
                    return;
                }
                System.out.println(fileName + "->\n" + newName + "\n");
                file.renameTo(FileResolver.newSameLevelFile(file, newName));
            } catch (HttpStatusException e) {
                System.err.println(e.getStatusCode() + ":" + e.getUrl());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        });
    }

    @Test
    public void testMove() {
        FileResolver fileResolver = new FileResolver("E:\\work\\vip视频\\2018期录播视频及资料\\2018年公开课往期录播");
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
