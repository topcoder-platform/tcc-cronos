/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition;

import com.topcoder.predictor.Situation;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 * An implementation of the Situation interface that completely describes a component competition, including the
 * technologies and participants in the component competition, the prize awarded for first place, the timeline, and
 * various other pieces of information.
 * </p>
 *
 * <p>
 * Thread-Safety: This class is mutable but thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class ComponentCompetitionSituation implements Situation {
    /**
     * Serialization version UID.
     */
    private static final long serialVersionUID = 4360574172089419078L;

    /**
     * Represents the ID of this component, wrapped in an AtomicReference, so it can be accessed in an atomic,
     * thread-safe manner. The underlying value will be set in the setter, and accessed with the getter. The
     * AtomicReference instance will never be null. The underlying value can be any value. The AtomicReference instance
     * will never change. The underlying value accessed as stated in the Usage section.
     */
    private final AtomicReference<Integer> componentId = new AtomicReference<Integer>();

    /**
     * Represents the ID of the component version, wrapped in an AtomicReference, so it can be accessed in an atomic,
     * thread-safe manner. The underlying value will be set in the setter, and accessed with the getter. The
     * AtomicReference instance will never be null. The underlying value can be any value. The AtomicReference instance
     * will never change. The underlying value accessed as stated in the Usage section.
     */
    private final AtomicReference<Integer> componentVersionId = new AtomicReference<Integer>();

    /**
     * Represents the ID of the project this component is part of, wrapped in an AtomicReference, so it can be accessed
     * in an atomic, thread-safe manner. The underlying value will be set in the setter, and accessed with the getter.
     * The AtomicReference instance will never be null. The underlying value can be any value. The AtomicReference
     * instance will never change. The underlying value accessed as stated in the Usage section.
     */
    private final AtomicReference<Integer> projectId = new AtomicReference<Integer>();

    /**
     * Represents the catalog this component is part of, wrapped in an AtomicReference, so it can be accessed in an
     * atomic, thread-safe manner. The underlying value will be set in the setter, and accessed with the getter. The
     * AtomicReference instance will never be null. The underlying value can be any value. The AtomicReference instance
     * will never change. The underlying value accessed as stated in the Usage section.
     */
    private final AtomicReference<String> catalog = new AtomicReference<String>();

    /**
     * Represents the name of this component, wrapped in an AtomicReference, so it can be accessed in an atomic,
     * thread-safe manner. The underlying value will be set in the setter, and accessed with the getter. The
     * AtomicReference instance will never be null. The underlying value can be any value. The AtomicReference instance
     * will never change. The underlying value accessed as stated in the Usage section.
     */
    private final AtomicReference<String> component = new AtomicReference<String>();

    /**
     * Represents the version of this component, wrapped in an AtomicReference, so it can be accessed in an atomic,
     * thread-safe manner. The underlying value will be set in the setter, and accessed with the getter. The
     * AtomicReference instance will never be null. The underlying value can be any value. The AtomicReference instance
     * will never change. The underlying value accessed as stated in the Usage section.
     */
    private final AtomicReference<String> version = new AtomicReference<String>();

    /**
     * Represents the category of the project this component is part of, wrapped in an AtomicReference, so it can be
     * accessed in an atomic, thread-safe manner. The underlying value will be set in the setter, and accessed with the
     * getter. The AtomicReference instance will never be null. The underlying value can be any value. The
     * AtomicReference instance will never change. The underlying value accessed as stated in the Usage section.
     */
    private final AtomicReference<String> projectCategory = new AtomicReference<String>();

    /**
     * Represents the status of the project this component is part of, wrapped in an AtomicReference, so it can be
     * accessed in an atomic, thread-safe manner. The underlying value will be set in the setter, and accessed with the
     * getter. The AtomicReference instance will never be null. The underlying value can be any value. The
     * AtomicReference instance will never change. The underlying value accessed as stated in the Usage section.
     */
    private final AtomicReference<String> projectStatus = new AtomicReference<String>();

    /**
     * Represents the posting date of this component, wrapped in an AtomicReference, so it can be accessed in an atomic,
     * thread-safe manner. The underlying value will be set in the setter, and accessed with the getter. The
     * AtomicReference instance will never be null. The underlying value can be any value, but if it and this.endValue's
     * undelying value are both not null, then this underlying value must be larger than this.endValue's undelying value
     * The AtomicReference instance will never change. The underlying value accessed as stated in the Usage section.
     */
    private final AtomicReference<Date> postingDate = new AtomicReference<Date>();

    /**
     * Represents the end date of this component, wrapped in an AtomicReference, so it can be accessed in an atomic,
     * thread-safe manner. The underlying value will be set in the setter, and accessed with the getter. The
     * AtomicReference instance will never be null. The underlying value can be any value, but if it and
     * this.postingValue's undelying value are both not null, then this underlying value must be smaller than
     * this.postingValue's undelying value The AtomicReference instance will never change. The underlying value accessed
     * as stated in the Usage section.
     */
    private final AtomicReference<Date> endDate = new AtomicReference<Date>();

    /**
     * Represents the ID of the scorecard used to score this component, wrapped in an AtomicReference, so it can be
     * accessed in an atomic, thread-safe manner. The underlying value will be set in the setter, and accessed with the
     * getter. The AtomicReference instance will never be null. The underlying value can be any value. The
     * AtomicReference instance will never change. The underlying value accessed as stated in the Usage section.
     */
    private final AtomicReference<Integer> scorecardId = new AtomicReference<Integer>();

    /**
     * Represents the number of final fix issues, wrapped in an AtomicReference, so it can be accessed in an atomic,
     * thread-safe manner. The underlying value will be set in the setter, and accessed with the getter. The
     * AtomicReference instance will never be null. The underlying value can be either null or not negative. The
     * AtomicReference instance will never change. The underlying value accessed as stated in the Usage section.
     */
    private final AtomicReference<Integer> finalFixCount = new AtomicReference<Integer>();

    /**
     * Represents the prize amont for this component, wrapped in an AtomicReference, so it can be accessed in an atomic,
     * thread-safe manner. The underlying value will be set in the setter, and accessed with the getter. The
     * AtomicReference instance will never be null. The underlying value can be either null or not negative. The
     * AtomicReference instance will never change. The underlying value accessed as stated in the Usage section.
     */
    private final AtomicReference<Double> prize = new AtomicReference<Double>();

    /**
     * Represents the flag whether this component is rated, wrapped in an AtomicReference, so it can be accessed in an
     * atomic, thread-safe manner. The underlying value will be set in the setter, and accessed with the getter. The
     * AtomicReference instance will never be null. The underlying value can be any value. The AtomicReference instance
     * will never change. The underlying value accessed as stated in the Usage section.
     */
    private final AtomicReference<Boolean> rated = new AtomicReference<Boolean>();

    /**
     * Represents the flag whether this component is part of the digital run, wrapped in an AtomicReference, so it can
     * be accessed in an atomic, thread-safe manner. The underlying value will be set in the setter, and accessed with
     * the getter. The AtomicReference instance will never be null. The underlying value can be any value. The
     * AtomicReference instance will never change. The underlying value accessed as stated in the Usage section.
     */
    private final AtomicReference<Boolean> digitalRun = new AtomicReference<Boolean>();

    /**
     * Represents the number of digital points assigned to this component, wrapped in an AtomicReference, so it can be
     * accessed in an atomic, thread-safe manner. The underlying value will be set in the setter, and accessed with the
     * getter. The AtomicReference instance will never be null. The underlying value can be either null or not negative.
     * The AtomicReference instance will never change. The underlying value accessed as stated in the Usage section.
     */
    private final AtomicReference<Integer> digitalRunPoints = new AtomicReference<Integer>();

    /**
     * Represents the description of this component, wrapped in an AtomicReference, so it can be accessed in an atomic,
     * thread-safe manner. The underlying value will be set in the setter, and accessed with the getter. The
     * AtomicReference instance will never be null. The underlying value can be any value. The AtomicReference instance
     * will never change. The underlying value accessed as stated in the Usage section.
     */
    private final AtomicReference<String> description = new AtomicReference<String>();

    /**
     * Represents the keywords identifying this component. The list reference is created in on class init. Its contents
     * are set and retrieved in the setter and getter, respectively. The list reference will never be null. Its contents
     * will never be null/empty. The list reference will never change, and its content count will vary from 0 to many.
     */
    private final List<String> keywords = new ArrayList<String>();

    /**
     * Represents the technologies associated with this component. The list reference is created in on class init. Its
     * contents are set and retrieved in the setter and getter, respectively. The list reference will never be null. Its
     * contents will never be null. The list reference will never change, and its content count will vary from 0 to
     * many.
     */
    private final List<Technology> technologies = new ArrayList<Technology>();

    /**
     * Represents the participants competing to win this component. The list reference is created in on class init. Its
     * contents are set and retrieved in the setter and getter, respectively. The list reference will never be null. Its
     * contents will never be null. The list reference will never change, and its content count will vary from 0 to
     * many.
     */
    private final List<Participant> participants = new ArrayList<Participant>();

    /**
     * Default empty constructor.
     */
    public ComponentCompetitionSituation() {
    }

    /**
     * Gets the componentId field value.
     *
     * @return the componentId field value
     */
    public Integer getComponentId() {
        return this.componentId.get();
    }

    /**
     * Sets the componentId field value.
     *
     * @param componentId
     *            the componentId field value
     */
    public void setComponentId(Integer componentId) {
        this.componentId.set(componentId);
    }

    /**
     * Gets the componentVersionId field value.
     *
     * @return the componentVersionId field value
     */
    public Integer getComponentVersionId() {
        return this.componentVersionId.get();
    }

    /**
     * Sets the componentVersionId field value.
     *
     * @param componentVersionId
     *            the componentVersionId field value
     */
    public void setComponentVersionId(Integer componentVersionId) {
        this.componentVersionId.set(componentVersionId);
    }

    /**
     * Gets the projectId field value.
     *
     * @return the projectId field value
     */
    public Integer getProjectId() {
        return this.projectId.get();
    }

    /**
     * Sets the projectId field value.
     *
     * @param projectId
     *            the projectId field value
     */
    public void setProjectId(Integer projectId) {
        this.projectId.set(projectId);
    }

    /**
     * Gets the catalog field value.
     *
     * @return the catalog field value
     */
    public String getCatalog() {
        return this.catalog.get();
    }

    /**
     * Sets the catalog field value.
     *
     * @param catalog
     *            the catalog field value
     */
    public void setCatalog(String catalog) {
        this.catalog.set(catalog);
    }

    /**
     * Gets the component field value.
     *
     * @return the component field value
     */
    public String getComponent() {
        return this.component.get();
    }

    /**
     * Sets the component field value.
     *
     * @param component
     *            the component field value
     */
    public void setComponent(String component) {
        this.component.set(component);
    }

    /**
     * Gets the version field value.
     *
     * @return the version field value
     */
    public String getVersion() {
        return this.version.get();
    }

    /**
     * Sets the version field value.
     *
     * @param version
     *            the version field value
     */
    public void setVersion(String version) {
        this.version.set(version);
    }

    /**
     * Gets the projectCategory field value.
     *
     * @return the projectCategory field value
     */
    public String getProjectCategory() {
        return this.projectCategory.get();
    }

    /**
     * Sets the projectCategory field value.
     *
     * @param projectCategory
     *            the projectCategory field value
     */
    public void setProjectCategory(String projectCategory) {
        this.projectCategory.set(projectCategory);
    }

    /**
     * Gets the projectStatus field value.
     *
     * @return the projectStatus field value
     */
    public String getProjectStatus() {
        return this.projectStatus.get();
    }

    /**
     * Sets the projectStatus field value.
     *
     * @param projectStatus
     *            the projectStatus field value
     */
    public void setProjectStatus(String projectStatus) {
        this.projectStatus.set(projectStatus);
    }

    /**
     * Gets the postingDate field value.
     *
     * @return the postingDate field value
     */
    public Date getPostingDate() {
        return this.postingDate.get();
    }

    /**
     * Sets the postingDate field value.
     *
     * @param postingDate
     *            the postingDate field value
     * @throws IllegalArgumentException
     *             If postingDate is not null, and the value of this.endDate is not null, and postingDate >
     *             this.endDate.get()
     */
    public void setPostingDate(Date postingDate) {
        if (postingDate != null && this.endDate.get() != null && postingDate.after(this.endDate.get())) {
            throw new IllegalArgumentException("The posting date should not be after end date.");
        }
        this.postingDate.set(postingDate);
    }

    /**
     * Gets the endDate field value.
     *
     * @return the endDate field value
     */
    public Date getEndDate() {
        return this.endDate.get();
    }

    /**
     * Sets the endDate field value.
     *
     * @param endDate
     *            the endDate field value
     * @throws IllegalArgumentException
     *             If endDate is not null, and the value of this.postingDate is not null, and endDate &lt;
     *             this.postingDate.get()
     */
    public void setEndDate(Date endDate) {
        if (this.postingDate.get() != null && endDate != null && postingDate.get().after(endDate)) {
            throw new IllegalArgumentException("The posting date should not be after end date.");
        }
        this.endDate.set(endDate);
    }

    /**
     * Gets the scorecardId field value.
     *
     * @return the scorecardId field value
     */
    public Integer getScorecardId() {
        return this.scorecardId.get();
    }

    /**
     * Sets the scorecardId field value.
     *
     * @param scorecardId
     *            the scorecardId field value
     */
    public void setScorecardId(Integer scorecardId) {
        this.scorecardId.set(scorecardId);
    }

    /**
     * Gets the finalFixCount field value.
     *
     * @return the finalFixCount field value
     */
    public Integer getFinalFixCount() {
        return this.finalFixCount.get();
    }

    /**
     * Sets the finalFixCount field value.
     *
     * @param finalFixCount
     *            the finalFixCount field value
     * @throws IllegalArgumentException
     *             If finalFixCount is not null and finalFixCount.intValue < 0
     */
    public void setFinalFixCount(Integer finalFixCount) {
        if (finalFixCount != null) {
            Helper.checkNotNegative(finalFixCount, "finalFixCount");
        }
        this.finalFixCount.set(finalFixCount);
    }

    /**
     * Gets the prize field value.
     *
     * @return the prize field value
     */
    public Double getPrize() {
        return this.prize.get();
    }

    /**
     * Sets the prize field value.
     *
     * @param prize
     *            the prize field value
     * @throws IllegalArgumentException
     *             If prize is not null and prize.doubleValue < 0
     */
    public void setPrize(Double prize) {
        if (prize != null) {
            Helper.checkNotNegative(prize, "prize");
        }
        this.prize.set(prize);
    }

    /**
     * Gets the rated field value.
     *
     * @return the rated field value
     */
    public Boolean getRated() {
        return this.rated.get();
    }

    /**
     * Sets the rated field value.
     *
     * @param rated
     *            the rated field value
     */
    public void setRated(Boolean rated) {
        this.rated.set(rated);
    }

    /**
     * Gets the digitalRun field value.
     *
     * @return the digitalRun field value
     */
    public Boolean getDigitalRun() {
        return this.digitalRun.get();
    }

    /**
     * Sets the digitalRun field value.
     *
     * @param digitalRun
     *            the digitalRun field value
     */
    public void setDigitalRun(Boolean digitalRun) {
        this.digitalRun.set(digitalRun);
    }

    /**
     * Gets the digitalRunPoints field value.
     *
     * @return the digitalRunPoints field value
     */
    public Integer getDigitalRunPoints() {
        return this.digitalRunPoints.get();
    }

    /**
     * Sets the digitalRunPoints field value.
     *
     * @param digitalRunPoints
     *            the digitalRunPoints field value
     * @throws IllegalArgumentException
     *             If digitalRunPoints is not null digitalRunPoints.intValue < 0
     */
    public void setDigitalRunPoints(Integer digitalRunPoints) {
        if (digitalRunPoints != null) {
            Helper.checkNotNegative(digitalRunPoints, "digitalRunPoints");
        }
        this.digitalRunPoints.set(digitalRunPoints);
    }

    /**
     * Gets the description field value.
     *
     * @return the description field value
     */
    public String getDescription() {
        return this.description.get();
    }

    /**
     * Sets the description field value.
     *
     * @param description
     *            the description field value
     */
    public void setDescription(String description) {
        this.description.set(description);
    }

    /**
     * Gets the keywords list.
     *
     * @return the keywords list
     */
    public List<String> getKeywords() {
        synchronized (this.keywords) {
            return new ArrayList<String>(this.keywords);
        }
    }

    /**
     * Sets the keywords list.
     *
     * @param keywords
     *            the keywords list
     * @throws IllegalArgumentException -
     *             If any list element is null/empty
     */
    public void setKeywords(List<String> keywords) {
        if (keywords != null) {
            Helper.checkList(keywords, "keywords");
        }
        synchronized (this.keywords) {
            // clear original keywords
            this.keywords.clear();
            if (keywords != null) {
                // add new keywords
                this.keywords.addAll(keywords);
            }
        }
    }

    /**
     * Gets the technologies list.
     *
     * @return the technologies list
     */
    public List<Technology> getTechnologies() {
        synchronized (this.technologies) {
            return new ArrayList<Technology>(this.technologies);
        }
    }

    /**
     * Sets the technologies list.
     *
     * @param technologies
     *            the technologies
     * @throws IllegalArgumentException
     *             If any list element is null
     */
    public void setTechnologies(List<Technology> technologies) {
        if (technologies != null) {
            Helper.checkList(technologies, "technologies");
        }
        synchronized (this.technologies) {
            // clear original technologies
            this.technologies.clear();
            if (technologies != null) {
                // add new technologies
                this.technologies.addAll(technologies);
            }
        }
    }

    /**
     * Gets the participants list.
     *
     * @return the participants list
     */
    public List<Participant> getParticipants() {
        synchronized (this.participants) {
            return new ArrayList<Participant>(this.participants);
        }
    }

    /**
     * Sets the participants list.
     *
     * @param participants
     *            the participants list
     * @throws IllegalArgumentException
     *             If any list element is null
     */
    public void setParticipants(List<Participant> participants) {
        if (participants != null) {
            Helper.checkList(participants, "participants");
        }
        synchronized (this.participants) {
            // clear original participants
            this.participants.clear();
            if (participants != null) {
                // add new participants
                this.participants.addAll(participants);
            }
        }
    }

    /**
     * Makes a deep copy of this sutiation.
     *
     * @return the clone of this situation.
     */
    public Object clone() {
        ComponentCompetitionSituation s = new ComponentCompetitionSituation();
        s.setCatalog(this.getCatalog());
        s.setComponent(this.getComponent());
        s.setComponentId(this.getComponentId());
        s.setComponentVersionId(this.getComponentVersionId());
        s.setDescription(this.getDescription());
        s.setDigitalRun(this.getDigitalRun());
        s.setDigitalRunPoints(this.getDigitalRunPoints());
        s.setEndDate(this.getEndDate());
        s.setFinalFixCount(this.getFinalFixCount());
        s.setKeywords(this.getKeywords());
        s.setPostingDate(this.getPostingDate());
        s.setPrize(this.getPrize());
        s.setProjectCategory(this.getProjectCategory());
        s.setProjectId(this.getProjectId());
        s.setProjectStatus(this.getProjectStatus());
        s.setRated(this.getRated());
        s.setScorecardId(this.getScorecardId());
        s.setVersion(this.getVersion());

        // set deep copy of participants
        List<Participant> p = new ArrayList<Participant>();
        for (Participant part : this.getParticipants()) {
            p.add((Participant) part.clone());
        }
        s.setParticipants(p);
        // set deep copy of technologies
        List<Technology> t = new ArrayList<Technology>();
        for (Technology tech : this.getTechnologies()) {
            t.add((Technology) tech.clone());
        }
        s.setTechnologies(t);
        return s;
    }
}
