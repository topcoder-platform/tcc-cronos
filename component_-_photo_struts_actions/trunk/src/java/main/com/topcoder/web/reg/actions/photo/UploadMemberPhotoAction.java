/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.memberphoto.manager.MemberPhotoManagementException;

/**
 * This Struts action is responsible for upload profile photo operation (which includes preview and commit stages,
 * distinguished by HTTP parameter with name referred by submittedActionParameterName configuration parameter). It
 * inherits from BaseMemberPhotoAction and leverages the configured DAOs and managers to perform it's job. Validation
 * of the uploaded file will be performed via Struts FileUploadInterceptor.
 *
 *
 * Thread Safety:
 * This class is mutable and technically not thread safe. However, when used as Spring bean (which is the expected
 * usage), the properties will be populated before execution of the business methods and thus this class will be
 * thread-safe. Utilized external classes are thread-safe under expected usage (i.e. when using in the Spring
 * framework).
 *
 * @author gevak, mumujava
 * @version 1.0
 */
public class UploadMemberPhotoAction extends BaseMemberPhotoAction {
    /**
    * <p>
    * The serial version uid.
    * </p>
    */
    private static final long serialVersionUID = 1L;

    /**
     * Stores the suffix for the redirect destination URL, used in case of successful "preview" operation. Populated
     * in execute() method and expected to be used (if properly configured) by Struts to construct the redirect
     * destination URL in case of successful execution of the "preview" operation.
     * Mutable, has getter. Initially null, but execute() method will never populate it with null value.
     */
    private String previewSuccessUrlSuffix;

    /**
     * HTTP parameter name under which the submitted action name (either "preview" or "commit") will be looked for.
     * It's used in execute() method to distinguish between "preview" and "commit" operations.
     * Has default value. Mutable, has getter and setter. Technically can be any value. Expected to be injected by
     * Spring and @PostConstruct method will ensure that it's not null and not empty.
     */
    private String submittedActionParameterName = "photoUploadAction";

    /**
     * Mapping between allowed content types and their corresponding file extensions. Key is the MIME type for the
     * uploaded file and corresponding value is the file extension (without ".") to be used to save the uploaded
     * photo file on disc.  Please note that validation of content type will be performed via Struts
     * FileUploadInterceptor. This variable is used in execute() method.
     * Mutable, has getter and setter. Technically can be any value. Expected to be injected by Spring and
     * @PostConstruct method will ensure that it's not null, not empty and doesn't contain null/empty key/values.
     */
    private Map<String, String> allowedContentTypesFileExtensions;

    /**
     * Path of the directory where the uploaded preview photo images are stored. It's used in execute() method.
     * Mutable, has getter and setter. Technically can be any value. Expected to be injected by Spring and
     * @PostConstruct method will ensure that it's not null and not empty.
     */
    private String photoPreviewDirectory;

    /**
     * HTTP parameter name under which the uploaded preview image file (stored on server in the directory referred by
     * photoPreviewDirectory) name will be passed in redirect URL during "preview" operation and looked for during
     * "commit" operation. It's used in execute() method.
     * Has default value. Mutable, has getter and setter. Technically can be any value. Expected to be injected by
     * Spring and @PostConstruct method will ensure that it's not null and not empty.
     */
    private String previewImageFileNameParameterName = "previewImageFileName";

    /**
     * HTTP parameter name under which the original (remote) image file name will be passed in redirect URL during
     * "preview" operation (not used during "commit" operation). It's used in execute() method.
     * Has default value. Mutable, has getter and setter. Technically can be any value. Expected to be injected by
     * Spring and @PostConstruct method will ensure that it's not null and not empty.
     */
    private String originalImageFileNameParameterName = "originalFileName";

    /**
     * Name of HTTP parameter for Left corner X-coordinate for cropping. Represent the target image's left upper
     * corner X coordinate parameter name which will be used to get  the input data from JCrop. The data will be
     * measured in pixels. It's used in execute() method.
     * Has default value. Mutable, has getter and setter. Technically can be any value. Expected to be injected by
     * Spring and @PostConstruct method will ensure that it's not null and not empty.
     */
    private String targetImageLeftCornerXParameterName = "targetImageLeftCornerX";

