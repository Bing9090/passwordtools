package com.dbc.passwdtools;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

import java.nio.charset.Charset;

public class SMTest {

    public static void main(String[] args) {
        String key = "dabaicaidabaicai";
        byte[] keyBytes = key.getBytes();
        //472c9b0724c0889e13c233f4f9f84464
        System.out.println("keyByte's length: " + keyBytes.length);
        String content = "1qaz2wsx@dbc";
        //String key = new String(sm4.getSecretKey().getEncoded());
        //System.out.println("key: " + key);
        /*
        SymmetricCrypto sm4 = SmUtil.sm4(key.getBytes());
        String encryptHex = sm4.encryptHex(content);
        System.out.println("encrypt: " + encryptHex);
        SymmetricCrypto sm42 = SmUtil.sm4(key.getBytes());
        String decryptStr = sm42.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println("decrypt: " + decryptStr);
        */
        /*
        SymmetricCrypto sm4 = SmUtil.sm4(key.getBytes());
        String encryptBase64 = sm4.encryptBase64(content);
        System.out.println("encrypt: " + encryptBase64);
        SymmetricCrypto sm42 = SmUtil.sm4(key.getBytes());
        String decryptBase64 = sm42.decryptStr(encryptBase64, CharsetUtil.CHARSET_UTF_8);
        System.out.println("decrypt: " + decryptBase64);
        */
        SM4 sm4 = new SM4(Mode.CBC, Padding.PKCS5Padding, keyBytes, keyBytes);
        String encryptBase64 = sm4.encryptBase64(content);
        System.out.println("encrypt: " + encryptBase64);
        SM4 sm4_2 = new SM4(Mode.CBC, Padding.PKCS5Padding, keyBytes, keyBytes);
        String decryptBase64 = sm4_2.decryptStr(encryptBase64, CharsetUtil.CHARSET_UTF_8);
        System.out.println("decrypt: " + decryptBase64);
    }

}
