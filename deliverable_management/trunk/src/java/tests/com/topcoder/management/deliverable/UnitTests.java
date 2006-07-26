/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.deliverable;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.management.deliverable.persistence.DeliverableCheckingExceptionTest;
import com.topcoder.management.deliverable.persistence.DeliverablePersistenceExceptionTest;
import com.topcoder.management.deliverable.persistence.UploadPersistenceExceptionTest;
import com.topcoder.management.deliverable.search.DeliverableFilterBuilderTests;
import com.topcoder.management.deliverable.search.SubmissionFilterBuilderTests;
import com.topcoder.management.deliverable.search.UploadFilterBuilderTests;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(AuditedDeliverableStructureTests.class);
        suite.addTestSuite(DeliverableTests.class);
        suite.addTestSuite(IdAlreadySetExceptionTests.class);
        suite.addTestSuite(NamedDeliverableStructureTests.class);
        suite.addTestSuite(PersistenceDeliverableManagerTests.class);
        suite.addTestSuite(PersistenceUploadManagerTests.class);
        suite.addTestSuite(SubmissionStatusTests.class);
        suite.addTestSuite(SubmissionTests.class);
        suite.addTestSuite(UploadStatusTests.class);
        suite.addTestSuite(UploadTests.class);
        suite.addTestSuite(UploadTypeTests.class);

        suite.addTestSuite(DeliverableCheckingExceptionTest.class);
        suite.addTestSuite(DeliverablePersistenceExceptionTest.class);
        suite.addTestSuite(UploadPersistenceExceptionTest.class);
        suite.addTestSuite(DeliverableFilterBuilderTests.class);
        suite.addTestSuite(SubmissionFilterBuilderTests.class);
        suite.addTestSuite(UploadFilterBuilderTests.class);

        suite.addTestSuite(Demo.class);
        return suite;
    }

}
