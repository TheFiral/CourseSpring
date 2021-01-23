package com.domain;

import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;

public class Cript {
    @Value("${upload.path}")
    private static String uploadPath;

    public static void Cript(File file) {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(1024, random);
            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey priv = pair.getPrivate();
            PublicKey pub = pair.getPublic();
            Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
            dsa.initSign(priv);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bufin = new BufferedInputStream(fis);
            byte[] buffer = new byte[1024];
            int len;
            while (bufin.available() != 0) {
                len = bufin.read(buffer);
                dsa.update(buffer, 0, len);
            }
            bufin.close();
            byte[] realSig = dsa.sign();
            FileOutputStream sigfos = new FileOutputStream(uploadPath + "sig.txt");
            sigfos.write(realSig);
            sigfos.close();
            byte[] key = pub.getEncoded();
            FileOutputStream keyfos = new FileOutputStream(uploadPath + "pub.txt");
            keyfos.write(key);
            keyfos.close();
        } catch (Exception e) {

        }
    }
}
