package org.unibl.etf.sni.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;


public class Hash {
    public static String sha512Hash(String plainText){
        return new String(Hex.encodeHex(DigestUtils.sha512(plainText)));
    }
}
