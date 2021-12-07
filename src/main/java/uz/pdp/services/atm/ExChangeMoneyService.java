package uz.pdp.services.atm;

import uz.pdp.dao.atm.AtmDao;
import uz.pdp.dao.bank.BankDao;
import uz.pdp.dao.card.CardDao;
import uz.pdp.dao.session.SessionDao;
import uz.pdp.enums.status.Status;
import uz.pdp.models.atm.Atm;
import uz.pdp.models.bank.Bank;
import uz.pdp.models.card.Card;
import uz.pdp.utils.Print;

import java.util.Objects;

import static uz.pdp.utils.Color.*;
import static uz.pdp.utils.Input.getStr;
import static uz.pdp.utils.Print.println;

public class ExChangeMoneyService {
    private ExChangeMoneyService instance;
    private Atm atm = SessionDao.sessionAtm;
    private int indexSes = -1;

    private ExChangeMoneyService() {
    }

    public ExChangeMoneyService getInstance() {
        if (Objects.isNull(instance)) instance = new ExChangeMoneyService();
        return instance;
    }

    public void mainMenu() {

        int count = 1;
        try{
            for (Atm atm1 : AtmDao.getInstance().list()) {
                println(GREEN, (count++) + ". " + atm1.getOrgName());
                println(YELLOW,atm.getFilName());
                Print.print(YELLOW,atm1.getCode());
            }
            String choice = getStr("Tanlang : ");
            int index = Integer.parseInt(choice);
            SessionDao.sessionAtm = AtmDao.getInstance().list().get(index - 1);
            if(SessionDao.sessionAtm.getStatus().equals(Status.ACTIVE) ||
                    SessionDao.sessionAtm.getStatus().equals(Status.UNBLOCKED) ) {
                sessionCardMenu();
            } else {
                println(RED,"Ushbu bankomat bloklangan");
                println(GREEN,"Iltimos boshqa bankomat tanlashga urunib ko'ring");
                mainMenu();
            }
        } catch (Exception e) {
            println(RED,"Bankomatlar topilmadi");
        }
    }

    private void sessionCardMenu() {
        String cardNum = getStr("Karta raqamingizni kiriting: ");
        String psw = getStr("Parolingizni kiriting: ");
        try {
            for (Card card : CardDao.getInstance().list()) {
                if (card.getCardNumber().equals(cardNum) && card.getPassword().equals(psw)) {
                    SessionDao.sessionCard = card;
                }
            }
        } catch (Exception e) {
            println(RED,"Ma'lumotlar noto'g'ri kiritilgan");
            sessionCardMenu();
        }
    }

    private void mainAtmMenu() {
        println(CYAN, "1. Sms xabarnomani yoqish");
        println(CYAN, "2. Sms xabarnomani o'chirish");
        println(CYAN," 3. Hisobni ko'rish");
        println(CYAN, "4. Naqd pul chiqarish");
        println(CYAN," 5. Valyuta almashtirish");
        println(CYAN," 6. Chiqish");
        String choice = getStr("Choose please: ");
        byAtmMenu(choice);
    }

    private void byAtmMenu(String choice) {
        if ("1".equals(choice)) messageOn();
        else if ("2".equals(choice)) messageOff();
        else if ("3".equals(choice)) showBalance();
        else if ("4".equals(choice)) cash();
        else if ("5".equals(choice)) exchangeMenu();
        else if ("6".equals(choice)) {
            logout();
            return;
        } else {
            println(RED, "Noto'g'ri menu ");
        }
        mainAtmMenu();
    }

    private void messageOn() {
        if(! SessionDao.sessionCard.isOn()) {
            String phoneNum = getStr("telefon raqamingizni kiriting: ");
            if(SessionDao.sessionCard.getPhoneNumber().equals(phoneNum)) {
                SessionDao.sessionCard.setOn(true);
                println(GREEN,"Sms xabar yoqildi");
                mainAtmMenu();
            } else {
                println(RED,"Telefon raqamingizni to'g'ri kiriting");
                messageOn();
            }
        } else {
            println(GREEN,"Sizning sms xabarnomangiz yoqiq");
            mainAtmMenu();
        }
    }

