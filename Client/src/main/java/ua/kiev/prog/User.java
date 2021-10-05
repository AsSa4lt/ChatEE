package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class User {
    private String login;
    private String password;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String toJSON() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }
    public static User fromJSON(String s) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(s, User.class);
    }


    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
    public int sendcheckuser() throws IOException {
        URL obj = new URL(Utils.getURL() + "/sendcheckuser");
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            String json = toJSON();
            os.write(json.getBytes(StandardCharsets.UTF_8));
            return conn.getResponseCode();
        }
    }

}