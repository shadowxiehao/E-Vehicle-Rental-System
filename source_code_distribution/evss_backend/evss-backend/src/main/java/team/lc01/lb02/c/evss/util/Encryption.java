package team.lc01.lb02.c.evss.util;

import java.math.BigInteger;
import java.security.MessageDigest;


public class Encryption {
    public static String encrypt(String source) {
        String md5 = null;
        try {
            MessageDigest mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(source.getBytes(), 0, source.length());
            md5 = new BigInteger(1, mdEnc.digest()).toString(32);
        } catch (Exception ex) {
            return null;
        }
        return md5;
    }
}