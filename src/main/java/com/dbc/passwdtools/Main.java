package com.dbc.passwdtools;

import org.jasypt.util.text.BasicTextEncryptor;

public class Main {

    public static void main(String[] args) {
        //String passwd = "159*Ospdb";
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        textEncryptor.setPassword("arcs");
        //要加密的密码
        String password = textEncryptor.encrypt(args[0]);
        System.out.println("en password: " + password);

        //String dep = textEncryptor.decrypt("smvQf6jiYe7sbvhtA3SkvqhGb8s3RfRV");
        //System.out.println("de password: " + dep);
    }
}
