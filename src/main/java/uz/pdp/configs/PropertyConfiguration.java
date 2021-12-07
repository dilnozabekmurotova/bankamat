package uz.pdp.configs;

import uz.pdp.dao.session.SessionDao;
import uz.pdp.enums.localizatsia.Language;
import uz.pdp.enums.url.Urls;

import java.io.FileReader;
import java.util.Objects;
import java.util.Properties;

public class PropertyConfiguration {
    private static Properties sessionLan = new Properties();
    private final static Properties uz = new Properties();
    private final static Properties ru = new Properties();
    private final static Properties en = new Properties();
    public static void init() {
        try {
            uz.load(new FileReader(Urls.UZPRO.getUrl()));
            ru.load(new FileReader(Urls.RUPRO.getUrl()));
            en.load(new FileReader(Urls.ENGPRO.getUrl()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void downloadLan(){
        if (Objects.isNull(SessionDao.sessionUser)){
            sessionLan = ru;
        }else {
            Language language = Language.findByCode(SessionDao.sessionUser.getLanguage());
            setPro(language.getCode());
        }

    }

    public static void setPro(String lang) {
        if ("uzb".equalsIgnoreCase(lang)) sessionLan= uz;
        else if ("rus".equalsIgnoreCase(lang))sessionLan= ru;
        else sessionLan = en;
    }
    public static Properties getPro(){return sessionLan;}
}
