package uz.pdp.services.atm;

import uz.pdp.configs.PropertyConfiguration;
import uz.pdp.dao.atm.AtmDao;
import uz.pdp.dao.bank.BankDao;
import uz.pdp.dao.filal.FilialDao;
import uz.pdp.dao.session.SessionDao;
import uz.pdp.enums.http.Http;
import uz.pdp.enums.menu.OtherMenu;
import uz.pdp.enums.menu.Reminder;
import uz.pdp.enums.status.Status;
import uz.pdp.models.atm.Atm;
import uz.pdp.models.auth.User;
import uz.pdp.models.filial.Filial;
import uz.pdp.services.BaseService;
import uz.pdp.services.menu.MenuService;
import uz.pdp.utils.Color;
import uz.pdp.utils.Input;
import uz.pdp.utils.Print;

import java.util.Objects;

public class AtmService implements BaseService {
    private static AtmService instance;
    Atm atm = SessionDao.sessionAtm;
    User user = SessionDao.sessionManager;
    Filial filial = SessionDao.sessionFilial;

    private AtmService() {
    }

    public static AtmService getInstance() {
        if (Objects.isNull(instance)) instance = new AtmService();
        return instance;
    }

    public void mainMenu() {

    }

    public void menu() {
        int choice = MenuService.getInstance().atmServiceMenu();
        if (choice == 1) add();
        else if (choice == 2) delete();
        else if (choice == 3) update();
        else if (choice == 4) list();
        else if (choice == 5) block();
        else if (choice == 6) unblock();
        else {
            Print.println(Color.PURPLE, "\t\t"+ PropertyConfiguration.getPro().getProperty(OtherMenu.QUIT.getPro()));
            return;
        }
        menu();
    }

    @Override
    public void add() {
        String name = BankDao.getInstance().findById(user.getBankId());
        if (Objects.isNull(name)) {
            Print.println(Color.RED, Http.HTTP_404);
            return;
        }
        filialAtm(user.getBranchId());
        String fil = Input.getStr(PropertyConfiguration.getPro().getProperty(Reminder.ENTER_ATM.getPro())+" : ");
        int num = FilialDao.getInstance().findFilial(user.getBankId(), fil);
        if (num < 0) {
            Print.println(Color.RED, Http.HTTP_404);
            return;
        }
        String code = Input.getStr(PropertyConfiguration.getPro().getProperty(Reminder.NEW_NAME.getPro())+" : ");
        int choice = AtmDao.getInstance().findAtm(user.getBankId(), user.getBranchId(), code);
        if (choice < 0) {
            Print.println(Color.RED,PropertyConfiguration.getPro().getProperty(Reminder.ANOTHER_NAME.getPro()));
            return;
        }
        Atm newAtm = new Atm(code, user.getBankId(), name, filial.getId(), filial.getName(), "0", "0",
                Status.ACTIVE, "500");
        AtmDao.getInstance().add(newAtm);
    }

    @Override
    public void delete() {
        int choice = check();
        if (choice < 0) return;
        AtmDao.getInstance().delete(choice);
    }

    @Override
    public void update() {
        int choice = check();
        if (choice < 0) return;
        String name = Input.getStr(PropertyConfiguration.getPro().getProperty(Reminder.NEW_NAME.getPro())+" : ");
        atm.setCode(name);
        AtmDao.getInstance().reset(choice, atm);
    }

    @Override
    public void list() {
        for (Atm atm1 : AtmDao.getInstance().list()) {
            if (atm1.getFilId().equals(filial.getId()))
                Print.println(Color.YELLOW, atm1.getCode());
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
        if (atm.getStatus().equals(st))
            Print.println(PropertyConfiguration.getPro().getProperty(Reminder.CANT_CHANGE.getPro()));
        else atm.setStatus(st);
        AtmDao.getInstance().reset(choice, atm);
    }

    private int check() {
        String name = Input.getStr(PropertyConfiguration.getPro().getProperty(Reminder.NAME.getPro())+" : ");
        int choice = AtmDao.getInstance().findAtm(user.getBankId(), user.getBranchId(), name);
        if (choice < 0) {
            Print.println(Color.RED, Http.HTTP_404);
            return -1;
        }
        return choice;
    }

    public void filialAtm(String str) {
        for (Atm atm : AtmDao.getInstance().list()) {
            if (atm.getFilId().equals(str)) {
                Print.println(atm.getCode());
            }
        }
    }
}
