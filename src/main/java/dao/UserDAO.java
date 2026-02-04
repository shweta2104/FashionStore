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

    // ✅ LOGIN METHOD
    public User login(String loginId, String password) {

        User user = null;

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                "SELECT * FROM user_master WHERE login_id=? AND password=?";

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

                // ✅ Extra Fields
                user.setAddress(rs.getString("address"));
                user.setCity(rs.getString("city"));
                user.setState(rs.getString("state"));
                user.setCountry(rs.getString("country"));
                user.setPin(rs.getString("pin"));
                user.setDob(rs.getString("dob"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    // ✅ REGISTER METHOD (UPDATED)
    public boolean register(User user) {

        boolean status = false;

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                "INSERT INTO user_master " +
                "(username, login_id, password, email, phone, address, city, state, country, pin, dob) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getLoginId());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhone());

            // ✅ New Fields
            ps.setString(6, user.getAddress());
            ps.setString(7, user.getCity());
            ps.setString(8, user.getState());
            ps.setString(9, user.getCountry());
            ps.setString(10, user.getPin());
            ps.setString(11, user.getDob());

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    // ❓ FORGOT PASSWORD
    public String getPassword(String loginId, String email) {

        String password = null;

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                "SELECT password FROM user_master WHERE login_id=? AND email=?";

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

    // 🖥️ Record Session ID (Session Listener)
    public void recordSession(String sessionId) {

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                "INSERT INTO session_master(session_id, created_at) VALUES (?, NOW())";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, sessionId);

            ps.executeUpdate();

            System.out.println("Session recorded: " + sessionId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
