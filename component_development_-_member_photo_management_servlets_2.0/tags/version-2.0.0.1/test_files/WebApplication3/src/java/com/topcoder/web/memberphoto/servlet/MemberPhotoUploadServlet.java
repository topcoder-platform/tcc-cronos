/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import com.topcoder.servlet.request.ConfigurationException;
import com.topcoder.servlet.request.FileDoesNotExistException;
import com.topcoder.servlet.request.FileUploadResult;
import com.topcoder.servlet.request.InvalidFileException;
import com.topcoder.servlet.request.MemoryFileUpload;
import com.topcoder.servlet.request.PersistenceException;
import com.topcoder.servlet.request.RequestParsingException;
import com.topcoder.servlet.request.UploadedFile;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.memberphoto.manager.MemberPhotoManagementException;
import com.topcoder.web.memberphoto.manager.MemberPhotoManager;

/**
 * <p>
 * This class extends the <code>HttpServlet</code>, and it allows web site members to upload their photos. It will
 * extract the uploaded image from the request using the FileUpload, the uploaded image must be of JPEG or PNG format
 * only. The image will be resized and cropped to fit into the configured target image width and height.
 * </p>
 * <p>
 * If the submitted flag is false, the image will be stored into a preview directory, and the servlet will forward to a
 * configured preview URL.
 * </p>
 * <p>
 * If the submitted flag is true, the image will be stored into a submitted directory, and some relevant information
 * will be stored to persistence using the MemberPhotoManager, and finally the servlet will redirect to a configured
 * submitted URL.
 * </p>
 * <p>
 * File processing should be synchronized since multiple servlets may access the same file concurrently.
 * </p>
 * <p>
 * NOTE: The form data will be validated, and if validation fails, it will forward to a validation error URL.
 * </p>
 * <p>
 * All its instance variables will be injected by the Spring. It should be marked with transactional annotation to
 * ensure the operation is transactional.
 * </p>
 * <p>
 * Version 2.0 changes <br>
 * 1. added the ability to process and validate GIF file formats<br>
 * 2. added the max byte size check for the image being uploaded when validating <br>
 * 3. added the configurable error code for the validation error for exceeded image file size <br>
 * 4. The submitted flag parameter is not used anymore. Instead each action has its own string name. <br>
 * 5. The servlet can be configured for crop information from JCrop through two new parameter names. <br>
 * 6. Logging has been added
 * </p>
 * <p>
 * <i>Sample code: </i><br>
 * <p>
 * 1.Sample Spring framework configuration file:
 * </p>
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8" ?&gt;
 * &lt;beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 * xsi:schemaLocation="http://www.springframework.org/schema/beans
 * http://www.springframework.org/schema/beans/spring-beans-2.0.xsd"&gt;
 *     &lt;bean id="uploadServlet" class="com.topcoder.web.memberphoto.servlet.MemberPhotoUploadServlet"&gt;
 *         &lt;property name="memberIdParameterName"&gt;
 *             &lt;value&gt;
 *                 member_id
 *             &lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name="memberIdSessionKey"&gt;
 *             &lt;value&gt;
 *                 member_id_session_key
 *             &lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name="fileUploaderNamespace"&gt;
 *             &lt;value&gt;
 *                 MemoryFileUpload
 *             &lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name="photoImageFormFileName"&gt;
 *             &lt;value&gt;
 *                 test.gif
 *             &lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name="photoImageErrorName"&gt;
 *             &lt;value&gt;
 *                 photo_image_error_name
 *             &lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name="memberIdErrorName"&gt;
 *             &lt;value&gt;
 *                 member_id_error_name
 *             &lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name="validationErrorForwardUrl"&gt;
 *             &lt;value&gt;
 *                 validation_error_forward_url
 *             &lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name="targetImageWidth"&gt;
 *             &lt;value&gt;
 *                 100
 *             &lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name="targetImageHeight"&gt;
 *             &lt;value&gt;
 *                 80
 *             &lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name="photoImageSubmittedDirectory"&gt;
 *             &lt;value&gt;
 *                 test_files/submitted
 *             &lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name="photoImagePreviewDirectory"&gt;
 *             &lt;value&gt;
 *                 test_files/preview
 *             &lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name="previewPhotoImagePathName"&gt;
 *             &lt;value&gt;
 *                 /preview
 *             &lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name="previewForwardUrl"&gt;
 *             &lt;value&gt;
 *                 /preview
 *             &lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name="submittedRedirectUrl"&gt;
 *             &lt;value&gt;
 *                 /submitted?${memberId}.jpeg
 *             &lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name="targetImageLeftCornerXParameterName"&gt;
 *             &lt;value&gt;
 *                 10
 *             &lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name="targetImageLeftCornerYParameterName"&gt;
 *             &lt;value&gt;
 *                 10
 *             &lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name="targetImageRightCornerXParameterName"&gt;
 *             &lt;value&gt;
 *                 10
 *             &lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name="targetImageRightCornerYParameterName"&gt;
 *             &lt;value&gt;
 *                 10
 *             &lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name="maxFileSize"&gt;
 *             &lt;value&gt;
 *                 100000000000
 *             &lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name="maxFileExceededErrorName"&gt;
 *             &lt;value&gt;
 *                 max_File_Exceeded_Error_Name
 *             &lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name="submittedActionParameterName"&gt;
 *             &lt;value&gt;
 *                 submit_action
 *             &lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name="photoImageCropDirectory"&gt;
 *             &lt;value&gt;
 *                 test_files/crop
 *             &lt;/value&gt;
 *         &lt;/property&gt;
 *         &lt;property name="memberPhotoManager" ref="memberPhotoManager" /&gt;
 *     &lt;/bean&gt;
 *     &lt;bean id="memberPhotoManager" class="com.topcoder.web.memberphoto.servlet.MockMemberPhotoManagerImpl"&gt;
 *     &lt;/bean&gt;
 * &lt;/beans&gt;
 * </pre>
 * <p>
 * 2.API usage.
 * </p>
 * 
 * <pre>
 * Resource rs = new ClassPathResource(conf_file);
 * BeanFactory factory = new XmlBeanFactory(rs);
 * MemberPhotoUploadServlet bean = (MemberPhotoUploadServlet) factory.getBean(&quot;uploadServlet&quot;);
 * return bean;
 * </pre>
 * 
 * </p>
 * <p>
 * <strong>Thread safety:</strong> The injected instance variables are immutable after injection. So this class is
 * thread-safe when serving user requests.
 * </p>
 * @author AleaActaEst, microsky
 * @version 1.0
 */
