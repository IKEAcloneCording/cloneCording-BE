package com.innovation.backend.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JasyptConfigDESTest {

    public static void main(String[] args) {
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword("password");

        String enc = pbeEnc.encrypt("plain_text");
        System.out.println("enc = " + enc);

        String des = pbeEnc.decrypt(enc);
        System.out.println("des = " + des);
    }
}