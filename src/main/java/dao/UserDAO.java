package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.User;
import util.DBConnection;

/**
 *
 * @author root
 */
public class UserDAO {

    public User login(String loginId, String password) {
        User user = null;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM user_master WHERE login_id=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, loginId);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setLoginId(rs.getString("login_id"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    // 📝 Register
    public boolean register(User user) {
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO user_master(username,login_id,password,email,phone) VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getLoginId());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhone());

            status = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    // ❓ Forgot Password
    public String getPassword(String loginId, String email) {
        String password = null;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT password FROM user_master WHERE login_id=? AND email=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, loginId);
            ps.setString(2, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                password = rs.getString("password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }

    // 🖥️ Record Session ID (for SessionListener)
    public void recordSession(String sessionId) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO session_master(session_id, created_at) VALUES (?, NOW())";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, sessionId);
            ps.executeUpdate();
            System.out.println("Session recorded: " + sessionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
