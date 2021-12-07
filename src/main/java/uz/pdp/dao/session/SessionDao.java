package uz.pdp.dao.session;

import uz.pdp.models.atm.Atm;
import uz.pdp.models.auth.User;
import uz.pdp.models.bank.Bank;
import uz.pdp.models.filial.Filial;
import uz.pdp.models.card.Card;



public class SessionDao {
     public static User sessionUser;
     public static User sessionManager;
     public static Bank sessionBank;
     public static Filial sessionFilial;
     public static Atm sessionAtm;
     public static Card sessionCard;
}
