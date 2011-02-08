/*
 * Copyright (C) 2008, 2011 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.web.memberphoto.manager;

import java.text.MessageFormat;
import java.util.Date;

import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.memberphoto.entities.Image;
import com.topcoder.web.memberphoto.entities.MemberImage;

/**
 * <p>
 * This class is a default implementation of <code>{@link MemberPhotoManager}</code> interface and implements the
 * following operations: retrieve member photo, save member photo and remove member photo. It's fields can be
 * initialized or set using setters or using various constructors.
 * </p>
 *
 * <p>
 * This design extensively reference operations from <code>{@link MemberPhotoDAO}</code> (delegates to its
 * implementation provided). <em>Logging Wrapper 2.0</em> is used to perform the needed logging operations.
 * </p>
 *
 * <p>
 * Changes in 2.0:
 * <ol>
 * <li>Added byUser parameter to saveMemberPhoto() and removeMemberPhoto() methods (to be used for auditing).</li>
 * <li>Added getMemberPhotos() method for retrieving recently added/updated member images.</li>
 * </ol>
 * </p>
 *
 * <p>
 * The sample api usage is as given below.
 *
 * <pre>
 * // configure the EntityManager:
 * EntityManagerFactory emf = Persistence.createEntityManagerFactory(&quot;memberPhotoManager&quot;);
 * EntityManager entityManager = emf.createEntityManager();
 *
 * // create a MemberPhotoDAO using the obtained EntityManager:
 * MemberPhotoDAO memberPhotoDAO = new JPAMemberPhotoDAO(entityManager);
 *
 * // create a MemberPhotoManager using the default constructor:
 * MemberPhotoManager memberPhotoManager = new MemberPhotoManagerImpl(memberPhotoDAO);
 *
 * // create a MemberPhotoManager using the logName="MemberPhoto":
 * String logName = &quot;MemberPhoto&quot;;
 * memberPhotoManager = new MemberPhotoManagerImpl(logName);
 *
 * // create a MemberPhotoManager using the obtained MemberPhotoDAO:
 * memberPhotoManager = new MemberPhotoManagerImpl(memberPhotoDAO);
 *
 * // create a MemberPhotoManager using the obtained MemberPhotoDAO
 * // and the logName="MemberPhoto":
 * memberPhotoManager = new MemberPhotoManagerImpl(logName, memberPhotoDAO);
 *
 * // we will use the member images and images mentioned above:
 *
 * // 1. get image for memberImage3 using it's id:
 * Image image = memberPhotoManager.getMemberPhoto(222444555);
 *
 * // 1.a. get image for member with ID = 222222333:
 * image = memberPhotoManager.getMemberPhoto(222222333);
 * // image must be null since the corresponding member image is removed
 *
 * // 2. save member photo for member with ID=222555666 using image
 * image = new Image();
 * image.setFileName(&quot;NewHandle4.jpg&quot;);
 * entityManager.getTransaction().begin();
 * memberPhotoManager.saveMemberPhoto(222555666, image, &quot;testUser&quot;);
 * entityManager.getTransaction().commit();
 *
 * // 3. save member photo for member with ID=222555777 using file name
 * entityManager.getTransaction().begin();
 * memberPhotoManager.saveMemberPhoto(222555777, &quot;PicForHandle5.jpg&quot;, &quot;testUser&quot;);
 * entityManager.getTransaction().commit();
 *
 * // 4. remove member photo for member with ID=222333444
 * entityManager.getTransaction().begin();
 * memberPhotoManager.removeMemberPhoto(222333444, &quot;testUser&quot;);
 * entityManager.getTransaction().commit();
 *
 * // 5. retrieve the first page of member photos, with 2 photos on the page:
 * PagedResult&lt;MemberImage&gt; pagedResult = memberPhotoManager.getMemberPhotos(1, 2);
 * // pagedResult.getTotalRecords() must be 3
 * // pagedResult.getRecords().size() must be 2
 * // pagedResult.getRecords().get(0).getMemberId() must be 222555777
 * // pagedResult.getRecords().get(0).isRemoved() must be false
 * // pagedResult.getRecords().get(0).getUpdatedBy() must be &quot;testUser&quot;
 * // pagedResult.getRecords().get(0).getImage().getId() must be 5
 * // pagedResult.getRecords().get(0).getImage().getFileName() must be &quot;PicForHandle5.jpg&quot;
 * // pagedResult.getRecords().get(1).getMemberId() must be 222555666
 * // pagedResult.getRecords().get(1).isRemoved() must be false
 * // pagedResult.getRecords().get(1).getUpdatedBy() must be &quot;testUser&quot;
 * // pagedResult.getRecords().get(1).getImage().getId() must be 4
 * // pagedResult.getRecords().get(1).getImage().getFileName() must be &quot;NewHandle4.jpg&quot;
 *
 * // we can enable detailed (using true) or standard (using false)
 * // logging:
 * ((MemberPhotoManagerImpl) memberPhotoManager).setVerboseLogging(true);
 * // now detailed logging is performed (if logging is enabled)
 *
 * // we can turn on (using a valid value) or off (null) the logging:
 * ((MemberPhotoManagerImpl) memberPhotoManager).setLog(null);
 * // now logging is not performed any more
 *
 * // we can turn on the logging using a valid value in this way:
 * Log log = LogManager.getLog(&quot;newMemberPhotoLog&quot;);
 * ((MemberPhotoManagerImpl) memberPhotoManager).setLog(log);
 * // now logging is performed (enabled)
 * </pre>
 *
 * </p>
 *
 * <p>
 * Thread Safety: This class can be considered as thread safe because its fields are injected once by Spring Framework
 * and it ensure that will not be changed afterwards and after the injection the Spring Framework will provide thread
 * safety. Used in a multi-threaded environment, the received arguments could be changed by external threads but we
 * can assume this will not happen and that the component will be used from the Spring Framework will in a thread safe
 * manner. Also <code>{@link MemberPhotoDAO}</code> implementations are required to be thread safe so thread safety
 * should not be a concern here.
 * </p>
 *
 * @author Mafy, cyberjag, saarixx, sparemax
 * @version 2.0
 */
