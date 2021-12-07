package uz.pdp.services.auth;

import uz.pdp.configs.PropertyConfiguration;
import uz.pdp.enums.menu.OtherMenu;
import uz.pdp.services.bank.BankService;
import uz.pdp.services.menu.MenuService;
import uz.pdp.utils.Color;
import uz.pdp.utils.Print;

import java.util.Objects;

public class SuperAdminService {
     private static SuperAdminService  instance;


     private SuperAdminService(){
     }

     public void menu(){
        int choice = MenuService.getInstance().superAdminMenu();
        if(choice==1) BankService.getInstance().menu();
        else if(choice==2) AdminService.getInstance().adminMenu();
        else {
            Print.println(Color.PURPLE, "\t\t"+PropertyConfiguration.getPro().getProperty(OtherMenu.QUIT.getPro()));
            return;
        }
         menu();
     }

    public static SuperAdminService getInstance(){
        if(Objects.isNull(instance)) instance = new SuperAdminService();
        return instance;
    }

}
