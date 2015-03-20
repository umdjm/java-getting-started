import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import dao.UserManager;
import businessObjects.User;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;
import java.sql.*;

public class Main extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    if (req.getRequestURI().endsWith("/add")){
        addUser(req,resp);
    } else {
        showUsers(req,resp);
    }
  }

  private void showUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      try {
          for (User user : UserManager.list()) {
              resp.getWriter().print("id: " + user.getId() + ", username: " + user.getUsername());
          }
      }
      catch(Exception e){
          resp.getWriter().print("Exception: " + e.toString());
      }
  }

  private void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      resp.getWriter().print("Not yet built");
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
