/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.stresstests;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.web.memberphoto.entities.Image;
import com.topcoder.web.memberphoto.entities.MemberImage;
import com.topcoder.web.memberphoto.manager.MemberPhotoManagerImpl;
import com.topcoder.web.memberphoto.manager.PagedResult;
import com.topcoder.web.memberphoto.manager.persistence.JPAMemberPhotoDAO;

/**
 * <p>
 * This class contains the stress tests for {@link MemberPhotoManagerImpl} class. It also
 * indirectly tests {@link JPAMemberPhotoDAO} class.
 * </p>
 * <p>
 * Changes in version 2.0:
 * <ol>
 * <li>Added tests for getMemberPhotos() method.</li>
 * <li>Fixed other inconsistencies.</li>
 * </ol>
 * </p>
 *
 * @author King_Bette, Lunarmony
 * @version 2.0
 * @since 1.0
 */
public class MemberPhotoManagerImplStressTest extends TestCase {
    /**
     * Represents the number of times to perform the test cases.
     */
    private static final int TIMES = 400;

    /**
     * Represents the page size used in the test cases.
     */
    private static final int PAGE_SIZE = 50;

    /**
     * Represents the <code>MemberPhotoManagerImpl</code> instance to test.
     */
    private MemberPhotoManagerImpl memberPhotoManagerImpl = null;

    /**
     * Represents the <code>MemberPhotoDAO</code> instance used for testing.
     */
    private JPAMemberPhotoDAO memberPhotoDAO;