@SuppressWarnings("serial")
@Transactional
public class MemberPhotoUploadServlet extends HttpServlet {

    /**
     * <p>
     * Constant for image file post-fix.
     * </p>
     */
    private String IMAGE_POSTFIX = ".jpeg";
    /**
     * <p>
     * Represents the request parameter name for member id.
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be non-null, non-empty string.
     * </p>
     */
    private String memberIdParameterName;
    /**
     * <p>
     * Represents the request form file name for photo image.
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be non-null, non-empty string.
     * </p>
     */
    private String photoImageFormFileName;
    /**
     * <p>
     * Represents the <code>MemberPhotoManager</code> to manage the member photo.
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be non-null.
     * </p>
     */
    private MemberPhotoManager memberPhotoManager;
    /**
     * <p>
     * Represents the session key for member id.
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be non-null, non-empty string.
     * </p>
     */
    private String memberIdSessionKey;
    /**
     * <p>
     * Represents the request attribute name of the error message if the member id is invalid.
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be non-null, non-empty string.
     * </p>
     */
    private String memberIdErrorName;
    /**
     * <p>
     * Represents the request attribute name of the error message if the photo image is in wrong format.
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be non-null, non-empty string.
     * </p>
     */
    private String photoImageErrorName;
    /**
     * <p>
     * Represent the URL to forward to if the validation error occurs.
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be non-null, non-empty string.
     * </p>
     */
    private String validationErrorForwardUrl;
    /**
     * <p>
     * Represent the target image width. Measured in pixels.
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be positive.
     * </p>
     */
    private int targetImageWidth;
    /**
     * <p>
     * Represent the target image height. Measured in pixels.
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be positive.
     * </p>
     */
    private int targetImageHeight;
    /**
     * <p>
     * Represent the target image's left upper corner X coordinate parameter name which will be used to get the input
     * data from JCrop.
     * </p>
     * <p>
     * (the data will be measured in pixels. )
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be non-null
     * </p>
     */
    private String targetImageLeftCornerXParameterName;
    /**
     * <p>
     * Represent the target image's left upper corner Y coordinate parameter name which will be used to get the input
     * data from JCrop.
     * </p>
     * <p>
     * (the data will be measured in pixels. )
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be non-null
     * </p>
     */
    private String targetImageLeftCornerYParameterName;
    /**
     * <p>
     * Represent the target image's right lower corner X coordinate parameter name which will be used to get the input
     * data from JCrop.
     * </p>
     * <p>
     * (the data will be measured in pixels. )
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be non-null
     * </p>
     */
    private String targetImageRightCornerXParameterName;
    /**
     * <p>
     * Represent the target image's right lower corner Y coordinate parameter name which will be used to get the input
     * data from JCrop.
     * </p>
     * <p>
     * (the data will be measured in pixels. )
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be non-null
     * </p>
     */
    private String targetImageRightCornerYParameterName;
    /**
     * <p>
     * Represent the directory to store the submitted photo images.
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be non-null, non-empty string.
     * </p>
     */
    private String photoImageSubmittedDirectory;
    /**
     * <p>
     * Represent the directory to store the preview photo images.
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be non-null, non-empty string.
     * </p>
     */
    private String photoImagePreviewDirectory;
    /**
     * <p>
     * Represent photoImageCropDirectory.
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be non-null, non-empty string.
     * </p>
     */
    private String photoImageCropDirectory;
    /**
     * <p>
     * Represent the request attribute name for the preview image path.
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be non-null, non-empty string.
     * </p>
     */
    private String previewPhotoImagePathName;
    /**
     * <p>
     * Represent the url to forward to if the operation succeeds and the submitted action is "preview"
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be non-null, non-empty string.
     * </p>
     */
    private String previewForwardUrl;
    /**
     * <p>
     * Represent the URL to redirect to if the operation succeeds and the submitted flag is specified.
     * </p>
     * <p>
     * The URL can contain a place-holder string: '${memberId}' (without quotes), it will be replaced by the real
     * memberId.
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be non-null, non-empty string.
     * </p>
     */
    private String submittedRedirectUrl;
    /**
     * <p>
     * Represent the namespace to create the <code>MemoryFileUpload</code>.
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be non-null, non-empty string.
     * </p>
     */
    private String fileUploaderNamespace;
    /**
     * <p>
     * Represents the MemberInformationRetriever to retrieve the member information.
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be non-null.
     * </p>
     */
    private MemberInformationRetriever memberInformationRetriever;
    /**
     * <p>
     * Represent the configured maximum file size (in bytes)
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be a positive number representing max number of bytes allowed.
     * </p>
     */
    private long maxFileSize;
    /**
     * <p>
     * Represents the request attribute name of the error message if the uploaded file size excceded the max allowed
     * size (in bytes).
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be non-null, non-empty string.
     * </p>
     */
    private String maxFileExceededErrorName;
    /**
     * <p>
     * Represents the request parameter name for submitted action name.
     * </p>
     * <p>
     * It's injected by the Spring.
     * </p>
     * <p>
     * After injection, it must be non-null, non-empty string.
     * </p>
     */
    private String submittedActionParameterName;
    /**
     * <p>
     * This is a logger which will be used to perform logging operations.
     * </p>
     * <p>
     * It's created upon initialization with the class name of this class as the logger name.
     * </p>
     * <p>
     * After injection, it must be non-null, non-empty string.
     * </p>
     */
    private Log log;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public MemberPhotoUploadServlet() {
    }

