/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import com.topcoder.timetracker.entry.expense.criteria.CompositeCriteria;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.criteria.FieldBetweenCriteria;
import com.topcoder.timetracker.entry.expense.criteria.FieldLikeCriteria;
import com.topcoder.timetracker.entry.expense.criteria.FieldMatchCriteria;
import com.topcoder.timetracker.entry.expense.criteria.NotCriteria;
import com.topcoder.timetracker.entry.expense.criteria.RejectReasonCriteria;

import junit.framework.TestCase;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * Accuracy tests for classes in package com.topcoder.timetracker.entry.expense.criteria.
 * </p>
 *
 * @author -oo-
 * @author brain_cn
 * @version 3.2
 * @version 1.1
 */
public class CriteriaAccuracyTests extends TestCase {
    /**
     * Test the constructor and methods in class RejectReasonCriteria.
     *
     * @throws Exception to JUnit
     */
    public void testRejectReasonCriteria() throws Exception {
        // test constructor and getRejectReasonId()
        Criteria criteria = new RejectReasonCriteria(10);
        assertEquals("the id is not correct", 10, ((RejectReasonCriteria) criteria).getRejectReasonId());

        // test getParameters()
        assertTrue("the arrays do not equal", Arrays.equals(new Object[] { new Long(10) }, criteria.getParameters()));
    }

    /**
     * Test the constructor and methods in class FieldLikeCriteria.
     *
     * @throws Exception to JUnit
     */
    public void testFieldLikeCriteria() throws Exception {
        // test constructor and "get" methods
        Criteria criteria = new FieldLikeCriteria("Field", "Pattern");
        assertEquals("the field is not correct", "Field", ((FieldLikeCriteria) criteria).getField());
        assertEquals("the pattern is not correct", "Pattern", ((FieldLikeCriteria) criteria).getPattern());

        // test getParameters()
        assertTrue("the arrays do not equal", Arrays.equals(new Object[] { "Pattern" }, criteria.getParameters()));

        // test getDescriptionContainsCriteria()
        String pattern = "%Criteria%";
        criteria = FieldLikeCriteria.getDescriptionContainsCriteria("Criteria");
        assertEquals("The field returned is not correct.", FieldLikeCriteria.DESCRIPTION_FIELD,
            ((FieldLikeCriteria) criteria).getField());
        assertEquals("The pattern returned is not correct.", pattern, ((FieldLikeCriteria) criteria).getPattern());

        Object[] parameters = new Object[] { pattern };
        assertTrue("The parameters returned is not correct.", Arrays.equals(parameters, criteria.getParameters()));
    }

    /**
     * Test the constructor and "get" methods in class FieldBetweenCriteria.
     *
     * @throws Exception to JUnit
     */
    public void testFieldBetweenCriteria1() throws Exception {
        Integer fromValue = new Integer(10);
        Integer toValue = new Integer(100);

        // test constructor and "get" methods
        Criteria criteria = new FieldBetweenCriteria("Field", fromValue, toValue);
        assertEquals("the field is not correct", "Field", ((FieldBetweenCriteria) criteria).getField());
        assertEquals("the fromValue is not correct", fromValue, ((FieldBetweenCriteria) criteria).getFromValue());
        assertEquals("the toValue is not correct", toValue, ((FieldBetweenCriteria) criteria).getToValue());

        // test getParameters()
        assertTrue("the arrays do not equal",
            Arrays.equals(new Object[] { fromValue, toValue }, criteria.getParameters()));
        criteria = new FieldBetweenCriteria("Field", fromValue, null);
        assertTrue("the arrays do not equal", Arrays.equals(new Object[] { fromValue }, criteria.getParameters()));
        criteria = new FieldBetweenCriteria("Field", null, toValue);
        assertTrue("the arrays do not equal", Arrays.equals(new Object[] { toValue }, criteria.getParameters()));
    }

