package uz.pdp.services.menu;

import uz.pdp.configs.PropertyConfiguration;
import uz.pdp.enums.menu.OtherMenu;
import uz.pdp.enums.menu.Reminder;
import uz.pdp.enums.permission.Admin;
import uz.pdp.enums.permission.Manager;
import uz.pdp.enums.permission.SuperAdmin;
import uz.pdp.utils.Menu;

import java.util.Objects;

public class MenuService {
    private static MenuService instance;

    private MenuService() {
    }

    public static MenuService getInstance() {
        if (Objects.isNull(instance)) instance = new MenuService();
        return instance;
    }

    public int mainServicesFirstMenu() {
        return Menu.menu("1. " + PropertyConfiguration.getPro().getProperty(OtherMenu.BANK.getPro()) +
                " /2. " + PropertyConfiguration.getPro().getProperty(OtherMenu.ATM.getPro()) +
                " /3. " + PropertyConfiguration.getPro().getProperty(OtherMenu.EXIT.getPro()), 1, 3);
    }

    public int mainServiceSecondMenu() {
        return Menu.menu("1. " + PropertyConfiguration.getPro().getProperty(OtherMenu.LOGIN.getPro()) +
                " /2. " + PropertyConfiguration.getPro().getProperty(OtherMenu.CHANGE_LAN.getPro()) +
                " /3. " + PropertyConfiguration.getPro().getProperty(OtherMenu.LOGOUT.getPro()), 1, 3);
    }

    public int filialServiceMenu() {
        return Menu.menu("1. " + PropertyConfiguration.getPro().getProperty(Admin.CREATE_BRANCH.getPropertyCode()) +
                " /2. " + PropertyConfiguration.getPro().getProperty(Admin.DELETE_BRANCH.getPropertyCode()) +
                " /3. " + PropertyConfiguration.getPro().getProperty(Admin.UPDATE_BRANCH.getPropertyCode()) +
                " /4. " + PropertyConfiguration.getPro().getProperty(Admin.LIST_BRANCH.getPropertyCode()) +
                " /5. " + PropertyConfiguration.getPro().getProperty(Admin.BLOCK_BRANCH.getPropertyCode()) +
                " /6. " + PropertyConfiguration.getPro().getProperty(Admin.UNBLOCK_BRANCH.getPropertyCode()) +
                " /7. " + PropertyConfiguration.getPro().getProperty(OtherMenu.QUIT.getPro()), 1, 7);
    }

    public int bankServiceMenu() {
        return Menu.menu("1. " + PropertyConfiguration.getPro().getProperty(SuperAdmin.CREAT_ORG.getPropertyCode()) +
                " /2. " + PropertyConfiguration.getPro().getProperty(SuperAdmin.DELETE_ORG.getPropertyCode()) +
                " /3. " + PropertyConfiguration.getPro().getProperty(SuperAdmin.UPDATE_ORG.getPropertyCode()) +
                " /4. " + PropertyConfiguration.getPro().getProperty(SuperAdmin.LIST_ORG.getPropertyCode()) +
                " /5. " + PropertyConfiguration.getPro().getProperty(SuperAdmin.BLOCK_ORG.getPropertyCode()) +
                " /6. " + PropertyConfiguration.getPro().getProperty(SuperAdmin.UNBLOCK_ORG.getPropertyCode()) +
                " /7. " + PropertyConfiguration.getPro().getProperty(OtherMenu.QUIT.getPro()), 1, 7);
    }

    public int atmServiceMenu() {
        return Menu.menu("1. " + PropertyConfiguration.getPro().getProperty(Manager.CREATE_ATM.getPropertyCode()) +
                " /2. " + PropertyConfiguration.getPro().getProperty(Manager.DELETE_ATM.getPropertyCode()) +
                " /3. " + PropertyConfiguration.getPro().getProperty(Manager.UPDATE_ATM.getPropertyCode()) +
                " /4. " + PropertyConfiguration.getPro().getProperty(Manager.LIST_ATM.getPropertyCode()) +
                " /5. " + PropertyConfiguration.getPro().getProperty(Manager.BLOCK_ATM.getPropertyCode()) +
                " /6. " + PropertyConfiguration.getPro().getProperty(Manager.UNBLOCK_ATM.getPropertyCode()) +
                " /7. " + PropertyConfiguration.getPro().getProperty(OtherMenu.QUIT.getPro()), 1, 7);
    }

