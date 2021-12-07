package uz.pdp.enums.url;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Urls {
    ATMJSON("json","src/main/resources/jsons/atm.json"),
    FILIALJSON("json","src/main/resources/jsons/filial.json"),
    ORGJSON("json","src/main/resources/jsons/organization.json"),
    USERJSON("json","src/main/resources/jsons/users.json"),
    CARDJSON("json", "src/main/resources/json/card.json"),
    APPPRO("property","src/main/resources/properties/app.properties"),
    ENGPRO("property","src/main/resources/properties/en.properties"),
    RUPRO("property","src/main/resources/properties/ru.properties"),
    UZPRO("property","src/main/resources/properties/uz.properties");
    private final String type;
    private final String url;

}
