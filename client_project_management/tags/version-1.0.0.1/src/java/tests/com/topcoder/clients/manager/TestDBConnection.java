package com.topcoder.clients.manager;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestDBConnection {
    public static void main(String[] args) throws Exception {
        Class.forName("com.informix.jdbc.IfxDriver");
        Connection conn = DriverManager.getConnection(
                "jdbc:informix-sqli://LENOVO-PC:1526/tcs:INFORMIXSERVER=ol_tcs", "informix", "123456");
        if (conn != null) {
            System.out.println("DB Connected.");
        } else {
            System.out.println("Failed to connect DB.");
        }
    }
}
