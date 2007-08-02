/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.result;

import com.orpheus.game.GameDataConfigurationException;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.GameDataLocalHome;
import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.FrontControllerLogger;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.frontcontroller.LogSupportResult;
import com.topcoder.web.frontcontroller.Result;
import com.topcoder.web.frontcontroller.ResultExecutionException;
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

/**
 * <p>This is a custom implementation of {@link Result} interface which is used for retrieving the content of the
 * image and writting it to response. The purpose of this result is to service the requests from browsers for
 * puzzle/brainteaser images to be displayed on a page. This implementation expects the request to provide the ID of a
 * record in <code>DOWNLOAD_OBJ</code> table providing the content of requested image and outputs the image content to
 * response then.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ImageResult implements LogSupportResult {

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
    private final String imageParamKey;

    /**
     * <p>Constructs new <code>ImageResult</code> instance initialized based on the configuration parameters provided by
     * the specified <code>XML</code> element.</p>
     *
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;game_data_jndi_name&gt;orpheus/GameData&lt;/game_data_jndi_name&gt;
     *      &lt;jndi_context_name&gt;default&lt;/jndi_context_name&gt;
     *      &lt;use_remote_interface&gt;true&lt;/use_remote_interface&gt;
     *      &lt;image_param_key&gt;downloadId&lt;/image_param_key&gt;
     *     &lt;/handler&gt;
     * </pre>
     *
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public ImageResult(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }

        this.imageParamKey = getElement(element, "image_param_key");
        this.gameDataJNDIName = getElement(element, "game_data_jndi_name");
        this.useRemoteInterface = Boolean.valueOf(getElement(element, "use_remote_interface")).booleanValue();
        String jndiContextName = getElement(element, "jndi_context_name");
        try {
            this.jndiContext = JNDIUtils.getContext(jndiContextName);
        } catch (NamingException e) {
            throw new GameDataConfigurationException("Could not obtain JNDI Context [" + jndiContextName + "]", e);
        } catch (ConfigManagerException e) {
            throw new GameDataConfigurationException("Could not obtain JNDI Context [" + jndiContextName + "]", e);
        }
    }

    /**
     * <p>Process the user request. Obtains the content of the requested image from persistent data store and writes
     * it's content to response.</p>
     *
     * @param context the ActionContext object for the current
     *        request
     * @throws ResultExecutionException if an unrecoverable error prevents the handler from successful execution.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public void execute(ActionContext context) throws ResultExecutionException {
        execute(context, LogFactory.getLog(), Level.DEBUG);
    }

    /**
     * <p>Process the user request. Obtains the content of the requested image from persistent data store and writes
     * it's content to response.</p>
     *
     * @param context a <code>ActionContext</code> providing the context surrounding the incoming request.
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
            // check whether to use remote look up and load image content from database
            long downloadObjectId = parseLongValue(request, this.imageParamKey);
            if (this.useRemoteInterface) {
                GameDataHome gameDataHome
                    = (GameDataHome) JNDIUtils.getObject(this.jndiContext, this.gameDataJNDIName, GameDataHome.class);
                gameData = gameDataHome.create();
                imageData = gameData.getDownloadData(downloadObjectId);
            } else {
                GameDataLocalHome gameDataLocalHome
                    = (GameDataLocalHome) JNDIUtils.getObject(this.jndiContext, this.gameDataJNDIName,
                                                              GameDataLocalHome.class);
                gameDataLocal = gameDataLocalHome.create();
                imageData = gameDataLocal.getDownloadData(downloadObjectId);
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
            // The error is catched and not propagated so the image simply is not rendered on a page
            logger.error("execute", e);
        }
    }

    /**
     * <p>Parses the specified parameter of specfied request into a <code>long</code> value.</p>
     *
     * @param request an <code>HttpServletRequest</code> representing the incoming request.
     * @param paramName a <code>String</code> providing the name of desired parameter.
     * @return a <code>long</code> providing the value of specified parameter.
     * @throws HandlerExecutionException if specified parameter can not be parsed into <code>long</code> value.
     */
    private long parseLongValue(HttpServletRequest request, String paramName) throws HandlerExecutionException {
        String value = request.getParameter(paramName);
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new HandlerExecutionException("Request parameter [" + paramName + "] must provide numeric value. "
                                                + "Current value is [" + value + "]");
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
}
