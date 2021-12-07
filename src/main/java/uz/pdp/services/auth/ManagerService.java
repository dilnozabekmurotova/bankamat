package uz.pdp.services.auth;

import uz.pdp.configs.PropertyConfiguration;
import uz.pdp.dao.auth.UserDao;
import uz.pdp.dao.filal.FilialDao;
import uz.pdp.dao.session.SessionDao;
import uz.pdp.enums.menu.OtherMenu;
import uz.pdp.enums.menu.Reminder;
import uz.pdp.enums.permission.Manager;
import uz.pdp.enums.role.Role;
import uz.pdp.enums.status.Status;
import uz.pdp.models.auth.User;
import uz.pdp.services.BaseService;
import uz.pdp.services.filial.FilialService;
import uz.pdp.services.menu.MenuService;
import uz.pdp.utils.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ManagerService implements BaseService {
    private static ManagerService instance;
    private final User user = SessionDao.sessionUser;

    private ManagerService() {
    }

    public static ManagerService getInstance() {
        if (Objects.isNull(instance)) instance = new ManagerService();
        return instance;
    }

    public void mainMenu(){
        int menu = MenuService.getInstance().managerServiceMainMenu();
        if (menu == 1) add();
        else if (menu== 2) delete();
        else {
            Print.println(Color.PURPLE, "\t\t"+ PropertyConfiguration.getPro().getProperty(OtherMenu.QUIT.getPro()));
            return;
        }
        mainMenu();
    }


    public void menu() {
        int choice = MenuService.getInstance().managerServiceMenu();
        if (choice == 1) add();
        else if (choice == 2) delete();
        else if (choice == 3) update();
        else if (choice == 4) list();
        else if (choice == 5) block();
        else if (choice == 6) unblock();
        else if (choice == 7) addPer();
        else {
            Print.println(Color.PURPLE, "\t\t"+PropertyConfiguration.getPro().getProperty(OtherMenu.QUIT.getPro()));
            return;
        }
        menu();
    }


    @Override
    public void add() {
        FilialService.createInstance().adminFilial(user.getBankId());
        String filialName = Input.getStr("Filial name : ");
        int num = FilialDao.getInstance().findFilial(user.getBankId(), filialName);
        if (num < 0) {
            Print.println(Color.RED, "Filial topilmadi");
            return;
        }
        String name = Input.getStr("Name : ");
        int in = UserDao.getInstance().findFilialMan(user.getBankId(), user.getBranchId(), name);
        if (in < 0) {
            Print.println(Color.RED, "This manager already have !");
            return;
        }
        String pass = Input.getStr("Password : ");
        List<String> permission = givingPermissions();
        User newMan = new User(name, pass, Role.MANAGER, user.getBankId(), user.getBranchId(), permission);
        UserDao.getInstance().add(newMan);
    }

    @Override
    public void delete() {
        int in = check();
        if (in < 0) return;
        UserDao.getInstance().delete(in);
    }

    @Override
    public void update() {
        int in = check();
        if (in < 0) return;
        updateManager(in);
    }

    @Override
    public void list() {
        List<User> users = UserDao.getInstance().list();
        for (User user1 : users) {
            if (user1.getRole().equals(Role.MANAGER) && user1.getBankId().equals(user.getBankId())
                    && user1.getBranchId().equals(user.getBranchId())) {
                Print.println(Color.YELLOW, user1.getName());
            }
        }
    }

    @Override
    public void block() {
        changeStatus(Status.BLOCKED);
    }

    @Override
    public void unblock() {
        changeStatus(Status.UNBLOCKED);
    }

    private void changeStatus(Status st) {
        int choice = check();
        if (choice < 0) return;
        User user = SessionDao.sessionManager;
        if (user.getStatus().equals(st)) Print.println(PropertyConfiguration.getPro().getProperty(Reminder.CANT_CHANGE.getPro()));
        else user.setStatus(st);
        UserDao.getInstance().reset(choice, user);
    }

    private void addPer() {
        int choice = check();
        if (choice < 0) return;
        Manager.showFalsePer();
        String num = Input.getStr(PropertyConfiguration.getPro().getProperty(Reminder.ENTER_INDEX.getPro()));
        Integer in = BaseUtils.isInt(num);
        if (Objects.isNull(in)){
            Print.println(Color.RED,PropertyConfiguration.getPro().getProperty(Reminder.WRONG_VALUE.getPro()));
            return;
        }
        String str = Manager.findPer(in);
        User user = SessionDao.sessionManager;
        List<String> pers =user.getPermissions();
        pers.add(str);
        UserDao.getInstance().reset(choice, user);
    }
    private void updateManager(int index) {
        int choice =MenuService.getInstance().managerServiceUpdateMenu();
        User user = SessionDao.sessionManager;
        if (choice == 1) {
            String name = Input.getStr(PropertyConfiguration.getPro().getProperty(Reminder.NEW_NAME.getPro()));
            user.setName(name);
        } else if (choice == 2) {
            String pass = Input.getStr(PropertyConfiguration.getPro().getProperty(Reminder.NEW_PASS.getPro()));
            user.setPassword(pass);
        } else {
            Print.println(Color.PURPLE, "\t\t"+PropertyConfiguration.getPro().getProperty(OtherMenu.QUIT.getPro()));
            return;
        }
        UserDao.getInstance().reset(index, user);
        updateManager(index);
    }

    private List<String> givingPermissions() {
        List<String> permission = Stream.of(Manager.values()).map(per -> per.isType() ? per.getPropertyCode() : null).collect(Collectors.toList());
        permission.removeIf(Objects::isNull);
        return permission;
    }

    private int check() {
        String name = Input.getStr(PropertyConfiguration.getPro().getProperty(Reminder.NAME.getPro()));
        int in = UserDao.getInstance().findFilialMan(user.getBankId(), user.getBranchId(), name);
        if (in < 0) {
            Print.println(Color.RED, PropertyConfiguration.getPro().getProperty(Reminder.DONT_FIND.getPro()));
            return -1;
        }
        return in;
    }
}
