package com.shortLink.ShortLink.util;

public class Base62Util {

    private static final String base62 =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String encode(Long value){

        if(value == 0){
            return "0";
        }

        StringBuilder result = new StringBuilder ();

        while(value >0){
            result.append (base62.charAt ((int) (value % 62)));

            value /= 62;
        }
        return result.reverse ().toString ();
    }

    public static Long decode(String code){

        long result = 0L;

        for(char c: code.toCharArray ()){
            int index = base62.indexOf (c);

            if(index == -1){
                throw new  IllegalArgumentException("Invalid base62 character:" + c);
            }

            result = result * 62 + index;
        }
        return result;
    }


}