    private void messageOff() {
        if( SessionDao.sessionCard.isOn()) {
            SessionDao.sessionCard.setOn(false);
            println(GREEN,"Sms xabarnoma o'chirildi ");
            mainAtmMenu();
        } else println(GREEN,"Sizning sms xabarnomangiz o'chiq");
    }

    private void showBalance() {
        println(GREEN," Ushbu operatsiya uchun chek talab qilinadimi ?");
        String choice = getStr("1. Ha / 2. Yo'q");
        String choice1 = checkMenu(choice);
        println(YELLOW,"Sizning hisobingizda " + SessionDao.sessionCard.getBalance() + " mavjud");
        if(choice1.equals("1")) {
            println(BLACK,                         "Chek"         );
            println(BLACK,"Sizning hisobingiz: " + SessionDao.sessionCard.getBalance());
        }
    }

    private void cash() {
        println(GREEN," Ushbu operatsiya uchun chek talab qilinadimi ?");
        String choice = getStr("1. Ha / 2. Yo'q");
        String choice1 = checkMenu(choice);
        String sum = cashMenu();
        Integer sum1 = Integer.parseInt(sum);
        Integer balance = Integer.parseInt(SessionDao.sessionCard.getBalance());
        Integer commission = sum1 / 100;
        if(sum1 < 10000) {
            println(RED,"Operatsiyani amalga oshirish imkonsiz iltimos boshqa summa kiriting");
            cash();
            return;
        }
        if(balance < sum1 || (balance < sum1 + commission)) {
            println(RED,"Balansingizda yetarli mablag' mavjud emas");
            mainAtmMenu();
        } else {
            String newBalance = Integer.toString(balance - (sum1 + commission));
            SessionDao.sessionCard.setBalance(newBalance);
            Bank bank = getBank(SessionDao.sessionAtm.getOrgId());
            // model da bank modelini balansini 0 qilib qoydim.
            Integer bankBalance = Integer.parseInt(bank.getBalance());
            bankBalance += commission;
            bank.setBalance(bankBalance.toString());
            println(YELLOW,"Operatsiya muvaffaqqiyatli amalga oshirildi");
            if(choice1.equals("1")) {
                println(BLACK,                         "Chek"         );
                println(BLACK,"Sizning hisobingizdan: " + (sum1+commission) + " yechildi");
                println(BLACK, "Komissiya " + commission);
            }
            mainAtmMenu();
        }
    }

    private void fromDollar() {
        if(! SessionDao.sessionCard.getCardNumber().startsWith("4000")) {
            println(RED,"Iltimos ushbu opertsiyani amalga oshirish uchun visa kartadan foydalaning");
            mainAtmMenu();
            return;
        }
        println(GREEN," Ushbu operatsiya uchun chek talab qilinadimi ?");
        String choice = getStr("1. Ha / 2. Yo'q");
        String choice1 = checkMenu(choice);
        String sum = getStr("Summani kiriting: ");
        Integer sum1 = Integer.parseInt(sum);
        Integer balance = Integer.parseInt(SessionDao.sessionCard.getDollarBalance());
        Double commission = (double)sum1  / 100;
        if(balance < sum1 || (balance < (sum1  + commission))) {
            println(RED,"Hisobingizda mablag' mavjud emas");
            mainAtmMenu();
            return;
        }
        String newBalance = Double.toString((balance - sum1 - commission));
        SessionDao.sessionCard.setDollarBalance(newBalance);
        Bank bank = getBank(SessionDao.sessionAtm.getOrgId());
        Double bankBalance = Double.parseDouble(bank.getBalance());
        bankBalance += (commission * 10700);
        bank.setBalance(bankBalance.toString());
        println(YELLOW,"Operatsiya muvaffaqqiyatli amalga oshirildi");
        if(choice1.equals("1")) {
            println(BLACK,                         "Chek"         );
            println(BLACK,"Sizning hisobingizdan: " + (sum1+commission) + " dollar yechildi");
            println(BLACK, "Komissiya " + commission + "dollar");
        }
        mainAtmMenu();
    }

