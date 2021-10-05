package ua.kiev.prog;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class UserServlet extends HttpServlet {
    private UserList userList = UserList.getInstance();
    private List <User> users = userList.getListuser();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        byte[] buf = requestBodyToArray(req);
        String bufStr = new String(buf, StandardCharsets.UTF_8);

        User user = User.fromJSON(bufStr);
        if (user != null) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getLogin().equals(user.getLogin()) && users.get(i).getPassword().equals(user.getPassword())) {
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    return;
                } else if (users.get(i).getLogin().equals(user.getLogin()) && (!users.get(i).getPassword().equals(user.getPassword()))) {
                    resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                    return;
                }
            }
            userList.add(user);
        }else
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    private byte[] requestBodyToArray(HttpServletRequest req) throws IOException {
        InputStream is = req.getInputStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);//запускаем цикл read в buf пытается считать столько сколько влезет
            if (r > 0)
                bos.write(buf, 0, r);// если хоть что то прочитанное то r вернет больше нуля через bos.write записываем прочитанные байты в ByteArrayOutputStream
        } while (r != -1);//читаем пока рид не вернет -1

        return bos.toByteArray();//когда все вычитали toByteArray возвращает все что мы накопили в ByteArrayOutputStream  в виде одного массива
    }
}
