package com.dbc.passwdtools;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.digest.SM3;
import cn.hutool.crypto.symmetric.SM4;
import org.jasypt.encryption.StringEncryptor;

/**
 * 1. jasypt-SM4加密算法实现类(非HMAC)。
 * 2. 因为SM4算法需要128bit长度的密钥，原始密码长度可能没这么长（需要16byte），
 * 故采用先sm3计算摘要(32byte)，然后截取前16byte作为SM4算法的密钥。
 * 3. SM4采用CBC模式，padding使用PKCS5Padding，IV（初始偏移量）使用sm3算法的后16byte。
 * 4. 最终输出结果采用base64编码。
 *
 * 本方法的缺点在于，由于并没有使用HMAC算法（混入salt值），所以同样的加密密码和明文每次加密产出的内容都是相同的。
 *
 * @author dabaicai
 * @date 2020-10-23
 */
public class SM4StringEncryptor implements StringEncryptor {

    private String encryptorPassword;

    public String getEncryptorPassword() {
        return encryptorPassword;
    }

    public void setEncryptorPassword(String encryptorPassword) {
        this.encryptorPassword = encryptorPassword;
    }

    @Override
    public String encrypt(String s) {
        SM3 sm3 = new SM3();
        byte[] srcKeyBytes = sm3.digest(encryptorPassword);
        byte[] keyBytes = new byte[16];
        byte[] ivBytes = new byte[16];
        System.arraycopy(srcKeyBytes, 0, keyBytes, 0, 16);
        System.arraycopy(srcKeyBytes, 16, ivBytes, 0, 16);
        SM4 sm4 = new SM4(Mode.CBC, Padding.PKCS5Padding, keyBytes, ivBytes);
        return sm4.encryptBase64(s);
    }

    @Override
    public String decrypt(String s) {
        SM3 sm3 = new SM3();
        byte[] srcKeyBytes = sm3.digest(encryptorPassword);
        byte[] keyBytes = new byte[16];
        byte[] ivBytes = new byte[16];
        System.arraycopy(srcKeyBytes, 0, keyBytes, 0, 16);
        System.arraycopy(srcKeyBytes, 16, ivBytes, 0, 16);
        SM4 sm4 = new SM4(Mode.CBC, Padding.PKCS5Padding, keyBytes, ivBytes);
        return sm4.decryptStr(s, CharsetUtil.CHARSET_UTF_8);
    }

    public static void main(String[] args) {
        // 加密
        SM4StringEncryptor sm4StringEncryptor = new SM4StringEncryptor();
        sm4StringEncryptor.setEncryptorPassword("dabaicai");
        String encryptText = sm4StringEncryptor.encrypt("1qaz2wsx@dbc");
        System.out.println("encrypt text: " + encryptText);

        // 解密
        String decryptText = sm4StringEncryptor.decrypt(encryptText);
        System.out.println("decryptText text: " + decryptText);
    }
}