    private void fromSum() {
        println(GREEN," Ushbu operatsiya uchun chek talab qilinadimi ?");
        String choice = getStr("1. Ha / 2. Yo'q");
        String choice1 = checkMenu(choice);
        String sum = getStr("Summani kiriting: ");
        Double sum1 = Double.parseDouble(sum);
        Double convert = sum1 / 10700;
        if(convert / 100 != 0) {
            println(RED,"Bizda faqt 100 dollarlik kupyura mavjud iltimos boshqa summa kiriting ");
            fromSum();
            return;
        }
        Double commission = sum1 / 100;
        Double balance = Double.parseDouble(SessionDao.sessionCard.getBalance());
        if(balance < sum1 || balance < (sum1 + commission)) {
            println(RED,"Hisobingizda yetarli mablag' mavjud emas ");
            mainAtmMenu();
        }
        String newBalance = Double.toString(balance - (sum1 + commission));
        SessionDao.sessionCard.setBalance(newBalance);
        Bank bank = getBank(SessionDao.sessionAtm.getOrgId());
        Double bankBalance = Double.parseDouble(bank.getBalance());
        bankBalance += commission;
        bank.setBalance(bankBalance.toString());
        println(YELLOW,"Operatsiya muvaffaqqiyatli amalga oshirildi");
        if(choice1.equals("1")) {
            println(BLACK,"\t\t\t\tChek");
            println(BLACK,"Sizning hisobingizdan: " + (sum1+commission) + " so'm yechildi");
            println(BLACK, "Komissiya " + commission + "so'm");
        }
        mainAtmMenu();
    }

    private void exchangeMenu() {
        println(CYAN," 1. Dollardan so'mga ");
        println(CYAN, "2. So'mdan dollarga ");
        String choice = getStr("Tanlang: ");
        byExchangeMenu(choice);
    }

    private void byExchangeMenu(String choice) {
        if(choice.equals("1")) fromDollar();
        else if(choice.equals("2")) fromSum();
        else {
            println(RED,"Noto'g'ri menu");
            exchangeMenu();
        }
    }

    private String checkMenu(String choice) {
        String s = "";
        if ("1".equals(choice)) s = "1";
        else if ("2".equals(choice))  s = "2";
        else {
            println(RED, "Noto'g'ri tanlov");
            showBalance();
        }
        return s;
    }

    private String cashMenu() {
        println(GREEN, "Summani kiriting: ");
        println(CYAN,"1. 100.000");
        println(CYAN,"2. 200.000");
        println(CYAN,"3. 300.000");
        println(CYAN,"4. 400.000");
        println(CYAN,"5. 500.000");
        println(CYAN,"6. Boshqa summa");
        String choice = getStr("Tanlang: ");
        return byCashMenu(choice);
    }

    private String byCashMenu(String choice) {
        String sum = "";
        if ("1".equals(choice))  sum = "100.000";
        else if ("2".equals(choice))  sum = "200.000";
        else if ("3".equals(choice))  sum = "300.000";
        else if ("4".equals(choice))  sum = "400.000";
        else if ("5".equals(choice))  sum = "500.000";
        else if ("6".equals(choice)) {
            String sum1 = getStr("Summani kiriting: ");
            if (Integer.parseInt(sum1) <= 0) {
                println(RED, "Iltimos summani qaytadan kiriting ! ");
                cashMenu();
            } else {
                sum = sum1;
            }
        }
        return sum;
    }

    private Bank getBank(String id) {
        Bank bank1 = null;
        for (Bank bank : BankDao.getInstance().list()) {
            if(bank.getId().equals(id)) bank1 = bank;
        }
        return bank1;
    }

    private void logout() {
        println(YELLOW,"Servisimizdan foydalanganingiz uchun rahmat ");
        SessionDao.sessionCard = null;
        SessionDao.sessionAtm = null;
        // mana shu joyda asosiy menuga otib yuborish kerak atm bank menuga man otolmadim xato korsatdi 😅
    }
}
