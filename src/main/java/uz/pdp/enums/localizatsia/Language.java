package uz.pdp.enums.localizatsia;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Elmurodov Javohir, Mon 12:35 PM. 11/22/2021
 */
@Getter
@RequiredArgsConstructor
public enum Language {
    UZB("UZB","Uzbek"),
    RUS("RUS","Russian"),
    ENG("ENG","English");
    private final String code;
    private final String name;

    public static void showAll() {
        for (Language code : values()) {
            System.out.println(code);
        }
    }

    public static Language findByCode(String code) {
        for (Language language : values()) {
            if (language.code.equalsIgnoreCase(code))
                return language;
        }
        String lang = ENG.code;
        return findByCode(lang);
    }



    @Override
    public String toString() {
        return name + " -> " + code;
    }
}