    public int superAdminMenu() {
        return Menu.menu("1. " + PropertyConfiguration.getPro().getProperty(OtherMenu.BANK.getPro()) +
                " /2. " + PropertyConfiguration.getPro().getProperty(OtherMenu.ADMIN.getPro()) +
                " /3. " + PropertyConfiguration.getPro().getProperty(OtherMenu.QUIT.getPro()), 1, 3);
    }
    public int managerServiceMenu() {
        return Menu.menu("1. " + PropertyConfiguration.getPro().getProperty(Admin.CREATE_MANAGER.getPropertyCode()) +
                " /2. " + PropertyConfiguration.getPro().getProperty(Admin.DELETE_MANAGER.getPropertyCode()) +
                " /3. " + PropertyConfiguration.getPro().getProperty(Admin.LIST_MANAGER.getPropertyCode()) +
                " /4. " + PropertyConfiguration.getPro().getProperty(Admin.UPDATE_MANAGER.getPropertyCode()) +
                " /5. " + PropertyConfiguration.getPro().getProperty(Admin.BLOCK_MANAGER.getPropertyCode()) +
                " /6. " + PropertyConfiguration.getPro().getProperty(Admin.UNBLOCK_MANAGER.getPropertyCode()) +
                " /7. " + PropertyConfiguration.getPro().getProperty(Admin.ADD_PER_MANAGER.getPropertyCode())+
                " /8. "+ PropertyConfiguration.getPro().getProperty(OtherMenu.QUIT.getPro()), 1, 8);
    }
    public int managerServiceMainMenu(){
        return Menu.menu("1. "+PropertyConfiguration.getPro().getProperty(OtherMenu.EMPLOYEE.getPro())+
                " /2. "+PropertyConfiguration.getPro().getProperty(OtherMenu.ATM.getPro())+
                " /3. "+PropertyConfiguration.getPro().getProperty(OtherMenu.QUIT.getPro()),1,3);
    }
    public int managerServiceUpdateMenu(){
        return  Menu.menu("1. Name " +PropertyConfiguration.getPro().getProperty(Reminder.NAME.getPro())+
                " /2. " +PropertyConfiguration.getPro().getProperty(Reminder.PASS.getPro())+
                " /3. "+PropertyConfiguration.getPro().getProperty(OtherMenu.QUIT.getPro()), 1, 2);
    }
    public int adminServiceMainMenu(){
        return Menu.menu("1. " + PropertyConfiguration.getPro().getProperty(Admin.CREATE_ADMIN.getPropertyCode()) +
                " /2. " + PropertyConfiguration.getPro().getProperty(Admin.DELETE_ADMIN.getPropertyCode()) +
                " /4. " + PropertyConfiguration.getPro().getProperty(Admin.UPDATE_ADMIN.getPropertyCode()) +
                " /3. " + PropertyConfiguration.getPro().getProperty(Admin.LIST_ADMIN.getPropertyCode()) +
                " /5. " + PropertyConfiguration.getPro().getProperty(Admin.BLOCK_ADMIN.getPropertyCode()) +
                " /6. " + PropertyConfiguration.getPro().getProperty(Admin.UNBLOCK_ADMIN.getPropertyCode()) +
                " /7. " + PropertyConfiguration.getPro().getProperty(Admin.ADD_PERMISSION_ADMIN.getPropertyCode())+
                " /8. "+ PropertyConfiguration.getPro().getProperty(OtherMenu.QUIT.getPro()), 1, 8);
    }
    public int adminServiceMenu(){
        return  Menu.menu("1. " +PropertyConfiguration.getPro().getProperty(OtherMenu.FILIAL.getPro())+
                " /2. " +PropertyConfiguration.getPro().getProperty(OtherMenu.ADMIN.getPro())+
                " /3. " +PropertyConfiguration.getPro().getProperty(OtherMenu.MANAGER.getPro())+
                " /4. "+ PropertyConfiguration.getPro().getProperty(OtherMenu.QUIT.getPro()), 1, 4);
    }
}
