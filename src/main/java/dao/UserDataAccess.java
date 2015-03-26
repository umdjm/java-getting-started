package dao;
import java.sql.*;

public class UserDataAccess {
    private Connection connection;

    public UserDataAccess(){}

    public ResultSet select(Integer id) throws Exception {
        prepareDatabase();
        String query = "SELECT id, username FROM member WHERE id = ? ";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        return ps.executeQuery();
    }

    public ResultSet selectAll() throws Exception {
        prepareDatabase();
        String query = "SELECT id, username FROM member ";
        PreparedStatement ps = connection.prepareStatement(query);
        return ps.executeQuery();
    }

    public void update(Integer id, String username) throws Exception {
        prepareDatabase();
        String query = "UPDATE member SET username = ? WHERE ID = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, username);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public Integer insert(String username) throws Exception {
        Integer id = null;
        prepareDatabase();
        String query = "INSERT INTO member (username) VALUES (?)";
        PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, username);
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next()){
            id = rs.getInt(1);
        }
        return id;
    }

    private void prepareDatabase() throws Exception {
        this.connection = DataManager.getConnection();

        Statement stmt = connection.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS member (id SERIAL PRIMARY KEY, username varchar(255))");
    }
}

