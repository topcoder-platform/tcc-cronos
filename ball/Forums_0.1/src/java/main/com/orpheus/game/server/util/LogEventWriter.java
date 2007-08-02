/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import javax.servlet.ServletContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * <p>A separate thread to be executed in the background. Such thread is responsible for checking the queue of events
 * to be logged to target database periodically and move the events from the queue to target database. Such events could
 * be used for building the statistics reports.</p>
 *
 * @author isv
 * @version 1.0
 */
public class LogEventWriter extends Thread {

    /**
     * <p>Represents the sleep interval, in milliseconds, which determines how often this thread checks the event queue
     * and writes the events to database.</p>
     */
    private final long sleepInterval;

    /**
     * <p>A <code>ServletContext</code> providing the details for servlet context.</p>
     */
    private final ServletContext context;

    /**
     * <p>A <code>DBConnectionFactory</code> to be used for obtaining connections to target database.</p>
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>Represents the flag (used to stop the thread) that is raised to signal the thread to stop execution. The flag
     * value is the single element of the array, and if set to <code>true</code> then the thread will stop. Thread
     * safety demands that all reads and updates of the flag value be synchronized on a common object; the designated
     * object for this purpose is the array object itself.</p>
     */
    private final boolean[] stopped = new boolean[] { false };

    /**
     * <p>A <code>Map</code> mapping the <code>String</code> name of request parameter to an <code>Integer</code></p>
     */
    private final Map parameterNamesMapping;

    /**
     * <p>Constructs new <code>LogEventWriter</code> instance.</p>
     *
     * @param sleepInterval a <code>long</code> providing the interval between the iterations of this thread.
     * @param context a <code>ServletContext</code> providing the current servlet context. 
     * @param connectionFactory a <code>DBConnectionFactory</code> to be used for obtaining connectioons to target
     *        database. 
     * @param params a <code>Map</code> mapping the parameter names to parameter type IDs.
     */
    public LogEventWriter(long sleepInterval, ServletContext context, DBConnectionFactory connectionFactory,
                          Map params) {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        if (connectionFactory == null) {
            throw new IllegalArgumentException("The parameter [connectionFactory] is NULL");
        }
        this.sleepInterval = sleepInterval;
        this.context = context;
        this.connectionFactory = connectionFactory;
        this.parameterNamesMapping = params;
    }

    /**
     * <p>Signals the thread to stop by raising the stop flag and interrupting this thread.</p>
     */
    public void stopWriter() {
        synchronized(this.stopped){
            this.stopped[0] = true;
        }
        this.interrupt();
    }

