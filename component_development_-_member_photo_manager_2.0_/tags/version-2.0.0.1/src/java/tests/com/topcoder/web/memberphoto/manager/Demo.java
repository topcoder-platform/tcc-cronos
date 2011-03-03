/*
 * Copyright (C) 2008, 2011 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.web.memberphoto.manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.memberphoto.entities.Image;
import com.topcoder.web.memberphoto.entities.MemberImage;
import com.topcoder.web.memberphoto.manager.persistence.JPAMemberPhotoDAO;

/**
 * <p>
 * Demo showing the usage of the component.
 * </p>
 *
 * @author cyberjag, sparemax
 * @version 2.0
 */
public class Demo extends TestCase {

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(Demo.class);
    }

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    @Override
    protected void setUp() throws Exception {
        TestHelper.clearDB();
        TestHelper.loadDB();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    @Override
    protected void tearDown() throws Exception {
        TestHelper.clearDB();
    }

    /**
     * The demo usage of the component.
     *
     * <p>
     * Note: The transactions shown in the demo is to remind the user that the transaction should be used. If you are
     * using any IoC containers like spring then it will be configured for transaction.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testDemo() throws Exception {
        // configure the EntityManager:
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("memberPhotoManager");
        EntityManager entityManager = emf.createEntityManager();

        // create a MemberPhotoDAO using the obtained EntityManager:
        MemberPhotoDAO memberPhotoDAO = new JPAMemberPhotoDAO(entityManager);

        // create a MemberPhotoManager using the default constructor:
        MemberPhotoManager memberPhotoManager = new MemberPhotoManagerImpl(memberPhotoDAO);

        // create a MemberPhotoManager using the logName="MemberPhoto":
        String logName = "MemberPhoto";
        memberPhotoManager = new MemberPhotoManagerImpl(logName);

        // create a MemberPhotoManager using the obtained MemberPhotoDAO:
        memberPhotoManager = new MemberPhotoManagerImpl(memberPhotoDAO);

        // create a MemberPhotoManager using the obtained MemberPhotoDAO
        // and the logName="MemberPhoto":
        memberPhotoManager = new MemberPhotoManagerImpl(logName, memberPhotoDAO);

        // we will use the member images and images mentioned above:

        // 1. get image for memberImage3 using it's id:
        Image image = memberPhotoManager.getMemberPhoto(222444555);

        // 1.a. get image for member with ID = 222222333:
        image = memberPhotoManager.getMemberPhoto(222222333);
        // image must be null since the corresponding member image is removed

        // 2. save member photo for member with ID=222555666 using image
        image = new Image();
        image.setFileName("NewHandle4.jpg");
        entityManager.getTransaction().begin();
        memberPhotoManager.saveMemberPhoto(222555666, image, "testUser");
        entityManager.getTransaction().commit();

        // 3. save member photo for member with ID=222555777 using file name
        entityManager.getTransaction().begin();
        memberPhotoManager.saveMemberPhoto(222555777, "PicForHandle5.jpg", "testUser");
        entityManager.getTransaction().commit();

        // 4. remove member photo for member with ID=222333444
        entityManager.getTransaction().begin();
        memberPhotoManager.removeMemberPhoto(222333444, "testUser");
        entityManager.getTransaction().commit();

        // 5. retrieve the first page of member photos, with 2 photos on the page:
        PagedResult<MemberImage> pagedResult = memberPhotoManager.getMemberPhotos(1, 2);
        // pagedResult.getTotalRecords() must be 3
        // pagedResult.getRecords().size() must be 2
        // pagedResult.getRecords().get(0).getMemberId() must be 222555777
        // pagedResult.getRecords().get(0).isRemoved() must be false
        // pagedResult.getRecords().get(0).getUpdatedBy() must be "testUser"
        // pagedResult.getRecords().get(0).getImage().getId() must be 5
        // pagedResult.getRecords().get(0).getImage().getFileName() must be "PicForHandle5.jpg"
        // pagedResult.getRecords().get(1).getMemberId() must be 222555666
        // pagedResult.getRecords().get(1).isRemoved() must be false
        // pagedResult.getRecords().get(1).getUpdatedBy() must be "testUser"
        // pagedResult.getRecords().get(1).getImage().getId() must be 4
        // pagedResult.getRecords().get(1).getImage().getFileName() must be "NewHandle4.jpg"

        // we can enable detailed (using true) or standard (using false)
        // logging:
        ((MemberPhotoManagerImpl) memberPhotoManager).setVerboseLogging(true);
        // now detailed logging is performed (if logging is enabled)

        // we can turn on (using a valid value) or off (null) the logging:
        ((MemberPhotoManagerImpl) memberPhotoManager).setLog(null);
        // now logging is not performed any more

        // we can turn on the logging using a valid value in this way:
        Log log = LogManager.getLog("newMemberPhotoLog");
        ((MemberPhotoManagerImpl) memberPhotoManager).setLog(log);
        // now logging is performed (enabled)
    }
}