public class MemberPhotoManagerImpl implements MemberPhotoManager {

    /**
     * <p>
     * This field represents the 'log' instance of the <code>{@link MemberPhotoManagerImpl}</code>. It will be
     * used to perform the logging operations. Contains an instance of Log interface. It can be enabled (not null)
     * or disabled (null).
     * </p>
     *
     * <p>
     * It is default to the default value of this data type when it is not assigned. Initialized in setLogName(..)
     * method or in constructor. It is used in all CRUD operations to log the method details or exceptions. It is
     * accessed through corresponding getLogName/setLogName methods. It is mutable and can be any value and is
     * optional.
     * </p>
     */
    private Log log;

    /**
     * <p>
     * This field represents the 'memberPhotoDAO' instance of the <code>{@link MemberPhotoManagerImpl}</code>.
     * Contains a concrete implementation of <code>{@link MemberPhotoDAO}</code>.
     * </p>
     *
     * <p>
     * It is default to the default value of this data type when it is not assigned. Initialized in
     * setMemberPhotoDAO(..) method or in some of the constructors. It is used in all CRUD methods delegates the
     * needed operations to this instance to achieve their purpose. It is accessed through corresponding
     * getter/setter methods. It is mutable. However it should not be initialized to null and is required.
     * </p>
     */
    private MemberPhotoDAO memberPhotoDAO;

    /**
     * <p>
     * This field represents the 'verboseLogging' property of the <code>{@link MemberPhotoManagerImpl}</code>.
     * The control flag defining whether the detailed logging actions should be performed (true value), or standard
     * logging (only exceptions) should be supported (false value).
     * </p>
     *
     * <p>
     * It is default to 'false' when it is not assigned. Initialized in setVerboseLogging(..) method. It is used in
     * all CRUD operations to determine the type of the log. It is accessed through corresponding
     * getVerboseLogging/setVerboseLogging methods. It is mutable. There are no restrictions at this moment for
     * valid values. It can take any value and is optional.
     * </p>
     */
    private boolean verboseLogging = false;