    /**
     * Test the static methods in class FieldBetweenCriteria.
     *
     * @throws Exception to JUnit
     */
    public void testFieldBetweenCriteria2() throws Exception {
        BigDecimal fromValue = new BigDecimal(10);
        BigDecimal toValue = new BigDecimal(100);
        Date fromDate = new Date(1000);
        Date toDate = new Date(10000);

        // test getAmountBetweenCriteria()
        Criteria criteria = FieldBetweenCriteria.getAmountBetweenCriteria(fromValue, toValue);
        assertEquals("the field is not correct", FieldBetweenCriteria.AMOUNT_FIELD,
            ((FieldBetweenCriteria) criteria).getField());
        assertEquals("the fromValue is not correct", fromValue, ((FieldBetweenCriteria) criteria).getFromValue());
        assertEquals("the toValue is not correct", toValue, ((FieldBetweenCriteria) criteria).getToValue());
        assertTrue("the arrays do not equal",
            Arrays.equals(new Object[] { fromValue, toValue }, criteria.getParameters()));

        // test getCreationDateBetweenCriteria()
        criteria = FieldBetweenCriteria.getCreationDateBetweenCriteria(fromDate, null);
        assertEquals("the field is not correct", FieldBetweenCriteria.CREATION_DATE_FIELD,
            ((FieldBetweenCriteria) criteria).getField());
        assertEquals("the fromValue is not correct", fromDate, ((FieldBetweenCriteria) criteria).getFromValue());
        assertEquals("the toValue is not correct", null, ((FieldBetweenCriteria) criteria).getToValue());
        assertTrue("the arrays do not equal", Arrays.equals(new Object[] { fromDate }, criteria.getParameters()));

        // test getModificationDateBetweenCriteria()
        criteria = FieldBetweenCriteria.getModificationDateBetweenCriteria(null, toDate);
        assertEquals("the field is not correct", FieldBetweenCriteria.MODIFICATION_DATE_FIELD,
            ((FieldBetweenCriteria) criteria).getField());
        assertEquals("the fromValue is not correct", null, ((FieldBetweenCriteria) criteria).getFromValue());
        assertEquals("the toValue is not correct", toDate, ((FieldBetweenCriteria) criteria).getToValue());
        assertTrue("the arrays do not equal", Arrays.equals(new Object[] { toDate }, criteria.getParameters()));
    }

    /**
     * Test the constructor and "get" methods in class FieldMatchCriteria.
     *
     * @throws Exception to JUnit
     */
    public void testFieldMatchCriteria1() throws Exception {
        Long value = new Long(10);

        // test constructor and "get" methods
        Criteria criteria = new FieldMatchCriteria("Field", value);
        assertEquals("the field is not correct", "Field", ((FieldMatchCriteria) criteria).getField());
        assertEquals("the value is not correct", value, ((FieldMatchCriteria) criteria).getValue());

        // test getParameters()
        assertTrue("the arrays do not equal", Arrays.equals(new Object[] { value }, criteria.getParameters()));
    }

    /**
     * Test the static methods in class FieldMatchCriteria.
     *
     * @throws Exception to JUnit
     */
    public void testFieldMatchCriteria2() throws Exception {
        int id = 10;
        String user = "user";
        boolean billable = true;

        // test getExpenseTypeMatchCriteria()
        Criteria criteria = FieldMatchCriteria.getExpenseTypeMatchCriteria(id);
        assertEquals("the field is not correct", FieldMatchCriteria.EXPENSE_TYPE_FIELD,
            ((FieldMatchCriteria) criteria).getField());
        assertEquals("the value is not correct", new Long(id), ((FieldMatchCriteria) criteria).getValue());
        assertTrue("the arrays do not equal", Arrays.equals(new Object[] { new Long(id) }, criteria.getParameters()));

        // test getExpenseStatusMatchCriteria()
        criteria = FieldMatchCriteria.getExpenseStatusMatchCriteria(id);
        assertEquals("the field is not correct", FieldMatchCriteria.EXPENSE_STATUS_FIELD,
            ((FieldMatchCriteria) criteria).getField());
        assertEquals("the value is not correct", new Long(id), ((FieldMatchCriteria) criteria).getValue());
        assertTrue("the arrays do not equal", Arrays.equals(new Object[] { new Long(id) }, criteria.getParameters()));

        // test getCreationUserMatchCriteria()
        criteria = FieldMatchCriteria.getCreationUserMatchCriteria(user);
        assertEquals("the field is not correct", FieldMatchCriteria.CREATION_USER_FIELD,
            ((FieldMatchCriteria) criteria).getField());
        assertEquals("the value is not correct", user, ((FieldMatchCriteria) criteria).getValue());
        assertTrue("the arrays do not equal", Arrays.equals(new Object[] { user }, criteria.getParameters()));

        // test getModificationUserMatchCriteria()
        criteria = FieldMatchCriteria.getModificationUserMatchCriteria(user);
        assertEquals("the field is not correct", FieldMatchCriteria.MODIFICATION_USER_FIELD,
            ((FieldMatchCriteria) criteria).getField());
        assertEquals("the value is not correct", user, ((FieldMatchCriteria) criteria).getValue());
        assertTrue("the arrays do not equal", Arrays.equals(new Object[] { user }, criteria.getParameters()));

        // test getBillableMatchCriteria()
        criteria = FieldMatchCriteria.getBillableMatchCriteria(billable);
        assertEquals("the field is not correct", FieldMatchCriteria.BILLABLE_FIELD,
            ((FieldMatchCriteria) criteria).getField());
        assertEquals("the value is not correct", new Short((short) (billable ? 1 : 0)),
            ((FieldMatchCriteria) criteria).getValue());
        assertTrue("the arrays do not equal",
            Arrays.equals(new Object[] { new Short((short) (billable ? 1 : 0)) }, criteria.getParameters()));
    }

