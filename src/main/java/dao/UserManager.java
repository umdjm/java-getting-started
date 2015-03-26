package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import businessObjects.User;

public class UserManager {

    public static List<User> list() throws Exception {
        List<User> users = new ArrayList<User>();
        UserDataAccess userDA = new UserDataAccess();
        ResultSet rs = userDA.selectAll();
        while(rs.next()){
            User user = parseResultSet(rs);
            users.add(user);
        }
        return users;
    }

    public static void generateUserName(User u){
        String random = UUID.randomUUID().toString();
        u.setUsername(random);
    }

    public static User retrieve(Integer id) throws Exception {
        User user = null;
        UserDataAccess userDA = new UserDataAccess();
        ResultSet rs = userDA.select(id);
        if (rs.next()) {
            user = parseResultSet(rs);
        }
        return user;
    }

    public static void update(User user) throws Exception {
        UserDataAccess userDA = new UserDataAccess();
        userDA.update(user.getId(), user.getUsername());
    }

    public static void insert(User user) throws Exception {
        UserDataAccess userDA = new UserDataAccess();
        Integer id = userDA.insert(user.getUsername());
        user.setId(id);
    }

    private static User parseResultSet(ResultSet rs) throws Exception{
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        return user;
    }
}

