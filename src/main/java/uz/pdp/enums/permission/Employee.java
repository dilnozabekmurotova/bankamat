package uz.pdp.enums.permission;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Employee {
    FILL_ATM("fill.atm"),
    MONITOR("monitor");
    private final String propertyCode;
}
