package uz.pdp.services.auth;

import uz.pdp.dao.auth.UserDao;
import uz.pdp.dao.session.SessionDao;
import uz.pdp.dao.atm.AtmDao;

import uz.pdp.enums.permission.Employee;
import uz.pdp.enums.role.Role;
import uz.pdp.enums.status.Status;
import uz.pdp.models.atm.Atm;
import uz.pdp.models.casatte.Cassette;
import uz.pdp.services.BaseService;
import uz.pdp.utils.Color;
import uz.pdp.utils.Input;
import uz.pdp.utils.Menu;
import uz.pdp.utils.Print;
import uz.pdp.models.auth.User;

import java.util.*;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmployeeService implements BaseService {
    private static EmployeeService instance;
    private final User session = SessionDao.sessionUser;

    private EmployeeService() {
    }

    public void mainMenu() {
        int choice = Menu.menu("1. Fill ATM /2. Monitor ATM /3. Quit", 1, 3);
        if (choice == 1) {
            fillAtm();
        } else if (choice == 2) {
            monitor();
        } else {
            Print.println(Color.PURPLE, "\t\t<<<<<< Quit >>>>>>");
            return;
        }
        mainMenu();
    }

    public void fillAtm() {
        emptyCassatteList();
        String code = Input.getStr("Enter the ATM code: ");
        int atmIndex = AtmDao.getInstance().findAtm(session.getBankId(), session.getBranchId(), code);
        Atm atm = AtmDao.getInstance().list().get(atmIndex);
        Map<Cassette, String> map = atm.getMapCassettes();
        int index = 1;
        for (Cassette cassette : map.keySet()) {
            Print.println(index + ". " + cassette.getType());
        }
        String cassette = Input.getStr("Enter index: ");
        String count = Input.getStr("Enter the count: ");
        for (Cassette cassettes : map.keySet()) {
            if (cassettes.getType().equals(cassette)) {
                map.put(cassettes, count);
                atm.setMapCassettes(map);
                AtmDao.getInstance().reset(atmIndex, atm);
                return;
            }
        }
    }
    public void emptyCassatteList() {
        for (Atm atm : AtmDao.getInstance().list()) {
            if (atm.getFilId().equals(session.getBranchId())) {
                Map<Cassette, String> maps = atm.getMapCassettes();
                Print.print(atm.getCode() + " -> ");
                for (Cassette cassette : maps.keySet()) {
                    String value = maps.get(cassette);
                    if (value.equals("0")) {
                        Print.print(Color.RED, cassette.getType() + " -> " + value + "\t");
                    }
                }
                Print.println("");
            }
        }
    }

    public void monitor() {
        for (Atm atm : AtmDao.getInstance().list()) {
            if (atm.getFilId().equals(session.getBranchId())) {
                Map<Cassette, String> maps = atm.getMapCassettes();
                Print.print(atm.getCode() + " -> ");
                int count = 1;
                for (Cassette cassette : maps.keySet()) {
                    String value = maps.get(cassette);
                    if (value.equals("0")) {
                        Print.print(Color.RED, count + ". " + cassette.getType() + " -> " + value + "\t");
                    } else
                        Print.print(Color.RED, count + ". " + cassette.getType() + " -> " + value + "\t");
                    count++;
                }
                Print.println("");
            }
        }
    }

    public void menu() {
        int choice = Menu.menu("1. Create /2. Delete /3. List/4. Update /5.Block /6. Unblock /7. Quit", 1, 7);
        if (choice == 1) add();
        else if (choice == 2) delete();
        else if (choice == 3) update();
        else if (choice == 4) list();
        else if (choice == 5) block();
        else if (choice == 6) unblock();
        else {
            Print.println(Color.PURPLE, "\t\t<<<<<< Quit >>>>>>");
            return;
        }
        menu();
    }

    @Override
    public void add() {
        if (session.getPermissions().contains("create.emp")) {
            String username = Input.getStr("Enter the name: ");
            String password = Input.getStr("Enter the password: ");
            int index = UserDao.getInstance().findByUsername(username);
            if (index != -1) {
                Print.println("Choose another name!");
                add();
                return;
            }
            List<String> permissions = Stream.of(Employee.values()).map(Employee::getPropertyCode).collect(Collectors.toList());
            User employee = new User(username, password, Role.EMPLOYEE, session.getBankId(), session.getBranchId(), permissions);
            UserDao.getInstance().add(employee);
        }
    }

    @Override
    public void delete() {
        list();
        String name = Input.getStr("Name: ");
        int index = UserDao.getInstance().findByUsername(name);
        if (index != -1)
            UserDao.getInstance().delete(index);
    }

    @Override
    public void update() {
        list();
        String name = Input.getStr("Name: ");
        int index = UserDao.getInstance().findByUsername(name);
        if (index != -1)
            updateEmployee(index);
    }

    @Override
    public void list() {
        for (User user : UserDao.getInstance().list()) {
            if (user.getRole().equals(Role.EMPLOYEE) && user.getBranchId().equals(session.getBranchId()))
                Print.println(user.getName());
        }
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

    private void updateEmployee(int index) {
        if (index != -1) {
            User user = UserDao.getInstance().list().get(index);
            int choice = Menu.menu("1. Name /2. Password /3. Quit", 1, 3);
            if (choice == 1) {
                String newUsername = Input.getStr("Enter new username: ");
                user.setName(newUsername);
            } else if (choice == 2) {
                String password = Input.getStr("Enter new password: ");
                user.setPassword(password);
            } else {
                Print.println(Color.PURPLE, "\t\t<<<<<< Quit >>>>>>");
                return;
            }
            UserDao.getInstance().reset(index, user);
        }
    }

    public static EmployeeService getInstance() {
        if (Objects.isNull(instance)) instance = new EmployeeService();
        return instance;
    }
}
