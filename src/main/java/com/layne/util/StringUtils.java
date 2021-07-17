package com.layne.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    /**
     * 将一个形如 : 1,2,1,25,6,5的字符串转为List<Long>
     * @param str
     * @return
     */
    public static List<Long> stringToLongs(String str){
        String[] strs = null;
        List<Long> list = null;
        if (!"".equals(str) && str != null) {
            strs = str.split(",");
            list = new ArrayList<>();
            for (String s : strs ) {
                list.add(Long.valueOf(s));
            }
        }


        return list;
    }
}