    /**
     * <p>
     * The method to init logger.
     * </p>
     */
    @PostConstruct
    public void init() {
        this.log = LogManager.getLog();
    }

    /**
     * <p>
     * it allows web site members to upload their photos.
     * </p>
     * <p>
     * Version 2.0 Changes<br>
     * 1. The GIF image format is now being handled in this version. This affects processing and validation<br>
     * 2. Size of the uploaded image is checked against a configured upper limit (in bytes)<br>
     * 3. There are three distinct actions now of "preview", "commit", and "crop"<br>
     * 4. width and height parameter now come from external source through
     * </p>
     * @param response the response.
     * @param request the request.
     * @throws IllegalArgumentException if any arg is null.
     * @throws IllegalStateException if the instance variables are not injected
     *             correctly (refer to each variable's constraint in its
     *             documentation).
     * @throws IOException if any i/o error occurs.
     * @throws MemberPhotoUploadException if any unexpected error occurs. (it's
     *             used to wrap all underlying exceptions).
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,
            MemberPhotoUploadException {
        logMsg(MessageFormat.format("{0} : Entering " + "MemberPhotoManagerImpl#doPost"
                + "(HttpServletRequest, HttpServletResponse)" + " - request {1}, response {2},", new Date(), request,
                response));
        try {
            // 'request' should not be null
            Helper.checkNull(request, "request");

            // 'response' should not be null
            Helper.checkNull(response, "response");
            try {
                checkState();
                // get submitted action from request
                String submittedAction = request.getParameter(submittedActionParameterName);
                // Make sure that the action is one of the following:1.
                // "preview" 2.
                // "crop"3. "commit"
                // If it is neither of those or is missing we assume that this
                // is:
                // "preview"
                if ((submittedAction == null)
                        || (!submittedAction.equals("preview") && !submittedAction.equals("crop") && !submittedAction.equals("commit"))) {
                    submittedAction = "preview";
                }

                // get memberId from request
                System.out.println("------------------" + memberIdParameterName);
                long memberId = Long.parseLong(request.getParameter(memberIdParameterName));
                // there is no special implements about this.
                try {
                    int targetImageLeftCornerX =
                            Integer.parseInt(request.getParameter(targetImageLeftCornerXParameterName));
                    int targetImageLeftCornerY =
                            Integer.parseInt(request.getParameter(targetImageLeftCornerYParameterName));
                    int targetImageRightCornerX =
                            Integer.parseInt(request.getParameter(targetImageRightCornerXParameterName));
                    int targetImageRightCornerY =
                            Integer.parseInt(request.getParameter(targetImageRightCornerYParameterName));
                } catch (Exception e) {
                    // ignore
                }
                // get memberId from session
                // NOTE: if the memberId doesn't exist in session or is not Long
                // value, simply throw IllegalStateException.

                long sessionMemberId;

                Object sessionMemberIdObj = request.getSession(true).getAttribute(memberIdSessionKey);

                if (!(sessionMemberIdObj instanceof Long)) {
                    throw new IllegalStateException("memberIdSessionKey is not a valid long value.");
                } else {
                    sessionMemberId = (Long) sessionMemberIdObj;
                }
                // get the uploaded file.
                MemoryFileUpload fileUploader = new MemoryFileUpload(fileUploaderNamespace);
                FileUploadResult fileUploadResult = fileUploader.uploadFiles(request);

                // get the uploaded photo
                UploadedFile uploadedFile = fileUploadResult.getUploadedFile(photoImageFormFileName);

                if (uploadedFile == null) {
                    throw new MemberPhotoUploadException("upload file is null.");
                }

                String contentType = uploadedFile.getContentType();

                // perform the validation.
                boolean validationError = false;
                // --- Check image file type
                if (!"image/png".equals(contentType) && !"image/jpeg".equals(contentType)
                        && !"image/gif".equals(contentType)) {
                    request.setAttribute(photoImageErrorName, "The image format is not supported.");
                    validationError = true;
                }
                if ("image/png".equals(contentType)) {
                    IMAGE_POSTFIX = ".png";
                }
                if ("image/jpeg".equals(contentType)) {
                    IMAGE_POSTFIX = ".jpeg";
                }
                if ("image/gif".equals(contentType)) {
                    IMAGE_POSTFIX = ".gif";
                }
                // --- member id should match the member id stored in session
                if (memberId != sessionMemberId) {
                    validationError = true;
                    request.setAttribute(memberIdErrorName, "The member id doesn't match the one in session.");
                }
                // --- The uploaded image cannot be greater that the configured
                // byte
                // limit
                if (uploadedFile.getSize() > maxFileSize) {
                    validationError = true;
                    request.setAttribute(maxFileExceededErrorName,
                            "The uploaded file exceeds the allowable max size of " + maxFileSize + " (bytes)");
                }

                // Report validation errors
                if (validationError) {
                    request.getRequestDispatcher(validationErrorForwardUrl).forward(request, response);
                    return;
                }
                BufferedImage originalImage = ImageIO.read(uploadedFile.getInputStream());
                // to load the image.

                // Crop and resize uploaded image

                BufferedImage resizedImage;

                int imageWidth = originalImage.getWidth();
                int imageHeight = originalImage.getHeight();

                if ((imageWidth == 0) || (imageHeight == 0)) {
                    throw new MemberPhotoUploadException("Either width or height of original image is null.");
                }
                if ((imageWidth != targetImageWidth) || (imageHeight != targetImageHeight)) {
                    // calculate the ratio
                    double wRatio = targetImageWidth / (double) originalImage.getWidth();
                    double hRatio = targetImageHeight / (double) originalImage.getHeight();

                    int resizedWidth;
                    int resizedHeight;

                    if (wRatio > hRatio) {
                        resizedWidth = targetImageWidth;
                        resizedHeight = (int) (originalImage.getHeight() * wRatio + 0.5);
                    } else {
                        resizedHeight = targetImageHeight;
                        resizedWidth = (int) (originalImage.getWidth() * hRatio + 0.5);
                    }
                    // scale the image.
                    resizedImage = new BufferedImage(resizedWidth, resizedHeight, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2D = resizedImage.createGraphics();
                    g2D.drawImage(originalImage, 0, 0, resizedWidth, resizedHeight, null);

                    // crop the image.
                    if (wRatio > hRatio) {
                        resizedImage =
                                resizedImage.getSubimage(0, (resizedHeight - targetImageHeight) / 2, targetImageWidth,
                                targetImageHeight);
                    } else {
                        resizedImage =
                                resizedImage.getSubimage((resizedWidth - targetImageWidth) / 2, 0, targetImageWidth,
                                targetImageHeight);
                    }
                } else {
                    resizedImage = originalImage;
                }

                // determine the action for image storing
                if (submittedAction.equals("preview")) {
                    String path = photoImagePreviewDirectory + "/" + memberId + IMAGE_POSTFIX;
                    upLoadPicture(memberId, resizedImage, this.photoImagePreviewDirectory);
                    // set image path to request & forward to the configured url
                    request.setAttribute(previewPhotoImagePathName, path);
                    request.getRequestDispatcher(previewForwardUrl).forward(request, response);
                }
                if (submittedAction.equals("commit")) {

                    upLoadPicture(memberId, resizedImage, this.photoImageSubmittedDirectory);
                    // save member photo and redirect to the result url.
                    memberPhotoManager.saveMemberPhoto(memberId, memberId + IMAGE_POSTFIX, memberInformationRetriever.getMemberInformation(memberId).getHandle());
                    String redirectUrl = submittedRedirectUrl;

                    // replace the ${memberId} place-holder in the redirectUrl
                    // with
                    // memberId value
                    // if the place-holder exists, and store the result back
                    // into
                    // redirectUrl
                    // local variable.
                    redirectUrl = redirectUrl.replace("${memberId}", String.valueOf(memberId));
                    response.sendRedirect(redirectUrl);
                }
                if (submittedAction.equals("crop")) {

                    upLoadPicture(memberId, resizedImage, this.photoImageCropDirectory);
                    // save member photo and redirect to the result url.
                    memberPhotoManager.saveMemberPhoto(memberId, memberId + IMAGE_POSTFIX, memberInformationRetriever.getMemberInformation(memberId).getHandle());
                    String redirectUrl = submittedRedirectUrl;

                    // replace the ${memberId} place-holder in the redirectUrl
                    // with
                    // memberId value if the place-holder exists,
                    // and store the result back into redirectUrl local
                    // variable.
                    redirectUrl = redirectUrl.replace("${memberId}", String.valueOf(memberId));
                    response.sendRedirect(redirectUrl);
                }

            } catch (ClassCastException e) {
                throw new MemberPhotoUploadException("The 'isAdministrator' is not valid boolean value.", e);
            } catch (NumberFormatException e) {
                throw new MemberPhotoUploadException("The input is not a number or is null value.", e);
            } catch (PersistenceException e) {
                throw new MemberPhotoUploadException("Error occurs while doing persistence.", e);
            } catch (FileDoesNotExistException e) {
                throw new MemberPhotoUploadException("Error occurs since file does not exist.", e);
            } catch (ServletException e) {
                throw new MemberPhotoUploadException("Servlet exception occurs.", e);
            } catch (ConfigurationException e) {
                throw new MemberPhotoUploadException("Error occurs while processing configuration.", e);
            } catch (RequestParsingException e) {
                throw new MemberPhotoUploadException("Error occurs while parsing request.", e);
            } catch (InvalidFileException e) {
                throw new MemberPhotoUploadException("Error occurs since file is invalid.", e);
            } catch (MemberPhotoManagementException e) {
                throw new MemberPhotoUploadException("Error occurs when manage photo.", e);
            } catch (MemberInformationRetrievalException e) {
                throw new MemberPhotoUploadException("Error occurs when get userId.", e);
            }
        } catch (IllegalArgumentException e) {
            throw logMsg("any arg is null", e);
        } catch (IllegalStateException e) {
            throw logMsg("the instance variables are not injected correctly", e);
        } catch (IOException e) {
            throw logMsg("i/o error occurs", e);
        } catch (MemberPhotoUploadException e) {
            throw logMsg("unexpected error occurs. details:" + e.getMessage(), e);
        } finally {
            logMsg(MessageFormat.format("{0} : Exiting " + "MemberPhotoManagerImpl#doPost"
                    + "(HttpServletRequest, HttpServletResponse)", new Date()));
        }
    }

    /**
     * <p>
     * Upload the picture to the server.
     * </p>
     * @param memberId the long of the member Id.
     * @param resizedImage the resized Image.
     * @throws IOException if can not write the image.
     */
    private void upLoadPicture(long memberId, BufferedImage resizedImage, String directory) throws IOException {
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdir();
        }
        // Store image
        String path = directory + "/" + memberId + IMAGE_POSTFIX;
        synchronized (this) {
            ImageIO.write(resizedImage, IMAGE_POSTFIX.substring(1), new File(path));
        }

    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param memberIdParameterName the member id parameter name.
     * @throws IllegalArgumentException if the argument is null or empty string.
     */
    public void setMemberIdParameterName(String memberIdParameterName) {
        Helper.checkString(memberIdParameterName, "memberIdParameterName");
        this.memberIdParameterName = memberIdParameterName;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param photoImageFormFileName the photo image form file name.
     * @throws IllegalArgumentException if the argument is null or empty string.
     */
    public void setPhotoImageFormFileName(String photoImageFormFileName) {
        Helper.checkString(photoImageFormFileName, "photoImageFormFileName");
        this.photoImageFormFileName = photoImageFormFileName;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param submittedActionParameterName submitted action parameter name.
     * @throws IllegalArgumentException if the argument is null or empty
     *             string.
     */
    public void setSubmittedActionParameterName(String submittedActionParameterName) {
        Helper.checkString(submittedActionParameterName, "submittedActionParameterName");
        this.submittedActionParameterName = submittedActionParameterName;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param memberPhotoManager the MemberPhotoManager object.
     * @throws IllegalArgumentException if the argument is null
     */
    public void setMemberPhotoManager(MemberPhotoManager memberPhotoManager) {
        Helper.checkNull(memberPhotoManager, "memberPhotoManager");
        this.memberPhotoManager = memberPhotoManager;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param memberIdSessionKey the member id session key.
     * @throws IllegalArgumentException if the argument is null or empty string.
     */
    public void setMemberIdSessionKey(String memberIdSessionKey) {
        Helper.checkString(memberIdSessionKey, "memberIdSessionKey");
        this.memberIdSessionKey = memberIdSessionKey;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param memberIdErrorName the member id error name.
     * @throws IllegalArgumentException if the argument is null or empty string.
     */
    public void setMemberIdErrorName(String memberIdErrorName) {
        Helper.checkString(memberIdErrorName, "memberIdErrorName");
        this.memberIdErrorName = memberIdErrorName;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param photoImageErrorName photo image error name.
     * @throws IllegalArgumentException if the argument is null or empty string.
     */
    public void setPhotoImageErrorName(String photoImageErrorName) {
        Helper.checkString(photoImageErrorName, "photoImageErrorName");
        this.photoImageErrorName = photoImageErrorName;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param validationErrorForwardUrl validation error forward URL.
     * @throws IllegalArgumentException if the argument is null or empty string.
     */
    public void setValidationErrorForwardUrl(String validationErrorForwardUrl) {
        Helper.checkString(validationErrorForwardUrl, "validationErrorForwardUrl");
        this.validationErrorForwardUrl = validationErrorForwardUrl;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param targetImageWidth target image width.
     * @throws IllegalArgumentException if the argument is not positive
     */
    public void setTargetImageWidth(int targetImageWidth) {
        checkPositive(targetImageWidth, "targetImageWidth");
        this.targetImageWidth = targetImageWidth;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param targetImageHeight target image height.
     * @throws IllegalArgumentException if the argument is not positive
     */
    public void setTargetImageHeight(int targetImageHeight) {
        checkPositive(targetImageHeight, "targetImageHeight");
        this.targetImageHeight = targetImageHeight;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param targetImageLeftCornerXParameterName target image Left corner X
     *            coordinate input parameter name
     * @throws IllegalArgumentException if the argument is null or empty string.
     */
    public void setTargetImageLeftCornerXParameterName(String targetImageLeftCornerXParameterName) {
        Helper.checkString(targetImageLeftCornerXParameterName, "targetImageLeftCornerXParameterName");
        this.targetImageLeftCornerXParameterName = targetImageLeftCornerXParameterName;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param targetImageLeftCornerYParameterName target image Left corner Y
     *            coordinate input parameter name
     * @throws IllegalArgumentException if the argument is null or empty string.
     */
    public void setTargetImageLeftCornerYParameterName(String targetImageLeftCornerYParameterName) {
        Helper.checkString(targetImageLeftCornerYParameterName, "targetImageLeftCornerYParameterName");
        this.targetImageLeftCornerYParameterName = targetImageLeftCornerYParameterName;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param targetImageRightCornerXParameterName target image Lower Right
     *            corner X coordinate input parameter
     * @throws IllegalArgumentException if the argument is null or empty string.
     */
    public void setTargetImageRightCornerXParameterName(String targetImageRightCornerXParameterName) {
        Helper.checkString(targetImageRightCornerXParameterName, "targetImageRightCornerXParameterName");
        this.targetImageRightCornerXParameterName = targetImageRightCornerXParameterName;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param targetImageRightCornerYParameterName target image Lower Right
     *            corner Y coordinate input parameter
     * @throws IllegalArgumentException if the argument is null or empty string.
     */
    public void setTargetImageRightCornerYParameterName(String targetImageRightCornerYParameterName) {
        Helper.checkString(targetImageRightCornerYParameterName, "targetImageRightCornerYParameterName");
        this.targetImageRightCornerYParameterName = targetImageRightCornerYParameterName;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param photoImageSubmittedDirectory photo image submitted directory.
     * @throws IllegalArgumentException if the argument is null or empty string.
     */
    public void setPhotoImageSubmittedDirectory(String photoImageSubmittedDirectory) {
        Helper.checkString(photoImageSubmittedDirectory, "photoImageSubmittedDirectory");
        this.photoImageSubmittedDirectory = photoImageSubmittedDirectory;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param photoImagePreviewDirectory photo image preview directory.
     * @throws IllegalArgumentException if the argument is null or empty string.
     */
    public void setPhotoImagePreviewDirectory(String photoImagePreviewDirectory) {
        Helper.checkString(photoImagePreviewDirectory, "photoImagePreviewDirectory");
        this.photoImagePreviewDirectory = photoImagePreviewDirectory;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param photoImageCropDirectory photo image crop directory.
     * @throws IllegalArgumentException if the argument is null or empty string.
     */
    public void setPhotoImageCropDirectory(String photoImageCropDirectory) {
        Helper.checkString(photoImageCropDirectory, "photoImageCropDirectory");
        this.photoImageCropDirectory = photoImageCropDirectory;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param previewPhotoImagePathName preview photo image path name.
     * @throws IllegalArgumentException if the argument is null or empty string.
     */
    public void setPreviewPhotoImagePathName(String previewPhotoImagePathName) {
        Helper.checkString(previewPhotoImagePathName, "previewPhotoImagePathName");
        this.previewPhotoImagePathName = previewPhotoImagePathName;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param previewForwardUrl the preview forward URL.
     * @throws IllegalArgumentException if the argument is null or empty string.
     */
    public void setPreviewForwardUrl(String previewForwardUrl) {
        Helper.checkString(previewForwardUrl, "previewForwardUrl");
        this.previewForwardUrl = previewForwardUrl;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param submittedRedirectUrl the submitted redirect URL.
     * @throws IllegalArgumentException if the argument is null or empty string.
     */
    public void setSubmittedRedirectUrl(String submittedRedirectUrl) {
        Helper.checkString(submittedRedirectUrl, "submittedRedirectUrl");
        this.submittedRedirectUrl = submittedRedirectUrl;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param fileUploaderNamespace the file uploader namespace.
     * @throws IllegalArgumentException if the argument is null or empty string.
     */
    public void setFileUploaderNamespace(String fileUploaderNamespace) {
        Helper.checkString(fileUploaderNamespace, "fileUploaderNamespace");
        this.fileUploaderNamespace = fileUploaderNamespace;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * s
     * @param maxFileSize the max allowed image size (in bytes)
     * @throws IllegalArgumentException if the argument is <= 0
     */
    public void setMaxFileSize(long maxFileSize) {
        checkPositive(maxFileSize, "maxFileSize");
        this.maxFileSize = maxFileSize;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param maxFileExceededErrorName the max file limit exceeded error name.
     * @throws IllegalArgumentException if the argument is null or empty string.
     */
    public void setMaxFileExceededErrorName(String maxFileExceededErrorName) {
        Helper.checkString(maxFileExceededErrorName, "maxFileExceededErrorName");
        this.maxFileExceededErrorName = maxFileExceededErrorName;
    }

    /**
     * <p>
     * Setter for the namesake instance variable.
     * </p>
     * @param memberInformationRetriever the member information retriever.
     * @throws IllegalArgumentException if the argument is null.
     */
    public void setMemberInformationRetriever(MemberInformationRetriever memberInformationRetriever) {
        Helper.checkNull(memberInformationRetriever, "memberInformationRetriever");
        this.memberInformationRetriever = memberInformationRetriever;
    }

    /**
     * <p>
     * Check the state.
     * </p>
     * @throws IllegalStateException if the instance variables are not injected
     *             correctly (refer to each variable's constraint in its
     *             documentation).
     */
    private void checkState() {
        // check 'submittedFlagParameterName' field state
        // Helper.checkState(submittedFlagParameterName,
        // "submittedFlagParameterName");

        // check 'submittedFlagParameterName' field state
        Helper.checkState(memberIdParameterName, "memberIdParameterName");

        // check 'memberIdSessionKey' field state
        Helper.checkState(memberIdSessionKey, "memberIdSessionKey");

        // check 'fileUploaderNamespace' field state
        Helper.checkState(fileUploaderNamespace, "fileUploaderNamespace");

        // check 'photoImageErrorName' field state
        Helper.checkState(photoImageErrorName, "photoImageErrorName");

        // check 'photoImageFormFileName' field state
        Helper.checkState(photoImageFormFileName, "photoImageFormFileName");

        // check 'memberIdErrorName' field state
        Helper.checkState(memberIdErrorName, "memberIdErrorName");

        // check 'validationErrorForwardUrl' field state
        Helper.checkState(validationErrorForwardUrl, "validationErrorForwardUrl");

        // check 'targetImageWidth' field state
        checkIntState(targetImageWidth, "targetImageWidth");

        // check 'targetImageHeight' field state
        checkIntState(targetImageHeight, "targetImageHeight");

        // check 'photoImageSubmittedDirectory' field state
        Helper.checkState(photoImageSubmittedDirectory, "photoImageSubmittedDirectory");

        // check 'photoImageSubmittedDirectory' field state
        Helper.checkState(photoImagePreviewDirectory, "photoImagePreviewDirectory");

        // check 'previewPhotoImagePathName' field state
        Helper.checkState(previewPhotoImagePathName, "previewPhotoImagePathName");

        // check 'previewForwardUrl' field state
        Helper.checkState(previewForwardUrl, "previewForwardUrl");

        // check 'submittedRedirectUrl' field state
        Helper.checkState(submittedRedirectUrl, "submittedRedirectUrl");

        // check 'memberPhotoManager' field state
        checkObjectState(memberPhotoManager, "memberPhotoManager");

        // check 'targetImageLeftCornerXParameterName' field state
        checkObjectState(targetImageLeftCornerXParameterName, "targetImageLeftCornerXParameterName");

        // check 'targetImageLeftCornerYParameterName' field state
        checkObjectState(targetImageLeftCornerYParameterName, "targetImageLeftCornerYParameterName");

        // check 'targetImageRightCornerXParameterName' field state
        checkObjectState(targetImageRightCornerXParameterName, "targetImageRightCornerXParameterName");

        // check 'targetImageRightCornerYParameterName' field state
        checkObjectState(targetImageRightCornerYParameterName, "targetImageRightCornerYParameterName");

        // check 'photoImageCropDirectory' field state
        Helper.checkState(photoImageCropDirectory, "photoImageCropDirectory");

        // check 'maxFileSize' field state
        checkLongState(maxFileSize, "maxFileSize");

        // check 'submittedActionParameterName' field state
        Helper.checkState(submittedActionParameterName, "submittedActionParameterName");

        // check 'maxFileExceededErrorName' field state
        Helper.checkState(maxFileExceededErrorName, "maxFileExceededErrorName");
    }

    /**
     * <p>
     * Checks whether an object is null, if it is, <code>IllegalStateException</code> is thrown.
     * </p>
     * @param param the integer to check
     * @param paramName the parameter name
     * @throws IllegalStateException if the object is null
     */
    private void checkObjectState(Object param, final String paramName) {
        if (param == null) {
            throw new IllegalStateException("The parameter '" + paramName + "' should not be null.");
        }
    }

    /**
     * <p>
     * Checks whether a integer is positive, if it is not, <code>IllegalStateException</code> is thrown.
     * </p>
     * @param param the integer to check
     * @param paramName the parameter name
     * @throws IllegalStateException if the integer is not positive
     */
    private void checkIntState(int param, final String paramName) {
        if (param <= 0) {
            throw new IllegalStateException("The parameter '" + paramName + "' should be positive.");
        }
    }

    /**
     * <p>
     * Checks whether a integer is positive, if it is not, <code>IllegalStateException</code> is thrown.
     * </p>
     * @param param the long to check
     * @param paramName the parameter name
     * @throws IllegalStateException if the integer is not positive
     */
    private void checkLongState(long param, final String paramName) {
        if (param <= 0) {
            throw new IllegalStateException("The parameter '" + paramName + "' should be positive.");
        }
    }

    /**
     * <p>
     * Checks whether a integer is positive, if it is not, <code>IllegalArgumentException</code> is thrown.
     * </p>
     * @param param the integer to check
     * @param paramName the parameter name
     * @throws IllegalArgumentException if the integer is not positive
     */
    private void checkPositive(int param, final String paramName) {
        if (param <= 0) {
            throw new IllegalArgumentException("The parameter '" + paramName + "' should be positive.");
        }
    }

    /**
     * <p>
     * Checks whether a integer is positive, if it is not, <code>IllegalArgumentException</code> is thrown.
     * </p>
     * @param param the long to check
     * @param paramName the parameter name
     * @throws IllegalArgumentException if the integer is not positive
     */
    private void checkPositive(long param, final String paramName) {
        if (param <= 0) {
            throw new IllegalArgumentException("The parameter '" + paramName + "' should be positive.");
        }
    }

    /**
     * Logs the INFO message.
     * @param message
     *            the message to log.
     */
    private void logMsg(String message) {
        if (log != null) {
            log.log(Level.INFO, message);
        }
    }

    /**
     * Logs the ERROR message.
     * @param <T>
     *            the error type.
     * @param message
     *            the message to log.
     * @param e
     *            the error
     * @return the passed in exception.
     */
    private <T extends Throwable> T logMsg(String message, T e) {
        if (log != null) {
            log.log(Level.ERROR, e, message);
        }
        return e;
    }
}
