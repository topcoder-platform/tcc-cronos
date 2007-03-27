/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;

import java.util.Date;

import com.topcoder.timetracker.common.persistence.DatabasePaymentTermDAO;


/**
 * <p>This test case contains demo for this component.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.1
 */
public class Demo extends BaseTestCase {

    /**
     * <p>
     * Gets a <code>PaymentTerm</code> to add.
     * </p>
     *
     * @return A <code>PaymentTerm</code> to add.
     */
    private PaymentTerm getPaymentTermToAdd() {
        //No need to set creation date, modification date, modification user
        //if you're planning to add payment term through SimpleCommonManager
        PaymentTerm paymentTerm = new PaymentTerm();
        paymentTerm.setCreationUser("creation_user");
        paymentTerm.setDescription("description");
        paymentTerm.setActive(true);
        paymentTerm.setTerm(2);
        return paymentTerm;
    }

    /**
     * <p>Runs demo.</p>
     *
     * @throws Exception to JUnit
     */
    public void testDemo() throws Exception {
        PaymentTerm first = this.getPaymentTermToAdd();
        PaymentTerm second = this.getPaymentTermToAdd();
        PaymentTerm third = this.getPaymentTermToAdd();

        PaymentTerm requiredPaymentTerm;
        PaymentTerm[] requiredPaymentTerms;

        SimpleCommonManager scManager = new SimpleCommonManager();
        DatabasePaymentTermDAO dao = new DatabasePaymentTermDAO("DatabasePaymentTermDAO");
        // 1. Add a PaymentTerm to persistence
        scManager.deleteAllPaymentTerms();
        scManager.addPaymentTerm(first);
        scManager.addPaymentTerm(second);

        //If you're planning to use DAO to add payment term directly
        //ensure the creation date is not null and equals modification date, and not exceeds current date
        //and ensure the modification user is set with a valid value
        Date currentDate = new Date();
        third.setCreationDate(currentDate);
        third.setModificationDate(currentDate);
        third.setModificationUser("modificationUser");
        dao.addPaymentTerm(third);

        long givenId = second.getId();
        long[] ids = {first.getId(), second.getId(), third.getId()};

        // 2. Modify a PaymentTerm
        second.setTerm(1);
        second.setActive(false);
        second.setDescription("Term changes to 1 day");
        second.setModificationUser("me");

        // 3. Update a PaymentTerm
        scManager.updatePaymentTerm(second);

        //If you're planning to use DAO to update payment term directly
        //ensure the creation date < modification date, and modification date does not exceeds current date
        third.setTerm(1);
        third.setActive(false);
        third.setDescription("Term changes to 1 day");
        third.setModificationUser("me");
        currentDate = new Date();
        third.setCreationDate(new Date(currentDate.getTime() - 1));
        third.setModificationDate(currentDate);
        dao.updatePaymentTerm(third);

        // 4. Retrieve a PaymentTerm using its id
        // 4.1 Retrieve a updated PaymentTerm
        requiredPaymentTerm = scManager.retrievePaymentTerm(givenId);
        // 4.2 Retrieve a non-exist PaymentTerm
        requiredPaymentTerm = scManager.retrievePaymentTerm(23);

        // 5. Retrieve some PaymentTerms using their ids
        requiredPaymentTerms = scManager.retrievePaymentTerms(ids);

        // 6. Retrieve all active PaymentTerms
        requiredPaymentTerms = scManager.retrieveActivePaymentTerms();

        // 7. Retrieve recently created PaymentTerms (with configured recent number of days)
        requiredPaymentTerms = scManager.retrieveRecentlyCreatedPaymentTerms();

        // 8. Retrieve recently created PaymentTerms (with specified number of days)
        requiredPaymentTerms = scManager.retrieveRecentlyCreatedPaymentTerms(Integer.MAX_VALUE);

        // 9. Retrieve recently modified PaymentTerms (with configured recent number of days)
        requiredPaymentTerms = scManager.retrieveRecentlyModifiedPaymentTerms();

        // 10. Retrieve recently modified PaymentTerms (with specified number of days)
        requiredPaymentTerms = scManager.retrieveRecentlyModifiedPaymentTerms(Integer.MAX_VALUE);

        // 11. Retrieve all PaymentTerms
        requiredPaymentTerms = scManager.retrieveAllPaymentTerms();

        // 12. Delete PaymentTerms corresponding to the given ids
        scManager.deletePaymentTerms(ids);

        // 13. Delete all PaymentTerms from persistence
        scManager.deleteAllPaymentTerms();

        // 14. Delete a non-exist PaymentTerm
        try {
            scManager.deletePaymentTerm(23);
        } catch (PaymentTermNotFoundException e) {
            //PaymentTermNotFoundException will be raised
        } finally {
            endDemo();
        }
    }

    /**
     * <p>
     * End demo.
     * </p>
     *
     * @throws Exception to JUnit
     */
    private void endDemo() throws Exception {
        this.wrapDB();
        this.endTest();
    }
}
