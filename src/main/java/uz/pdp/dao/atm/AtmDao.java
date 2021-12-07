package uz.pdp.dao.atm;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import uz.pdp.dao.BaseDao;
import uz.pdp.dao.session.SessionDao;
import uz.pdp.enums.url.Urls;
import uz.pdp.models.atm.Atm;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AtmDao implements BaseDao<Atm> {
    private final Type atmList = new TypeToken<List<Atm>>() {}.getType();
    private static AtmDao instance;
    String path = Urls.ATMJSON.getUrl();
    private AtmDao(){};

    public List<Atm> get(String url){
        List<Atm> userList = new ArrayList<>();
        try (FileReader reader = new FileReader(url);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String jsonString = bufferedReader.lines().collect(Collectors.joining());
            userList = new Gson().fromJson(jsonString, atmList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }
    public void set(List<Atm> list,String url){
        try (FileWriter writer = new FileWriter(url);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            String jsonData = new Gson().toJson(list);
            bufferedWriter.write(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void add(Atm obj) {
        List<Atm> atms = get(path);
        atms.add(obj);
        set(atms,path);
    }

    @Override
    public void delete(int index) {
        List<Atm> atms = get(path);
        atms.remove(index);
        set(atms,path);
    }

    @Override
    public List<Atm> list() {
        return get(path);
    }

    @Override
    public void reset(int index, Atm obj) {
        List<Atm> atms = get(path);
        atms.set(index,obj);
        set(atms,path);
    }
    public int findAtm(String bankId,String filialId,String name){
        List<Atm> atms = get(path);
        for (int i = 0; i < atms.size(); i++) {
            Atm atm = atms.get(i);
            if (atm.getOrgId().equals(bankId) && atm.getFilId().equals(filialId)
                    && atm.getCode().equalsIgnoreCase(name)) {
                SessionDao.sessionAtm=atm;
                return i;
            }
        }
        return -1;
    }
    public Atm findByCode(String code){
        List<Atm> atms = get(path);
        for (Atm atm : atms) {
            if (atm.getCode().equals(code))return atm;
        }
        return null;
    }
    public int size(){
        return get(path).size();
    }

    public static AtmDao getInstance(){
        if (Objects.isNull(instance))instance=new AtmDao();
        return instance;
    }
}
