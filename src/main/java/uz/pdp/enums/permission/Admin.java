package uz.pdp.enums.permission;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import uz.pdp.models.auth.User;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.*;

@Getter
@RequiredArgsConstructor
public enum Admin {
    CREATE_BRANCH(false,"creat.fil"),
    DELETE_BRANCH(false,"delete.fil"),
    UPDATE_BRANCH(false,"update.fil"),
    LIST_BRANCH(true,"list.fil"),
    BLOCK_BRANCH(true,"block.fil"),
    UNBLOCK_BRANCH(true,"unblock.fil"),

    ADD_PERMISSION_ADMIN(true,"add.permission.admin"),
    CREATE_ADMIN(true,"create.admin"),
    DELETE_ADMIN(true,"delete.admin"),
    LIST_ADMIN(true,"list.admin"),
    UPDATE_ADMIN(true,"update.admin"),
    BLOCK_ADMIN(true, "block.admin"),
    UNBLOCK_ADMIN(true, "UNblock.admin"),

    CREATE_MANAGER(false,"create.manager"),
    DELETE_MANAGER(false,"delete.manager"),
    UNBLOCK_MANAGER(true,"unblock.manager"),
    BLOCK_MANAGER(true,"block.manager"),
    UPDATE_MANAGER(false,"update.manager"),
    LIST_MANAGER(true,"list.manager"),
    ADD_PER_MANAGER(false,"add.permission.manager");
    private final boolean type;
    private final String propertyCode;
    private static final Map<Integer, String> map = new HashMap<>();

    public static void showFalsePer(User user){
        List<String> list= Stream.of(Admin.values()).map(per-> !user.getPermissions().
                contains(per.getPropertyCode())?
                per.getPropertyCode():null).collect(Collectors.toList());
        for (int i = 0; i < list.size(); i++) {
            map.put(i+1,list.get(i));
        }
    }

    public static String findPer(int index){
        return map.get(index);
    }

}
