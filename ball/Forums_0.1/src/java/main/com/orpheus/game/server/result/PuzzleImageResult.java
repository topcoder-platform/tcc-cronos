/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.result;

import com.orpheus.game.GameDataConfigurationException;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.GameDataLocalHome;
import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;
import com.topcoder.validation.emailconfirmation.InvalidAddressException;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.FrontControllerLogger;
import com.topcoder.web.frontcontroller.ResultExecutionException;
import com.topcoder.web.frontcontroller.LogSupportResult;
import com.topcoder.web.frontcontroller.results.DownloadData;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>A custom <code>Result</code> implemenetation which is to be used for retrieving the images comprising the puzzle.
 * Due to constraints implied by <code>Puzzle Framework</code> component this implementation expects the request to
 * provide the puzzle resource name uniquelly identifying the puzzle piece to retrieve image for. This implementation
 * maps the puzzle resource name to ID of corresponding record from <code>DOWNLOAD_OBJ</code> table and outputs the
 * image content to response then.</p>
 *
 * @author isv
 * @version 1.0
 */
public class PuzzleImageResult implements LogSupportResult {

    /**
     * <p>A <code>String</code> providng the SQL command to be used for mapping the puzzle resource name to ID of a
     * record in <code>DOWNLOAD_OBJ</code> table providing the puzzle piece image content.</p>  
     */
    private static final String SELECT_SQL = "SELECT download_obj_id FROM puzzle_resource WHERE name = ?";

    /**
     * <p>A <code>boolean</code> flag indicating whether the remote or local interface should be used for locating and
     * invoking <code>Game Data EJB</code>.</p>
     */
    private final boolean useRemoteInterface;

    /**
     * <p>A <code>String</code> providing the name to be used to lookup the <code>Game Data EJB</code> home interface in
     * <code>JNDI</code> context.</p>
     */
    private final String gameDataJNDIName;

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>A <code>String</code> providing the name of the request parameter which is respected to provide the ID of a
     * downloadable object corresponding to requested image.</p>
     */
    private final String puzzleResourceNameParamKey;

    /**
     * <p>A <code>DBConnectionFactory</code> to be used for establishing the connections to target database.</p>
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>Constructs new <code>PuzzleImageResult</code> instance initialized based on the configuration parameters
     * provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * <pre>
     *     &lt;result name=&quot;x&quot type=&quot;y&quot;&gt;
     *      &lt;puzzle_resource_param_key&gt;puzzleResource&lt;/puzzle_resource_param_key&gt;
     *      &lt;game_data_jndi_name&gt;orpheus/GameData&lt;/game_data_jndi_name&gt;
     *      &lt;jndi_context_name&gt;default&lt;/jndi_context_name&gt;
     *      &lt;use_remote_interface&gt;true&lt;/use_remote_interface&gt;
     *     &lt;/handler&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public PuzzleImageResult(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        this.puzzleResourceNameParamKey = getElement(element, "puzzle_resource_param_key");
        this.gameDataJNDIName = getElement(element, "game_data_jndi_name");
        this.useRemoteInterface = Boolean.valueOf(getElement(element, "use_remote_interface")).booleanValue();
        String jndiContextName = getElement(element, "jndi_context_name");
        try {
            this.jndiContext = JNDIUtils.getContext(jndiContextName);
            this.connectionFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        } catch (NamingException e) {
            throw new GameDataConfigurationException("Could not obtain JNDI Context [" + jndiContextName + "]", e);
        } catch (ConfigManagerException e) {
            throw new GameDataConfigurationException("Could not obtain JNDI Context [" + jndiContextName + "]", e);
        } catch (ConfigurationException e) {
            throw new GameDataConfigurationException("Could not obtain DB Connection Factory ", e);
        } catch (UnknownConnectionException e) {
            throw new GameDataConfigurationException("Could not obtain DB Connection Factory ", e);
        }
    }

    /**
     * <p>Executes this result. Locates the content of the image for the requested puzzle reource and outputs it to the
     * response.</p>
     *
     * @param context an <code>ActionContext</code> providing the context surrounding the incoming request.
     * @throws ResultExecutionException if an unrecoverable error prevents this result from successful execution.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public void execute(ActionContext context) throws ResultExecutionException {
        execute(context, LogFactory.getLog(), Level.DEBUG);
    }

    /**
     * <p>Executes this result. Locates the content of the image for the requested puzzle reource and outputs it to the
     * response.</p>
     *
     * @param context a <code>ActionContext</code> providing the context surrounding the incoming request.
     * @param actualLogger a <code>Log</code> to log the events to.
     * @param loggingLevel a <code>Level</code> specifying the logging level. 
     * @throws ResultExecutionException if an unrecoverable error prevents the handler from successful execution.
     * @throws IllegalArgumentException if specified <code>context</code> is <code>null</code>.
     */
    public void execute(ActionContext context, Log actualLogger, Level loggingLevel) throws ResultExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        FrontControllerLogger logger = new FrontControllerLogger(this.getClass().getName(), actualLogger, loggingLevel);

