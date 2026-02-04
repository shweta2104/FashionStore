package controller;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import java.sql.*;
import util.DBConnection;
import util.BirthdayEmailUtil;

@Singleton
public class BirthdayScheduler {

    @Schedule(hour="9", minute="0", second="0", persistent=false)
    public void sendBirthdayEmails() {

        System.out.println("Running Birthday Email Scheduler...");

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT name, email FROM user_master " +
                "WHERE DAY(dob)=DAY(CURDATE()) AND MONTH(dob)=MONTH(CURDATE())"
            );

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                BirthdayEmailUtil.sendBirthdayMail(
                    rs.getString("email"),
                    rs.getString("name")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