    /**
     * Name of HTTP parameter for Left corner Y-coordinate for cropping. Represent the target image's left upper corner
     * Y coordinate parameter name which will be used to get  the input data from JCrop. The data will be measured in
     * pixels. It's used in execute() method.
     * Has default value. Mutable, has getter and setter. Technically can be any value. Expected to be injected by
     * Spring and @PostConstruct method will ensure that it's not null and not empty.
     */
    private String targetImageLeftCornerYParameterName = "targetImageLeftCornerY";

    /**
     * Name of HTTP parameter for Right corner X-coordinate for cropping. Represent the target image's right bottom
     * corner X coordinate parameter name which will be used to get  the input data from JCrop. The data will be
     * measured in pixels. It's used in execute() method.
     * Has default value. Mutable, has getter and setter. Technically can be any value. Expected to be injected by
     * Spring and @PostConstruct method will ensure that it's not null and not empty.
     */
    private String targetImageRightCornerXParameterName = "targetImageRightCornerX";

    /**
     * Name of HTTP parameter for Right corner Y-coordinate for cropping. Represent the target image's left bottom
     * corner Y coordinate parameter name which will be used to get  the input data from JCrop. The data will be
     * measured in pixels. It's used in execute() method.
     * Has default value. Mutable, has getter and setter. Technically can be any value. Expected to be injected by
     * Spring and @PostConstruct method will ensure that it's not null and not empty.
     */
    private String targetImageRightCornerYParameterName = "targetImageRightCornerY";

    /**
     * Refers to uploaded file. It's used in execute() method.
     * Mutable. Populated by Struts FileUploadInterceptor via setter.
     */
    private File file;

    /**
     * MIME content type of uploaded file. It's used in execute() method.
     * Mutable. Populated by Struts FileUploadInterceptor via setter.
     */
    private String contentType;

    /**
     * Remote filename of the uploaded file. It's used in execute() method.
     * Mutable. Populated by Struts FileUploadInterceptor via setter.
     */
    private String filename;

    /**
     * Target image width. It's used in execute() method.
     * Mutable, has getter and setter. Technically can be any value. Expected to be injected by Spring and
     * @PostConstruct method will ensure that it's positive.
     */
    private int targetImageWidth;

    /**
     * Target image height. It's used in execute() method.
     * Mutable, has getter and setter. Technically can be any value. Expected to be injected by Spring and
     * @PostConstruct method will ensure that it's positive.
     */
    private int targetImageHeight;
    /**
     * The http servlet request.
     */
    private HttpServletRequest request;

    /**
     * Default empty constructor.
     */
    public UploadMemberPhotoAction() {
    }

