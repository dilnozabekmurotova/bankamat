package uz.pdp.dao.bank;

import uz.pdp.dao.BaseDao;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import uz.pdp.dao.session.SessionDao;
import uz.pdp.enums.status.Status;
import uz.pdp.enums.url.Urls;
import uz.pdp.models.bank.Bank;
import java.io.*;
import java.util.stream.Collectors;
import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

public class BankDao implements BaseDao<Bank> {
    private static BankDao instance;
    private final String path = Urls.ORGJSON.getUrl();
    private final Type bankListType = new TypeToken<List<Bank>>() {}.getType();
    List<Bank> bankList;

    {
        bankList = get(path);
    }

    private BankDao() {
    }

    @Override
    public void add(Bank obj) {
        bankList.add(obj);
       set(bankList, path);
    }

    @Override
    public void delete(int index) {
        bankList.remove(index);
        set(bankList, path);
    }


    @Override
    public List<Bank> list() {return bankList;}

    public void reset(int index, Bank bank){
        bankList.set(index, bank);
        set(bankList, path);
    }

    public void block(int index){
        block(index, Status.BLOCKED);
    }

    public void block(int index, Status status){
        bankList.get(index).setStatus(status);
        set(bankList, path);
    }

    public String findById(String id){
        for (Bank bank : bankList) {
            if (bank.getId().equals(id)){
                SessionDao.sessionBank =bank;
                return bank.getName();
            }
        }
        return null;
    }


    public static BankDao getInstance() {
        if (Objects.isNull(instance))
            instance = new BankDao();
        return instance;
    }

    public List<Bank> get(String url){
        List<Bank> bankList = new ArrayList<>();
        try (FileReader reader = new FileReader(url);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String jsonString = bufferedReader.lines().collect(Collectors.joining());
            bankList = new Gson().fromJson(jsonString, bankListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bankList;
    }

    public void set(List<Bank> list,String url){
        try (FileWriter writer = new FileWriter(url);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            String jsonData = new Gson().toJson(list);
            bufferedWriter.write(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


