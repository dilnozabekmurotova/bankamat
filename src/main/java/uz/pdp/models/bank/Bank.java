package uz.pdp.models.bank;

import lombok.*;
import uz.pdp.enums.status.Status;
import uz.pdp.models.Entity;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Bank extends Entity {
    private String name;
    private Status status = Status.ACTIVE;
    private String balance;

    public Bank(String name){
        this.name = name;
        this.balance = "";
    }
}
