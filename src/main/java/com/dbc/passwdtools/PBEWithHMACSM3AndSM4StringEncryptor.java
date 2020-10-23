package com.dbc.passwdtools;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.digest.SM3;
import cn.hutool.crypto.symmetric.SM4;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.salt.RandomSaltGenerator;
import org.jasypt.salt.SaltGenerator;

/**
 * 使用PBEWithHMACSM3AndSM4计算密文
 * 先通过HMACSM3计算密码的hash，然后将其前16byte(128bit)作为SM4算法的密钥
 * 最终生成的密文是HMACSM3的8byte的salt+密文
 *
 * 解密部分是先取前8byte为salt，然后使用HMACSM3计算SM4的解密密码，然后对去掉前8位salt的剩余部分(密文)解密
 *
 * @author dabaicai
 * @date 2020-10-23
 */

public class PBEWithHMACSM3AndSM4StringEncryptor implements StringEncryptor  {

    // salt长度，默认8byte
    private int saltSizeBytes = 8;
    // salt生成对象
    private SaltGenerator saltGenerator = null;
    // salt
    private byte[] salt;

    private String encryptorPassword;

    public String getEncryptorPassword() {
        return encryptorPassword;
    }

    public void setEncryptorPassword(String encryptorPassword) {
        this.encryptorPassword = encryptorPassword;
    }

    /**
     * 初始化方法，设置saltGenerator
     */
    public synchronized void initialize(String encryptorPassword) {
        this.encryptorPassword = encryptorPassword;
        if (this.saltGenerator == null) {
            this.saltGenerator = new RandomSaltGenerator();
        }
    }

    @Override
    public String encrypt(String s) {
        this.salt = this.saltGenerator.generateSalt(this.saltSizeBytes);
        //System.out.println("salt size: " + this.salt.length);
        SM3 sm3 = new SM3();
        sm3.setSalt(this.salt);
        //String hexString = sm3.digestHex(s);
        //System.out.println("hexString: " + hexString);
        byte[] srcKeyBytes = sm3.digest(encryptorPassword);
        //System.out.println("srcKeyBytes size: " + srcKeyBytes.length);
        byte[] keyBytes = new byte[16];
        byte[] ivBytes = new byte[16];
        System.arraycopy(srcKeyBytes, 0, keyBytes, 0, 16);
        System.arraycopy(srcKeyBytes, 16, ivBytes, 0, 16);
        SM4 sm4 = new SM4(Mode.CBC, Padding.PKCS5Padding, keyBytes, ivBytes);
        byte[] encryptBytes = sm4.encrypt(s, CharsetUtil.CHARSET_UTF_8);
        System.out.println("encryptBytes size: " + encryptBytes.length);
        // 前8位设置为salt，后面为密文
        byte[] finalBytes = new byte[this.salt.length + encryptBytes.length];
        System.arraycopy(this.salt, 0, finalBytes, 0, this.salt.length);
        System.arraycopy(encryptBytes, 0, finalBytes, this.salt.length, encryptBytes.length);
        System.out.println("finalBytes size: " + finalBytes.length);
        return null;
    }

    @Override
    public String decrypt(String s) {
        return null;
    }

    public static void main(String[] args) {
        PBEWithHMACSM3AndSM4StringEncryptor pbeWithHMACSM3AndSM4StringEncryptor = new PBEWithHMACSM3AndSM4StringEncryptor();
        pbeWithHMACSM3AndSM4StringEncryptor.initialize("dabaicai");
        pbeWithHMACSM3AndSM4StringEncryptor.encrypt("1qaz2wsx@dbc");
    }
}
