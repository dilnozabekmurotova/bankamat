package uz.pdp.utils;

import java.util.Scanner;

import static uz.pdp.utils.Print.print;

public class Input {

    public static final Scanner SCANNER_NUM = new Scanner(System.in);
    public static final Scanner SCANNER_STR = new Scanner(System.in);

    public static Integer getInt() {
        return getInt("");
    }
    public static Integer getInt(String str) {
        print(str);
        return SCANNER_NUM.nextInt();
    }

    public static Double getDbl() {
        return getDbl("");
    }
    public static Double getDbl(String str) {
        print(str);
        return SCANNER_NUM.nextDouble();
    }

    public static Float getFlt() {
        return getFlt("");
    }
    public static Float getFlt(String str) {
        print(str);
        return SCANNER_NUM.nextFloat();
    }

    public static String getStr() {
        return getStr("");
    }
    public static String getStr(String str) {
        print(str);
        return SCANNER_STR.nextLine();
    }

    public static Long getLng() {
        return getLng("");
    }
    public static Long getLng(String str) {
        print(str);
        return SCANNER_NUM.nextLong();
    }

}
