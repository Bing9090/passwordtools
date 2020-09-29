package com.dbc.passwdtools;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * @author dabaicai
 * 2020-09-29
 * useage: java -jar passwdtools.jar "{salt}" "{passwd}"
 */

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Args error! The useage: java -jar passwdtools.jar \"{salt}\" \"{passwd}\"");
            return;
        }
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        textEncryptor.setPassword(args[0]);
        //要加密的密码
        String password = textEncryptor.encrypt(args[1]);
        System.out.println("en password: " + password);

        //String dep = textEncryptor.decrypt("smvQf6jiYe7sbvhtA3SkvqhGb8s3RfRV");
        //System.out.println("de password: " + dep);
    }
}
