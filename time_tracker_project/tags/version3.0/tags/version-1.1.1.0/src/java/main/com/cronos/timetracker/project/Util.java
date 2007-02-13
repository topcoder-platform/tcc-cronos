/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project;

import com.topcoder.util.generator.guid.Generator;
import com.topcoder.util.generator.guid.UUID;
import com.topcoder.util.generator.guid.UUIDType;
import com.topcoder.util.generator.guid.UUIDUtility;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;


/**
 * <p>
 * A utility class to check the arguments passed to the methods as well as generate unique ids. This class is declared
 * as package-private, so that it can be used by classes from the same package only.
 * </p>
 *
 * @author DanLazar, colau
 * @author real_vg, TCSDEVELOPER
 * @version 1.1
 *
 * @since 1.0
 */
class Util {
    /**
     * <p>
     * The GUID Generator to generate unique ids for clients and projects.
     * </p>
     */
    private static Generator generator = UUIDUtility.getGenerator(UUIDType.TYPEINT32);

    /**
     * <p>
     * Private constructor to prevent instantiation.
     * </p>
     */
    private Util() {
    }

    /**
     * <p>
     * Checks if the given string is null or empty.
     * </p>
     *
     * @param str the string to check against.
     *
     * @throws NullPointerException if str is null.
     * @throws IllegalArgumentException if str is empty.
     */
    static void checkString(String str) {
        if (str == null) {
            throw new NullPointerException("String argument is null");
        }
        if (str.trim().length() == 0) {
            throw new IllegalArgumentException("String argument is empty string");
        }
    }

    /**
     * <p>
     * Checks if the given Object array is null, empty or contains null element.
     * </p>
     *
     * @param array the array to check against.
     *
     * @throws IllegalArgumentException if array is null, empty or contains null element.
     *
     * @since 1.1
     */
    static void checkArray(Object[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array argument is null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array argument is empty");
        }
        if (Arrays.asList(array).contains(null)) {
            throw new IllegalArgumentException("Array argument contains null element");
        }
    }

    /**
     * <p>
     * Checks if the given set is non-null and contains Integer elements only.
     * </p>
     *
     * @param set the set to check against.
     *
     * @throws NullPointerException if set is null.
     * @throws IllegalArgumentException if set contains null or non-Integer element.
     */
    static void checkIntegerSet(Set set) {
        if (set == null) {
            throw new NullPointerException("Set argument is null");
        }
        for (Iterator i = set.iterator(); i.hasNext();) {
            Object obj = i.next();

            if (obj == null) {
                throw new IllegalArgumentException("Set argument contains null element");
            }
            if (!(obj instanceof Integer)) {
                throw new IllegalArgumentException("Set argument contains non-Integer element");
            }
        }
    }

    /**
     * <p>
     * Checks if the client contains all necessary fields for update.
     * </p>
     *
     * @param client the client to check.
     *
     * @throws NullPointerException if the client is null
     * @throws InsufficientDataException if name, creationUser or modificationUser is null
     */
    static void checkUpdateClient(Client client) throws InsufficientDataException {
        if (client == null) {
            throw new NullPointerException("client is null");
        }
        if (client.getName() == null) {
            throw new InsufficientDataException("name field is null");
        }
        if (client.getCreationUser() == null) {
            throw new InsufficientDataException("creationUser field is null");
        }
        if (client.getModificationUser() == null) {
            throw new InsufficientDataException("modificationUser is null");
        }
    }

    /**
     * <p>
     * Checks if the client contains all necessary fields for addition. If the id of the given client is -1, an id will
     * be generated using GUID Generator.
     * </p>
     *
     * @param client the client to check.
     *
     * @throws NullPointerException if the client is null
     * @throws IllegalArgumentException if creationDate or modificationDate is not null
     * @throws InsufficientDataException if name, creationUser or modificationUser is null
     */
    static void checkAddClient(Client client) throws InsufficientDataException {
        if (client == null) {
            throw new NullPointerException("client is null");
        }
        if (client.getCreationDate() != null) {
            throw new IllegalArgumentException("creationDate field is not null");
        }
        if (client.getModificationDate() != null) {
            throw new IllegalArgumentException("modificationDate field is not null");
        }
        checkUpdateClient(client);

        // if id is -1, generate an id for it
        if (client.getId() == -1) {
            client.setId(getNextInt());
        }
    }

