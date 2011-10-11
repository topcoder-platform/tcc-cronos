/*
 * Copyright (C) 2006-2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.failuretests.mock;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <p>
 * A mock implementation of {@link Connection} class to be used for testing.
 * Overrides the protected methods declared by a super-class. The overridden methods are declared with
 * package private access so only the test cases could invoke them. The overridden methods simply call
 * the corresponding method of a super-class.
 * <p>
 * Version 1.6.2 (Online Review Phases) Change notes:
 * <ol>
 * <li>unimplemented methods added.</li>
 * <li>change Map into HashMap&lt;T,T></li>
 * <li>method that uses Map, now using HashMap&lt;T,T></li>
 * <li>add common codes into method checkAndThrowException.</li>
 * </ol>
 * </p>
 *
 * @author isv, moon.river, TMALBONPH
 * @version 1.6.2
 * @since 1.3
 */
public class MockConnection implements Connection {

    /**
     * <p>A <code>Map</code> mapping the <code>String</code> method signatures to <code>Map</code>s mapping
     * the <code>String</code> names of the arguments to <code>Object</code>s representing the values of  arguments
     * which have been provided by the caller of the method.</p>
     */
    static HashMap<String, Object> methodArguments = new HashMap<String, Object>();

    /**
     * <p>A <code>Map</code> mapping the <code>String</code> method signatures to <code>Exception</code>s
     * to be thrown by methods.</p>
     */
    private static HashMap<String, Throwable> throwExceptions = new HashMap<String, Throwable>();

    /**
     * <p>A <code>Map</code> mapping the <code>String</code> method signatures to <code>Object</code>s to be
     * returned by methods.</p>
     */
    private static HashMap<String, Object> methodResults = new HashMap<String, Object>();

    /**
     * <p>A <code>Throwable</code> representing the exception to be thrown from any method of the mock class.</p>
     */
    private static Throwable globalException = null;

    /**
     * Default constructor.
     */
    public MockConnection() {
        // does nothing.
    }

