/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.admin.util;

import com.orpheus.game.PuzzleConfig;
import com.orpheus.game.server.handler.AbstractGameServerHandler;
import com.orpheus.game.server.util.AdminDataEJBAdapter;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.topcoder.servlet.request.FileUpload;
import com.topcoder.servlet.request.FileUploadResult;
import com.topcoder.servlet.request.LocalFileUpload;
import com.topcoder.servlet.request.UploadedFile;
import com.topcoder.util.image.manipulation.Image;
import com.topcoder.util.image.manipulation.image.MutableMemoryImage;
import com.topcoder.util.puzzle.PuzzleData;
import com.topcoder.util.puzzle.PuzzleGenerator;
import com.topcoder.util.puzzle.PuzzleType;
import com.topcoder.util.puzzle.PuzzleTypeSource;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.frontcontroller.results.DownloadData;
import org.w3c.dom.Element;

import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * <p>A custom {@link Handler} implementation to be used for creating the practice puzzles.</p>
 *
 * @author isv
 * @version 1.0
 */
public class CreatePracticePuzzleHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>Constructs new <code>CreatePracticePuzzleHandler</code> instance initialized based on the configuration
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
     *      &lt;puzzle_type_source_key&gt;puzzle_type_source&lt;/puzzle_type_source_key&gt;
     *     &lt;/handler&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public CreatePracticePuzzleHandler(Element element) {
        readAsString(element, PUZZLE_TYPE_SOURCE_ATTR_NAME_CONFIG, true);
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
            // Get the puzzle type source
            ServletContext servletContext = getServletContext(context);
            PuzzleTypeSource puzzleTypeSource
                = (PuzzleTypeSource) servletContext.getAttribute(getString(PUZZLE_TYPE_SOURCE_ATTR_NAME_CONFIG));
            // Get the parameters providing the puzzle configuration
            HttpServletRequest request = context.getRequest();
            String puzzleTypeName = request.getParameter("puzzleType");
            String puzzleWidth = request.getParameter("puzzleWidth");
            String puzzleHeight = request.getParameter("puzzleHeight");
            PuzzleConfig config
                = new PuzzleConfig(puzzleTypeName, new Integer(puzzleWidth), new Integer(puzzleHeight), 1);
            // Get the content of the image to be used for generating the puzzle and save the content to DB
            FileUpload fileUpload = new LocalFileUpload("com.orpheus.game.server.FileUpload");
            FileUploadResult fileUploadResult = fileUpload.uploadFiles(request);
            UploadedFile uploadedFile = fileUploadResult.getUploadedFile("puzzleImage");
            GameDataEJBAdapter gameDataEJBAdapter = getGameDataEJBAdapter(this.jndiContext);
            long imageId = gameDataEJBAdapter.recordBinaryObject("Practice Puzzle", uploadedFile.getContentType(),
                                                                 getFileContent(uploadedFile));
            DownloadData imageDownloadData = gameDataEJBAdapter.getDownloadData(imageId);
            Image image = new MutableMemoryImage(ImageIO.read(imageDownloadData.getContent()));
            // Set puzzle generator configuration
            PuzzleType puzzleType = puzzleTypeSource.getPuzzleType(config.getPuzzleTypeName());
            PuzzleGenerator puzzleGenerator = puzzleType.createGenerator();
            puzzleGenerator.setAttribute("image", image);
            puzzleGenerator.setAttribute("width", config.getWidth());
            puzzleGenerator.setAttribute("height", config.getHeight());
            // Generate puzzle and save it back to DB
            PuzzleData puzzleData = puzzleGenerator.generatePuzzle();
            AdminDataEJBAdapter adminDataEKBAdapter = getAdminDataEJBAdapter(this.jndiContext);
            long[] puzzleIds = adminDataEKBAdapter.storePuzzles(new PuzzleData[]{puzzleData});
            request.setAttribute("practicePuzzleId", new Long(puzzleIds[0]));
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not create practice puzzle", e);
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
