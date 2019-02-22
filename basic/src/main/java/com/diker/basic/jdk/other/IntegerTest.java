package com.diker.basic.jdk.other;

/**
 * @author diker
 * @since 2019/2/22
 */
public class IntegerTest {

    public static void main(String[] args) {
        //int整数转成二进制字符串
        System.out.println(Integer.toBinaryString(31));

        //int整数转成十六进制字符串
        System.out.println(Integer.toHexString(31));

        //八进制转成int整数
        System.out.println(Integer.decode("011"));
        System.out.println(Integer.decode("-011"));

        //十六进制转成int整数
        System.out.println(Integer.decode("0x11"));
        System.out.println(Integer.decode("-0x11"));
        System.out.println(Integer.decode("#11"));
        System.out.println(Integer.decode("-#11"));

        //字符串转成int整数，默认10进制
        System.out.println(Integer.parseInt("11"));

        //任何进制的数转成int证书， 第二位标识进制可自行定义
        System.out.println(Integer.parseInt("11", 16));
        System.out.println(Integer.parseInt("11", 7));
    }
}
