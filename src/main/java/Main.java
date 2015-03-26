import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import dao.UserManager;
import businessObjects.User;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.UUID;


public class Main extends HttpServlet {
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
          resp.getWriter().print("id: " + user.getId() + ", username: " + user.getUsername());
      }
  }

  private void addUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
      User u = new User();
      UserManager.generateUserName(u);
      UserManager.insert(u);
  }

    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

  public static void main(String[] args) throws Exception {
    Server server = new Server(Integer.valueOf(System.getenv("PORT")));
    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    server.setHandler(context);
    context.addServlet(new ServletHolder(new Main()),"/*");
    server.start();
    server.join();
  }
}
