package com.dbc.passwdtools.sm;

/**
 * SM2 - 非对称加密，椭圆曲线公钥密码算法
 */

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;

import java.io.IOException;
import java.nio.charset.Charset;

public class Sm2Demo {

    public static void main(String[] args) throws IOException {

        // 接收者
        SM2 sm2_reciver = new SM2();
        // 生成公私钥对
        sm2_reciver.initKeys();
        String publickey_base64 = sm2_reciver.getPublicKeyBase64();
        String privatekey_base64 = sm2_reciver.getPrivateKeyBase64();
        System.out.println("privatekey(base64): " + privatekey_base64);
        System.out.println("publickey(base64): " + publickey_base64);

        /**
         * 这里就可以把公钥的base64形式提供出去了，供各发送者加密使用
         */

        // 加密的明文
        String plaintext = "Hello world";

        // 发送者设置公钥使用接收者的公钥
        SM2 sm2_sender = new SM2(null, publickey_base64);
        // 公钥加密
        String encrypt_text_base64 = sm2_sender.encryptBase64(plaintext, Charset.forName("UTF-8"), KeyType.PublicKey);
        System.out.println("encrypt_text_base64: " + encrypt_text_base64);

        // 接收者私钥解密
        String decrypt_text = sm2_reciver.decryptStr(encrypt_text_base64, KeyType.PrivateKey, Charset.forName("UTF-8"));
        System.out.println("decrypt_text: " + decrypt_text);

    }

}