    /**
     * Default no-arg constructor. Constructs a new <code>MemberPhotoManagerImpl</code> instance. Initializes the
     * log with a default value.
     */
    public MemberPhotoManagerImpl() {
        this.log = LogManager.getLog();
    }

    /**
     * Constructs a new <code>MemberPhotoManagerImpl</code> instance. Initialize the log with a value created
     * using the given logName.
     *
     * @param logName
     *            the name of the log to be instantiated. It cannot be <code>null</code> or empty string
     *
     * @throws IllegalArgumentException
     *             if logName is <code>null</code> or empty string.
     */
    public MemberPhotoManagerImpl(String logName) {
        ExceptionUtils.checkNullOrEmpty(logName, null, null, "The parameter 'logName' cannot be null or empty.");
        this.log = LogManager.getLog(logName);
    }

    /**
     * Constructs a new <code>MemberPhotoManagerImpl</code> instance. Initialize the log with a default value and
     * set the memberPhotoDAO field with the given memberPhotoDAO.
     *
     * @param memberPhotoDAO
     *            an instance of the <code>MemberPhotoDAO</code> to be set to the related field of this class. It
     *            cannot be <code>null</code>
     *
     * @throws IllegalArgumentException
     *             if memberPhotoDAO is <code>null</code>
     */
    public MemberPhotoManagerImpl(MemberPhotoDAO memberPhotoDAO) {
        ExceptionUtils.checkNull(memberPhotoDAO, null, null, "The parameter 'memberPhotoDAO' cannot be null.");
        this.log = LogManager.getLog();
        this.memberPhotoDAO = memberPhotoDAO;
    }

    /**
     * Constructs a new <code>MemberPhotoManagerImpl</code> instance. Initialize the log with a value created
     * using the given logName and set the memberPhotoDAO field with the given memberPhotoDAO.
     *
     * @param logName
     *            the name of the log to be instantiated. It cannot be <code>null</code> or empty string
     * @param memberPhotoDAO
     *            an instance of the <code>MemberPhotoDAO</code> to be set to the related field of this class. It
     *            cannot be <code>null</code>
     *
     * @throws IllegalArgumentException
     *             if logName is <code>null</code> or empty string.
     * @throws IllegalArgumentException
     *             if memberPhotoDAO is <code>null</code>
     */
    public MemberPhotoManagerImpl(String logName, MemberPhotoDAO memberPhotoDAO) {
        ExceptionUtils.checkNullOrEmpty(logName, null, null, "The parameter 'logName' cannot be null or empty.");
        ExceptionUtils.checkNull(memberPhotoDAO, null, null, "The parameter 'memberPhotoDAO' cannot be null.");
        this.log = LogManager.getLog(logName);
        this.memberPhotoDAO = memberPhotoDAO;
    }

    /**
     * <p>
     * Performs the retrieval of a member photo using the given member id from the persistence. This method performs
     * logging.
     * </p>
     *
     * <p>
     * <em>Changes in 2.0:</em>
     * <ol>
     * <li>Method returns null instead of throwing MemberPhotoNotFoundException.</li>
     * </ol>
     * </p>
     *
     * @param memberId
     *            the identifier of the member that photo should be retrieved from the persistence. Can be any value.
     *
     * @return the member image corresponding to the given member id retrieved from the persistence (null if doesn't
     *         exist or is removed).
     *
     * @throws IllegalStateException
     *             if the memberPhotoDAO is not set (or it is set to a <code>null</code> value) or not properly
     *             configured.
     * @throws MemberPhotoManagementException
     *             if any error occurs while performing this operation.
     */
    public Image getMemberPhoto(long memberId) throws MemberPhotoManagementException {
        long time = System.currentTimeMillis();
        log(MessageFormat.format("{0} : Entering MemberPhotoManagerImpl#getMemberPhoto(long) - memberId {1}",
                new Date(), memberId));
        Image image = null;

        try {
            // Delegate the call to DAO
            image = Helper.checkState(memberPhotoDAO, "memberPhotoDAO").getMemberPhoto(memberId);
        } catch (MemberPhotoDAOException e) {
            throw log("The call to persistence layer failed while deleting MemberPhoto.", e);
        } catch (IllegalStateException e) {
            throw log("The memberPhotoDAO is not valid. Failure while retrieving MemberPhoto.", e);
        }

        time = System.currentTimeMillis() - time;
        log(MessageFormat.format("{0} : Exiting MemberPhotoManagerImpl#getMemberPhoto(long) - return {1}."
                + " Total time spent in the method {2} ms.", new Date(), image, time));
        return image;
    }

