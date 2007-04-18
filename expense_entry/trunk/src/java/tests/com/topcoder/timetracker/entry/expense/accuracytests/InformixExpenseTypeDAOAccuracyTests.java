/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.entry.expense.accuracytests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.expense.ExpenseType;
import com.topcoder.timetracker.entry.expense.persistence.InformixExpenseTypeDAO;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.criteria.FieldBetweenCriteria;
import com.topcoder.timetracker.entry.expense.criteria.FieldLikeCriteria;
import com.topcoder.timetracker.entry.expense.criteria.FieldMatchCriteria;

/**
 * <p>
 * The accuracy tests for the {@link com.topcoder.timetracker.entry.expense.ExpenseType } class. It covers only new 2.0
 * requirements.
 * </p>
 * 
 * @author kr00tki, brain_cn
 * @version 3.2
 * @version 2.0
 */
public class InformixExpenseTypeDAOAccuracyTests extends TestCase {
	/**
	 * The InformixExpenseTypeDAO instance to test on.
	 */
	private InformixExpenseTypeDAO persistence = null;

	/**
	 * Sets up the test environment.
	 * 
	 * @throws Exception to JUnit.
	 */
	protected void setUp() throws Exception {
    	ConfigHelper.loadNamespaces();

		// Create the persistence
		persistence = new InformixExpenseTypeDAO();
		TestHelper.initDatabase();
	}

	/**
	 * Clears after test.
	 * 
	 * @throws Exception to JUnit.
	 */
	protected void tearDown() throws Exception {
        TestHelper.clearDatabase();
    	ConfigHelper.releaseNamespaces();
	}

	/**
	 * Creates the test ExpenseType object.
	 * 
	 * @param typeId the id.
	 * @param companyId the company id.
	 * @return the ExpenseType object.
	 */
	private ExpenseType createType(int typeId, int companyId) {
		ExpenseType type = new ExpenseType(typeId);
		type.setDescription("desc");
		type.setCompanyId(companyId);
		type.setCreationUser("user");
		type.setModificationUser("user2");

		return type;
	}

	/**
	 * Tests {@link InformixExpenseTypeDAO#addType(ExpenseType)} method accuracy.
	 * 
	 * @throws Exception to JUnit.
	 */
	public void testAddType() throws Exception {
		ExpenseType type = createType(5, 10);
		type.setActive(true);
		persistence.addType(type);

		ExpenseType type2 = persistence.retrieveType(type.getId());
		assertEquals("Incorrect company.", type.getCompanyId(), type2.getCompanyId());
		assertEquals("Incorrect active flag", type.getActive(), type2.getActive());
		TestHelper.assertEquals(type, type2);
	}

	/**
	 * Tests {@link InformixExpenseTypeDAO#updateType(ExpenseType)} method accuracy.
	 * 
	 * @throws Exception to JUnit.
	 */
	public void testUpdateType() throws Exception {
		ExpenseType type = createType(5, 10);
		type.setActive(false);
		persistence.addType(type);

		type.setCompanyId(20);
		type.setActive(true);
		assertTrue("Should be updated.", persistence.updateType(type));

		ExpenseType type2 = persistence.retrieveType(type.getId());
		assertEquals("Incorrect company.", type.getCompanyId(), type2.getCompanyId());
		assertEquals("Incorrect active flag", type.getActive(), type2.getActive());
		TestHelper.assertEquals(type, type2);
	}

	/**
	 * Tests {@link InformixExpenseTypeDAO#searchEntries(Criteria)} method accuracy. Check the company search.
	 * 
	 * @throws Exception to JUnit.
	 */
	public void testSearchEntriesByCompany() throws Exception {
		persistence.addType(createType(10, 30));
		persistence.addType(createType(20, 30));
		persistence.addType(createType(30, 20));

		Criteria criteria = FieldMatchCriteria.getExpenseTypeCompanyIdMatchCriteria(30);
		ExpenseType[] result = persistence.searchEntries(criteria);
		assertEquals("Should have 2 entries", 2, result.length);
		assertEquals("Incorrect id.", 10, result[0].getId());
		assertEquals("Incorrect id.", 20, result[1].getId());
	}

