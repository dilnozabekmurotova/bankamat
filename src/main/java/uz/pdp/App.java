package uz.pdp;

import uz.pdp.services.MainService;
import uz.pdp.utils.Print;

public class App {

    public static void main(String[] args) {
        try {
            MainService.getInstance().menu();
        } catch (Exception e) {
            Print.println("Ertagimiz tugadi");
            e.printStackTrace();
        }
    }
}
