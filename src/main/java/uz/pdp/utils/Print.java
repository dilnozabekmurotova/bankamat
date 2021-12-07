package uz.pdp.utils;

public class Print {
    public static void print(Object obj) {
        print(Color.BLUE, obj);
    }

    public static void print(String color, Object obj) {
        System.out.print(color + obj + Color.RESET);
    }

    public static void println(Object obj) {
        println(Color.BLUE, obj);
    }

    public static void println(String color, Object obj) {
        System.out.println(color + obj + Color.RESET);
    }
}