    /**
     * <p>
     * Checks if the project contains all necessary fields for update.
     * </p>
     *
     * @param project the project to check.
     *
     * @throws NullPointerException if the project is null
     * @throws InsufficientDataException if any field except creationDate and modificationDate is null
     */
    static void checkUpdateProject(Project project) throws InsufficientDataException {
        if (project == null) {
            throw new NullPointerException("project is null");
        }
        if (project.getName() == null) {
            throw new InsufficientDataException("name field is null");
        }
        if (project.getDescription() == null) {
            throw new InsufficientDataException("description field is null");
        }
        if (project.getStartDate() == null) {
            throw new InsufficientDataException("startDate field is null");
        }
        if (project.getEndDate() == null) {
            throw new InsufficientDataException("endDate field is null");
        }
        if (project.getCreationUser() == null) {
            throw new InsufficientDataException("creationUser field is null");
        }
        if (project.getModificationUser() == null) {
            throw new InsufficientDataException("modificationUser field is null");
        }
    }

    /**
     * <p>
     * Checks if the project contains all necessary fields for addition. If the id of the given project is -1, an id
     * will be generated using GUID Generator.
     * </p>
     *
     * @param project the project to check.
     *
     * @throws NullPointerException if the project is null
     * @throws IllegalArgumentException if creationDate or modificationDate is not null
     * @throws InsufficientDataException if any field except creationDate and modificationDate is null
     */
    static void checkAddProject(Project project) throws InsufficientDataException {
        if (project == null) {
            throw new NullPointerException("project is null");
        }
        if (project.getCreationDate() != null) {
            throw new IllegalArgumentException("creationDate field is not null");
        }
        if (project.getModificationDate() != null) {
            throw new IllegalArgumentException("modificationDate field is not null");
        }
        checkUpdateProject(project);

        // if id is -1, generate an id for it
        if (project.getId() == -1) {
            project.setId(getNextInt());
        }
    }

    /**
     * <p>
     * Checks if the project manager contains all necessary fields for addition.
     * </p>
     *
     * @param projectManager the project manager to check.
     *
     * @throws NullPointerException if the projectManager is null
     * @throws IllegalArgumentException if creationDate or modificationDate is not null
     * @throws InsufficientDataException if any field except creationDate and modificationDate is null
     */
    static void checkAddProjectManager(ProjectManager projectManager)
        throws InsufficientDataException {
        if (projectManager == null) {
            throw new NullPointerException("projectManager is null");
        }
        if (projectManager.getCreationDate() != null) {
            throw new IllegalArgumentException("creationDate field is not null");
        }
        if (projectManager.getModificationDate() != null) {
            throw new IllegalArgumentException("modificationDate field is not null");
        }
        if (projectManager.getProject() == null) {
            throw new InsufficientDataException("project field is null");
        }
        if (projectManager.getCreationUser() == null) {
            throw new InsufficientDataException("creationUser field is null");
        }
        if (projectManager.getModificationUser() == null) {
            throw new InsufficientDataException("modificationUser field is null");
        }
    }

    /**
     * <p>
     * Checks if the project worker contains all necessary fields for update.
     * </p>
     *
     * @param projectWorker the project worker to check.
     *
     * @throws NullPointerException if the projectWorker is null
     * @throws InsufficientDataException if any field except creationDate and modificationDate is null
     */
    static void checkUpdateWorker(ProjectWorker projectWorker)
        throws InsufficientDataException {
        if (projectWorker == null) {
            throw new NullPointerException("projectWorker is null");
        }
        if (projectWorker.getProject() == null) {
            throw new InsufficientDataException("project field is null");
        }
        if (projectWorker.getStartDate() == null) {
            throw new InsufficientDataException("startDate field is null");
        }
        if (projectWorker.getEndDate() == null) {
            throw new InsufficientDataException("endDate field is null");
        }
        if (projectWorker.getPayRate() == null) {
            throw new InsufficientDataException("payRate field is null");
        }
        if (projectWorker.getCreationUser() == null) {
            throw new InsufficientDataException("creationUser field is null");
        }
        if (projectWorker.getModificationUser() == null) {
            throw new InsufficientDataException("modificationUser field is null");
        }
    }

    /**
     * <p>
     * Checks if the project worker contains all necessary fields for addition.
     * </p>
     *
     * @param projectWorker the project worker to check.
     *
     * @throws NullPointerException if the projectWorker is null
     * @throws IllegalArgumentException if creationDate or modificationDate is not null
     * @throws InsufficientDataException if any field except creationDate and modificationDate is null
     */
    static void checkAddWorker(ProjectWorker projectWorker)
        throws InsufficientDataException {
        if (projectWorker == null) {
            throw new NullPointerException("projectWorker is null");
        }
        if (projectWorker.getCreationDate() != null) {
            throw new IllegalArgumentException("creationDate field is not null");
        }
        if (projectWorker.getModificationDate() != null) {
            throw new IllegalArgumentException("modificationDate field is not null");
        }
        checkUpdateWorker(projectWorker);
    }

    /**
     * <p>
     * Gets the next unique integer returned by the GUID Generator.
     * </p>
     *
     * @return the next unique integer
     */
    private static int getNextInt() {
        UUID uuid = generator.getNextUUID();

        return Long.valueOf(uuid.toString(), 16).intValue();
    }
}
