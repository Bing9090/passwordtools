package com.dbc.passwdtools.sm;

import cn.hutool.crypto.digest.SM3;

/**
 * SM3 - 摘要算法(类似hash)
 */

public class Sm3Demo {

    public static void main(String[] args) {
        SM3 sm3 = new SM3();
        String text = "Hello World";
        // 因为数组不好打印，所以转化为16进制显示
        String sm3_hash = sm3.digestHex(text);
        System.out.println("sm3_hash: " + sm3_hash);
    }

}