	/**
	 * Tests {@link InformixExpenseTypeDAO#searchEntries(Criteria)} method accuracy. Check the creation user search.
	 * 
	 * @throws Exception to JUnit.
	 */
	public void testSearchEntriesByCreationDate() throws Exception {
		persistence.addType(createType(10, 10));
		persistence.addType(createType(20, 10));
		persistence.addType(createType(30, 20));

		Thread.sleep(2000);
		Criteria criteria = FieldBetweenCriteria.getExpenseTypeCreationDateBetweenCriteria(null, new Date());
		ExpenseType[] result = persistence.searchEntries(criteria);
		assertEquals("Should have 4 entries", 4, result.length);
	}

	/**
	 * Tests {@link InformixExpenseTypeDAO#searchEntries(Criteria)} method accuracy. Check the modification date search.
	 * 
	 * @throws Exception to JUnit.
	 */
	public void testSearchEntriesByModificationDate() throws Exception {
		persistence.addType(createType(10, 10));
		persistence.addType(createType(20, 10));
		persistence.addType(createType(30, 20));

		Thread.sleep(2000);
		Criteria criteria = FieldBetweenCriteria.getExpenseTypeModificationDateBetweenCriteria(null, new Date());
		ExpenseType[] result = persistence.searchEntries(criteria);
		assertEquals("Should have 4 entries", 4, result.length);
	}

	/**
	 * Tests {@link InformixExpenseTypeDAO#searchEntries(Criteria)} method accuracy. Check the modification user search.
	 * 
	 * @throws Exception to JUnit.
	 */
	public void testSearchEntriesByModificationUser() throws Exception {
		persistence.addType(createType(10, 10));

		Criteria criteria = FieldMatchCriteria.getExpenseTypeModificationUserMatchCriteria("user2");
		ExpenseType[] result = persistence.searchEntries(criteria);
		assertEquals("Should have 1 entry", 1, result.length);
	}

	/**
	 * Tests {@link InformixExpenseTypeDAO#searchEntries(Criteria)} method accuracy. Check the creation user search.
	 * 
	 * @throws Exception to JUnit.
	 */
	public void testSearchEntriesByCreationUser() throws Exception {
		persistence.addType(createType(10, 10));

		Criteria criteria = FieldMatchCriteria.getExpenseTypeCreationUserMatchCriteria("user");
		ExpenseType[] result = persistence.searchEntries(criteria);
		assertEquals("Should have 1 entry", 1, result.length);
	}

	/**
	 * Tests {@link InformixExpenseTypeDAO#searchEntries(Criteria)} method accuracy. Check the description search.
	 * 
	 * @throws Exception to JUnit.
	 */
	public void testSearchEntriesByDescription() throws Exception {
		persistence.addType(createType(10, 10));

		Criteria criteria = FieldLikeCriteria.getExpenseTypeDescriptionContainsCriteria("de");
		ExpenseType[] result = persistence.searchEntries(criteria);
		assertEquals("Should have 1 entry", 1, result.length);
	}

	/**
	 * Tests {@link InformixExpenseTypeDAO#searchEntries(Criteria)} method accuracy. Check the active flag search.
	 * 
	 * @throws Exception to JUnit.
	 */
	public void testSearchEntriesByActiveFlag() throws Exception {
		ExpenseType type = createType(10, 10);
		type.setActive(true);
		persistence.addType(type);

		type = createType(20, 10);
		persistence.addType(type);

		type = createType(30, 20);
		persistence.addType(type);

		Criteria criteria = FieldMatchCriteria.getExpenseTypeActiveMatchCriteria(false);
		ExpenseType[] result = persistence.searchEntries(criteria);
		assertEquals("Should have 2 active entries", 2, result.length);
	}
}
