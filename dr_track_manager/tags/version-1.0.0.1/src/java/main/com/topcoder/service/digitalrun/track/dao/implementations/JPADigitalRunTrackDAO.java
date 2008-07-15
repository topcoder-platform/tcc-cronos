/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.dao.implementations;

import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.service.digitalrun.entity.ProjectType;
import com.topcoder.service.digitalrun.entity.Track;
import com.topcoder.service.digitalrun.track.DigitalRunTrackDAO;
import com.topcoder.service.digitalrun.track.DigitalRunTrackManagerPersistenceException;
import com.topcoder.service.digitalrun.track.EntityNotFoundException;
import com.topcoder.service.digitalrun.track.Helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;


/**
 * <p>
 * This class implements <code>DigitalRunTrackDAO</code> interface. It also extends the <code>AbstractDAO</code> class.
 * This class manages <code>Track</code> entities in a JPA persistence (currently the JPA persistence will use
 * Hibernate as a provider but any provider can be used). It uses Search Builder component to search for
 * DigitalRunPoints entities. Each public method performs logging.
 * </p>
 *
 * <p>
 * This class is not completely thread safe because it doesn't manage transactions and it is also mutable. Anyway, the
 * intent is to use this implementation in a stateless session bean so there will be no thread safety issues generated
 * by this class since the container will ensure thread safety.
 * </p>
 *
 * @see com.topcoder.service.digitalrun.track.DigitalRunTrackDAO
 * @see com.topcoder.service.digitalrun.track.dao.implementations.AbstractDAO
 * @author DanLazar, waits
 * @version 1.0
 */
public class JPADigitalRunTrackDAO extends AbstractDAO implements DigitalRunTrackDAO {
    /**
     * <p>
     * SQL to insert a record into the track_project_type_xref table.
     * </p>
     */
    private static final String ADD_PROJECT_TYPE_TRACK =
        "INSERT INTO track_project_type_xref (track_id, project_type_id) VALUES( :trackId, :projectTypeId)";

    /**
     * <p>
     * SQL to delete record from track_project_type_xref table.
     * </p>
     */
    private static final String DELETE_TRACK_PROJECT_TYPE =
        "DELETE FROM track_project_type_xref WHERE track_id = :trackId AND project_type_id = :projectTypeId";

    /**
     * <p>
     * Query clause to find active track.
     * </p>
     */
    private static final String QUERY_ACTIVE_TRACKS =
        "SELECT t FROM Track t WHERE t.trackStatus.id ="
         + " :activeStatusId AND t.startDate <= :current AND t.endDate >= :current";

    /**
     * <p>
     * The SearchBundle instance that will be used to search for Track entities instances that match a filter.  It has
     * a setter; it cannot be set to null.It will be used in searchTracks method to search for Tracks instances that
     * match a given filter.
     * </p>
     */
    private SearchBundle searchBundle;

    /**
     * <p>
     * The id that indicates the active status.It has a setter; it can not be negative. Used in getActiveTracks method.
     * </p>
     */
    private long activeStatusId;

    /**
     * <p>
     * Default constructor. Simply call super constructor.
     * </p>
     */
    public JPADigitalRunTrackDAO() {
    }

