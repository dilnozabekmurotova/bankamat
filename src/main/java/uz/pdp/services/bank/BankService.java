package uz.pdp.services.bank;

import uz.pdp.configs.PropertyConfiguration;
import uz.pdp.dao.bank.BankDao;
import uz.pdp.enums.menu.OtherMenu;
import uz.pdp.enums.menu.Reminder;
import uz.pdp.enums.permission.SuperAdmin;
import uz.pdp.enums.status.Status;
import uz.pdp.models.bank.Bank;
import uz.pdp.services.BaseService;
import uz.pdp.services.menu.MenuService;
import uz.pdp.utils.Color;
import uz.pdp.utils.Input;
import uz.pdp.utils.Menu;
import uz.pdp.utils.Print;

import java.util.List;
import java.util.Objects;

public class BankService implements BaseService {

    private static BankService instance;


    private BankService() {}

    public void menu(){
        int choice = MenuService.getInstance().bankServiceMenu();
        if (choice == 1)  add();
        else if (choice == 2)  delete();
        else if (choice == 3)  update();
        else if (choice == 4)  list();
        else if (choice == 5)  block();
        else if (choice == 6)  unblock();
        else {
            Print.println(Color.PURPLE, "\t\t"+PropertyConfiguration.getPro().getProperty(Reminder.QUITED.getPro()));
            return;
        }
        menu();
    }

    @Override
    public void add() {
        String name = Input.getStr(PropertyConfiguration.getPro().getProperty(Reminder.ENTER_BANK.getPro()));
        if (findByName(name) !=-1) {
            Print.println(Color.RED, PropertyConfiguration.getPro().getProperty(Reminder.ANOTHER_NAME.getPro()));
            add();
            return;
        }
        Bank bank = new Bank(name);
        BankDao.getInstance().add(bank);
    }

    @Override
    public void delete() {
        String name = Input.getStr(PropertyConfiguration.getPro().getProperty(Reminder.ENTER_BANK.getPro()));
        int bankIndex = getBank(name);
        if(bankIndex==-1) return;
        if (sureDelete()) {
            BankDao.getInstance().delete(bankIndex);
            Print.println(Color.GREEN, PropertyConfiguration.getPro().getProperty(Reminder.DELETED.getPro()));
        }
    }

    @Override
    public void update() {
        String name = Input.getStr(PropertyConfiguration.getPro().getProperty(Reminder.ENTER_BANK.getPro()));
        int bankIndex = getBank(name);
       if(bankIndex ==-1) return;
        if (sureDelete()) {
            String newName = Input.getStr(PropertyConfiguration.getPro().getProperty(Reminder.ENTER_BANK.getPro()));
            Bank bank = BankDao.getInstance().list().get(bankIndex);
            bank.setName(newName);
            BankDao.getInstance().reset(bankIndex, bank);
            Print.println(Color.GREEN, PropertyConfiguration.getPro().getProperty(Reminder.UPDATED.getPro()));
        }
    }

    @Override
    public void list() {
        int count = 1;
        for (Bank bank : BankDao.getInstance().list()) {
            Print.println(Color.BLUE, count+". "+bank.getName());
            count++;
        }
    }

    @Override
    public void block() {
        String name = Input.getStr(PropertyConfiguration.getPro().getProperty(Reminder.ENTER_BANK.getPro()));
        int bankIndex = getBank(name);
        if(bankIndex ==-1) return;
        BankDao.getInstance().block(bankIndex);
    }

    @Override
    public void unblock() {
        String name = Input.getStr(PropertyConfiguration.getPro().getProperty(Reminder.ENTER_BANK.getPro()));
        int bankIndex = getBank(name);
        if(bankIndex ==-1) return;
        BankDao.getInstance().block(bankIndex, Status.UNBLOCKED);
    }

    private int getBank(String name){
        int bank = findByName(name);
        if (bank==-1) {
            Print.println(Color.RED, PropertyConfiguration.getPro().getProperty(Reminder.DONT_FIND.getPro()));
            return -1;
        }
        return bank;
    }

    private boolean sureDelete() {
        String choice = Input.getStr(PropertyConfiguration.getPro().getProperty(Reminder.DO_YOU_DEL.getPro())+"yes/ no");
        return choice.toLowerCase().startsWith("y");
    }

    private int findByName(String name) {
        for (int i = 0; i < BankDao.getInstance().list().size(); i++) {
            if(BankDao.getInstance().list().get(i).getName().equalsIgnoreCase(name))
                return i;
        }
        return -1;
    }

    public String chooseBank(){
        list();
        String name = Input.getStr(PropertyConfiguration.getPro().getProperty(Reminder.ENTER_BANK.getPro()));
        int index = findByName(name);
        if(index==-1) {
            Print.println(Color.RED, PropertyConfiguration.getPro().getProperty(Reminder.WRONG_VALUE.getPro()));
            return chooseBank();
        }
        return BankDao.getInstance().list().get(index).getId();
    }


    public static BankService getInstance() {
        if (Objects.isNull(instance))
            instance = new BankService();
        return instance;
    }
}
