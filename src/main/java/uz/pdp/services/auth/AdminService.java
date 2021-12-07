package uz.pdp.services.auth;

import uz.pdp.configs.PropertyConfiguration;
import uz.pdp.dao.auth.UserDao;
import uz.pdp.dao.session.SessionDao;
import uz.pdp.enums.menu.OtherMenu;
import uz.pdp.enums.menu.Reminder;
import uz.pdp.enums.permission.Admin;
import uz.pdp.enums.role.Role;
import uz.pdp.enums.status.Status;
import uz.pdp.models.auth.User;
import uz.pdp.services.BaseService;
import uz.pdp.services.bank.BankService;
import uz.pdp.services.filial.FilialService;
import uz.pdp.services.menu.MenuService;
import uz.pdp.utils.Color;
import uz.pdp.utils.Input;
import uz.pdp.utils.Menu;
import uz.pdp.utils.Print;

import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdminService implements BaseService {
    private static AdminService instance;

    private AdminService() {
    }

    public void menu() {
        int choice = MenuService.getInstance().adminServiceMenu();
        if (choice == 1) FilialService.createInstance().menu();
        else if (choice == 2) adminMenu();
        else if (choice == 3) ManagerService.getInstance().menu();
        else {
            Print.println(Color.PURPLE, "\t\t"+ PropertyConfiguration.getPro().getProperty(Reminder.QUITED.getPro()));
            return;
        }
        menu();
    }

    public void adminMenu() {

        int num = MenuService.getInstance().adminServiceMainMenu();
        if (num == 1)add();
        else if (num == 2)delete();
        else if (num == 3)update();
        else if (num == 4)list();
        else if (num == 5)block();
        else if (num == 6)unblock();
        else if (num == 7)addPer();
        else {
            Print.println(Color.PURPLE, "\t\t"+ PropertyConfiguration.getPro().getProperty(Reminder.QUITED.getPro()));
            return;
        }
        adminMenu();

    }

    private void addPer() {
        if (SessionDao.sessionUser.getPermissions().contains("add.permission.admin")) {
            list();
            String name = Input.getStr("Name: ");
            int index = UserDao.getInstance().findByUsername(name);
            User user = UserDao.getInstance().list().get(index);
            List<String> permissions = user.getPermissions();

            Admin.showFalsePer(user);
            String choice = "";
            while (!choice.startsWith("n")) {
                int per = Input.getInt("Enter the index: ");
                String permission = Admin.findPer(per);
                permissions.add(permission);

                choice = Input.getStr("Do you want to continue: y/n ");
            }
            user.setPermissions(permissions);
            UserDao.getInstance().reset(index, user);
        }
    }

    @Override
    public void add() {
        String username = Input.getStr("Enter the name: ");
        String password = Input.getStr("Enter the password: ");
        int index = UserDao.getInstance().findByUsername(username);
        if (index != -1) {
            Print.println("Choose another name!");
            add();
            return;
        }
        String bankId = BankService.getInstance().chooseBank();
        List<String> permissions;
        if (SessionDao.sessionUser.getRole().equals(Role.SUPER_ADMIN)) {
            permissions = Stream.of(Admin.values()).map(Admin::getPropertyCode).collect(Collectors.toList());
        } else permissions = givingPermissions();
        User admin = new User(username, password, Role.ADMIN, bankId, permissions);
        UserDao.getInstance().add(admin);
    }

    @Override
    public void delete() {
        String bankId;
        if (SessionDao.sessionUser.getRole().equals(Role.SUPER_ADMIN))
            bankId = BankService.getInstance().chooseBank();
        else bankId = SessionDao.sessionUser.getBankId();
        int getIndex = getAdminIndex(bankId);

        if (getIndex != -1) UserDao.getInstance().delete(getIndex);
    }

    @Override
    public void update() {
        String bankId;
        if (SessionDao.sessionUser.getRole().equals(Role.SUPER_ADMIN))
            bankId = BankService.getInstance().chooseBank();
        else bankId = SessionDao.sessionUser.getBankId();
        int getIndex = getAdminIndex(bankId);

        updateAdmin(getIndex);
    }

    @Override
    public void list() {
        String bankId;
        if (SessionDao.sessionUser.getRole().equals(Role.SUPER_ADMIN))
            bankId = BankService.getInstance().chooseBank();
        else bankId = SessionDao.sessionUser.getBankId();
        showAdmin(bankId);
    }

    @Override
    public void block() {
        list();
        String name = Input.getStr("Name: ");
        int index = UserDao.getInstance().findByUsername(name);
        UserDao.getInstance().block(index);
    }

    @Override
    public void unblock() {
        list();
        String name = Input.getStr("Name: ");
        int index = UserDao.getInstance().findByUsername(name);
        UserDao.getInstance().block(index, Status.UNBLOCKED);
    }


    private List<String> givingPermissions() {
        List<String> permission = Stream.of(Admin.values()).map(per -> per.isType() ? per.getPropertyCode() : null).collect(Collectors.toList());
        permission.removeIf(Objects::isNull);
        return permission;
    }

    private void showAdmin(String bankId) {
        for (int i = 0; i < UserDao.getInstance().list().size(); i++) {
            User user = UserDao.getInstance().list().get(i);
            if (user.getRole().equals(Role.ADMIN) &&
                    user.getBankId().equals(bankId) &&
                    !user.getId().equals(SessionDao.sessionUser.getId())) {
                Print.println(user.getName());
            }
        }
    }

    public int getAdminIndex(String bankId) {
        showAdmin(bankId);
        String name = Input.getStr("Name: ");
        int id = UserDao.getInstance().findByUsername(name);
        if (id == -1) {
            Print.println(Color.RED, "Not found admin!");
            return -1;
        }
        return id;
    }

    public void updateAdmin(int getIndex) {
        if (getIndex != -1) {
            User user = UserDao.getInstance().list().get(getIndex);
            int choice = Menu.menu("1. Name /2. Password /3. Quit", 1, 3);
            if (choice == 1) {
                String newUsername = Input.getStr("Enter new username: ");
                user.setName(newUsername);
            } else if (choice == 2) {
                String password = Input.getStr("Enter new password: ");
                user.setPassword(password);
            } else {
                Print.println(Color.PURPLE, "\t\t"+ PropertyConfiguration.getPro().getProperty(Reminder.QUITED.getPro()));
                return;
            }
            UserDao.getInstance().reset(getIndex, user);
        }
    }

    public static AdminService getInstance() {
        if (Objects.isNull(instance)) {
            instance = new AdminService();
        }
        return instance;
    }
}
