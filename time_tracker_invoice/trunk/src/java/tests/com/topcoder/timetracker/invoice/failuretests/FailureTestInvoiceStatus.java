/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.failuretests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.timetracker.invoice.InvoiceStatus;

/**
 * This class contains unit tests for <code>InvoiceStatus</code> class.
 * 
 * @author enefem21
 * @version 1.0
 */
public class FailureTestInvoiceStatus extends TestCase {
	/**
	 * The Date instance used to test.
	 */
	private Date date = new Date();

	/**
	 * Tests
	 * <code>InvoiceStatus(long id, String description, String creationUser,
	 * String modificationUser, Date creationDate, Date modificationDate)</code>
	 * method for failure with null Description. IllegalArgumentException should
	 * be thrown.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testInvoiceStatusNullDescription() throws Exception {
		try {
			new InvoiceStatus(1, null, "C", "M", date, date);
			fail("testInvoiceStatus_NullDescription is failure.");
		} catch (IllegalArgumentException iae) {
			// pass
		}
	}

	/**
	 * Tests
	 * <code>InvoiceStatus(long id, String description, String creationUser,
	 * String modificationUser, Date creationDate, Date modificationDate)</code>
	 * method for failure with empty Description, IllegalArgumentException
	 * should be thrown.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testInvoiceStatusEmptyDescription() throws Exception {
		try {
			new InvoiceStatus(1, " ", "C", "M", date, date);
			fail("testInvoiceStatus_EmptyDescription is failure.");
		} catch (IllegalArgumentException iae) {
			// pass
		}
	}

	/**
	 * Tests
	 * <code>InvoiceStatus(long id, String description, String creationUser,
	 * String modificationUser, Date creationDate, Date modificationDate)</code>
	 * method for failure with null CreationUser. IllegalArgumentException
	 * should be thrown.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testInvoiceStatusNullCreationUser() throws Exception {
		try {
			new InvoiceStatus(1, "status", null, "M", date, date);
			fail("testInvoiceStatus_NullCreationUser is failure.");
		} catch (IllegalArgumentException iae) {
			// pass
		}
	}

	/**
	 * Tests
	 * <code>InvoiceStatus(long id, String description, String creationUser,
	 * String modificationUser, Date creationDate, Date modificationDate)</code>
	 * method for failure with empty CreationUser, IllegalArgumentException
	 * should be thrown.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testInvoiceStatusEmptyCreationUser() throws Exception {
		try {
			new InvoiceStatus(1, "status", " ", "M", date, date);
			fail("testInvoiceStatus_EmptyCreationUser is failure.");
		} catch (IllegalArgumentException iae) {
			// pass
		}
	}

	/**
	 * Tests
	 * <code>InvoiceStatus(long id, String description, String creationUser,
	 * String modificationUser, Date creationDate, Date modificationDate)</code>
	 * method for failure with null ModificationUser. IllegalArgumentException
	 * should be thrown.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testInvoiceStatusNullModificationUser() throws Exception {
		try {
			new InvoiceStatus(1, "status", "C", null, date, date);
			fail("testInvoiceStatus_NullModificationUser is failure.");
		} catch (IllegalArgumentException iae) {
			// pass
		}
	}

	/**
	 * Tests
	 * <code>InvoiceStatus(long id, String description, String creationUser,
	 * String modificationUser, Date creationDate, Date modificationDate)</code>
	 * method for failure with empty ModificationUser, IllegalArgumentException
	 * should be thrown.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testInvoiceStatusEmptyModificationUser() throws Exception {
		try {
			new InvoiceStatus(1, "status", "C", " ", date, date);
			fail("testInvoiceStatus_EmptyModificationUser is failure.");
		} catch (IllegalArgumentException iae) {
			// pass
		}
	}

	/**
	 * Tests
	 * <code>InvoiceStatus(long id, String description, String creationUser,
	 * String modificationUser, Date creationDate, Date modificationDate)</code>
	 * method for failure with null CreationDate. IllegalArgumentException
	 * should be thrown.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testInvoiceStatusNullCreationDate() throws Exception {
		try {
			new InvoiceStatus(1, "status", "C", "M", null, date);
			fail("testInvoiceStatus_NullCreationDate is failure.");
		} catch (IllegalArgumentException iae) {
			// pass
		}
	}

	/**
	 * Tests
	 * <code>InvoiceStatus(long id, String description, String creationUser,
	 * String modificationUser, Date creationDate, Date modificationDate)</code>
	 * method for failure with null ModificationDate. IllegalArgumentException
	 * should be thrown.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testInvoiceStatusNullModificationDate() throws Exception {
		try {
			new InvoiceStatus(1, "status", "C", "M", date, null);
			fail("testInvoiceStatus_NullModificationDate is failure.");
		} catch (IllegalArgumentException iae) {
			// pass
		}
	}
}