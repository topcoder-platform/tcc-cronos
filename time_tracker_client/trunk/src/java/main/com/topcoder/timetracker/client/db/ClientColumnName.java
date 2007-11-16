/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.db;

import com.topcoder.util.collection.typesafeenum.Enum;

/**
 * <p>
 * This class is enumeration of the column name's alias in client table. It make all the column names can be
 * configured.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is thread safe by being immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class ClientColumnName extends Enum {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = 2576728866471256451L;

	/**
     * <p>
     * Represents the name column name in client table. It will never be null. It will be referenced in
     * ClientInformixDAO class.
     * </p>
     */
    public static final ClientColumnName NAME = new ClientColumnName("NAME");

    /**
     * <p>
     * Represents the greek_name column name in client table. It will never be null. It will be referenced in
     * ClientInformixDAO class.
     * </p>
     */
    public static final ClientColumnName GREEK_NAME = new ClientColumnName("GREEK_NAME");

    /**
     * <p>
     * Represents the company id column name in client table. It will never be null. It will be referenced
     * in ClientInformixDAO class.
     * </p>
     */
    public static final ClientColumnName COMPANY_ID = new ClientColumnName("COMPANY_ID");

    /**
     * <p>
     * Represents the creation date column name in client_project table. It will never be null. It will be
     * referenced in ClientInformixDAO class.
     * </p>
     */
    public static final ClientColumnName CREATION_DATE = new ClientColumnName("CREATION_DATE");

    /**
     * <p>
     * Represents the creation user column name in client_project table. It will never be null. It will be
     * referenced in ClientInformixDAO class.
     * </p>
     */
    public static final ClientColumnName CREATION_USER = new ClientColumnName("CREATION_USER");

    /**
     * <p>
     * Represents the modification date column name in client_project table. It will never be null. It will be
     * referenced in ClientInformixDAO class.
     * </p>
     */
    public static final ClientColumnName MODIFICATION_DATE = new ClientColumnName("MODIFICATION_DATE");

    /**
     * <p>
     * Represents the modification user column name in client_project table. It will never be null. It will be
     * referenced in ClientInformixDAO class.
     * </p>
     */
    public static final ClientColumnName MODIFICATION_USER = new ClientColumnName("MODIFICATION_USER");

    /**
     * <p>
     * Represents the start date column name in client_project table. It will never be null. It will be referenced
     * in ClientInformixDAO class.
     * </p>
     */
    public static final ClientColumnName START_DATE = new ClientColumnName("START_DATE");

    /**
     * <p>
     * Represents theend date column name in client_project table. It will never be null. It will be referenced
     * in ClientInformixDAO class.
     * </p>
     */
    public static final ClientColumnName END_DATE = new ClientColumnName("END_DATE");

    /**
     * <p>
     * Represents thesales tax column name in client_project table. It will never be null. It will be referenced
     * in ClientInformixDAO class.
     * </p>
     */
    public static final ClientColumnName SALES_TAX = new ClientColumnName("SALES_TAX");

    /**
     * <p>
     * Represents theactive column name in client_project table. It will never be null. It will be referenced in
     * ClientInformixDAO class.
     * </p>
     */
    public static final ClientColumnName ACTIVE = new ClientColumnName("ACTIVE");

    /**
     * <p>
     * Represents thepayment term id column name in client_project table. It will never be null. It will be
     * referenced in ClientInformixDAO class.
     * </p>
     */
    public static final ClientColumnName PAYMENT_TERM_ID = new ClientColumnName("PAYMENT_TERM_ID");

    /**
     * <p>
     * Represents the all column in client_project table. It will never be null. It will be referenced in
     * ClientInformixDAO class.
     * </p>
     */
    public static final ClientColumnName ALL = new ClientColumnName("ALL");

    /**
     * <p>
     * Represents the id column name in client_project table. It will never be null. It will be referenced in
     * ClientInformixDAO class.
     * </p>
     */
    public static final ClientColumnName ID = new ClientColumnName("ID");

    /**
     * <p>
     * Represents the name of the client column. It will never be null or empty. It is set in the constructor. It will
     * be referenced in the getName and toString methods.
     * </p>
     */
    private final String name;

    /**
     * <p>
     * Constructs the ClientColumnName.
     * </p>
     *
     * @param name non null, non empty name
     */
    private ClientColumnName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Get the name.
     * </p>
     *
     * @return non null, non empty name
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Return the string presentation.
     * </p>
     *
     * @return non null, non empty name
     */
    public String toString() {
        return name;
    }
}
