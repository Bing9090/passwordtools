package com.dbc.passwdtools;

import org.jasypt.util.text.AES256TextEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;

/**
 * @author dabaicai
 * 2020-09-29
 * useage: java -jar passwdtools.jar "{salt}" "{passwd}"
 */

public class Main {

    public static void main(String[] args) {
        /*
        args = new String[3];
        args[0] = "1";
        args[1] = "daabicai";
        args[2] = "1qaz2wsx@dbc";
        System.out.println("args[1]: " + args[1] + ", args[2]: " + args[2]);
        */
        if (args.length != 3) {
            System.out.println("Args error! The useage: java -jar passwdtools.jar {Algorithm} \"{salt}\" \"{passwd}\"");
            return;
        }
        int algorithm = Integer.parseInt(args[0]);
        String encryptText = "";
        switch (algorithm) {
            case 1:
                // PBEWithHMACSM3AndSM4
                PBEWithHMACSM3AndSM4StringEncryptor pbeWithHMACSM3AndSM4StringEncryptor = new PBEWithHMACSM3AndSM4StringEncryptor();
                pbeWithHMACSM3AndSM4StringEncryptor.initialize(args[1]);
                encryptText = pbeWithHMACSM3AndSM4StringEncryptor.encrypt(args[2]);
                System.out.println("encryptText: " + encryptText);
                break;
            case 2:
                // SM4
                SM4StringEncryptor sm4StringEncryptor = new SM4StringEncryptor();
                sm4StringEncryptor.setEncryptorPassword(args[1]);
                encryptText = sm4StringEncryptor.encrypt(args[2]);
                System.out.println("encrypt text: " + encryptText);
                break;
            case 3:
                // PBEWithHMACSHA512AndAES_256
                AES256TextEncryptor aes256TextEncryptor = new AES256TextEncryptor();
                aes256TextEncryptor.setPassword(args[1]);
                encryptText = aes256TextEncryptor.encrypt(args[2]);
                System.out.println("encrypt text: " + encryptText);
                break;
            case 4:
                BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
                textEncryptor.setPassword(args[1]);
                encryptText = textEncryptor.encrypt(args[2]);
                System.out.println("encrypt text: " + encryptText);
                break;
            default:
                System.out.println("The algorithm Only supports 1-PBEWithHMACSM3AndSM4 | 2-SM4 | 3-PBEWithHMACSHA512AndAES_256 | 4-PBEWithMD5AndDES");
        }
    }
}
