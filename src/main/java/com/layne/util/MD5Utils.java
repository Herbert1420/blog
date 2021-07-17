package com.layne.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 */
public class MD5Utils {

    public static String code(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int i = 1;
            StringBuffer buffer = new StringBuffer("");
            for(int offest = 0; offest < byteDigest.length; offest ++ ){
                i = byteDigest[offest];
                if (i < 0){
                    i += 256;
                }
                if (i < 16){
                    buffer.append("0");
                }
                buffer.append(Integer.toHexString(i));
            }
            //32位加密
            return buffer.toString();
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }

    }

    public static void main(String[] args) {
        System.out.println(code("1679432960c"));
    }


}