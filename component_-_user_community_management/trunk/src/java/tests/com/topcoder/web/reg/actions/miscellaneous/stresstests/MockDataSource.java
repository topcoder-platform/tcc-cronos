/**
 * 
 */
package com.topcoder.web.reg.actions.miscellaneous.stresstests;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.topcoder.shared.util.DBMS;

/**
 * @author moon.river
 * @version 1.0
 */
public class MockDataSource implements DataSource {

    /**
     * @return
     * @throws SQLException
     */
    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return new PrintWriter(System.out);
    }

    /**
     * @return
     * @throws SQLException
     */
    @Override
    public int getLoginTimeout() throws SQLException {
        return 10;
    }

    /**
     * @param arg0
     * @throws SQLException
     */
    @Override
    public void setLogWriter(PrintWriter arg0) throws SQLException {
    }

    /**
     * @param arg0
     * @throws SQLException
     */
    @Override
    public void setLoginTimeout(int arg0) throws SQLException {
    }

    /**
     * @param arg0
     * @return
     * @throws SQLException
     */
    @Override
    public boolean isWrapperFor(Class<?> arg0) throws SQLException {
        return false;
    }

    /**
     * @param <T>
     * @param arg0
     * @return
     * @throws SQLException
     */
    @Override
    public <T> T unwrap(Class<T> arg0) throws SQLException {
        return null;
    }

    /**
     * @return
     * @throws SQLException
     */
    @Override
    public Connection getConnection() throws SQLException {
        Properties props = new Properties();
        try {
            props.load(new FileReader("test_files/stress/stress.properties"));
            Class.forName("com.informix.jdbc.IfxDriver");
            Connection conn = DriverManager.getConnection(props.getProperty("jdbc.url"),
                            props.getProperty("jdbc.user"), props.getProperty("jdbc.password"));
            return conn;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param arg0
     * @param arg1
     * @return
     * @throws SQLException
     */
    @Override
    public Connection getConnection(String arg0, String arg1) throws SQLException {
        Properties props = new Properties();
        try {
            props.load(new FileReader("test_files/stress/stress.properties"));
            Class.forName("com.informix.jdbc.IfxDriver");
            Connection conn = DriverManager.getConnection(props.getProperty("jdbc.url"),
                            props.getProperty("jdbc.user"), props.getProperty("jdbc.password"));
            return conn;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
