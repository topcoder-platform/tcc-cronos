/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.orpheus.game.GameOperationLogicUtility;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.ImageInfo;
import com.orpheus.game.persistence.entities.DomainImpl;
import com.orpheus.game.persistence.entities.ImageInfoImpl;
import com.topcoder.servlet.request.FileDoesNotExistException;
import com.topcoder.servlet.request.FileUpload;
import com.topcoder.servlet.request.FileUploadResult;
import com.topcoder.servlet.request.LocalFileUpload;
import com.topcoder.servlet.request.UploadedFile;
import com.topcoder.user.profile.BaseProfileType;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>A custom handler to be plugged into sponsor registration workflow. The purpose of this handler is to save the
 * details for domains and images submitted by sponsor to persistent data store as currently there is no such handler
 * provided by the existing <code>Web Registration</code> component.</p>
 *
 * @author isv
 * @version 1.0
 */
public class SponsorRegistrationHandler implements Handler {

    /**
     * <p>A <code>String</code> representing the name of session attribute used to store <code>UserProfile</code>.</p>
     */
    private static final String USER_PROFILE_SESSION_ATTR = "_user_profile";

    /**
     * <p>A <code>UserProfileManager</code> to be used to get the <code>UserProfile</code> corresponding to registered
     * sponsor.
     */
    private final UserProfileManager profileManager;