    /**
     * <p>Executes a single iteration of this thread. In this iteration the thread will get all events collected in the
     * queue so far and log them to target DB.</p>
     */
    public void run() {
        System.err.println("LogEventWriter started");

        // it will not stop until the manager stops it
        for (;;) {
            try {
                Thread.sleep(this.sleepInterval);
            } catch (InterruptedException e) {
                //ignore
            }
            synchronized (this.stopped) {
                if (this.stopped[0]) {
                    break;
                }
            }

            System.err.println("LogEventWriter is awaken and starts writting log events to DB ...");

            Connection connection = null;
            PreparedStatement ps1 = null;
            PreparedStatement ps2 = null;
            PreparedStatement ps3 = null;
            PreparedStatement ps4 = null;
            ResultSet rs = null;

            Collection queue = (Collection) this.context.getAttribute("LogEventQueue");
            LinkedList badEvents = new LinkedList();
            LinkedList localQueue = new LinkedList();
            try {
                // Copy the events from global queue to local queue in order to avoild locking on global queue while
                // writting the existing events to DB
                synchronized (queue) {
                    localQueue.addAll(queue);
                    queue.clear();
                }
                System.out.println("LogEventWriter will move " + localQueue.size() + " events to database");
                if (localQueue.isEmpty()) {
                    continue;
                }
                // Connect to DB and prepare statements to be used throughout the cycle for each event
                connection = this.connectionFactory.createConnection();
                connection.setAutoCommit(false);

                ps1 = connection.prepareStatement("INSERT INTO log_event (type_id, session_id, time) "
                                                 + "VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
                ps2 = connection.prepareStatement("INSERT INTO log_event_parameter (event_id, param_id, value) "
                                                  + "VALUES (?,?,?)");
                ps3 = connection.prepareStatement("SELECT id FROM session WHERE user_id = ? "
                                                  + "AND session_id = ? AND ip_address = ?");
                ps4 = connection.prepareStatement("INSERT INTO session (user_id, session_id, ip_address) "
                                                 + "VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
                // Iterate over all events collected so far and move them to DB
                LogEvent event;
                Map sessionIdsCache = new HashMap();
                Iterator iterator = localQueue.iterator();
                while (iterator.hasNext()) {
                    event = (LogEvent) iterator.next();
                    // All exceptions occurring while logging a single event are caught and logged to prevent the
                    // subsequent events from the queue from logging to DB.
                    try {
                        // Get the ID of related session and if session is not recorded in DB yet then record session
                        //  to DB
                        long sessionId;
                        String sessionCacheKey
                            = event.getUserId() + "," + event.getSessionId() + "," + event.getIpAddress();
                        if (!sessionIdsCache.containsKey(sessionCacheKey)) {
                            ps3.setObject(1, event.getUserId());
                            ps3.setString(2, event.getSessionId());
                            ps3.setString(3, event.getIpAddress());
                            rs = ps3.executeQuery();
                            if (rs.next()) {
                                sessionId = rs.getLong(1);
                                rs.close();
                            } else {
                                rs.close();
                                ps4.setObject(1, event.getUserId());
                                ps4.setString(2, event.getSessionId());
                                ps4.setString(3, event.getIpAddress());
                                ps4.executeUpdate();
                                rs = ps4.getGeneratedKeys();
                                rs.next();
                                sessionId = rs.getLong(1);
                                sessionIdsCache.put(sessionCacheKey, new Long(sessionId));
                                rs.close();
                            }
                        } else {
                            Long sid = (Long) sessionIdsCache.get(sessionCacheKey);
                            sessionId = sid.longValue();
                        }

                        // Insert event and get the generated event ID
                        ps1.setInt(1, event.getEventTypeId());
                        ps1.setLong(2, sessionId);
                        ps1.setTimestamp(3, new Timestamp(event.getTime().getTime()));
                        ps1.executeUpdate();
                        rs = ps1.getGeneratedKeys();
                        rs.next();
                        long eventId = rs.getLong(1);
                        rs.close();
                        // Insert event parameters
                        Map parameters = event.getParameters();
                        Iterator paramIterator = parameters.entrySet().iterator();
                        Map.Entry entry;
                        while (paramIterator.hasNext()) {
                            entry = (Map.Entry) paramIterator.next();
                            String paramName = (String) entry.getKey();
                            Integer paramTypeId = (Integer) this.parameterNamesMapping.get(paramName);
                            String[] paramValues = (String[]) entry.getValue();
                            if (paramValues.length > 0) {
                                for (int i = 0; i < paramValues.length; i++) {
                                    ps2.setLong(1, eventId);
                                    ps2.setInt(2, paramTypeId.intValue());
                                    ps2.setString(3, paramValues[i]);
                                }
                                ps2.addBatch();
                            }
                        }
                        ps2.executeBatch();
                        connection.commit();
                    } catch (Exception e) {
                        if (event != null) {
                            badEvents.add(event);
                        }
                        System.err.println("LogEventWriter got an exception while writting log events to DB (The "
                                            + "thread is not interrupted). The problematic event is : " + event
                                            + ". The error is : " + e.getMessage());
                        e.printStackTrace(System.err);
                    }
                    // Remove the event from queue anyway regardless the status of it's writting to DB
                    iterator.remove();
                }
                sessionIdsCache.clear();
            } catch (Exception e) {
                // Log exception details
                System.err.println("LogEventWriter got an exception while writting log events to DB (The thread is not"
                                   + " interrupted): " + e.getMessage());
                e.printStackTrace(System.err);
                // Put the unwritten events back to global queue to try to write them at next iteration
                synchronized (queue) {
                    System.out.println("LogEventWriter has moved " + localQueue.size() + " events back to global queue");
                    queue.addAll(localQueue);
                    localQueue.clear();
                }
                // Rollback the last transaction
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException e1) {
                        System.err.println("LogEventWriter could not rollback the latest transaction. "
                                           + e1.getMessage());
                    }
                }
            } finally {
                close(rs);
                close(ps4);
                close(ps3);
                close(ps2);
                close(ps1);
                close(connection);
            }
            // If there were any bad events detected then put them to global queue to give another chance to be written
            // to DB
            if (!badEvents.isEmpty()) {
                synchronized (queue) {
                    System.out.println("LogEventWriter has moved " + badEvents.size() + " problematic events back to "
                                       + "global queue");
                    queue.addAll(badEvents);
                    badEvents.clear();
                }
            }
            System.err.println("LogEventWriter has finished writting log events to DB. "
                               + "The thread will sleep for " + this.sleepInterval + " ms");
        }
        System.err.println("LogEventWriter stopped");
    }

    /**
     * <p>Try to close the Resultset object if it is not null. Returns silently if any SQL error occurs.</p>
     *
     * @param rs the ResultSet object.
     */
    private static void close(ResultSet rs) {
        if (rs != null) {
            try {
                Statement stmt = rs.getStatement();

                rs.close();
                close(stmt);
            } catch (SQLException e) {
                // silently go on
            }
        }
    }

    /**
     * <p>Try to close the statement object if it is not null. Returns silently if any SQL error occurs.</p>
     *
     * @param stmt the statement objec
     */
    private static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // silently go on
            }
        }
    }

    /**
     * <p>Try to close the Connection object if it is not null. Returns silently if any SQL error occurs.</p>
     *
     * @param conn the Connection object.
     */
    private static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // silently go on
            }
        }
    }
}
