package servlet;

import businessObjects.User;
import dao.UserManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@WebServlet("/users/*")
public class UsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getRequestURI().endsWith("/add"))
                addUser(req,resp);
            else showUsers(req, resp);
        }
        catch(Exception e){
            handleException(e, resp);
        }
    }

    private void showUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("users", UserManager.list());
        request.getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
}

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = UserManager.createUser();
        response.getWriter().print("Added user {id: " + user.getId() + ", username: " + user.getUsername() + "}");
    }

    public static void handleException(final Throwable throwable, HttpServletResponse resp) throws IOException {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        resp.getWriter().print("Exception: " + sw.getBuffer().toString());
    }
}