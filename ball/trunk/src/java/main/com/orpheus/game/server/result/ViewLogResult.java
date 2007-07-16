/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.result;

import com.orpheus.game.GameDataConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Result;
import com.topcoder.web.frontcontroller.ResultExecutionException;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.MessageFormat;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
 * <p>A custom {@link Result} implementation to be used for writting the log entries to response as plain text.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ViewLogResult implements Result {

    /**
     * <p>A <code>DBConnectionFactory</code> to be used for establishing the connections to target database.</p>
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>Constructs new <code>ViewLogResult</code> instance initialized based on the configuration parameters
     * provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * <pre>
     *     &lt;result name=&quot;x&quot type=&quot;y&quot;&gt;
     *     &lt;/result&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public ViewLogResult(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        try {
            this.connectionFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        } catch (Exception e) {
            throw new GameDataConfigurationException("Could not instantiate DB Connection Factory", e);
        }
    }

    /**
     * <p>Generate user response. Its implementation would generally write response to the HttpServletResponse object.
     * </p>
     *
     * @param context the ActionContext object.
     * @throws IllegalArgumentException if the context is null.
     * @throws ResultExecutionException if fail to genearte the response.
     */
    public void execute(ActionContext context) throws ResultExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        HttpServletRequest request = context.getRequest();
        HttpServletResponse response = context.getResponse();
        response.setContentType("text/plain");

        String gameId = request.getParameter("gameId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String domain = request.getParameter("domain");
        String handle = request.getParameter("handle");

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            StringBuffer query = new StringBuffer();
            query.append("SELECT le.time, le.id, let.text, s.user_id, s.session_id, s.ip_address, lep.value,letp.sequence_number, u.handle " +
                           "FROM log_entry_type let " +
                           "INNER JOIN log_event le ON let.id=le.type_id " +
                           "INNER JOIN session s ON s.id=le.session_id " +
                           "LEFT JOIN any_user u ON s.user_id=u.id " +
                           "LEFT JOIN log_event_parameter lep ON le.id=lep.event_id " +
                           "LEFT JOIN log_parameter lp ON lep.param_id=lp.id " +
                           "LEFT JOIN log_entry_type_param letp ON letp.type_id=let.id AND letp.param_id=lep.param_id " +
                           "WHERE 1=1 ");
            int gameIdPos = 0;
            int domainPos = 0;
            int handlePos = 0;
            int startDatePos = 0;
            int endDatePos = 0;
            if (gameId != null && gameId.trim().length() > 0) {
                query.append("AND le.id IN (SELECT le2.id " +
                                           "FROM log_event le2 " +
                                           "INNER JOIN log_event_parameter lep2 ON le2.id=lep2.event_id " +
                                           "INNER JOIN log_parameter lp2 ON lep2.param_id=lp2.id " +
                                           "WHERE lp2.name='gameId' AND lep2.value=?) ");
                gameIdPos = 1;
            }
            if (domain != null && domain.trim().length() > 0) {
                query.append("AND le.id IN (SELECT le2.id " +
                                           "FROM log_event le2 " +
                                           "INNER JOIN log_event_parameter lep2 ON le2.id=lep2.event_id " +
                                           "INNER JOIN log_parameter lp2 ON lep2.param_id=lp2.id " +
                                           "WHERE lp2.name='domain' AND lep2.value=?) ");
                domainPos = gameIdPos + 1;
            }
            if (handle != null && handle.trim().length() > 0) {
                query.append("AND u.handle=? ");
                handlePos = Math.max(gameIdPos, domainPos) + 1;
            }
            if (startDate != null && startDate.trim().length() > 0) {
                query.append("AND le.time>=? ");
                startDatePos = Math.max(Math.max(gameIdPos, domainPos), handlePos) + 1;
            }
            if (endDate != null && endDate.trim().length() > 0) {
                query.append("AND le.time<=? ");
                endDatePos = Math.max(Math.max(Math.max(gameIdPos, domainPos), handlePos), startDatePos) + 1;
            }
            query.append("ORDER BY le.time,le.id,letp.sequence_number");
            ps = connection.prepareStatement(query.toString());
            if (gameIdPos > 0) {
                ps.setString(gameIdPos, gameId);
            }
            if (domainPos > 0) {
                ps.setString(domainPos, domain);
            }
            if (handlePos > 0) {
                ps.setString(handlePos, handle);
            }
            DateFormat df = new SimpleDateFormat(request.getParameter("dateFormat"));
            if (startDatePos > 0) {
                ps.setTimestamp(startDatePos, new Timestamp(df.parse(startDate).getTime()));
            }
            if (endDatePos > 0) {
                ps.setTimestamp(endDatePos, new Timestamp(df.parse(endDate).getTime()));
            }

            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            int entryCnt = 0;
            String messageTemplate = "{0} : User ({1}, {2}) : {3}";
            ServletOutputStream responseOutputStream = response.getOutputStream();
            long prevId = -1;
            String prevAction = null;
            List prevActionArgs = new ArrayList();
            Date prevTime = null;
            String prevIp = null;
            String prevHandle = null;
            rs = ps.executeQuery();
            while (rs.next()) {
                long currId = rs.getLong("id");
                String currAction = rs.getString("text");
                String currHandle = rs.getString("handle");
                if (rs.wasNull()) {
                    currHandle = "ANONYMOUS";
                }
                String currIp = rs.getString("ip_address");
                Date currTime = rs.getTimestamp("time");
                if (currId != prevId) {
                    if (prevId != -1) {
                        String text = MessageFormat.format(prevAction, prevActionArgs.toArray());
                        String logEntry
                            = MessageFormat.format(messageTemplate,
                                                   new Object[] {dateFormatter.format(prevTime), prevHandle, prevIp,
                                                                 text});
                        responseOutputStream.println(logEntry);
                        entryCnt++;
                        if (entryCnt % 5 == 0) {
                            responseOutputStream.flush();
                        }
                    }
                    prevId = currId;
                    prevAction = currAction;
                    prevHandle = currHandle;
                    prevTime = currTime;
                    prevIp = currIp;
                    prevActionArgs.clear();
                }
                String value = rs.getString("value");
                if (!rs.wasNull()) {
                    prevActionArgs.add(value);
                }
            }
            rs.close();
            if (prevId != -1) {
                String text = MessageFormat.format(prevAction, prevActionArgs.toArray());
                String logEntry
                    = MessageFormat.format(messageTemplate,
                                           new Object[] {dateFormatter.format(prevTime), prevHandle, prevIp, text});
                responseOutputStream.println(logEntry);
            }
            responseOutputStream.flush();
            responseOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                ServletOutputStream responseOutputStream = response.getOutputStream();
                if (!response.isCommitted()) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
                responseOutputStream.println("An unexpected error has been encountered while retrieving log entries");
                responseOutputStream.flush();
                responseOutputStream.close();
            } catch (IOException e1) {
                throw new ResultExecutionException("Could not commit the response in case of unexpected error", e);
            }
        } finally {
            close(rs);
            close(ps);
            close(connection);
        }

    }

    /**
     * <p>Gets a new connection to target database. The returned connection is configured to have the auto-commit
     * feature switched off and the transaction isolation level preventing the "dirty" reads.</p>
     *
     * @return a <code>Connection</code> providing a new connection to target database.
     * @throws DBConnectionException if a connection could not be established.
     */
    private Connection getConnection() throws DBConnectionException {
        return this.connectionFactory.createConnection();
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
