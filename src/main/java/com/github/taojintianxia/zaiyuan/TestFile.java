package com.github.taojintianxia.zaiyuan;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestFile {
    
    public static void main(String...args) throws IOException {
        String prefix = "ShardingSphere-JDBC-";
        File outputFile = new File(prefix+System.currentTimeMillis()+".log");
        outputFile.createNewFile();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(prefix+System.currentTimeMillis()+".log"));
        bufferedWriter.write("1 : aaa \n");
        bufferedWriter.write("2 : bbb \n");
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
