package com.github.yangguang19.jvm;

/**
 * @Classname CodeBlock03
 * @Description 类加载顺序
 * @Date 2020/8/19 20:35
 * @Created by Administrator
 */

class CodeZY {

    /** 加载优先级: 静态代码块 > 构造块 > 构造方法 */

    public CodeZY() {
        System.out.println("Code的构造方法111");
    }

    {
        System.out.println("Code的构造块222");
    }

    static {
        System.out.println("Code的静态代码块333");
    }
}

public class CodeBlock03 {  //主类  CodeBlock03.class
    {
        System.out.println("CodeBlock03的构造块444");
    }

    static {
        System.out.println("CodeBlock03的静态代码快555");
    }

    public CodeBlock03() {
        System.out.println("CodeBlock03的构造方法666");
    }

    public static void main(String[] args) {
        System.out.println("==我是分割线==========CodeBlock03的main方法777");
        new CodeZY();
        System.out.println("------------------");
        new CodeZY();
        System.out.println("------------------");
        new CodeBlock03();
    }
}
