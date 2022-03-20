package com.lin;

import org.jasypt.util.text.BasicTextEncryptor;

public class EncryptorUtils {
    private EncryptorUtils(){}

    public static String encrypt(String salt,String text){
        BasicTextEncryptor bte=new BasicTextEncryptor();
        bte.setPassword(salt);
        return bte.encrypt(text);
    }

    public static String decrypt(String salt,String text){
        BasicTextEncryptor bte=new BasicTextEncryptor();
        bte.setPassword(salt);
        return bte.decrypt(text);
    }

    public static void main(String[] args) {
        //print 2271Ksjx+c1+TuobPlZN9g==
        System.out.println(encrypt("lingo","abcd"));
        //print abcd
        System.out.println(decrypt("lingo","2271Ksjx+c1+TuobPlZN9g=="));
    }
}
