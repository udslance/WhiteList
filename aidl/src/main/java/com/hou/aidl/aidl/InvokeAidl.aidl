// InvokeAidl.aidl
package com.hou.aidl.aidl;

// Declare any non-default types here with import statements

interface InvokeAidl {
    /**
     *  两数相加
     * @param a 整数 a
     * @param b 整数 b
     * @return a + b
     */
    int sum(int a, int b);

    /**
     * 将字符串逆序
     * @param b 字符串
     * @return 逆序字符串
     */
    String printString(String b);
}