    /**
     * <p>
     * Performs the save of a member photo for the given image and member id in the persistence. This method performs
     * logging.
     * </p>
     *
     * <p>
     * <em>Changes in 2.0:</em>
     * <ol>
     * <li>Added byUser parameter.</li>
     * </ol>
     * </p>
     *
     * @param memberId
     *            the identifier of the member that photo should be saved in the persistence. Can be any value.
     * @param image
     *            the image that should be saved for the member with the given id in the persistence. Should not be
     *            null.
     * @param byUser
     *            the username of the user that performs the operation. Cannot be null/empty.
     *
     * @throws IllegalArgumentException
     *             if image is <code>null</code> or byUser is null/empty.
     * @throws IllegalStateException
     *             if the memberPhotoDAO is not set (or it is set to a <code>null</code> value) or not properly
     *             configured.
     * @throws MemberPhotoManagementException
     *             if any error occurs while performing this operation.
     */
    public void saveMemberPhoto(long memberId, Image image, String byUser) throws MemberPhotoManagementException {
        long time = System.currentTimeMillis();
        log(MessageFormat.format(
            "{0} : Entering MemberPhotoManagerImpl#saveMemberPhoto(long, Image, String) - memberId {1}, image {2},"
                + " byUser {3}", new Date(), memberId, image, byUser));

        try {
            // Delegate the call to DAO
            Helper.checkState(memberPhotoDAO, "memberPhotoDAO").saveMemberPhoto(memberId, image, byUser);
        } catch (MemberPhotoDAOException e) {
            throw log("The call to persistence layer failed while deleting MemberPhoto.", e);
        } catch (IllegalStateException e) {
            throw log("The memberPhotoDAO is not valid. Failure while saving MemberPhoto.", e);
        } catch (IllegalArgumentException e) {
            throw log(
                "Parameter validation failure occurred for MemberPhotoManagerImpl#saveMemberPhoto(long, Image).", e);
        }
        time = System.currentTimeMillis() - time;
        log(MessageFormat.format("{0} : Exiting MemberPhotoManagerImpl#saveMemberPhoto(long, Image, String) - void."
            + " Total time spent in the method {1} ms.", new Date(), time));
    }

