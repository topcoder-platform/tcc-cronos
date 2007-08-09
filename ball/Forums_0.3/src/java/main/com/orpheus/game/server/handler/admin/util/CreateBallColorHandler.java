/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.admin.util;

import com.orpheus.game.server.handler.AbstractGameServerHandler;
import com.orpheus.game.server.util.AdminDataEJBAdapter;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.topcoder.servlet.request.FileUpload;
import com.topcoder.servlet.request.FileUploadResult;
import com.topcoder.servlet.request.LocalFileUpload;
import com.topcoder.servlet.request.UploadedFile;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * <p>A custom {@link Handler} implementation to be used for creating the Ball colors.</p>
 *
 * @author isv
 * @version 1.0
 */
public class CreateBallColorHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>Constructs new <code>CreateBallColorHandler</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * Here is what the xml element likes:
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;admin_data_jndi_name&gt;orpheus/AdminData&lt;/admin_data_jndi_name&gt;
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
    public CreateBallColorHandler(Element element) {
        readAsString(element, GAME_EJB_JNDI_NAME_CONFIG, true);
        readAsString(element, ADMIN_EJB_JNDI_NAME_CONFIG, true);
        readAsBoolean(element, USER_REMOTE_INTERFACE_CONFIG, true);
        this.jndiContext = getJNDIContext(element);
    }

    /**
     * <p>Processes the incoming request. Creates a new puzzle based on specified image, width and height.</p>
     *
     * @param context an <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return <code>null</code> if request has been serviced successfully. Otherwise an exception will be raised.
     * @throws HandlerExecutionException if un unrecoverable error prevents the successful request processing.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        try {
            // Get the parameters providing the new Ball color configuration
            HttpServletRequest request = context.getRequest();
            String colorName = request.getParameter("colorName");
            // Get the content of the image to be used for new Ball color and save the content to DB
            FileUpload fileUpload = new LocalFileUpload("com.orpheus.game.server.FileUpload");
            FileUploadResult fileUploadResult = fileUpload.uploadFiles(request);
            UploadedFile uploadedFile = fileUploadResult.getUploadedFile("ballColorImage");
            GameDataEJBAdapter gameDataEJBAdapter = getGameDataEJBAdapter(this.jndiContext);
            long imageId = gameDataEJBAdapter.recordBinaryObject("Practice Puzzle", uploadedFile.getContentType(),
                                                                 getFileContent(uploadedFile));
            // Create new ball color in DB
            AdminDataEJBAdapter adminDataEJBAdapter = getAdminDataEJBAdapter(this.jndiContext);
            adminDataEJBAdapter.createBallColor(colorName, imageId);
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not create Ball color", e);
        }
    }

    /**
     * <p>Gets the content of an image file uploaded by the administrator.</p>
     *
     * @param uploadedFile an <code>UploadedFile</code> representing an image file uploaded by the administrator.
     * @return a <code>byte</code> array providing the content of the uploaded file.
     * @throws Exception if an unexpected error occurs.
     */
    private byte[] getFileContent(UploadedFile uploadedFile) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream((int) uploadedFile.getSize());
        InputStream imageContentStream = uploadedFile.getInputStream();
        byte[] data = new byte[1024];
        int bytesRead;
        while ((bytesRead = imageContentStream.read(data)) > 0) {
            baos.write(data, 0, bytesRead);
        }
        return baos.toByteArray();
    }
}
