package uz.pdp.services.filial;

import uz.pdp.configs.PropertyConfiguration;
import uz.pdp.dao.filal.FilialDao;
import uz.pdp.dao.session.SessionDao;
import uz.pdp.enums.http.Http;
import uz.pdp.enums.menu.OtherMenu;
import uz.pdp.enums.menu.Reminder;
import uz.pdp.enums.status.Status;
import uz.pdp.models.filial.Filial;
import uz.pdp.services.BaseService;
import uz.pdp.services.menu.MenuService;
import uz.pdp.utils.*;

import java.util.Objects;

public class FilialService implements BaseService {
    private static FilialService instance;
    private FilialService() {
    }
    public static FilialService createInstance(){
        if (Objects.isNull(instance)){
            instance = new FilialService();
        }
        return instance;
    }

    public  void menu(){
        int choice = MenuService.getInstance().filialServiceMenu();
        if (choice==1) add();
        else if (choice==2) delete();
        else if (choice==3) update();
        else if (choice==4) list();
        else if (choice==5) block();
        else if (choice==6) unblock();
        else{
            Print.println(Color.PURPLE,"\t\t"+PropertyConfiguration.getPro().getProperty(OtherMenu.EXIT.getPro()));
            return;
        }
        menu();
    }

    @Override
    public void list() {
        for (Filial filial : FilialDao.getInstance().list()) {
            if (filial.getBankId().equalsIgnoreCase(SessionDao.sessionUser.getBankId())){
                System.out.println(filial);
            }
        }
    }
    @Override
    public void add() {
        String name = Input.getStr("(Quit...) "+PropertyConfiguration.getPro().getProperty(Reminder.ENTER_FILIAL.getPro())+" : ");
        if (name.toLowerCase().startsWith("q")) return;
        if(FilialDao.getInstance().findFilial(SessionDao.sessionUser.getBankId(),name)>=0){
            Print.println(Color.RED,PropertyConfiguration.getPro().getProperty(Reminder.ANOTHER_NAME.getPro()));
            add();
            return;
        }
        Filial filial = new Filial(SessionDao.sessionUser.getBankId(),SessionDao.sessionUser.getName(), name,"0","0");
        FilialDao.getInstance().add(filial);
    }

    @Override
    public void delete() {
        int choice = check();
        if (choice<0) return;
        FilialDao.getInstance().delete(choice);
    }

    @Override
    public void update() {
        int choice = check();
        if (choice<0) return;
        updateFilial(choice);
    }
    @Override
    public void block(){
        changeStatus(Status.BLOCKED);
    }
    @Override
    public void unblock(){
        changeStatus(Status.UNBLOCKED);
    }

    private void changeStatus(Status st){
        int choice = check();
        if (choice<0) return;
        Filial filial = SessionDao.sessionFilial;
        if (filial.getStatus().equals(st)) Print.println(PropertyConfiguration.getPro().getProperty(Reminder.CANT_CHANGE.getPro()));
        else filial.setStatus(st);
        FilialDao.getInstance().reset(choice,filial);
    }

    private void updateFilial(int index){
        String name = Input.getStr(PropertyConfiguration.getPro().getProperty(Reminder.ENTER_FILIAL.getPro()));
        Filial filial = SessionDao.sessionFilial;
        filial.setName(name);
        FilialDao.getInstance().reset(index,filial);
    }

    public void adminFilial(String bankId){
        for (Filial filial : FilialDao.getInstance().list()) {
            if (filial.getBankId().equals(bankId)){
                Print.println(Color.YELLOW,filial.getName());
            }
        }
    }
    private int check(){
        String name = Input.getStr(PropertyConfiguration.getPro().getProperty(Reminder.ENTER_FILIAL.getPro()));
        int choice =FilialDao.getInstance().findFilial(SessionDao.sessionUser.getBankId(),name);
        if(choice<0){
            Print.println(Color.RED, Http.HTTP_404);
            return -1;
        }
        return choice;
    }

}
