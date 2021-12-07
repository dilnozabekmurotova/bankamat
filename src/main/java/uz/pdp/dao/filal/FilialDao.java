package uz.pdp.dao.filal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import uz.pdp.dao.BaseDao;
import uz.pdp.dao.session.SessionDao;
import uz.pdp.enums.url.Urls;
import uz.pdp.models.filial.Filial;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FilialDao implements BaseDao<Filial> {
    private final String path =Urls.FILIALJSON.getUrl();
    private final Type filialType = new TypeToken<List<Filial>>() {}.getType();
    private static FilialDao instance;

    private FilialDao(){
    }


    public List<Filial> get(String url){
        List<Filial> userList = new ArrayList<>();
        try (FileReader reader = new FileReader(url);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String jsonString = bufferedReader.lines().collect(Collectors.joining());
            userList = new Gson().fromJson(jsonString, filialType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }
    public void set(List<Filial> list,String url){
        try (FileWriter writer = new FileWriter(url);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            String jsonData = new Gson().toJson(list);
            bufferedWriter.write(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void add(Filial obj) {
        List<Filial> list = get(path);
        list.add(obj);
        set(list,path);
    }

    @Override
    public void delete(int index) {
        List<Filial> list = get(path);
        list.remove(index);
        set(list,path);
    }
    @Override
    public List<Filial> list() {
        return get(path);
    }
    @Override
    public void reset(int index,Filial filial){
        List<Filial> list = get(path);
        list.set(index,filial);
        set(list,path);
    }
    public int findFilial(String bankId,String name){
        List<Filial> list = get(path);
        for (int i = 0; i < list.size(); i++) {
           if(list.get(i).getBankId().equalsIgnoreCase(bankId)
                   && list.get(i).getName().equalsIgnoreCase(name)){
               SessionDao.sessionFilial = list.get(i);
               return i;
           }
        }
        return -1;
    }

    public int size(){
       return get(path).size();
    }

    public static FilialDao getInstance(){
        if (Objects.isNull(instance)){
            instance=new FilialDao();
        }
        return instance;
    }

}

