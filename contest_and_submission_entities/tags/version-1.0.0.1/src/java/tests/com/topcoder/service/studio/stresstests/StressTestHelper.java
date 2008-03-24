package com.topcoder.service.studio.stresstests;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class StressTestHelper {
    /**
     * <p>
     * Creates a SessionFactory instance used for tests.
     * </p>
     *
     * @return the SessionFactory instance
     *
     * @throws Exception
     *             to JUnit.
     */
    public static SessionFactory createSessionFactory()
        throws Exception {
        Configuration config = new Configuration().configure("hibernate.cfg.xml");

        return config.buildSessionFactory();
    }

    /**
     * <p>
     * Clears all tables in database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public static void clearTables() throws Exception {
        Session session = createSessionFactory().openSession();

        Transaction t = session.beginTransaction();
        String[] tables = new String[] {"delete from submission", "delete from submission_type_lu",
            "delete from submission_status_lu", "delete from submission_review",
            "delete from submission_prize_xref", "delete from submission_payments",
            "delete from review_status_lu", "delete from prize", "delete from prize_type_lu",
            "delete from payment_status_lu", "delete from path", "delete from mime_type_lu",
            "delete from file_type_lu", "delete from document", "delete from document_type_lu",
            "delete from contest", "delete from contest_type_lu", "delete from contest_type_config",
            "delete from contest_status_relation", "delete from contest_status_lu",
            "delete from contest_status_icon", "delete from contest_result",
            "delete from contest_registration", "delete from contest_property_lu",
            "delete from contest_prize_xref", "delete from contest_file_type_xref",
            "delete from contest_document_xref", "delete from contest_config",
            "delete from contest_category_lu", "delete from config" };

        for (int i = 0; i < tables.length; i++) {
            Query query = session.createSQLQuery(tables[i]);
            query.executeUpdate();
        }

        t.commit();
        session.close();
    }
}