    /**
     * Test the constructor and "get" methods in class NotCriteria.
     *
     * @throws Exception to JUnit
     */
    public void testNotCriteria() throws Exception {
        // prepare another criteria
        Long value = new Long(10);
        Criteria criteria = new FieldMatchCriteria("Field", value);

        // test the constructor and "get" methods
        Criteria notCriteria = new NotCriteria(criteria);
        assertEquals("the inner criteria is not correct", criteria, ((NotCriteria) notCriteria).getCriteria());
        assertTrue("the parameters are not correct",
            Arrays.equals(criteria.getParameters(), notCriteria.getParameters()));

        // test getWhereClause()
        String expected = ("not(" + criteria.getWhereClause() + ")").toLowerCase().replaceAll("[ ]", "");
        String result = notCriteria.getWhereClause().toLowerCase().replaceAll("[ ]", "");
        assertEquals("the \"where\" clause is not correct", expected, result);
    }

    /**
     * Test the constructor and methods in class CompositeCriteria.
     *
     * @throws Exception to JUnit
     */
    public void testCompositeCriteria1() throws Exception {
        // prepare some other criterias
        Criteria[] criterias = new Criteria[] {
                new FieldMatchCriteria("FieldMatchCriteria", new Long(1)),
                new FieldBetweenCriteria("FieldBetweenCriteria", new Long(10), new Long(100)),
                new RejectReasonCriteria(1000)
            };

        // test constructor and "get" methods
        String operator = "operator";
        Criteria composite = new CompositeCriteria(operator, criterias);
        assertEquals("the composition keyword is not correct", operator,
            ((CompositeCriteria) composite).getCompositionKeyword());
        assertTrue("the criterias are not correct",
            Arrays.equals(criterias, ((CompositeCriteria) composite).getCriteria()));

        // test getWhereClause()
        String expected = "(" + criterias[0].getWhereClause() + ")";

        for (int i = 1; i < criterias.length; ++i) {
            expected += (operator + "(" + criterias[i].getWhereClause() + ")");
        }

        expected = expected.toLowerCase().replaceAll("[ ]", "");

        String result = composite.getWhereClause().toLowerCase();
        result = result.replaceAll("[ ]", "");
        assertEquals("the \"where\" clause is not correct", expected, result);

        // test getParameters()
        List expectedArray = new ArrayList();

        for (int i = 0; i < criterias.length; ++i) {
            expectedArray.addAll(Arrays.asList(criterias[i].getParameters()));
        }

        Object[] resultArray = composite.getParameters();
        assertTrue("the parameters are not correct", Arrays.equals(expectedArray.toArray(), resultArray));
    }

    /**
     * Test the static methods in class CompositeCriteria.
     *
     * @throws Exception to JUnit
     */
    public void testCompositeCriteria2() throws Exception {
        // prepare some other criterias
        Criteria[] criterias = new Criteria[] {
                new FieldMatchCriteria("FieldMatchCriteria", new Long(1)),
                new FieldBetweenCriteria("FieldBetweenCriteria", new Integer(10), new Integer(100)),
            };

        // test getAndCompositeCriteria()
        Criteria composite = CompositeCriteria.getAndCompositeCriteria(criterias[0], criterias[1]);
        String expected = "(" + criterias[0].getWhereClause() + ")" + CompositeCriteria.AND_COMPOSITION + "(" +
            criterias[1].getWhereClause() + ")";
        expected = expected.toLowerCase().replaceAll("[ ]", "");

        String result = composite.getWhereClause().toLowerCase();
        result = result.replaceAll("[ ]", "");
        assertEquals("the \"where\" clause is not correct", expected, result);

        // test getOrCompositeCriteria()
        composite = CompositeCriteria.getOrCompositeCriteria(criterias[0], criterias[1]);
        expected = "(" + criterias[0].getWhereClause() + ")" + CompositeCriteria.OR_COMPOSITION + "(" +
            criterias[1].getWhereClause() + ")";
        expected = expected.toLowerCase().replaceAll("[ ]", "");
        result = composite.getWhereClause().toLowerCase();
        result = result.replaceAll("[ ]", "");
        assertEquals("the \"where\" clause is not correct", expected, result);
    }
}
