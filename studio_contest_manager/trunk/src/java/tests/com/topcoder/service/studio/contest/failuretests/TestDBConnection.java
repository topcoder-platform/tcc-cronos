package com.topcoder.service.studio.contest.failuretests;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestDBConnection {
    public static void main(String[] args) throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE", "system", "tcs");
        if (conn != null) {
            System.out.println("DB Connected.");
        } else {
            System.out.println("Failed to connect DB.");
        }
        conn.close();
    }
}