    /**
     * <p>
     * Performs the save of a member photo for the given file name and member id in the persistence. This method
     * performs logging
     * </p>
     *
     * <p>
     * <em>Changes in 2.0:</em>
     * <ol>
     * <li>Added byUser parameter.</li>
     * </ol>
     * </p>
     *
     * @param memberId
     *            the identifier of the member that photo should be saved in the persistence. Can be any value.
     * @param fileName
     *            the file name that should be saved for the member with the given id in the persistence. Should
     *            not be <code>null</code> or empty String.
     * @param byUser
     *            the username of the user that performs the operation. Cannot be null/empty.
     *
     * @throws IllegalArgumentException
     *             if fileName or byUser is <code>null</code> or empty String.
     * @throws IllegalStateException
     *             if the memberPhotoDAO is not set (or it is set to a <code>null</code> value) or not properly
     *             configured.
     * @throws MemberPhotoManagementException
     *             if any error occurs while performing this operation.
     */
    public void saveMemberPhoto(long memberId, String fileName, String byUser) throws MemberPhotoManagementException {
        long time = System.currentTimeMillis();
        log(MessageFormat.format(
            "{0} : Entering MemberPhotoManagerImpl#saveMemberPhoto(long, String, String) - memberId {1},"
                + " fileName {2}, byUser {3}", new Date(), memberId, fileName, byUser));

        try {
            // delegate the call to DAO
            Helper.checkState(memberPhotoDAO, "memberPhotoDAO").saveMemberPhoto(memberId, fileName, byUser);
        } catch (MemberPhotoDAOException e) {
            throw log("The call to persistence layer failed while deleting MemberPhoto.", e);
        } catch (IllegalStateException e) {
            throw log("The memberPhotoDAO is not valid. Failure while saving MemberPhoto.", e);
        } catch (IllegalArgumentException e) {
            throw log(
                "Parameter validation failure occurred for MemberPhotoManagerImpl#saveMemberPhoto(long, String).", e);
        }

        time = System.currentTimeMillis() - time;
        log(MessageFormat.format("{0} : Exiting MemberPhotoManagerImpl#saveMemberPhoto(long, String, String) - void."
            + " Total time spent in the method {1} ms.", new Date(), time));
    }

    /**
     * <p>
     * Performs the deletion of a member photo for the given member id in the persistence. This method performs
     * logging.
     * </p>
     *
     * <p>
     * <em>Changes in 2.0:</em>
     * <ol>
     * <li>Added byUser parameter.</li>
     * <li>Method can throw MemberPhotoNotFoundException.</li>
     * </ol>
     * </p>
     *
     * @param memberId
     *            the identifier of the member that photo should be deleted from the persistence. Can be any value.
     * @param byUser
     *            the username of the user that performs the operation. Cannot be null/empty.
     *
     * @throws IllegalStateException
     *             if the memberPhotoDAO is not set (or it is set to a <code>null</code> value) or not properly
     *             configured.
     * @throws IllegalArgumentException
     *             if byUser is <code>null</code> or empty String.
     * @throws MemberPhotoManagementException
     *             if any error occurs while performing this operation.
     */
    public void removeMemberPhoto(long memberId, String byUser) throws MemberPhotoManagementException {
        long time = System.currentTimeMillis();
        log(MessageFormat.format(
            "{0} : Entering MemberPhotoManagerImpl#removeMemberPhoto(long, String) - memberId {1}, byUser {2}",
            new Date(), memberId, byUser));
        try {
            // Delegate the call to DAO
            Helper.checkState(memberPhotoDAO, "memberPhotoDAO").removeMemberPhoto(memberId, byUser);
        } catch (MemberPhotoDAOException e) {
            throw log("The call to persistence layer failed while deleting MemberPhoto.", e);
        }  catch (MemberPhotoNotFoundException e) {
            throw log("The call to persistence layer failed while deleting MemberPhoto.", e);
        } catch (IllegalStateException e) {
            throw log("The memberPhotoDAO is not valid. Failure while deleting MemberPhoto.", e);
        } catch (IllegalArgumentException e) {
            throw log(
                "Parameter validation failure occurred for MemberPhotoManagerImpl#removeMemberPhoto(long, String).",
                e);
        }

        time = System.currentTimeMillis() - time;
        log(MessageFormat.format("{0} : Exiting MemberPhotoManagerImpl#removeMemberPhoto(long, String) - void."
            + " Total time spent in the method {1} ms.", new Date(), time));
    }