    /**
     * <p>Constructs new <code>SponsorRegistrationHandler</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>Here is an example of the xml element:</p>
     *
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *         &lt;object_factory&gt;
     *             &lt;namespace&gt;com.topcoder.web.user.objectfactory&lt;/namespace&gt;
     *             &lt;user_profile_manager_key&gt;user_profile_manager&lt;/user_profile_manager_key&gt;
     *         &lt;/object_factory&gt;
     *     &lt;/handler&gt;
     * </pre>
     *
     * @param config an <code>Element</code> providing the configuration parameters for this instance.
     */
    public SponsorRegistrationHandler(Element config) {
        if (config == null) {
            throw new IllegalArgumentException("The parameter [config] is NULL");
        }
        String ns = getValue(config, "/handler/object_factory/namespace");
        String key = getValue(config, "/handler/object_factory/user_profile_manager_key");
        try { // create profileManager via 'ns' and 'key'
            ConfigManagerSpecificationFactory cmsf = new ConfigManagerSpecificationFactory(ns);
            this.profileManager = (UserProfileManager) new ObjectFactory(cmsf).createObject(key);
        } catch (SpecificationConfigurationException e) {
            throw new IllegalArgumentException("Failed to create ConfigManagerSpecificationFactory, caused by "
                                               + e.getMessage());
        } catch (IllegalReferenceException e) {
            throw new IllegalArgumentException("Failed to create ConfigManagerSpecificationFactory, caused by "
                                               + e.getMessage());
        } catch (InvalidClassSpecificationException e) {
            throw new IllegalArgumentException("Failed to create ProfileManager, caused by " + e.getMessage());
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Failed to create ProfileManager, The key:" + key
                                               + " defined in object factory is not type of ProfileManager.");
        }
    }

    /**
     * <p>Processes the incoming request. Obtains the details for domains and images submitted by the sponsor during
     * registration and saves them to persistent data store thus completing the sponsor registration process.</p>
     *
     * @param context an <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return <code>null</code> if request has been serviced successfully. Otherwise an exception will be raised.
     * @throws HandlerExecutionException if un unrecoverable error prevents the successful request processing.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }

        try {
            // Gather the info for domains and associated images to be saved to DB
            Domain[] domains = getDomains(context);

            GameOperationLogicUtility golu = GameOperationLogicUtility.getInstance();
            if (golu.isUseLocalInterface()) {
                GameDataLocal gameData = golu.getGameDataLocalHome().create();
                for (int i = 0; i < domains.length; i++) {
                    gameData.createDomain(domains[i]);
                }
            } else {
                GameData gameData = golu.getGameDataRemoteHome().create();
                for (int i = 0; i < domains.length; i++) {
                    gameData.createDomain(domains[i]);
                }
            }
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not register domains specified by sponsor", e);
        }
        return null;
    }

    /**
     * <p>Gets the list of domains and associated images submitted by sponsor.</p>
     *
     * @param context a <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return a <code>Domain</code> array providing the details for domains submitted by sponsor during registration.
     * @throws Exception if an unexpected error occurs.
     */
    private Domain[] getDomains(ActionContext context) throws Exception {
        List domains = new ArrayList();

        // Read the uploaded image files
        HttpServletRequest request = context.getRequest();
        FileUpload fileUpload = new LocalFileUpload("com.orpheus.game.server.FileUpload");
        FileUploadResult fileUploadResult = fileUpload.uploadFiles(request);

        // Get the sponsor profile saved by registration and find the profile persisted in DB, remove profile from
        // session
        HttpSession session = request.getSession(false);
        UserProfile profile = (UserProfile) session.getAttribute(USER_PROFILE_SESSION_ATTR);
        session.removeAttribute(USER_PROFILE_SESSION_ATTR);
        Map searchParams = new HashMap();
        String emailAddress = (String) profile.getProperty(BaseProfileType.EMAIL_ADDRESS);
        searchParams.put(BaseProfileType.EMAIL_ADDRESS, emailAddress);
        UserProfile[] userProfiles = this.profileManager.searchUserProfiles(searchParams);
        if ((userProfiles == null) || (userProfiles.length != 1)) {
            throw new HandlerExecutionException("The sponsor profile for email address [" + emailAddress
                                                + "] is not found in persistent data store");
        }
        Long sponsorId = (Long) userProfiles[0].getIdentifier();

        // Obtain the appropriate EJB based on configuration settings
        GameOperationLogicUtility golu = GameOperationLogicUtility.getInstance();
        GameData gameData = null;
        GameDataLocal gameDataLocal = null;
        if (golu.isUseLocalInterface()) {
            gameDataLocal = golu.getGameDataLocalHome().create();
        } else {
            gameData = golu.getGameDataRemoteHome().create();
        }

        // Create domains by analyzing the request parameters and submitted images
        long imageId;
        String imageDescription;
        List images = new ArrayList();
        for (int i = 1; i <= 5; i++){
            String domainName = request.getParameter("domain_" + i);
            if (domainName != null) {
                images.clear();
                for (int j = 1; j <= 5; j++) {
                    try {
                        imageDescription = request.getParameter("imageName_" + i + "_" + j);
                        if (imageDescription != null) {
                            UploadedFile uploadedFile = fileUploadResult.getUploadedFile("imageUpload_" + i + "_" + j);
                            if (golu.isUseLocalInterface()) {
                                imageId
                                    = gameDataLocal.recordBinaryObject(imageDescription, uploadedFile.getContentType(),
                                                                       getFileContent(uploadedFile));
                            } else {
                                imageId = gameData.recordBinaryObject(imageDescription, uploadedFile.getContentType(),
                                                                      getFileContent(uploadedFile));
                            }
                            images.add(new ImageInfoImpl(null, imageId, imageDescription, null));
                        }
                    } catch (FileDoesNotExistException e) {
                        // This error is ignored as we have no other way to check if the file exists in FileUpload
                    }
                }
                domains.add(new DomainImpl(null, sponsorId, request.getParameter("domain_" + i),
                                           null, (ImageInfo[]) images.toArray(new ImageInfoImpl[images.size()])));
            }
        }
        return (Domain[]) domains.toArray(new Domain[domains.size()]);
    }

    /**
     * <p>Gets the content of an image file uploaded by the sponsor during registration.</p>
     *
     * @param uploadedFile an <code>UploadedFile</code> representing an image file uploaded by sponsor during
     *        registration.
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

    /**
     * <p>Gets the textual value of the element matching the specified <code>XPath</code> expression.</p>
     *
     * @param element an <code>Element</code> to get the textual value for.
     * @param xpath a <code>String</code> providing the <code>XPath</code> expression to be used for locating the
     *        desired element.
     * @return a <code>String</code> providing the text value of specified element.
     */
    private static final String getValue(Element element, String xpath) {
        Element targetElement = getElement(element, xpath);
        // IllegalArgumentException will be thrown if can not find
        if (targetElement == null) {
            throw new IllegalArgumentException("The xml is invalid. " + xpath + " is missing.");
        }
        return getValue(targetElement);
    }

    /**
     * <p>Gets the textual value of specified element.</p>
     *
     * @param element an <code>Element</code> to get the textual value for.
     * @return a <code>String</code> providing the text value of specified element.
     * @throws IllegalArgumentException if element contains no content or the node content is empty.
     */
    private static final String getValue(Element element) {
        // The first child text_node be treat as value of element
        NodeList children = element.getChildNodes();

        for (int i = 0; i < children.getLength(); ++i) {
            Node child = children.item(i);

            if (child.getNodeType() == Node.TEXT_NODE) {
                String value = child.getNodeValue();
                if (value.trim().length() == 0) {
                    throw new IllegalArgumentException("The element contains empty value.");
                }
                return value;
            }
        }

        throw new IllegalArgumentException("The element contains no content.");
    }

    /**
     * <p>Get the element matching the given <code>XPath</code> expression.</p>
     *
     * @param element an <code>Element</code> to find the nested element in.
     * @param xpath a <code>String</code> providing the <code>XPath</code> expression to be used for locating the
     *        desired element.
     * @return a <code>Element</code> matching the specified path or <code>null</code> if such element is not found.
     */
    private static final Element getElement(Element element, String xpath) {
        if (xpath.startsWith("/")) {
            xpath = xpath.substring(1);
        }
        String[] paths = xpath.split("/");
        if (element.getNodeName().equals(paths[0])) {
            if (paths.length == 1) {
                return element;
            } else {
                NodeList subNodes = element.getElementsByTagName(paths[1]);
                // In fact, there should be no multiple nodes of same name nested within a same element in context of
                // Web Application User Logic component
                if (subNodes.getLength() > 1) {
                    throw new IllegalArgumentException("Invalid XML content. There are multiple [" + paths[1]
                                                       + "] nodes");
                } else if (subNodes.getLength() == 1) {
                    return getElement((Element) subNodes.item(0), xpath.substring(xpath.indexOf('/', 1)));
                }
            }
        }
        // The requested element is not found
        return null;
    }
}
