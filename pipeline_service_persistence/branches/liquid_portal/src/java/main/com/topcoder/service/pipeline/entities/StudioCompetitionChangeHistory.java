/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * <p>
 * The studio competition change history mapped to the 'studio_competition_change_history' table.
 * </p>
 *
 * @author TCSASSEMBLER
 * @since Pipeline Conversion Service Layer Assembly v1.0
 * @version 1.0
 *
 */
@Entity
@Table(name = "studio_competition_change_history")
@javax.persistence.AttributeOverride(name = "pipelineInfoId",
		column = @Column(name = "studio_competition_pipeline_info_id"))
public class StudioCompetitionChangeHistory extends CompetitionChangeHistory {
    /** serial version UID. */
    private static final long serialVersionUID = 4016503906610685299L;

    /** The pipeline info that the Competition belongs to. */
    @ManyToOne(targetEntity = StudioCompetitionPipelineInfo.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "studio_competition_pipeline_info_id", insertable = false, updatable = false)
    private StudioCompetitionPipelineInfo pipelineInfo;

    /**
     * Creates a new StudioCompetitionChangeHistory object.
     */
    public StudioCompetitionChangeHistory() {
    }

    /**
     * <p>
     * Gets the pipelineInfo.
     * </p>
     *
     * @return the pipelineInfo
     */
    public StudioCompetitionPipelineInfo getPipelineInfo() {
        return pipelineInfo;
    }

    /**
     * <p>
     * Sets the pipelineInfo.
     * </p>
     *
     * @param pipelineInfo the pipelineInfo to set
     */
    public void setPipelineInfo(StudioCompetitionPipelineInfo pipelineInfo) {
        this.pipelineInfo = pipelineInfo;
    }
}
