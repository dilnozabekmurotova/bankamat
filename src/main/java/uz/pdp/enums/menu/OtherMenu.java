package uz.pdp.enums.menu;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OtherMenu {
    LOGIN("login"),
    LOGOUT("logout"),
    REGISTER("register"),
    EXIT("exit"),
    QUIT("quit"),
    BANK("bank"),
    FILIAL("filial"),
    ATM("atm"),
    ADMIN("admin"),
    MANAGER("manager"),
    EMPLOYEE("employee"),
    SWITCH_ON_NOTIFICATION("switch.on.notification"),
    SWITCH_OFF_NOTIFICATION("switch.off.notification"),
    SHOW_BALANCE("show.balance"),
    GET_CASH_MONEY("get.cash.money"),
    LOGGED_OUT("logged.out"),
    CHANGE_LAN("change.lan"),
    EXCHANGE_MONEY("exchange.money");

    private final String pro;
}
