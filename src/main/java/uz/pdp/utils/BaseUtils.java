package uz.pdp.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BaseUtils {
    public static String generateUniqueId() {
        return UUID.randomUUID().toString().replace("-","");
    }

    public static String maskPan(String str, String sign) {
        return mask(str, 6, 13, sign);
    }

    public static String maskPassword(String str, String sign) {
        return mask(str, -1, -1, sign);
    }

    private static String mask(String str, int start, int end, String sign) {
        if (start < 0) start = 0;
        if (end < 0) end = str.length();
        return "" + new StringBuilder(str).replace(start, end, sign.repeat(end - start));
    }

    public static ArrayList<String> genContentList(String text) {
        return new ArrayList<>(List.of(text.split(",")));
    }

    public static Integer getRandom(int startNum, int boundNum){
        return new Random().nextInt(boundNum-startNum)+startNum;
    }

    public static String getDate(){
        return LocalDateTime.now().toString();
    }
    public static String getTime(){
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(myFormatObj);
    }

    public static Integer getRandom(int boundLimit) {
        return new Random().nextInt(boundLimit);
    }

    public static Integer isInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (Exception e) {
            return null;
        }
    }

}
