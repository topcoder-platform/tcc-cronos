/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.entities;

import com.topcoder.management.resource.Resource;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


/**
 * <p>The entity class mapped to the 'studio_competition_pipeline_info' table.</p>
 *
 * @since Pipeline Conversion Service Layer Assembly v1.0
 */
@Entity
@Table(name = "studio_competition_pipeline_info")
public class StudioCompetitionPipelineInfo extends CompetitionPipelineInfo {
    /** Default serial version id. */
    private static final long serialVersionUID = 1L;

    /** The resources. */
    @ManyToMany(targetEntity = Resource.class, fetch = FetchType.EAGER)
    @JoinTable(name = "studio_competition_pipeline_resources", joinColumns =  {
        @JoinColumn(name = "studio_competition_pipeline_info_id")
    }
    , inverseJoinColumns =  {
        @JoinColumn(name = "resource_id")
    }
    )
    private Set<Resource> resources;

    /** The contest id. */
    @Column(name = "contest_id")
    private Long contestId;

    /**
     * Creates a new StudioCompetitionPipelineInfo object.
     */
    public StudioCompetitionPipelineInfo() {
    }

    /**
     * <p>
     * Gets the contestId
     * </p>
     *
     * @return the contestId
     */
    public Long getContestId() {
        return contestId;
    }

    /**
     * <p>
     * Sets the contestId
     * </p>
     *
     * @param contestId the contestId to set
     */
    public void setContestId(Long contestId) {
        this.contestId = contestId;
    }

    /**
     * <p>
     * Gets the resources
     * </p>
     *
     * @return the resources
     */
    public Set<Resource> getResources() {
        return resources;
    }

    /**
     * <p>
     * Sets the resources
     * </p>
     *
     * @param resources the resources to set
     */
    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }
}
