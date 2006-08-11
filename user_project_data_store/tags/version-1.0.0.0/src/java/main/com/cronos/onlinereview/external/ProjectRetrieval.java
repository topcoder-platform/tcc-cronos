/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * User Project Data Store 1.0
 */
package com.cronos.onlinereview.external;

/**
 * <p>This is the interface for the User Project Data Store component which retrieves projects from
 * persistent storage.</p>
 * <p>There is a method to retrieve individual projects by their id and additional methods that retrieve
 * sets of projects in bulk.</p>
 * <p>Implementations of this interface should be implemented in a thread-safe manner.</p>
 *
 * @author dplass, oodinary
 * @version 1.0
 */
public interface ProjectRetrieval {

    /**
     * <p>Retrieves the external project with the given id.</p>
     *
     * @return the external project which has the given id, or null if not found.
     * @param id the id of the project we are interested in.
     *
     * @throws IllegalArgumentException if id is not positive.
     * @throws RetrievalException if any exception occurred during processing; it will wrap the
     * underlying exception.
     */
    public ExternalProject retrieveProject(long id) throws RetrievalException;

    /**
     * <p>Retrieves the external projects with the given name and version.</p>
     * <p>Note that since names and version texts are not unique (probably due to being in different
     * catalogs) there may be more than one project that corresponds to this name and version.</p>
     *
     * @return external projects which have the given name and version text.
     * @param name the name of the project we are interested in.
     * @param version the version of the project we are interested in.
     *
     * @trows IllegalArgumentException if either argument is null or empty after trim.
     * @throws RetrievalException if any exception occurred during processing; it will wrap the
     * underlying exception.
     */
    public ExternalProject[] retrieveProject(String name, String version) throws RetrievalException;

    /**
     * <p>Retrieves the external projects with the given ids.</p>
     * <p>Note that retrieveProjects(ids)[i] will not necessarily correspond to ids[i].</p>
     * <p>If an entry in ids was not found, no entry in the return array will be present.
     * If there are any duplicates in the input array, the output will NOT contain a duplicate
     * ExternalProject.</p>
     *
     * @return an array of external projects who have the given ids. If none of the given ids were found,
     * an empty array will be returned. The index of the entries in the array will not necessarily
     * directly correspond to the entries in the ids array.
     * @param ids the ids of the projects we are interested in.
     *
     * @throws IllegalArgumentException if ids is null or any entry is not positive.
     * @throws RetrievalException if any exception occurred during processing; it will wrap the
     * underlying exception.
     */
    public ExternalProject[] retrieveProjects(long[] ids) throws RetrievalException;

    /**
     * <p>Retrieves the external projects with the given names and versions.</p>
     * <p>Both the name AND version has to match for the record to be retrieved. Note that
     * retrieveProjects(names, versions)[i] will not necessarily correspond to names[i] and versions[i].</p>
     * <p>If an entry in names/versions was not found, no entry in the return array will be present.
     * If there are any duplicates in the input array, the output will NOT contain a duplicate
     * ExternalProject.</p>
     *
     * @return an array of external projects who have the given names and versions. If none were found,
     * an empty array will be returned. The index of the entries in the array will not necessarily
     * directly correspond to the entries in the input array.
     * @param names the names of the projects we are interested in; each entry corresponds to the
     * same indexed entry in the versions array.
     * @param versions the versions of the projects we are interested in; each entry corresponds to
     * the same indexed entry in the names array.
     *
     * @throws IllegalArgumentException if either array is null or any entry in either array is null
     * or empty after trim.
     * @throws RetrievalException if any exception occurred during processing; it will wrap the
     * underlying exception.
     */
    public ExternalProject[] retrieveProjects(String[] names, String[] versions) throws RetrievalException;
}


