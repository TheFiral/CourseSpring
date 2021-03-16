package com.domain;


import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;

public class ElectronicPrinting {

    public static void print(File file) {
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
            FileOutputStream sigfos = new FileOutputStream("sig.txt");
            sigfos.write(realSig);
            sigfos.close();
            byte[] key = pub.getEncoded();
            FileOutputStream keyfos = new FileOutputStream("pub.txt");
            keyfos.write(key);
            keyfos.close();
        } catch (Exception e) {

        }
    }

    public static String checkSignFile(MultipartFile file){
        try{
            File newFile = new File("fileForCheck.docx");
            file.transferTo(newFile);
            FileInputStream keyfis = new FileInputStream("pub.txt");
            byte[] encKey = new byte[keyfis.available()];
            keyfis.read(encKey);
            keyfis.close();
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
            KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
            PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
            FileInputStream sigfis = new FileInputStream("sig.txt");
            byte[] sigToVerify = new byte[sigfis.available()];
            sigfis.read(sigToVerify );
            sigfis.close();
            Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
            sig.initVerify(pubKey);
            FileInputStream datafis = new FileInputStream(newFile);
            BufferedInputStream bufin = new BufferedInputStream(datafis);
            byte[] buffer = new byte[1024];
            int len;
            while (bufin.available() != 0) {
                len = bufin.read(buffer);
                sig.update(buffer, 0, len);
            }
            bufin.close();
            boolean verifies = sig.verify(sigToVerify);
            if(verifies){
                return ": оригинален";
            }
            else{
                return ": не прошел проверку";
            }
        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
        return ": не прошел проверку";
    }
}