    /**
     * <p>
     * Retrieves the member photos with optional pagination.
     * This method performs logging.
     * </p>
     *
     * @param pageSize
     *            the number of member images on the page (ignored if pageNo == 0).
     * @param pageNo
     *            the number of the page to be retrieved (0 if all member photos should be retrieved).
     *
     * @return the retrieved member photos (not null).
     *
     * @throws IllegalStateException
     *             if the memberPhotoDAO is not set (or it is set to a <code>null</code> value) or not properly
     *             configured.
     * @throws IllegalArgumentException
     *             if pageNo &lt; 0, (pageNo &gt; 0 and pageSize &lt;= 0).
     * @throws MemberPhotoManagementException
     *             if any error occurs while performing this operation.
     *
     * @since 2.0
     */
    public PagedResult<MemberImage> getMemberPhotos(int pageNo, int pageSize) throws MemberPhotoManagementException {
        long time = System.currentTimeMillis();
        log(MessageFormat.format("{0} : Entering MemberPhotoManagerImpl#getMemberPhotos(int, int) - pageNo {1},"
            + " pageSize {2}", new Date(), pageNo, pageSize));
        PagedResult<MemberImage> result = null;
        try {
            // Delegate the call to DAO
            result = Helper.checkState(memberPhotoDAO, "memberPhotoDAO").getMemberPhotos(pageNo, pageSize);
        } catch (MemberPhotoDAOException e) {
            throw log("The call to persistence layer failed while deleting MemberPhoto.", e);
        } catch (IllegalStateException e) {
            throw log("The memberPhotoDAO is not valid. Failure while deleting MemberPhoto.", e);
        } catch (IllegalArgumentException e) {
            throw log("Parameter validation failure occurred for MemberPhotoManagerImpl#getMemberPhotos(int, int).",
                e);
        }

        log(MessageFormat.format("{0} : Exiting MemberPhotoManagerImpl#getMemberPhotos(int, int) - return {1}."
            + " Total time spent in the method {2} ms.", new Date(), result, time));
        return result;
    }

    /**
     * Gets the 'memberPhotoDAO' property. Please refer to the related field for more information.
     *
     * @return the value of the instance of the <code>MemberPhotoDAO</code>. This field stores the concrete
     *         implementation of the <code>MemberPhotoDAO</code>.
     */
    public MemberPhotoDAO getMemberPhotoDAO() {
        return memberPhotoDAO;
    }

    /**
     * Sets the 'memberPhotoDAO' property. Please refer to the related field for more information.
     *
     * @param memberPhotoDAO
     *            the new value of the instance of the <code>MemberPhotoDAO</code>. This field stores the
     *            concrete implementation of the <code>MemberPhotoDAO</code>. The IoC should handle the validity
     *            if the passed value.
     */
    public void setMemberPhotoDAO(MemberPhotoDAO memberPhotoDAO) {
        this.memberPhotoDAO = memberPhotoDAO;
    }

    /**
     * Gets the 'verboseLogging' property. Please refer to the related field for more information.
     *
     * @return the value of the 'verboseLogging' property. It can be any value.
     */
    public boolean isVerboseLogging() {
        return verboseLogging;
    }

    /**
     * Sets the 'verboseLogging' property. Please refer to the related field for more information.
     *
     * @param verboseLogging
     *            the new value of the control flag defining whether the detailed logging actions should be
     *            performed (true value), or standard logging (only exceptions) should be supported (false value).
     */
    public void setVerboseLogging(boolean verboseLogging) {
        this.verboseLogging = verboseLogging;
    }

    /**
     * Gets the 'log' property. Please refer to the related field for more information.
     *
     * @return the value of the 'log' property. It can be any value.
     */
    public Log getLog() {
        return log;
    }

    /**
     * <p>
     * Sets the 'log' property. Please refer to the related field for more information.
     * </p>
     *
     * @param log
     *            the name of the log to be instantiated. It cab be any value.
     */
    public void setLog(Log log) {
        this.log = log;
    }

    /**
     * Logs the DEBUG message.
     *
     * @param message
     *            the message to log.
     */
    private void log(String message) {
        if (log != null && verboseLogging) {
            log.log(Level.DEBUG, message);
        }
    }

    /**
     * Logs the ERROR message.
     *
     * @param <T>
     *            the error type.
     * @param message
     *            the message to log.
     * @param e
     *            the error
     *
     * @return the passed in exception.
     */
    private <T extends Throwable> T log(String message, T e) {
        if (log != null) {
            log.log(Level.ERROR, e, message);
        }

        return e;
    }
}
