package uz.pdp.enums.menu;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Reminder {
    GOOD_BYE("good.bye"),
    ENTER_NAME("enter.name"),
    ENTER_PASS("enter.pass"),
    DONT_FIND("dont.find"),
    ENTER_LANGUAGE("enter.language"),
    ENTER_INDEX("enter.index"),
    DO_YOU_WANT("do.you.want"),
    ANOTHER_NAME("another.name"),
    NAME("name"),
    PASS("pass"),
    NEW_NAME("new.name"),
    NEW_PASS("new.pass"),
    ENTER_COUNT("enter.count"),
    ENTER_ATM("enter.atm"),
    ENTER_FILIAL("enter.filial"),
    CHOOSE_ANOTHER_ATM("choose.another.atm"),
    CHOOSE_ANOTHER_MEN("choose.another.men"),
    ENTER_BANK("enter.bank"),
    DO_YOU_DEL("do.you.delete"),
    DELETED("deleted"),
    WRONG_VALUE("wrong.value"),
    UPDATED("update"),
    QUITED("quited"),
    CANT_CHANGE("cant.change");

    private final String pro;

}
