/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.web.memberphoto.entities.ImageTest;
import com.topcoder.web.memberphoto.entities.MemberImageTest;
import com.topcoder.web.memberphoto.manager.Demo;
import com.topcoder.web.memberphoto.manager.HelperTest;
import com.topcoder.web.memberphoto.manager.MemberPhotoDAOExceptionTest;
import com.topcoder.web.memberphoto.manager.MemberPhotoManagementExceptionTest;
import com.topcoder.web.memberphoto.manager.MemberPhotoManagerImplTest;
import com.topcoder.web.memberphoto.manager.MemberPhotoNotFoundExceptionTest;
import com.topcoder.web.memberphoto.manager.PagedResultTest;
import com.topcoder.web.memberphoto.manager.persistence.JPAMemberPhotoDAOTest;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author cyberjag, sparemax
 * @version 2.0
 */
public class UnitTests extends TestCase {

    /**
     * Returns the unit test suite.
     *
     * @return the unit tests
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(PagedResultTest.suite());

        suite.addTest(ImageTest.suite());
        suite.addTest(MemberImageTest.suite());
        suite.addTest(HelperTest.suite());
        suite.addTest(MemberPhotoDAOExceptionTest.suite());
        suite.addTest(MemberPhotoManagementExceptionTest.suite());
        suite.addTest(MemberPhotoManagerImplTest.suite());
        suite.addTest(MemberPhotoNotFoundExceptionTest.suite());
        suite.addTest(JPAMemberPhotoDAOTest.suite());
        suite.addTest(Demo.suite());

        return suite;
    }
}
