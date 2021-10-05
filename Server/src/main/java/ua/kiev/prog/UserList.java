package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.LinkedList;
import java.util.List;

public class UserList {
    private static final UserList userList = new UserList();

    private final Gson gson;
    private final List<User> listuser = new LinkedList<>();

    public static UserList getInstance() {
        return userList;
    }

    private UserList() {
        gson = new GsonBuilder().create();
    }

    public synchronized void add (User user){
        listuser.add(user);
    }
    public synchronized String toJSON () {
        return gson.toJson(listuser);
    }
    public synchronized List<User> getListuser(){
        return listuser;
    }


}