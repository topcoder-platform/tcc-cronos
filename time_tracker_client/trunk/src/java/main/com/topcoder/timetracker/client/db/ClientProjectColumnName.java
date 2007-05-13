/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.db;

import com.topcoder.util.collection.typesafeenum.Enum;

/**
 * This class is enumeration of the column name's alias in client_project table. It make all the column names can be
 * configured.
 *
 * <p>
 * <b>Thread Safety:</b> This class is thread safe by being immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class ClientProjectColumnName extends Enum {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = -2745117141586917844L;

	/**
     * <p>
     * Represents the client id column name in client_project table. It will never be null. It will be referenced in
     * ClientInformixDAO class.
     * </p>
     */
    public static final ClientProjectColumnName PROJECT_ID = new ClientProjectColumnName("PROJECT_ID");

    /**
     * <p>
     * Represents the client id column name in client_project table. It will never be null. It will be referenced in
     * ClientInformixDAO class.
     * </p>
     */
    public static final ClientProjectColumnName CLIENT_ID = new ClientProjectColumnName("CLIENT_ID");

    /**
     * <p>
     * Represents the modification date column name in client_project table. It will never be null. It will be
     * referenced in ClientInformixDAO class.
     * </p>
     */
    public static final ClientProjectColumnName MODIFICATION_DATE = new ClientProjectColumnName("MODIFICATION_DATE");

    /**
     * <p>
     * Represents the modification user column name in client_project table. It will never be null. It will be
     * referenced in ClientInformixDAO class.
     * </p>
     */
    public static final ClientProjectColumnName MODIFICATION_USER = new ClientProjectColumnName("MODIFICATION_USER");

    /**
     * <p>
     * Represents the creation date column name in client_project table. It will never be null. It will be referenced
     * in ClientInformixDAO class.
     * </p>
     */
    public static final ClientProjectColumnName CREATION_DATE = new ClientProjectColumnName("CREATION_DATE");

    /**
     * <p>
     * Represents the creation user column name in client_project table. It will never be null. It will be referenced
     * in ClientInformixDAO class.
     * </p>
     */
    public static final ClientProjectColumnName CREATION_USER = new ClientProjectColumnName("CREATION_USER");

    /**
     * <p>
     * Represents the name of the client_project column. It will never be null or empty. It is set in the constructor.
     * It will be referenced&nbsp; in the getName and toString methods.
     * </p>
     */
    private final String name;

    /**
     * <p>
     * Constructs the ClientProjectColumnName.
     * </p>
     *
     * @param name non null, non empty name
     */
    private ClientProjectColumnName(String name) {
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
     * Get the name.
     * </p>
     *
     * @return non null, non empty name
     */
    public String toString() {
        return name;
    }
}
