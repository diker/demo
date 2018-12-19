package com.diker.basic.util;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 代码行数统计
 * @author diker
 * @since 2018/12/7
 */
public class CodeLineCalculator {

    static long commentLine = 0;
    static long whiteLine = 0;
    static long normalLine = 0;
    static long totalLine = 0;
    static boolean comment = false;
    static Pattern p1 = Pattern.compile("/\\*");
    static Pattern p2 = Pattern.compile("\\*/");


    public static void main(String[] args) {
        File file = new File("D:\\test");
        getChild(file);
        System.out.println("有效代码行数: " + normalLine);
        System.out.println("注释行数: " + commentLine);
        System.out.println("空白行数: " + whiteLine);
        System.out.println("总代码行数: " + totalLine);
    }

    private static void getChild(File child) { // 遍历子目录
        if (child.getName().matches(".*\\.java$")) { // 只查询java文件
            try {
                BufferedReader br = new BufferedReader(new FileReader(child));
                String line = "";
                while ((line = br.readLine()) != null) {
                    parse(line);
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (child.listFiles() != null) {
            for (File f : child.listFiles()) {
                getChild(f);
            }
        }
    }

    private static void parse(String line) {
        line = line.trim();
        totalLine++;
        if (line.length() == 0) {
            whiteLine++;
        } else if (comment) {
            commentLine++;
            if (line.endsWith("*/")) {
                comment = false;
            } else if (line.matches(".*\\*/.+")) {
                normalLine++;
                System.out.println(line);
                comment = false;
            }
        } else if (line.startsWith("//")) {
            commentLine++;
        } else if (line.matches(".+//.*")) {
            commentLine++;
            normalLine++;
            System.out.println(line);
        } else if (line.startsWith("/*") && line.matches(".+\\*/.+")) {
            commentLine++;
            normalLine++;
            System.out.println(line);
            if (findPair(line)) {
                comment = false;
            } else {
                comment = true;
            }
        } else if (line.startsWith("/*") && !line.endsWith("*/")) {
            commentLine++;
            comment = true;
        } else if (line.startsWith("/*") && line.endsWith("*/")) {
            commentLine++;
            comment = false;
        } else if (line.matches(".+/\\*.*") && !line.endsWith("*/")) {
            commentLine++;
            normalLine++;
            System.out.println(line);
            if (findPair(line)) {
                comment = false;
            } else {
                comment = true;
            }
        } else if (line.matches(".+/\\*.*") && line.endsWith("*/")) {
            commentLine++;
            normalLine++;
            System.out.println(line);
            comment = false;
        } else {
            normalLine++;
            System.out.println(line);
        }
    }

    private static boolean findPair(String line) { // 查找一行中/*与*/是否成对出现
        int count1 = 0;
        int count2 = 0;
        Matcher m = p1.matcher(line);
        while (m.find()) {
            count1++;
        }
        m = p2.matcher(line);
        while (m.find()) {
            count2++;
        }
        return (count1 == count2);
    }


}
