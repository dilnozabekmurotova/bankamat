package uz.pdp.models.auth;

import lombok.*;
import uz.pdp.enums.role.Role;
import uz.pdp.enums.status.Status;
import uz.pdp.models.Entity;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User extends Entity {
    private String name;
    private String password;
    private Role role;
    private String language;
    private Status status = Status.ACTIVE;
    private String bankId;
    private String branchId;
    private String atmId;
    private List<String> permissions;

    public User(String username, String password, Role role, String bankId,List<String> permissions ){
        this.name = username;
        this.password = password;
        this.role = role;
        this.bankId = bankId;
        this.permissions = permissions;
    }
    public User(String username, String password, Role role, String bankId,String branchId,List<String> permissions ){
        this.name = username;
        this.password = password;
        this.branchId = branchId;
        this.role = role;
        this.bankId = bankId;
        this.permissions = permissions;
    }
}