    /**
     * Represents the entity manager.
     */
    private static EntityManager entityManager;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception throws exception if any.
     */
    @Override
    protected void setUp() throws Exception {
        if (entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory("memberPhotoManagerStress").createEntityManager();
        }
        executeSQLFile("test_files/StressTests/Clear.sql");

        this.memberPhotoDAO = new JPAMemberPhotoDAO();
        this.memberPhotoDAO.setEntityManager(entityManager);
        this.memberPhotoManagerImpl = new MemberPhotoManagerImpl();
        this.memberPhotoManagerImpl.setVerboseLogging(false);
        this.memberPhotoManagerImpl.setMemberPhotoDAO(this.memberPhotoDAO);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception throws exception if any.
     */
    @Override
    protected void tearDown() throws Exception {
        executeSQLFile("test_files/StressTests/Clear.sql");
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(MemberPhotoManagerImplStressTest.class);
    }

    /**
     * <p>
     * Stress test for MemberPhotoManagerImpl class.
     * </p>
     * <p>
     * Expects a valid instance from persistence.
     * </p>
     *
     * <p>
     * Changes in version 2.0:
     * <ol>
     * <li>Added tests for getMemberPhotos() method.</li>
     * <li>Fixed other inconsistencies.</li>
     * </ol>
     * </p>
     *
     * @throws Exception if any error occurs
     * @since 1.0
     */
    public void testStress1() throws Exception {
        String byUser = "TopCoder";
        for (int i = 1; i <= TIMES; i++) {
            entityManager.getTransaction().begin();
            this.memberPhotoManagerImpl.saveMemberPhoto(i * 100, "filename" + i, byUser);
            entityManager.getTransaction().commit();
        }
        for (int i = 1; i <= TIMES; i++) {
            Image image = this.memberPhotoManagerImpl.getMemberPhoto(i * 100);
            assertEquals("filename mismatch", "filename" + i, image.getFileName());
        }

        // version 2.0: tests paging
        HashSet<Integer> retrievedImages = new HashSet<Integer>();
        for (int i = 1; i <= TIMES / PAGE_SIZE + 5; ++i) {
            PagedResult<MemberImage> results = this.memberPhotoManagerImpl.getMemberPhotos(i, PAGE_SIZE);
            if (i > TIMES / PAGE_SIZE) {
                assertEquals("There should be no results on this page.", 0, results.getRecords().size());
            } else {
                assertEquals("the number of results should be correct.", PAGE_SIZE, results.getRecords().size());
            }
            assertEquals("the number of all results should be correct.", TIMES, results.getTotalRecords());
            for (MemberImage image : results.getRecords()) {
                assertFalse("each image should only be retrieved once.",
                        retrievedImages.contains((int) image.getId()));
                retrievedImages.add((int) image.getId());
            }
        }
        assertEquals("the number of images on the last page should be correct.", 2 * TIMES / 5,
                this.memberPhotoManagerImpl.getMemberPhotos(2, TIMES * 3 / 5).getRecords().size());
        assertEquals("the number of images when pageNo = 0 should be correct.", TIMES,
                this.memberPhotoManagerImpl.getMemberPhotos(0, TIMES * 3 / 5).getRecords().size());

        for (int i = 2; i <= TIMES; i += 2) {
            entityManager.getTransaction().begin();
            this.memberPhotoManagerImpl.removeMemberPhoto(i * 100, byUser);
            entityManager.getTransaction().commit();
        }
        for (int i = 1; i <= TIMES; i++) {
            if (i % 2 == 1) {
                Image image = this.memberPhotoManagerImpl.getMemberPhoto(i * 100);
                assertEquals("filename mismatch", "filename" + i, image.getFileName());
            } else {
                assertNull("should return null when the image is absent.",
                        this.memberPhotoManagerImpl.getMemberPhoto(i * 100));
            }
        }
    }

    /**
     * <p>
     * Stress test for MemberPhotoManagerImpl class.
     * </p>
     * <p>
     * Expects a valid instance from persistence.
     * </p>
     *
     * <p>
     * Changes in version 2.0:
     * <ol>
     * <li>Added tests for getMemberPhotos() method.</li>
     * <li>Fixed other inconsistencies.</li>
     * </ol>
     * </p>
     *
     * @throws Exception if any error occurs
     * @since 1.0
     */
    public void testStress2() throws Exception {
        String byUser = "StressTests";
        for (int i = 1; i <= TIMES; i++) {
            Image image = new Image();
            image.setFileName("filename" + i);
            entityManager.getTransaction().begin();
            entityManager.persist(image);
            entityManager.getTransaction().commit();

            entityManager.getTransaction().begin();
            this.memberPhotoManagerImpl.saveMemberPhoto(i * 100, image, byUser);
            entityManager.getTransaction().commit();
        }
        for (int i = 1; i <= TIMES; i++) {
            Image image = this.memberPhotoManagerImpl.getMemberPhoto(i * 100);
            assertEquals("filename mismatch", "filename" + i, image.getFileName());
        }

        // version 2.0: tests paging
        HashSet<Integer> retrievedImages = new HashSet<Integer>();
        for (int i = 1; i <= TIMES + 5; ++i) {
            PagedResult<MemberImage> results = this.memberPhotoManagerImpl.getMemberPhotos(i, 1);
            if (i > TIMES) {
                assertEquals("the number of results should be correct.", 0, results.getRecords().size());
            } else {
                assertEquals("the number of results should be correct.", 1, results.getRecords().size());
            }
            assertEquals("the number of all results should be correct.", TIMES, results.getTotalRecords());
            for (MemberImage image : results.getRecords()) {
                assertFalse("each image should only be retrieved once.",
                        retrievedImages.contains((int) image.getId()));
                retrievedImages.add((int) image.getId());
            }
        }

        for (int i = 2; i <= TIMES; i += 2) {
            entityManager.getTransaction().begin();
            this.memberPhotoManagerImpl.removeMemberPhoto(i * 100, byUser);
            entityManager.getTransaction().commit();
        }
        for (int i = 1; i <= TIMES; i++) {
            if (i % 2 == 1) {
                Image image = this.memberPhotoManagerImpl.getMemberPhoto(i * 100);
                assertEquals("filename mismatch", "filename" + i, image.getFileName());
            } else {
                assertNull("should return null when the image is absent.",
                        this.memberPhotoManagerImpl.getMemberPhoto(i * 100));
            }
        }
    }

    /**
     * Executes the SQL scripts contained in a specific file.
     *
     * @param fileName
     *            the name of the file in which the SQL scripts are stored
     * @throws Exception
     *             if any database related errors occurred
     * @since 2.0
     */
    private static void executeSQLFile(String fileName) throws Exception {
        entityManager.clear();
        entityManager.getTransaction().begin();
        try {
            Scanner scanner = new Scanner(new File(fileName));
            scanner.useDelimiter(";");
            while (scanner.hasNext()) {
                String command = scanner.next().trim();
                if (command.length() != 0) {
                        entityManager.createNativeQuery(command).executeUpdate();
                }
            }
            scanner.close();
            entityManager.getTransaction().commit();
            entityManager.clear();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }
}