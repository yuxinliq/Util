package com.yu.util.file;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.net.MalformedURLException;

@RunWith(SpringRunner.class)
public class SmbFileTest {
    @Test
    public void test() throws Exception {
        SmbFileResolver fileResolver = new SmbFileResolver("/Multimedia/01_Asia/北原多香子全集");
        fileResolver.showAllSmbFileNames();
    }

    @Test
    public void testMove() throws Exception {
        SmbFileResolver fileResolver = new SmbFileResolver("/Multimedia/01_Asia/02_合集");
        fileResolver.moveAll2BaseDir();
    }

    @Test
    public void test2() throws Exception {
        SmbFileResolver fileResolver = new SmbFileResolver("/Multimedia/01_Asia/02_合集");
        fileResolver.doEvent4All((file) -> {
            String fileName = file.getName();
            String newName = geNewName(file);
            if (newName == null) {
                return;
            }
            System.out.println(fileName + "->" + newName);
            file.renameTo(SmbFileResolver.newSameLevelSmbFile(file, newName));
        });
    }

    private String geNewName(SmbFile file) {
        String str2clear = "-";
        String fileName = file.getName();
        if (!fileName.contains(str2clear)) {
            return null;
        }
        return fileName.replace(str2clear, "");
    }

    public static void main(String[] args) throws Exception {
        SmbFileResolver fileResolver = new SmbFileResolver("E:/media/君島みお/1");
        fileResolver.addSuffix4All();
    }
}
