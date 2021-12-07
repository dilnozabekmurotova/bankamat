package uz.pdp.dao.auth;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import uz.pdp.dao.BaseDao;
import uz.pdp.dao.session.SessionDao;
import uz.pdp.enums.status.Status;
import uz.pdp.enums.url.Urls;
import uz.pdp.models.auth.User;
import uz.pdp.utils.Color;
import uz.pdp.utils.Print;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDao implements BaseDao<User> {
    private final Type userType = new TypeToken<List<User>>() {}.getType();
    private final String path = Urls.USERJSON.getUrl();
    private static UserDao instance;

    private UserDao() {
    }

    public List<User> get(String url){
        List<User> userList = new ArrayList<>();
        try (FileReader reader = new FileReader(url);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String jsonString = bufferedReader.lines().collect(Collectors.joining());
            userList = new Gson().fromJson(jsonString, userType);
        } catch (IOException e) {
            Print.println(Color.RED);
        }
        return userList;
    }
    public void set(List<User> list,String url){
        try (FileWriter writer = new FileWriter(url);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            String jsonData = new Gson().toJson(list);
            bufferedWriter.write(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(User obj) {
        List<User> list = get(path);
        list.add(obj);
        set(list, path);
    }

    @Override
    public void delete(int index) {
        List<User> list = get(path);
        list.remove(index);
        set(list, path);
    }

    @Override
    public List<User> list() {
        return get(path);
    }

    @Override
    public void reset(int index, User obj) {
        List<User> list = get(path);
        list.set(index, obj);
        set(list, path);
    }

    public void block(int index){
        block(index, Status.BLOCKED);
    }
    public void block(int index, Status status){
        List<User> list = get(path);
        list.get(index).setStatus(status);
        set(list, path);

    }

    public int findUserForLogin(String name, String Pass) {
        List<User> list = get(path);
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            if (user.getName().equalsIgnoreCase(name) && user.getPassword().equals(Pass)) {
                SessionDao.sessionUser = user;

                return i;
            }
        }
        return -1;
    }

    public int findByUsername(String name){
        List<User> list = get(path);
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            if (user.getName().equalsIgnoreCase(name)){
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return get(path).size();
    }

    public int findFilialMan(String bankId,String filialId,String name){
        List<User> list = get(path);
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            if (user.getBankId().equals(bankId) && user.getBranchId().equals(filialId)
                    && user.getName().equalsIgnoreCase(name)) {
                SessionDao.sessionManager=user;
                return i;
            }
        }
        return -1;
    }

    public static UserDao getInstance() {
        if (Objects.isNull(instance)) instance = new UserDao();
        return instance;
    }

}
