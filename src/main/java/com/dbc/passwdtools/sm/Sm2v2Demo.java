package com.dbc.passwdtools.sm;

/**
 * SM2 - 非对称加密，椭圆曲线公钥密码算法
 */

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class Sm2v2Demo {

    public static void main(String[] args) throws IOException, InvalidAlgorithmParameterException, NoSuchAlgorithmException {

        /*
        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        byte[] privateKeyBytes = pair.getPrivate().getEncoded();
        byte[] publicKeyBytes = pair.getPublic().getEncoded();

        String privateKey = Base64Encoder.encode(privateKeyBytes);
        String publicKey = Base64Encoder.encode(publicKeyBytes);

        System.out.println(String.format("privateKeyBytes length: %s, \nprivateKey: %s", privateKeyBytes.length, privateKey));
        System.out.println(String.format("publicKeyBytes length: %s, \npublicKey: %s", publicKeyBytes.length, publicKey));
        */
        // 获取SM2椭圆曲线的参数
        final ECGenParameterSpec sm2Spec = new ECGenParameterSpec("sm2p256v1");
// 获取一个椭圆曲线类型的密钥对生成器
        final KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", new BouncyCastleProvider());
// 使用SM2参数初始化生成器
        kpg.initialize(sm2Spec);

// 使用SM2的算法区域初始化密钥生成器
        kpg.initialize(sm2Spec, new SecureRandom());
// 获取密钥对
        KeyPair keyPair = kpg.generateKeyPair();
        byte[] privateBytes = keyPair.getPrivate().getEncoded();
        System.out.println("privateBytes length: " + privateBytes.length);

    }

}