        GameData gameData = null;
        GameDataLocal gameDataLocal = null;
        try {
            HttpServletRequest request = context.getRequest();
            DownloadData imageData;

            // Map puzzle resource name to download obj ID
            String puzzleResourceName = request.getParameter(this.puzzleResourceNameParamKey);
            long downloadObjId = getDownloadObjId(puzzleResourceName);

            // check whether to use remote look up and load image content from database
            if (this.useRemoteInterface) {
                GameDataHome gameDataHome
                    = (GameDataHome) JNDIUtils.getObject(this.jndiContext, this.gameDataJNDIName, GameDataHome.class);
                gameData = gameDataHome.create();
                imageData = gameData.getDownloadData(downloadObjId);
            } else {
                GameDataLocalHome gameDataLocalHome
                    = (GameDataLocalHome) JNDIUtils.getObject(this.jndiContext, this.gameDataJNDIName,
                                                              GameDataLocalHome.class);
                gameDataLocal = gameDataLocalHome.create();
                imageData = gameDataLocal.getDownloadData(downloadObjId);
            }
            // Set the response content type
            HttpServletResponse response = context.getResponse();
            response.setContentType(imageData.getMediaType());

            // Write image content to servlet response
            ServletOutputStream responseOutputStream = response.getOutputStream();
            InputStream imageContentStream = imageData.getContent();
            byte[] data = new byte[1024];
            int bytesRead;
            while ((bytesRead = imageContentStream.read(data)) > 0) {
                responseOutputStream.write(data, 0, bytesRead);
            }
            responseOutputStream.flush();
            responseOutputStream.close();
        } catch (Exception e) {
            // The error is caught and not propagated so the image simply is not rendered on a page
            logger.error("execute", e);
        }
    }

    /**
     * <p>Gets the text value of the specified node (key) from the given element.</p>
     *
     * @param element an <code>Element</code> providing the element to be used.
     * @param key a <code>String</code> providing the tag name for element to be extracted.
     * @return a <code>String</code> providing the text value of the given tag.
     * @throws IllegalArgumentException if the given <code>key</code> is not present or has empty value.
     */
    private static String getElement(Element element, String key) {
        NodeList nodeList;
        nodeList = element.getElementsByTagName(key);
        if (nodeList.getLength() == 0) {
            throw new IllegalArgumentException("Key '" + key + "' is missing in the element");
        }
        if (nodeList.getLength() != 1) {
            throw new IllegalArgumentException("Key '" + key + "' is occurring more than once in the element");
        }
        Node node = nodeList.item(0).getFirstChild();
        if (node == null) {
            throw new IllegalArgumentException("The [" + key + "] element is not found");
        }
        String value = node.getNodeValue();
        if ((value == null) || (value.trim().length() == 0)) {
            throw new IllegalArgumentException("The [" + key + "] element is empty");
        }
        return value;
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
     * <p>Gets the ID of a downloadable object providing the content for a specified puzzle resource.</p>
     *
     * @param puzzleResource a <code>String</code> providing the name of the puzzle resorce to get the ID of
     *        corresponding downloadable object for.
     * @return a <code>long</code> providing the ID of a downloadable object for the image corresponding to specified
     *         puzzle resource.
     * @throws InvalidAddressException if the operation fails for unrecoverable reason.
     */
    private long getDownloadObjId(String puzzleResource) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            stmt = connection.prepareStatement(SELECT_SQL);
            stmt.setString(1, puzzleResource);
            rs = stmt.executeQuery();
            if (rs.next()) {
                // TODO : Currently we do not validate against multiple rows returned
                return rs.getLong(1);
            }
        } catch (Exception e) {
            // Printout exception and ignore
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return Long.MIN_VALUE;
    }
}
