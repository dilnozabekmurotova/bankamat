package uz.pdp.utils;

import static uz.pdp.utils.BaseUtils.isInt;

public class Menu {
    public static int menu(String text, int begin, int end) {
        genMenu(text);
        String choice = Input.getStr("\n>>>");
        Integer integer = isInt(choice);

        if (integer == null) {
            Print.println(Color.RED, "Wrong menu !");
            return menu(text, begin, end);
        }

        if (integer < begin || integer > end) {
            Print.println(Color.RED, "Wrong menu !");
            return menu(text, begin, end);
        }
        return integer;
    }

    private static void genMenu(String text) {
        String[] split = text.split("/");
        int length = getMaxLength(split);
        for (int i = 0; i < split.length; i++) {
            if (i > 0 && i % 2 == 0) {
                System.out.println();
            }
            Print.print(split[i] + " ".repeat(length - split[i].length()));
        }
    }

    private static int getMaxLength(String[] split) {
        int max = 0;
        for (String s : split) {
            if (max < s.length()) {
                max = s.length();
            }
        }
        return max;
    }
}
