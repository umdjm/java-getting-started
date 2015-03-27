package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.UserManager;
import businessObjects.User;

@WebServlet(name = "MyServlet", urlPatterns = {"/users/*"})
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getRequestURI().endsWith("/add")){
                addUser(req,resp);
            } else {
                showUsers(req,resp);
            }
        }
        catch(Exception e){
            resp.getWriter().print("Exception: " + getStackTrace(e));
        }
    }

    private void showUsers(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        for (User user : UserManager.list()) {
            resp.getWriter().print("id: " + user.getId() + ", username: " + user.getUsername() + "\n");
        }
    }

    private void addUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = new User();
        UserManager.generateUserName(user);
        UserManager.insert(user);

        resp.getWriter().print("Added user {id: " + user.getId() + ", username: " + user.getUsername() + "}");
    }

    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}