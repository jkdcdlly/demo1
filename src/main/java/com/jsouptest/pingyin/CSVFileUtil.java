package com.jsouptest.pingyin;


import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVFileUtil {
    // CSV文件编码
    public static final String ENCODE = "UTF-8";

    private FileInputStream fis = null;
    private InputStreamReader isw = null;
    private BufferedReader br = null;


    public CSVFileUtil(String filename) throws Exception {
        fis = new FileInputStream(filename);
        isw = new InputStreamReader(fis, ENCODE);
        br = new BufferedReader(isw);
    }

    // ==========以下是公开方法=============================

    /**
     * 从CSV文件流中读取一个CSV行。
     *
     * @throws Exception
     */
    public void readLine(String tager) throws Exception {
        PrintWriter printWriter = new PrintWriter(new FileWriter(tager, true));
        StringBuffer readLine = new StringBuffer();
        boolean bReadNext = true;
        String[] array;
        int i = 0;
        try {
            String strReadLine = br.readLine();
            while ((strReadLine = br.readLine()) != null) {

                array = strReadLine.split(",");
                if (array.length < 5) {
                    continue;
                }

                String name = array[0].trim().replaceAll("[\\uE096\\uF8F5、]", "");
                String CtfId = array[4].trim();

                try {
                    printWriter.println(name + "," + PinyinHelper.convertToPinyinString(name, "", PinyinFormat.WITHOUT_TONE) + "," + CtfId);
                } catch (Exception e) {
                    System.out.println(name + CtfId);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }

    /**
     * 把CSV文件的一行转换成字符串数组。指定数组长度，不够长度的部分设置为null。
     */
    public static String[] fromCSVLine(String source, int size) {
        ArrayList tmpArray = fromCSVLinetoArray(source);
        if (size < tmpArray.size()) {
            size = tmpArray.size();
        }
        String[] rtnArray = new String[size];
        tmpArray.toArray(rtnArray);
        return rtnArray;
    }

    /**
     * 判断是否为中文
     *
     * @param str
     * @return
     */
    public static boolean isChineseChar(String str) {
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }

    /**
     * 提取中文
     *
     * @param str
     * @return
     */
    public static String extractChineseChar(String str) {
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]*");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }

        return m.group();
    }

    /**
     * 判断是否为中文
     *
     * @param str
     * @return
     */
    public static boolean hasluanma(String str) {
        boolean temp = false;
        Pattern p = Pattern.compile("[\\uF8F5,\\uE863,\\uE81B]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }


    /**
     * 把CSV文件的一行转换成字符串数组。不指定数组长度。
     */
    public static ArrayList fromCSVLinetoArray(String source) {
        if (source == null || source.length() == 0) {
            return new ArrayList();
        }
        int currentPosition = 0;
        int maxPosition = source.length();
        int nextComma = 0;
        ArrayList rtnArray = new ArrayList();
        while (currentPosition < maxPosition) {
            nextComma = nextComma(source, currentPosition);
            rtnArray.add(nextToken(source, currentPosition, nextComma));
            currentPosition = nextComma + 1;
            if (currentPosition == maxPosition) {
                rtnArray.add("");
            }
        }
        return rtnArray;
    }


    /**
     * 把字符串类型的数组转换成一个CSV行。（输出CSV文件的时候用）
     */
    public static String toCSVLine(String[] strArray) {
        if (strArray == null) {
            return "";
        }
        StringBuffer cvsLine = new StringBuffer();
        for (int idx = 0; idx < strArray.length; idx++) {
            String item = addQuote(strArray[idx]);
            cvsLine.append(item);
            if (strArray.length - 1 != idx) {
                cvsLine.append(',');
            }
        }
        return cvsLine.toString();
    }

    /**
     * 字符串类型的List转换成一个CSV行。（输出CSV文件的时候用）
     */
    public static String toCSVLine(ArrayList strArrList) {
        if (strArrList == null) {
            return "";
        }
        String[] strArray = new String[strArrList.size()];
        for (int idx = 0; idx < strArrList.size(); idx++) {
            strArray[idx] = (String) strArrList.get(idx);
        }
        return toCSVLine(strArray);
    }

    // ==========以下是内部使用的方法=============================

    /**
     * 计算指定文字的个数。
     *
     * @param str   文字列
     * @param c     文字
     * @param start 开始位置
     * @return 个数
     */
    private int countChar(String str, char c, int start) {
        int i = 0;
        int index = str.indexOf(c, start);
        return index == -1 ? i : countChar(str, c, index + 1) + 1;
    }

    /**
     * 查询下一个逗号的位置。
     *
     * @param source 文字列
     * @param st     检索开始位置
     * @return 下一个逗号的位置。
     */
    private static int nextComma(String source, int st) {
        int maxPosition = source.length();
        boolean inquote = false;
        while (st < maxPosition) {
            char ch = source.charAt(st);
            if (!inquote && ch == ',') {
                break;
            } else if ('"' == ch) {
                inquote = !inquote;
            }
            st++;
        }
        return st;
    }

    /**
     * 取得下一个字符串
     */
    private static String nextToken(String source, int st, int nextComma) {
        StringBuffer strb = new StringBuffer();
        int next = st;
        while (next < nextComma) {
            char ch = source.charAt(next++);
            if (ch == '"') {
                if ((st + 1 < next && next < nextComma) && (source.charAt(next) == '"')) {
                    strb.append(ch);
                    next++;
                }
            } else {
                strb.append(ch);
            }
        }
        return strb.toString();
    }

    /**
     * 在字符串的外侧加双引号。如果该字符串的内部有双引号的话，把"转换成""。
     *
     * @param item 字符串
     * @return 处理过的字符串
     */
    private static String addQuote(String item) {
        if (item == null || item.length() == 0) {
            return "\"\"";
        }
        StringBuffer sb = new StringBuffer();
        sb.append('"');
        for (int idx = 0; idx < item.length(); idx++) {
            char ch = item.charAt(idx);
            if ('"' == ch) {
                sb.append("\"\"");
            } else {
                sb.append(ch);
            }
        }
        sb.append('"');
        return sb.toString();
    }


}
