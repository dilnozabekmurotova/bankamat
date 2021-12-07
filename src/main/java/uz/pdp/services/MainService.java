package uz.pdp.services;

import uz.pdp.configs.PropertyConfiguration;
import uz.pdp.dao.auth.UserDao;
import uz.pdp.dao.session.SessionDao;
import uz.pdp.enums.localizatsia.Language;
import uz.pdp.enums.menu.OtherMenu;
import uz.pdp.enums.menu.Reminder;
import uz.pdp.enums.role.Role;
import uz.pdp.services.atm.AtmService;
import uz.pdp.services.auth.AdminService;
import uz.pdp.services.auth.EmployeeService;
import uz.pdp.services.auth.ManagerService;
import uz.pdp.services.auth.SuperAdminService;
import uz.pdp.services.menu.MenuService;
import uz.pdp.utils.Color;
import uz.pdp.utils.Input;
import uz.pdp.utils.Menu;
import uz.pdp.utils.Print;

import java.util.Objects;

public class MainService {
    private static MainService instance;

    private MainService() {
    }

    public static MainService getInstance() {
        if (Objects.isNull(instance)) instance = new MainService();
        return instance;
    }

    static {
        PropertyConfiguration.init();
        PropertyConfiguration.downloadLan();
    }

    public  void menu() {
        int menu = MenuService.getInstance().mainServicesFirstMenu();
        if (menu == 1) MainService.getInstance().secondMenu();
        else if (menu == 2) AtmService.getInstance().mainMenu();
        else {
            Print.println(Color.PURPLE, PropertyConfiguration.getPro().getProperty(Reminder.GOOD_BYE.getPro()));
            return;
        }
        menu();
    }

    public void secondMenu() {
        int menu = MenuService.getInstance().mainServiceSecondMenu();
        if (menu==1) login();
        else if(menu==2) changeLan();
        else{
            Print.println(Color.PURPLE, "\t\t"+PropertyConfiguration.getPro().getProperty(OtherMenu.LOGGED_OUT.getPro()));
            return;
        }
        secondMenu();
    }

    public void login(){
        String name = Input.getStr(PropertyConfiguration.getPro().getProperty(Reminder.ENTER_NAME.getPro())+" : ");
        String pass= Input.getStr(PropertyConfiguration.getPro().getProperty(Reminder.ENTER_PASS.getPro())+" : ");
        if(UserDao.getInstance().findUserForLogin(name,pass)<0){
            Print.println(Color.RED, PropertyConfiguration.getPro().getProperty(Reminder.DONT_FIND.getPro()));
            return;
        }
        PropertyConfiguration.downloadLan();
        if (SessionDao.sessionUser.getRole().equals(Role.SUPER_ADMIN))SuperAdminService.getInstance().menu();
        else if (SessionDao.sessionUser.getRole().equals(Role.ADMIN))AdminService.getInstance().menu();
        else if (SessionDao.sessionUser.getRole().equals(Role.MANAGER))ManagerService.getInstance().menu();
        else if (SessionDao.sessionUser.getRole().equals(Role.EMPLOYEE))EmployeeService.getInstance().menu();
    }
    public void changeLan(){
        Language.showAll();
        String name = Input.getStr(PropertyConfiguration.getPro().getProperty(Reminder.ENTER_LANGUAGE.getPro())+" : ");
        Language language = Language.findByCode(name);
        PropertyConfiguration.setPro(language.getCode());
    }
}
