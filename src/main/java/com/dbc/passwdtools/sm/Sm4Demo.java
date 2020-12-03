package com.dbc.passwdtools.sm;

import cn.hutool.crypto.symmetric.SM4;

/**
 * SM4 - 对称加密
 * 注意：SM4必须使用128bit的密钥，也就是16Byte
 * 比较推荐的写法是将原始密钥sm3后得到32Bytes，将其中16Bytes作为SM4的密钥
 *
 * 另外SM4有多种模式：CBC / CFB / CTR / CTS / OFB / PCBC，其中CBC和ECB较为常用，请根据需要设置
 * Padding也有多种模式：NoPadding / ZeroPadding / ISO10126Padding / OAEPPadding / PKCS1Padding / PKCS5Padding / SSL3Padding
 * 其中PKCS5Padding较为常用
 * 参考如下方法进行初始化：
 * SM4 sm4 = new SM4(Mode.CBC, Padding.PKCS5Padding, keyBytes, ivBytes);
 */

public class Sm4Demo {

    public static void main(String[] args) {
        // 当然也可以使用随机生成的密钥
        String key = "gundamOOgundamOO";
        byte[] key_bytes = key.getBytes();
        System.out.println("key-length(Byte): " + key_bytes.length);
        // 明文
        String plain_text = "Hello World";
        // 因为是对称，密钥一样的，这里就不分2个对象了
        SM4 sm4 = new SM4(key_bytes);
        // 加密
        String encrypt_text_base64 = sm4.encryptBase64(plain_text);
        System.out.println("encrypt_text_base64: " + encrypt_text_base64);
        // 解密
        String decrypt_text = sm4.decryptStr(encrypt_text_base64);
        System.out.println("decrypt_text: " + decrypt_text);
    }

}