    /**
     * <p>
     * Creates a new Track entity into persistence. Returns the Track instance with id generated.
     * </p>
     *
     * @param track the entity to be created
     *
     * @return entity with id set
     *
     * @throws IllegalArgumentException if argument is null or if its id is positive
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public Track createTrack(Track track) throws DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("createTrack(Track)", getLogger());

        try {
            return persist(track, "Track");
        } finally {
            Helper.logExitInfo("createTrack(Track)", getLogger());
        }
    }

    /**
     * <p>
     * Updates the given Track instance into persistence.
     * </p>
     *
     * @param track the entity to be updated
     *
     * @throws IllegalArgumentException if argument is null
     * @throws EntityNotFoundException if a digital run points entity with Track.id does not exist in the persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void updateTrack(Track track) throws DigitalRunTrackManagerPersistenceException, EntityNotFoundException {
        Helper.logEntranceInfo("updateTrack(Track)", getLogger());

        try {
            update(track, "Track");
        } finally {
            Helper.logExitInfo("updateTrack(Track)", getLogger());
        }
    }

    /**
     * <p>
     * Removes the Track entity identified by the given id from persistence.
     * </p>
     *
     * @param trackId the id that identified the entity to be removed
     *
     * @throws IllegalArgumentException if argument is negative
     * @throws EntityNotFoundException if there is no Track entity with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void removeTrack(long trackId) throws EntityNotFoundException, DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("removeTrack(trackId)", getLogger());

        try {
            remove(Track.class, trackId);
        } finally {
            Helper.logExitInfo("removeTrack(trackId)", getLogger());
        }
    }

    /**
     * <p>
     * Gets the Track entity identified by the given id from persistence.
     * </p>
     *
     * @param trackId the id that identifies the entity top be retrieved
     *
     * @return the entity identified by the id
     *
     * @throws IllegalArgumentException if argument is negative
     * @throws EntityNotFoundException if there is no Track entity  with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public Track getTrack(long trackId) throws EntityNotFoundException, DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("getTrack(trackId)", getLogger());

        try {
            return find(Track.class, trackId);
        } finally {
            Helper.logExitInfo("getTrack(trackId)", getLogger());
        }
    }

    /**
     * <p>
     * Searches the Track entities that match the given filter.  If there is no such entity that matches the given
     * filter an empty list is returned.
     * </p>
     *
     * @param filter the filter used for searching
     *
     * @return the matching digital run points entities
     *
     * @throws IllegalArgumentException if argument is null
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    @SuppressWarnings("unchecked")
    public List < Track > searchTracks(Filter filter) throws DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("searchTracks(Filter)", getLogger());

        try {
            Helper.checkNull(filter, "filter", getLogger());

            List < Track > tracks = (List < Track >) searchBundle.search(filter);

            return (tracks == null) ? new ArrayList<Track>() : tracks;
        } catch (ClassCastException cce) {
            Helper.logException(cce, getLogger());
            throw new DigitalRunTrackManagerPersistenceException("Fail to search tracks", cce);
        } catch (SearchBuilderException e) {
            Helper.logException(e, getLogger());
            throw new DigitalRunTrackManagerPersistenceException("Fail to search tracks", e);
        } finally {
            Helper.logExitInfo("searchTracks(Filter)", getLogger());
        }
    }

    /**
     * <p>
     * Gets the active tracks from persistence. An empty list will be returned if there are no active tracks.
     * </p>
     *
     * @return a list with active tracks or empty list if there are no active tracks
     *
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    @SuppressWarnings("unchecked")
    public List < Track > getActiveTracks() throws DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("getActiveTracks()", getLogger());

        try {
            EntityManager em = this.getEntityManager();
            List < Track > tracks = em.createQuery(QUERY_ACTIVE_TRACKS).setParameter("activeStatusId", activeStatusId)
                                   .setParameter("current", new Date(), TemporalType.DATE).getResultList();

            return (tracks == null) ? new ArrayList<Track>() : tracks;
        } catch (DigitalRunTrackManagerPersistenceException e) {
            throw e;
        } catch (Exception e) {
            Helper.logException(e, getLogger());
            throw new DigitalRunTrackManagerPersistenceException("Fail to find tracks", e);
        } finally {
            Helper.logExitInfo("getActiveTracks()", getLogger());
        }
    }

    /**
     * <p>
     * Adds a project type to the given track.
     * </p>
     *
     * @param projectType the project type
     * @param track the track
     *
     * @throws IllegalArgumentException if any argument is null
     * @throws EntityNotFoundException if the track or project type does not exist in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void addTrackProjectType(Track track, ProjectType projectType)
        throws EntityNotFoundException, DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("addTrackProjectType(Track,ProjectType)", getLogger());

        try {
            updateTrack(track, projectType, ADD_PROJECT_TYPE_TRACK);
        } finally {
            Helper.logExitInfo("addTrackProjectType(Track,ProjectType)", getLogger());
        }
    }

    /**
     * <p>
     * Removes a project type from the given track.
     * </p>
     *
     * @param projectType the project type
     * @param track the track
     *
     * @throws IllegalArgumentException if any argument is null
     * @throws EntityNotFoundException if the track or project type does not exist in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    public void removeTrackProjectType(Track track, ProjectType projectType)
        throws EntityNotFoundException, DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("removeTrackProjectType(Track,ProjectType)", getLogger());

        try {
            updateTrack(track, projectType, DELETE_TRACK_PROJECT_TYPE);
        } finally {
            Helper.logExitInfo("removeTrackProjectType(Track,ProjectType)", getLogger());
        }
    }

    /**
     * <p>
     * Removes or adds projectType into the track.
     * </p>
     *
     * @param projectType the project type
     * @param track the track
     * @param query the update query string
     *
     * @throws IllegalArgumentException if any argument is null
     * @throws EntityNotFoundException if the track or project type does not exist in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    private void updateTrack(Track track, ProjectType projectType, String query)
        throws EntityNotFoundException, DigitalRunTrackManagerPersistenceException {
        try {
            Helper.checkNull(projectType, "ProjectType", getLogger());
            Helper.checkNull(track, "Track", getLogger());

            EntityManager em = this.getEntityManager();

            if (em.find(Track.class, track.getId()) == null) {
                String msg = "The track does not exist.";
                Helper.logError(msg, getLogger());
                throw new EntityNotFoundException(msg);
            }

            if (em.find(ProjectType.class, projectType.getId()) == null) {
                String msg = "The ProjectType does not exist.";
                Helper.logError(msg, getLogger());
                throw new EntityNotFoundException(msg);
            }

            em.createNativeQuery(query).setParameter("trackId", track.getId())
              .setParameter("projectTypeId", projectType.getId()).executeUpdate();
        } catch (IllegalArgumentException iae) {
            throw iae;
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (DigitalRunTrackManagerPersistenceException dpe) {
            throw dpe;
        } catch (Exception e) {
            Helper.logException(e, getLogger());
            throw new DigitalRunTrackManagerPersistenceException("Fail to update track", e);
        }
    }

    /**
     * <p>
     * Sets the searchBundle field.
     * </p>
     *
     * @param searchBundle the search bundle
     *
     * @throws IllegalArgumentException if argument is null
     */
    public void setSearchBundle(SearchBundle searchBundle) {
        Helper.checkNull(searchBundle, "SearchBundle", getLogger());
        this.searchBundle = searchBundle;
    }

    /**
     * <p>
     * Sets the activeStatusId field.
     * </p>
     *
     * @param activeStatusId the active status id
     *
     * @throws IllegalArgumentException if parameter less than 0
     */
    public void setActiveStatusId(long activeStatusId) {
        Helper.checkNegative(activeStatusId, "activeStatusId", getLogger());
        this.activeStatusId = activeStatusId;
    }
}
