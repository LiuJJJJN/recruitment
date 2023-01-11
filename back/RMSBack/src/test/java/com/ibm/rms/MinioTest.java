package com.ibm.rms;

import com.ibm.rms.utils.MinioUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;

@SpringBootTest
public class MinioTest {

    @Autowired
    private MinioUtils minioUtils;

    @Test
    public void testMinioUpload() throws Exception {
        File file = new File("D:\\Document\\简历.pdf");
        InputStream inputStream = Files.newInputStream(file.toPath());
        minioUtils.putObject("rms", file.getName(), inputStream, file.length(), "application/pdf");
    }

}
