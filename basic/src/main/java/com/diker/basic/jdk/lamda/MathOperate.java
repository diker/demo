package com.diker.basic.jdk.lamda;

/**
 * @author diker
 * @since 2019/1/18
 */
public class MathOperate {

    public int add(Operator operator) {
       return operator.operate(1, 2);
    }

    public static void main(String[] args) {
        MathOperate mathOperate = new MathOperate();
        System.out.println(mathOperate.add((a, b) -> a + b));
    }
}
