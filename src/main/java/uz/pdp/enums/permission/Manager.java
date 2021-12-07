package uz.pdp.enums.permission;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uz.pdp.configs.PropertyConfiguration;
import uz.pdp.dao.session.SessionDao;
import uz.pdp.utils.Color;
import uz.pdp.utils.Print;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum Manager {
    CREATE_EMPLOYEE(false, "create.emp"),
    DELETE_EMPLOYEE(false, "delete.emp"),
    BLOCK_EMPLOYEE(true, "block.emp"),
    UNBLOCK_EMPLOYEE(true, "unblock.emp"),
    LIST_EMPLOYEE(true, "list.emp"),
    UPDATE_EMPLOYEE(false, "update.emp"),
    CREATE_ATM(false, "create.atm"),
    DELETE_ATM(false, "delete.atm"),
    BLOCK_ATM(true, "block.atm"),
    UNBLOCK_ATM(true, "unblock.atm"),
    LIST_ATM(true, "list.atm"),
    UPDATE_ATM(false, "update.atm");
    private final boolean type;
    private final String propertyCode;

    private static final Map<Integer, String> map = new HashMap<>();
    static int i = 0;
    public static  void showFalsePer() {
        List<String> pers = SessionDao.sessionManager.getPermissions();
        for (String per : pers) {
            for (Manager value : Manager.values()) {
                if (value.getPropertyCode().equals(per)) {
                    String s = PropertyConfiguration.getPro().getProperty(value.getPropertyCode());
                    map.put(i, value.getPropertyCode());
                    System.out.println(i + ". " + s);
                    i++;
                }
            }
        }
    }
    public static String findPer(int index){
        if (index<0 || i<index){
            Print.println(Color.RED,"Wrong menu, Dont find this permission");
            return null;
        }
        return map.get(index);
    }
}
