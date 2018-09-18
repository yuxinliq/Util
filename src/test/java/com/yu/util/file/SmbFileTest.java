package com.yu.util.file;

import com.yu.util.xml.SingleHtmlResolver;
import org.jsoup.HttpStatusException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SmbFileTest {
    @Test
    public void test() throws Exception {
        SmbFileResolver fileResolver = new SmbFileResolver("/Multimedia/01_Asia/北原多香子全集");
        fileResolver.showAllSmbFileNames();
    }

    @Test
    public void testMove2Base() throws Exception {
        SmbFileResolver fileResolver = new SmbFileResolver("/Multimedia/01_Asia/02_合集");
        fileResolver.moveAll2BaseDir();
    }

    @Test
    public void testRename() throws Exception {
        SmbFileResolver fileResolver = new SmbFileResolver("/Multimedia/01_Asia/01_单人");
        fileResolver.doEvent4All((file) -> {
            String fileName = file.getName();
            String newName = FileUtil.excludeTag(file.getName());
            if (newName == null) {
                return;
            }
            System.out.println(fileName + "->\n" + newName);
            file.renameTo(SmbFileResolver.newSameLevelSmbFile(file, newName));
        });
    }

    @Test
    public void testSingleRenameHtml() throws Exception {
        SmbFileResolver fileResolver = new SmbFileResolver("\\Multimedia\\01_Asia\\篠田步美\\undo");
        fileResolver.doEvent4All((file) -> {
            String fileName = file.getName();
            try {
                String newName = new SingleHtmlResolver(fileName).getName(fileName);
                if (newName == null) {
                    return;
                }
                System.out.println(fileName + "->\n" + newName);
                file.renameTo(SmbFileResolver.newSameLevelSmbFile(file, newName));
            } catch (HttpStatusException e) {
                System.err.println(e.getStatusCode() + ":" + e.getUrl());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        });
    }

    public static void main(String[] args) throws Exception {
        SmbFileResolver fileResolver = new SmbFileResolver("E:/media/君島みお/1");
        fileResolver.addSuffix4All();
    }
}
