package uz.pdp.loader;

import uz.pdp.exceptions.APIException;

public final class Loaders {
    private Loaders() {
    }

    public static void init() throws APIException {
        AppConfig.init();
    }
}
