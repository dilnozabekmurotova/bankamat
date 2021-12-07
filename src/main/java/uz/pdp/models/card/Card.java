package uz.pdp.models.card;

import lombok.*;
import uz.pdp.models.Entity;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Card extends Entity {
    private String cardNumber;
    private String expireDate;
    private boolean isOn;
    private String phoneNumber;
    private String balance;
    private String password;
    private String dollarBalance;
}
