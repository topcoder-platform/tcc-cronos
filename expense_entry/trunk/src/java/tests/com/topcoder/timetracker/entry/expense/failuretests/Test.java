/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

public class Test extends FailureTestBase {

    
    public void testT() throws Exception {
//        Connection con = new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE).createConnection();
//        Statement stmt = con.createStatement();
//        ResultSet rs = stmt.executeQuery("select bo from test_bool");
//        rs.next();
//        System.out.println(rs.getBoolean(1));
//        
//        rs.next();
//        System.out.println(rs.getBoolean(1));
//        
//        rs.close();
//        stmt.close();
//        con.close();
        
        
        int a = 0xfffffffe;
        String number = "7fffffff";
        
        System.out.println(a);
        long value = Long.parseLong(number, 16);
        System.out.println(value);
        System.out.println((int)value);
        
        Integer.parseInt(number, 16);
    }
}
