package com.jsouptest.pingyin;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2015/10/11.
 */
public class FileUtil {
    public static void main(String[] args) throws Exception {

        String[] files = new File("D:\\Downloads\\2000w").list();

        Arrays.asList(files).forEach((file) -> {
            Runnable runnable1 = () -> {
                long start = System.currentTimeMillis();
                CSVFileUtil csvFileUtil = null;
                try {
                    csvFileUtil = new CSVFileUtil("D:\\Downloads\\2000w\\" + file);
                    csvFileUtil.readLine("d:\\Downloads\\result\\" + file.split("\\.")[0] + ".txt");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                long end = System.currentTimeMillis();
                System.out.println(file.split("\\.")[0] + "耗时:" + ((end - start) / 1000) + "秒");
            };
            runnable1.run();

        });

    }
}