    /**
     * <p>Common method to check if method is to throw the given exception.</p>
     * <p>
     * Version 1.6.2 (Online Review Phases) Change notes:
     * <ol>
     * <li>move all common code checks in here.</li>
     * <li>added to make this class small in size.</li>
     * </ol>
     * </p>
     *
     * @param exception the Exception to check.
     *
     * @throws SQLException If parameter exception is not <code>null</code>
     * and an instance of SQLException.
     * @throws RuntimeException If parameter exception is not <code>null</code>
     * and not an instance of SQLException.
     */
    private static void checkAndThrowException(Throwable exception) throws SQLException {
        if (exception != null) {
            if (exception instanceof SQLException) {
                throw (SQLException) exception;
            } else {
                throw new RuntimeException("The test may not be configured properly", exception);
            }
        }
    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#getHoldability()
     * @throws SQLException
     */
    public int getHoldability() throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "getHoldability";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return ((Integer) MockConnection.methodResults.get(methodName)).intValue();

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#getTransactionIsolation()
     * @throws SQLException
     */
    public int getTransactionIsolation() throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "getTransactionIsolation";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return ((Integer) MockConnection.methodResults.get(methodName)).intValue();

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#clearWarnings()
     * @throws SQLException
     */
    public void clearWarnings() throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "clearWarnings";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#close()
     * @throws SQLException
     */
    public void close() throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "close";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#commit()
     * @throws SQLException
     */
    public void commit() throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "commit";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#rollback()
     * @throws SQLException
     */
    public void rollback() throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "rollback";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#getAutoCommit()
     * @throws SQLException
     */
    public boolean getAutoCommit() throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "getAutoCommit";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return ((Boolean) MockConnection.methodResults.get(methodName)).booleanValue();

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#isClosed()
     * @throws SQLException
     */
    public boolean isClosed() throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "isClosed";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return ((Boolean) MockConnection.methodResults.get(methodName)).booleanValue();

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#isReadOnly()
     * @throws SQLException
     */
    public boolean isReadOnly() throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "isReadOnly";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return ((Boolean) MockConnection.methodResults.get(methodName)).booleanValue();

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#setHoldability(int)
     * @throws SQLException
     */
    public void setHoldability(int int0) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "setHoldability_int";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Integer(int0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#setTransactionIsolation(int)
     * @throws SQLException
     */
    public void setTransactionIsolation(int int0) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "setTransactionIsolation_int";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Integer(int0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#setAutoCommit(boolean)
     * @throws SQLException
     */
    public void setAutoCommit(boolean boolean0) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "setAutoCommit_boolean";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", Boolean.valueOf(boolean0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#setReadOnly(boolean)
     * @throws SQLException
     */
    public void setReadOnly(boolean boolean0) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "setReadOnly_boolean";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", Boolean.valueOf(boolean0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#getCatalog()
     * @throws SQLException
     */
    public String getCatalog() throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "getCatalog";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (String) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#setCatalog(String)
     * @throws SQLException
     */
    public void setCatalog(String string0) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "setCatalog_String";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#getMetaData()
     * @throws SQLException
     */
    public DatabaseMetaData getMetaData() throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "getMetaData";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (DatabaseMetaData) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#getWarnings()
     * @throws SQLException
     */
    public SQLWarning getWarnings() throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "getWarnings";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (SQLWarning) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#setSavepoint()
     * @throws SQLException
     */
    public Savepoint setSavepoint() throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "setSavepoint";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Savepoint) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#releaseSavepoint(Savepoint)
     * @throws SQLException
     */
    public void releaseSavepoint(Savepoint savepoint0) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "releaseSavepoint_Savepoint";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", savepoint0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#rollback(Savepoint)
     * @throws SQLException
     */
    public void rollback(Savepoint savepoint0) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "rollback_Savepoint";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", savepoint0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#createStatement()
     * @throws SQLException
     */
    public Statement createStatement() throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "createStatement";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Statement) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#createStatement(int, int)
     * @throws SQLException
     */
    public Statement createStatement(int int0, int int1) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "createStatement_int_int";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Integer(int0));
        arguments.put("2", new Integer(int1));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Statement) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#createStatement(int, int, int)
     * @throws SQLException
     */
    public Statement createStatement(int int0, int int1, int int2) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "createStatement_int_int_int";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Integer(int0));
        arguments.put("2", new Integer(int1));
        arguments.put("3", new Integer(int2));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Statement) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#getTypeMap()
     * @throws SQLException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Map getTypeMap() throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "getTypeMap";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Map) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#nativeSQL(String)
     * @throws SQLException
     */
    public String nativeSQL(String string0) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "nativeSQL_String";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (String) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#prepareCall(String)
     * @throws SQLException
     */
    public CallableStatement prepareCall(String string0) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "prepareCall_String";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (CallableStatement) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#prepareCall(String, int, int)
     * @throws SQLException
     */
    public CallableStatement prepareCall(String string0, int int0, int int1) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "prepareCall_String_int_int";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", string0);
        arguments.put("2", new Integer(int0));
        arguments.put("3", new Integer(int1));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (CallableStatement) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#prepareCall(String, int, int, int)
     * @throws SQLException
     */
    public CallableStatement prepareCall(String string0, int int0, int int1, int int2) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "prepareCall_String_int_int_int";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", string0);
        arguments.put("2", new Integer(int0));
        arguments.put("3", new Integer(int1));
        arguments.put("4", new Integer(int2));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (CallableStatement) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#prepareStatement(String)
     * @throws SQLException
     */
    public PreparedStatement prepareStatement(String string0) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "prepareStatement_String";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (PreparedStatement) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#prepareStatement(String, int)
     * @throws SQLException
     */
    public PreparedStatement prepareStatement(String string0, int int0) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "prepareStatement_String_int";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", string0);
        arguments.put("2", new Integer(int0));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (PreparedStatement) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#prepareStatement(String, int, int)
     * @throws SQLException
     */
    public PreparedStatement prepareStatement(String string0, int int0, int int1) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "prepareStatement_String_int_int";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", string0);
        arguments.put("2", new Integer(int0));
        arguments.put("3", new Integer(int1));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (PreparedStatement) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#prepareStatement(String, int, int, int)
     * @throws SQLException
     */
    public PreparedStatement prepareStatement(String string0, int int0, int int1, int int2) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "prepareStatement_String_int_int_int";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", string0);
        arguments.put("2", new Integer(int0));
        arguments.put("3", new Integer(int1));
        arguments.put("4", new Integer(int2));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (PreparedStatement) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#prepareStatement(String, int[])
     * @throws SQLException
     */
    public PreparedStatement prepareStatement(String string0, int[] intA0) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "prepareStatement_String_int[]";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", string0);
        arguments.put("2", intA0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (PreparedStatement) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#setSavepoint(String)
     * @throws SQLException
     */
    public Savepoint setSavepoint(String string0) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "setSavepoint_String";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", string0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Savepoint) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @see Connection#prepareStatement(String, String[])
     * @throws SQLException
     */
    public PreparedStatement prepareStatement(String string0, String[] stringA0) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "prepareStatement_String_String[]";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", string0);
        arguments.put("2", stringA0);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (PreparedStatement) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>Sets the result to be returned by the specified method.</p>
     *
     * @param methodSignature a <code>String</code> uniquely distinguishing the target method among other methods
     *        declared by the implemented interface/class.
     * @param result an <code>Object</code> representing the result to be returned by specified method.
     */
    public static void setMethodResult(String methodSignature, Object result) {
        MockConnection.methodResults.put(methodSignature, result);
    }

    /**
     * <p>Gets the value of the specified argument which has been passed to the specified method by the caller.</p>
     *
     * @param  methodSignature a <code>String</code> uniquely distinguishing the target method among other methods
     * @param  argumentName a <code>String</code> providing the name of the argument to get the value for.
     * @return an <code>Object</code> (including <code>null</code>) providing the value of the specified argument
     *         which has been supplied by the caller of the specified method.
     * @throws IllegalArgumentException if the specified argument does not exist.
     */
    public static Object getMethodArgument(String methodSignature, String argumentName) {
        @SuppressWarnings("rawtypes")
        Map arguments = (Map) MockConnection.methodArguments.get(methodSignature);
        if (!arguments.containsKey(argumentName)) {
            throw new IllegalArgumentException("The argument name " + argumentName + " is unknown.");
        }
        return arguments.get(argumentName);
    }

    /**
     * <pChecks if the specified method has been called during the test by the caller.</p>
     *
     * @param  methodSignature a <code>String</code> uniquely distinguishing the target method among other methods
     * @return <code>true</code> if specified method was called; <code>false</code> otherwise.
     */
    public static boolean wasMethodCalled(String methodSignature) {
        return methodArguments.containsKey(methodSignature);
    }

    /**
     * <p>Gets the values of the arguments which have been passed to the specified method by the caller.</p>
     *
     * @param  methodSignature a <code>String</code> uniquely distinguishing the target method among other methods
     * @return a <code>List</code> of <code>Map</code> providing the values of the arguments on each call.
     *         which has been supplied by the caller of the specified method.
     */
    @SuppressWarnings("rawtypes")
    public static List getMethodArguments(String methodSignature) {
        return (List) MockConnection.methodArguments.get(methodSignature);
    }

    /**
     * <p>Sets the exception to be thrown when the specified method is called.</p>
     *
     * @param methodSignature a <code>String</code> uniquely distinguishing the target method among other methods
     * @param exception a <code>Throwable</code> representing the exception to be thrown when the specified method is
     *        called. If this argument is <code>null</code> then no exception will be thrown.
     */
    public static void throwException(String methodSignature, Throwable exception) {
        if (exception != null) {
            MockConnection.throwExceptions.put(methodSignature, exception);
        } else {
            MockConnection.throwExceptions.remove(methodSignature);
        }
    }

    /**
     * <p>Sets the exception to be thrown when the specified method is called.</p>
     *
     * @param exception a <code>Throwable</code> representing the exception to be thrown whenever any method is
     *        called. If this argument is <code>null</code> then no exception will be thrown.
     */
    public static void throwGlobalException(Throwable exception) {
        MockConnection.globalException = exception;
    }

    /**
     * <p>Releases the state of <code>MockConnection</code> so all collected method arguments,
     * configured method results and exceptions are lost.</p>
     */
    public static void releaseState() {
        MockConnection.methodArguments.clear();
        MockConnection.methodResults.clear();
        MockConnection.throwExceptions.clear();
        MockConnection.globalException = null;
    }

    /**
     * <p>Initializes the initial state for all created instances of <code>MockConnection</code> class.</p>
     */
    public static void init() {
    }

// ------------------------------------------------------------------ additional methods 1.6.2

    /**
     * <p>
     * Implementation of JDBC 4.0's Wrapper interface.
     * </p>
     * <p>
     * http://static.springsource.org/spring/docs/3.0.x
     * /javadoc-api/org/springframework/jdbc/datasource/AbstractDataSource.html
     * </p>
     * @param iface the Interface
     * @return the accessible method.
     * @see java.sql.Wrapper#unwrap(java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface == null) {
            throw new SQLException("Interface argument must not be null");
        }
        if (!Connection.class.equals(iface)) {
            throw new SQLException("Connection of type ["
                + getClass().getName()
                + "] can only be unwrapped as [java.sql.Connection], not as ["
                + iface.getName());
        }
        return (T) this;
    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param iface the Class<?>.
     *
     * @return the boolean.
     *
     * @since 1.6.2
     * @see Connection#isWrapperFor(Class<?>)
     * @throws SQLException If any persistence error occur.
     */
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "isWrapperFor_Class<?>";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", iface);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return ((Boolean) MockConnection.methodResults.get(methodName)).booleanValue();

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param map the Map<String, Class<?>>.
     *
     * @since 1.6.2
     * @see Connection#setTypeMap(Map<String, Class<?>>)
     * @throws SQLException If any persistence error occur.
     */
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "setTypeMap_Map<String, Class<?>>";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", map);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @return the Clob.
     *
     * @since 1.6.2
     * @see Connection#createClob()
     * @throws SQLException If any persistence error occur.
     */
    public Clob createClob() throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "createClob";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Clob) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @return the Blob.
     *
     * @since 1.6.2
     * @see Connection#createBlob()
     * @throws SQLException If any persistence error occur.
     */
    public Blob createBlob() throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "createBlob";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Blob) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @return the NClob.
     *
     * @since 1.6.2
     * @see Connection#createNClob()
     * @throws SQLException If any persistence error occur.
     */
    public NClob createNClob() throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "createNClob";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (NClob) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @return the SQLXML.
     *
     * @since 1.6.2
     * @see Connection#createSQLXML()
     * @throws SQLException If any persistence error occur.
     */
    public SQLXML createSQLXML() throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "createSQLXML";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (SQLXML) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param timeout the int.
     *
     * @return the boolean.
     *
     * @since 1.6.2
     * @see Connection#isValid(int)
     * @throws SQLException If any persistence error occur.
     */
    public boolean isValid(int timeout) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "isValid_int";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", new Integer(timeout));
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return ((Boolean) MockConnection.methodResults.get(methodName)).booleanValue();

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param name the String.
     * @param value the String.
     *
     * @since 1.6.2
     * @see Connection#setClientInfo(String, String)
     * @throws SQLClientInfoException If any persistence error occur.
     */
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        String methodName = "setClientInfo_String_String";

        try {
            checkAndThrowException(MockConnection.globalException);
            Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
            if (exception != null) {
                checkAndThrowException(exception);
            }
        } catch (SQLException ex) {
            throw new SQLClientInfoException (ex.getMessage(), null);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", name);
        arguments.put("2", value);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param properties the Properties.
     *
     * @since 1.6.2
     * @see Connection#setClientInfo(Properties)
     * @throws SQLClientInfoException If any persistence error occur.
     */
    public void setClientInfo(Properties properties) throws SQLClientInfoException {

        String methodName = "setClientInfo_Properties";

        try {
            checkAndThrowException(MockConnection.globalException);
            Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
            if (exception != null) {
                checkAndThrowException(exception);
            }
        } catch (SQLException ex) {
            throw new SQLClientInfoException (ex.getMessage(), null);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", properties);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param name the String.
     *
     * @return the String.
     *
     * @since 1.6.2
     * @see Connection#getClientInfo(String)
     * @throws SQLException If any persistence error occur.
     */
    public String getClientInfo(String name) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "getClientInfo_String";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", name);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (String) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @return the Properties.
     *
     * @since 1.6.2
     * @see Connection#getClientInfo()
     * @throws SQLException If any persistence error occur.
     */
    public Properties getClientInfo() throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "getClientInfo";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Properties) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param typeName the String.
     * @param elements the Object[].
     *
     * @return the Array.
     *
     * @since 1.6.2
     * @see Connection#createArrayOf(String, Object[])
     * @throws SQLException If any persistence error occur.
     */
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "createArrayOf_String_Object[]";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", typeName);
        arguments.put("2", elements);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Array) MockConnection.methodResults.get(methodName);

    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through
     * {@link #setMethodResult(String, Object)} method.</p>
     *
     * @param typeName the String.
     * @param attributes the Object[].
     *
     * @return the Struct.
     *
     * @since 1.6.2
     * @see Connection#createStruct(String, Object[])
     * @throws SQLException If any persistence error occur.
     */
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        checkAndThrowException(MockConnection.globalException);

        String methodName = "createStruct_String_Object[]";

        Throwable exception = (Throwable) MockConnection.throwExceptions.get(methodName);
        if (exception != null) {
            checkAndThrowException(exception);
        }

        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("1", typeName);
        arguments.put("2", attributes);
        @SuppressWarnings("unchecked")
        List<Object> args = (List<Object>) MockConnection.methodArguments.get(methodName);
        if (args == null) {
            args = new ArrayList<Object>();
            MockConnection.methodArguments.put(methodName, args);
        }
        args.add(arguments);

        return (Struct) MockConnection.methodResults.get(methodName);

    }

}
