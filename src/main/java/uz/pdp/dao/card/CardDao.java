package uz.pdp.dao.card;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import uz.pdp.enums.url.Urls;
import uz.pdp.models.bank.Bank;
import uz.pdp.models.card.Card;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CardDao {
    private static CardDao instance;
    private final String path = Urls.CARDJSON.getUrl();
    private final Type cardType = new TypeToken<List<Card>>() {}.getType();
    List<Card> cards;

    private CardDao() {
    }

    public List<Card> get(String url){
        List<Card> cardList = new ArrayList<>();
        try (FileReader reader = new FileReader(url);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String jsonString = bufferedReader.lines().collect(Collectors.joining());
            cardList = new Gson().fromJson(jsonString, cardType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cardList;
    }

    public void set(List<Card> list,String url){
        try (FileWriter writer = new FileWriter(url);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            String jsonData = new Gson().toJson(list);
            bufferedWriter.write(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  List<Card> list(){
        return get(path);
    }

    public static CardDao getInstance() {
        if (Objects.isNull(instance))
            instance = new CardDao();
        return instance;
    }
}

 /*   Card card0 = new Card().builder().cardNumber("8600111111111111").expireDate("01/23")
            .phoneNumber("998901111111").isOn(false).balance("2000000").password("1111").build();
    Card card1 = new Card().builder().cardNumber("860022222222222").expireDate("01/23")
            .phoneNumber("998901111111").isOn(false).balance("2000000").password("1111").build();
    Card card2 = new Card().builder().cardNumber("4000111111111111").expireDate("01/23")
            .phoneNumber("998901111111").isOn(false).balance("2000000").password("1111")
            .dollarBalance("200").build();
    Card card3 = new Card().builder().cardNumber("40000222222222222").expireDate("01/23")
            .phoneNumber("998901111111").isOn(false).balance("2000000").password("1111")
            .dollarBalance("200").build();
    Card card4 = new Card().builder().cardNumber("9860111111111111").expireDate("01/23")
            .phoneNumber("998901111111").isOn(false).balance("2000000").password("1111").build();
    Card card5 = new Card().builder().cardNumber("9860122222222222").expireDate("01/23")
            .phoneNumber("998901111111").isOn(false).balance("2000000").password("1111").build();

    public ArrayList<Card> cards = new ArrayList<Card>();*/