    /**
     * This method will be called by Spring framework after initialization (configuration) of this bean. It
     * validates the configuration parameters.
     *
     * @throws MemberPhotoActionConfigurationException - if any configuration parameter has invalid value as per
     * class variable docs.
     */
    @Override
    @PostConstruct
    public void checkParameters() {
        super.checkParameters();
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(submittedActionParameterName,
                "submittedActionParameterName", MemberPhotoActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmpty(allowedContentTypesFileExtensions,
                "allowedContentTypesFileExtensions", MemberPhotoActionConfigurationException.class);
        ValidationUtility.checkNotNullKeys(allowedContentTypesFileExtensions,
                "allowedContentTypesFileExtensions", MemberPhotoActionConfigurationException.class);
        ValidationUtility.checkNotNullValues(allowedContentTypesFileExtensions,
                "allowedContentTypesFileExtensions", MemberPhotoActionConfigurationException.class);
        ValidationUtility.checkNotEmptyKeys(allowedContentTypesFileExtensions, true,
                "allowedContentTypesFileExtensions", MemberPhotoActionConfigurationException.class);
        ValidationUtility.checkNotEmptyValues(allowedContentTypesFileExtensions, true,
                "allowedContentTypesFileExtensions", MemberPhotoActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(photoPreviewDirectory, "photoPreviewDirectory",
                MemberPhotoActionConfigurationException.class);

        ValidationUtility.checkNotNullNorEmptyAfterTrimming(previewImageFileNameParameterName,
                "previewImageFileNameParameterName", MemberPhotoActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(originalImageFileNameParameterName,
                "originalImageFileNameParameterName", MemberPhotoActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(targetImageLeftCornerXParameterName,
                "targetImageLeftCornerXParameterName", MemberPhotoActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(targetImageLeftCornerYParameterName,
                "targetImageLeftCornerYParameterName", MemberPhotoActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(targetImageRightCornerXParameterName,
                "targetImageRightCornerXParameterName", MemberPhotoActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(targetImageRightCornerYParameterName,
                "targetImageRightCornerYParameterName", MemberPhotoActionConfigurationException.class);

        ValidationUtility.checkPositive(targetImageWidth, "targetImageWidth",
                MemberPhotoActionConfigurationException.class);
        ValidationUtility.checkPositive(targetImageHeight, "targetImageHeight",
                MemberPhotoActionConfigurationException.class);
    }

    /**
     * This is where the logic of the action is executed. It allows web site administrators to upload member photos.
     * This logic is divided into 2 parts:
     * 1) Preview (uploads file to preview directory and redirects to proper URL).
     * 2) Commit (saves uploaded image to DB, performs audit, etc).
     *
     * @throws IllegalStateException if {submit action is determined as "preview"} and {file is null or contentType
     *         or filename is null/empty}.
     * @throws MemberPhotoUploadException if any other error occurs.
     * @return A string representing the logical result of the execution.
     */
    @Override
    public String execute() throws MemberPhotoUploadException {
        final String methodSignature = "UploadMemberPhotoAction.execute";
        LoggingWrapperUtility.logEntrance(getLog(), methodSignature, null, null);
        logRequestParameter(request);
        try {
            long userId = getActiveUserId(request, methodSignature);
            // Load User info from DAO:
            User user = getUserDAO().find(userId);
            if (user == null) {
                addActionError("No user found with the specified id");
                throw LoggingWrapperUtility.logException(getLog(), methodSignature,
                        new MemberPhotoUploadException("No user found with the specified id " + userId));
            }
            String submittedAction = request.getParameter(submittedActionParameterName);
            if (!"commit".equals(submittedAction)) {
                preview(methodSignature, user);
                LoggingWrapperUtility.logExit(getLog(), methodSignature, new Object[]{"previewSuccess"});
                return "previewSuccess";
            } else {
                //commit
                String previewFileName = commit(methodSignature, userId);

                // Make audit record:
                AuditRecord auditRecord = new AuditRecord();
                auditRecord.setOperationType("Upload Profile Photo");
                auditRecord.setHandle(user.getHandle());
                auditRecord.setIpAddress(request.getRemoteAddr());
                auditRecord.setNewValue(previewFileName);
                auditRecord.setTimestamp(new Date());
                getAuditDAO().audit(auditRecord);
            }
        } catch (IOException e) {
            addActionError("IO error occurs when removing the photo");
            throw LoggingWrapperUtility.logException(getLog(), methodSignature,
                    new MemberPhotoUploadException("I/O occurs when preview/save the member photo", e));
        } catch (MemberPhotoManagementException e) {
            addActionError("MemberPhotoManagementExceptionoccurs when removing the photo");
            throw LoggingWrapperUtility.logException(getLog(), methodSignature,
                    new MemberPhotoUploadException("Error occurs when save the member photo", e));
        }
        LoggingWrapperUtility.logExit(getLog(), methodSignature, new Object[]{SUCCESS});
        return SUCCESS;
    }

    /**
     * Commits the image.
     *
     * @param methodSignature the method signature
     * @param userId the user id
     * @return the preview image path
     * @throws MemberPhotoManagementException if error occurs when retrieve the user photo
     * @throws IOException if any IO error occurs
     * @throws MemberPhotoUploadException if any other error occurs
     */
    private String commit(final String methodSignature, long userId) throws MemberPhotoUploadException,
        IOException, MemberPhotoManagementException {
        BufferedInputStream imageStream = null;
        try {

            // Crop and resize image:
            String previewFileName = request.getParameter(previewImageFileNameParameterName);
            if (previewFileName == null || previewFileName.trim().isEmpty()) {
                addActionError("No request parameter for " + previewImageFileNameParameterName);
                throw LoggingWrapperUtility.logException(getLog(), methodSignature, new MemberPhotoUploadException(
                        "No request parameter for " + previewImageFileNameParameterName));
            }
            int lastIndexOf = previewFileName.lastIndexOf('.');
            if (lastIndexOf < 0 || lastIndexOf == previewFileName.length() - 1) {
                addActionError("Unable to extract extension name");
                throw LoggingWrapperUtility.logException(getLog(), methodSignature, new MemberPhotoUploadException(
                        "Unable to extract extension name from previewFileName '" + previewFileName + "'"));
            }
            //extract extension from previewFileName;
            String fileExtension = previewFileName.substring(lastIndexOf + 1);
            imageStream = new BufferedInputStream(new FileInputStream(
                    new File(photoPreviewDirectory, previewFileName)));
            BufferedImage originalImage = ImageIO.read(imageStream);
            BufferedImage resizedImage = resizedImage(methodSignature, originalImage);
            //  Save image to storing path:
            //Create directory referred by photoStoredDirectory if not exists yet;
            File storedImageFile = new File(getPhotoStoredDirectory(), previewFileName);
            ImageIO.write(resizedImage, fileExtension, storedImageFile);
            getMemberPhotoManager().saveMemberPhoto(userId, storedImageFile.getAbsolutePath(),
                    String.valueOf(userId));
            return previewFileName;
        } finally {
            if (imageStream != null) {
                imageStream.close();
            }
        }
    }

    /**
     * Resize the image.
     *
     * @param methodSignature the method signature
     * @param originalImage the original image
     * @return the resized image
     * @throws MemberPhotoUploadException if any error occurs
     */
    private BufferedImage resizedImage(final String methodSignature, BufferedImage originalImage)
        throws MemberPhotoUploadException {
        BufferedImage resizedImage;
        int imageWidth = originalImage.getWidth();
        int imageHeight = originalImage.getHeight();
        if ((imageWidth == 0) || (imageHeight == 0)) {
            addActionError("Either width or height of original image is 0.");
            throw LoggingWrapperUtility.logException(getLog(), methodSignature,
                    new MemberPhotoUploadException("Either width or height of original image is 0."));
        }
        try {
        // Retrieve crop info:
        int targetImageLeftCornerX = getIntegerParameter(targetImageLeftCornerXParameterName);
        int targetImageLeftCornerY = getIntegerParameter(targetImageLeftCornerYParameterName);
        int targetImageRightCornerX = getIntegerParameter(targetImageRightCornerXParameterName);
        int targetImageRightCornerY = getIntegerParameter(targetImageRightCornerYParameterName);
        // crop
        if (targetImageLeftCornerX != -1 && targetImageLeftCornerY != -1
                && targetImageRightCornerX != -1 && targetImageRightCornerY != -1) {
            imageWidth = targetImageRightCornerX - targetImageLeftCornerX;
            imageHeight = targetImageRightCornerY - targetImageLeftCornerY;
            // scale the image.
            double ratio = targetImageWidth / (double) imageWidth;
            int resizedWidth = (int) (originalImage.getWidth() * ratio);
            int resizedHeight = (int) (originalImage.getHeight() * ratio);
            resizedImage = new BufferedImage(resizedWidth, resizedHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2D = resizedImage.createGraphics();
            g2D.drawImage(originalImage, 0, 0, resizedWidth, resizedHeight, null);
            int startX = (int) (targetImageLeftCornerX * ratio);
            int startY = (int) (targetImageLeftCornerY * ratio);
            resizedImage = resizedImage.getSubimage(startX, startY, targetImageWidth,
                    targetImageHeight);
        } else {
            if ((imageWidth != targetImageWidth) || (imageHeight != targetImageHeight)) {
                // scale the image.
                resizedImage = new BufferedImage(targetImageWidth, targetImageHeight,
                        BufferedImage.TYPE_INT_RGB);
                Graphics2D g2D = resizedImage.createGraphics();
                g2D.drawImage(originalImage, 0, 0, targetImageWidth, targetImageHeight, null);
            } else {
                resizedImage = originalImage;
            }
        }
        } catch (RasterFormatException e) {
            addActionError("Failed to resize the image.");
            throw LoggingWrapperUtility.logException(getLog(), methodSignature,
                    new MemberPhotoUploadException("Failed to resize the image.", e));
        }
        return resizedImage;
    }

    /**
     * Preview the image.
     *
     * @param methodSignature the method signature.
     * @param user the user
     * @throws IllegalStateException  if {submit action is determined as "preview"} and {file is null or contentType
     *         or filename is null/empty}.
     * @throws IOException if any io error occurs
     * @throws MemberPhotoUploadException if other error occurs
     */
    private void preview(final String methodSignature, User user) throws MemberPhotoUploadException, IOException {
        ValidationUtility.checkNotNull(file,
                "file", IllegalStateException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(contentType,
                "contentType", IllegalStateException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(filename,
                "filename", IllegalStateException.class);

        // preview is the default action
        // Get the uploaded file extension
        String fileExtension = allowedContentTypesFileExtensions.get(contentType);
        if (fileExtension == null) {
            addActionError("The contentType is not allowed");
            throw LoggingWrapperUtility.logException(getLog(), methodSignature,
                    new MemberPhotoUploadException("The contentType '" + contentType
                            + "' is not allowed"));
        }
        // Create directory referred by photoPreviewDirectory if not exists yet;
        new File(photoPreviewDirectory).mkdirs();
        // Save uploaded file to proper directory on disk:
        String path = new File(photoPreviewDirectory, user.getHandle() + "." + fileExtension)
                .getAbsolutePath();
        if(!file.isFile()) {
            addActionError("The uploaded file is not a valid file");
            throw LoggingWrapperUtility.logException(getLog(), methodSignature,
                new MemberPhotoUploadException("The uploaded file '" + file.getPath() + "' is not a valid file"));
        }
        synchronized (this) {
            // Move file referred by "file" class variable to location specified in "path" variable.
            move(file.getPath(), path);
        }

        //Redirect request to photo preview page:
        previewSuccessUrlSuffix = "&" + previewImageFileNameParameterName + "=" + user.getHandle()
                + "." + fileExtension + "&" + originalImageFileNameParameterName + "=" + filename;
    }

    /**
     * Retrieve the current user id from BasicAuthentication.
     * @param request the http request.
     * @param methodSignature the method signature
     * @throws MemberPhotoUploadException if any error occurs
     * @return the user id
     * */
    private long getActiveUserId(HttpServletRequest request, String methodSignature)
        throws MemberPhotoUploadException {
        BasicAuthentication auth = (BasicAuthentication) request.getSession().getAttribute(
                getAuthenticationSessionKey());
        if (auth == null) {
            addActionError("The BasicAuthentication should be injected.");
            throw LoggingWrapperUtility.logException(getLog(), methodSignature,
                    new MemberPhotoUploadException("The BasicAuthentication should be injected."));
        }
        com.topcoder.shared.security.User activeUser = auth.getActiveUser();
        if (activeUser == null) {
            addActionError("No active user in the session.");
            throw LoggingWrapperUtility.logException(getLog(), methodSignature,
                    new MemberPhotoUploadException("No active user in the session."));
        }
        long userId = activeUser.getId();
        return userId;
    }
    /**
     * Gets an integer parameter.
     * @param param the parameter name
     * @return the parameter
     */
    private int getIntegerParameter(String param) {
        String att = request.getParameter(param);
        if (att != null) {
            try {
                return Integer.parseInt(att);
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        return -1;
    }

    /**
     * Get The suffix for the redirect destination URL, used in case of successful "preview" operation.
     *
     * @return The suffix for the redirect destination URL, used in case of successful "preview" operation.
     */
    public String getPreviewSuccessUrlSuffix() {
        return previewSuccessUrlSuffix;
    }

    /**
     * Get HTTP parameter name under which the submitted action name (either "preview" or "commit") will be looked for.
     *
     * @return HTTP parameter name under which the submitted action name (either "preview" or "commit")
     * will be looked for.
     */
    public String getSubmittedActionParameterName() {
        return submittedActionParameterName;
    }

    /**
     * Set HTTP parameter name under which the submitted action name (either "preview" or "commit") will be looked for.
     *
     * @param submittedActionParameterName HTTP parameter name under which the submitted action name
     * (either "preview" or "commit") will be looked for.
     */
    public void setSubmittedActionParameterName(String submittedActionParameterName) {
        this.submittedActionParameterName = submittedActionParameterName;
    }

    /**
     * Get Mapping between allowed content types and their corresponding file extensions.
     * Key is the MIME type for the uploaded file and corresponding value is the file extension
     * (without ".") to be used to save the uploaded photo file on disc.
     *
     * @return Mapping between allowed content types and their corresponding file extensions.
     */
    public Map<String, String> getAllowedContentTypesFileExtensions() {
        return allowedContentTypesFileExtensions;
    }

    /**
     * Set Mapping between allowed content types and their corresponding file extensions.
     * Key is the MIME type for the uploaded file and corresponding value is the file extension (without ".")
     * to be used to save the uploaded photo file on disc.
     *
     * @param allowedContentTypesFileExtensions Mapping between allowed content types and their
     * corresponding file extensions.
     */
    public void setAllowedContentTypesFileExtensions(Map<String, String> allowedContentTypesFileExtensions) {
        this.allowedContentTypesFileExtensions = allowedContentTypesFileExtensions;
    }

    /**
     * Get Path of the directory where the uploaded preview photo images are stored.
     *
     * @return Path of the directory where the uploaded preview photo images are stored.
     */
    public String getPhotoPreviewDirectory() {
        return photoPreviewDirectory;
    }

    /**
     * Set Path of the directory where the uploaded preview photo images are stored.
     *
     * @param photoPreviewDirectory Path of the directory where the uploaded preview photo images are stored.
     */
    public void setPhotoPreviewDirectory(String photoPreviewDirectory) {
        this.photoPreviewDirectory = photoPreviewDirectory;
    }

    /**
     * Get HTTP parameter name under which the uploaded preview image file (stored on server in the directory referred
     * by photoPreviewDirectory) name will be passed in redirect URL during "preview" operation and looked for during
     * "commit" operation.
     *
     * @return HTTP parameter name under which the uploaded preview image file (stored on server in the directory
     * referred by photoPreviewDirectory) name will be passed in redirect URL during "preview" operation and looked
     * for during "commit" operation.
     */
    public String getPreviewImageFileNameParameterName() {
        return previewImageFileNameParameterName;
    }

    /**
     * Set HTTP parameter name under which the uploaded preview image file (stored on server in the directory referred
     * by photoPreviewDirectory) name will be passed in redirect URL during "preview" operation and looked for during
     * "commit" operation.
     *
     * @param previewImageFileNameParameterName HTTP parameter name under which the uploaded preview image file
     * (stored on server in the directory referred by photoPreviewDirectory) name will be passed in redirect URL
     * during "preview" operation and looked for during "commit" operation.
     */
    public void setPreviewImageFileNameParameterName(String previewImageFileNameParameterName) {
        this.previewImageFileNameParameterName = previewImageFileNameParameterName;
    }

    /**
     * Get HTTP parameter name under which the original (remote) image file name will be passed in redirect URL during
     * "preview" operation (not used during "commit" operation).
     *
     * @return HTTP parameter name under which the original (remote) image file name will be passed in redirect URL
     * during "preview" operation (not used during "commit" operation).
     */
    public String getOriginalImageFileNameParameterName() {
        return originalImageFileNameParameterName;
    }

    /**
     * Set HTTP parameter name under which the original (remote) image file name will be passed in redirect URL
     * during "preview" operation (not used during "commit" operation).
     *
     * @param originalImageFileNameParameterName HTTP parameter name under which the original (remote) image file
     * name will be passed in redirect URL during "preview" operation (not used during "commit" operation).
     */
    public void setOriginalImageFileNameParameterName(String originalImageFileNameParameterName) {
        this.originalImageFileNameParameterName = originalImageFileNameParameterName;
    }

    /**
     * Get Name of HTTP parameter for Left corner X-coordinate for cropping.
     *
     * @return Name of HTTP parameter for Left corner X-coordinate for cropping.
     */
    public String getTargetImageLeftCornerXParameterName() {
        return targetImageLeftCornerXParameterName;
    }

    /**
     * Set Name of HTTP parameter for Left corner X-coordinate for cropping.
     *
     * @param targetImageLeftCornerXParameterName Name of HTTP parameter for  Left corner X-coordinate for cropping.
     */
    public void setTargetImageLeftCornerXParameterName(String targetImageLeftCornerXParameterName) {
        this.targetImageLeftCornerXParameterName = targetImageLeftCornerXParameterName;
    }

    /**
     * Get Name of HTTP parameter for Left corner Y-coordinate for cropping.
     *
     * @return Name of HTTP parameter for Left corner Y-coordinate for cropping.
     */
    public String getTargetImageLeftCornerYParameterName() {
        return targetImageLeftCornerYParameterName;
    }

    /**
     * Set Name of HTTP parameter for  Left corner Y-coordinate for cropping.
     *
     * @param targetImageLeftCornerYParameterName Name of HTTP parameter for Left corner Y-coordinate for cropping.
     */
    public void setTargetImageLeftCornerYParameterName(String targetImageLeftCornerYParameterName) {
        this.targetImageLeftCornerYParameterName = targetImageLeftCornerYParameterName;
    }

    /**
     * Get Name of HTTP parameter for Right corner X-coordinate for cropping.
     *
     * @return Name of HTTP parameter for Right corner X-coordinate for cropping.
     */
    public String getTargetImageRightCornerXParameterName() {
        return targetImageRightCornerXParameterName;
    }

    /**
     * Set Name of HTTP parameter for Right corner X-coordinate for cropping.
     *
     * @param targetImageRightCornerXParameterName Name of HTTP parameter for Right corner X-coordinate for cropping.
     */
    public void setTargetImageRightCornerXParameterName(String targetImageRightCornerXParameterName) {
        this.targetImageRightCornerXParameterName = targetImageRightCornerXParameterName;
    }

    /**
     * Get Name of HTTP parameter for  Right corner Y-coordinate for cropping.
     *
     * @return Name of HTTP parameter for Right corner Y-coordinate for cropping.
     */
    public String getTargetImageRightCornerYParameterName() {
        return targetImageRightCornerYParameterName;
    }

    /**
     * Set Name of HTTP parameter for Right corner Y-coordinate for cropping.
     *
     * @param targetImageRightCornerYParameterName  Name of HTTP parameter for Right corner Y-coordinate for cropping.
     */
    public void setTargetImageRightCornerYParameterName(String targetImageRightCornerYParameterName) {
        this.targetImageRightCornerYParameterName = targetImageRightCornerYParameterName;
    }

    /**
     * Sets File object, which refers to uploaded file.
     *
     * @param file File object, which refers to uploaded file.
     */
    public void setUpload(File file) {
        this.file = file;
    }

    /**
     * Sets MIME content type of uploaded file.
     *
     * @param contentType MIME content type of uploaded file.
     */
    public void setUploadContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Sets Remote filename of the uploaded file.
     *
     * @param filename Remote filename of the uploaded file.
     */
    public void setUploadFileName(String filename) {
        this.filename = filename;
    }

    /**
     * Gets Target image width.
     *
     * @return Target image width.
     */
    public int getTargetImageWidth() {
        return targetImageWidth;
    }

    /**
     * Sets Target image width.
     *
     * @param targetImageWidth Target image width.
     */
    public void setTargetImageWidth(int targetImageWidth) {
        this.targetImageWidth = targetImageWidth;
    }

    /**
     * Gets Target image height.
     *
     * @return Target image height.
     */
    public int getTargetImageHeight() {
        return targetImageHeight;
    }

    /**
     * Sets Target image height.
     *
     * @param targetImageHeight Target image height.
     */
    public void setTargetImageHeight(int targetImageHeight) {
        this.targetImageHeight = targetImageHeight;
    }

    /**
     * Sets the http servlet request.
     *
     * @param request the http servlet request.
     */
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}
