/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Represents the entity class for contest info for contestmonitor widget.
 * </p>
 *
 * <p>
 * Version 1.1 (Prototype Conversion Studio Multi-Rounds Assembly - Submission Viewer UI) Change notes:
 *  - Added fields for round type, milestone date, number of milestone prizes and amount.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author will.xie, pulky
 * @version 1.1
 */
public class SimpleContestData implements Serializable {

    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -4603421184647057450L;

    /**
     * Represents the contest id.
     */
    private Long contestId;

    /**
     * Represents the contest name.
     */
    private String name;
    
    /**
     * Represents the start date.
     */
    private XMLGregorianCalendar startDate;

    /**
     * Represents the end date.
     */
    private XMLGregorianCalendar endDate;
    
    /**
     * Represents the Registrants number.
     */
    private int num_reg;
    
    /**
     * Represents the submission number.
     */
    private int num_sub;


	/**
     * Represents the status id.
	 */
    private long statusId;

    /**
     * Represents the status name.
     */
    private String sname;

    /**
     * List of prize amounts. Index in the list represents the prize placement.
     */
    private List<Double> prizes;

    /**
     * <p>
     * Represents the type of content.
     * </p>
     * 
     * @since Cockpit Submission Viewer Widget Enhancement Part 1.
     */
    private String contestType;

    /**
     * <p>
     * Represents the user permission.
     * </p>
     * 
     * @since BUGR-1797 .
     */
    private String permission;

    /**
     * <p>
     * Represents multi round format flag
     * </p>
     *
     * @since 1.1
     */
    private boolean multiRound;

    /**
     * The milestone amount. Can be any value. Has getter and setter.
     *
     * @since 1.1
     */
    private Double milestonePrizeAmount;

    /**
     * The number of submissions that get milestone prize. Can be any value. Has getter
     * and setter.
     *
     * @since 1.1
     */
    private Integer numberOfMilestonePrizes;

    /**
     * The milestone date. Can be any value. Has getter and setter.
     *
     * @since 1.1
     */
    private XMLGregorianCalendar milestoneDate;

    /**
     * Default constructor.
     */
    public SimpleContestData() {
        // empty
    }
    
    /**
     * Returns the status name.
     *
     * @return the status name.
     */
    public String getSname() {
        return sname;
    }

	 /**
     * Updates the sname with the specified value.
     *
     * @param sname
     *            the status name to set.
     */
	public void setSname(String sname) {
		this.sname = sname;
	}
    /**
     * Returns the contestId.
     *
     * @return the contestId.
     */
    public Long getContestId() {
        return contestId;
    }

    /**
     * Updates the contestId with the specified value.
     *
     * @param contestId
     *            the contestId to set.
     */
    public void setContestId(Long contestId) {
        this.contestId = contestId;
    }
    
    /**
     * Returns the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the name with the specified value.
     *
     * @param name
     *            the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Returns the startDate.
     *
     * @return the startDate.
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Updates the startDate with the specified value.
     *
     * @param startDate
     *            the startDate to set.
     */
    public void setStartDate(XMLGregorianCalendar startDate) {
        this.startDate = startDate;
    }

    /**
     * Returns the endDate.
     *
     * @return the endDate.
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Updates the endDate with the specified value.
     *
     * @param endDate
     *            the endDate to set.
     */
    public void setEndDate(XMLGregorianCalendar endDate) {
        this.endDate = endDate;
    }
    /**
     * Returns the num_reg.
     *
     * @return the num_reg.
     */
	public int getNum_reg() {
		return num_reg;
	}

	/**
     * Updates the num_reg.
     *
     * @param num_reg the num_reg.
     */
	public void setNum_reg(int num_reg) {
		this.num_reg = num_reg;
	}

	/**
     * Returns the num_sub.
     *
     * @return the num_sub.
     */
	public int getNum_sub() {
		return num_sub;
	}

	/**
     * Updates the num_sub.
     *
     * @param num_sub the num_sub.
     */
	public void setNum_sub(int num_sub) {
		this.num_sub = num_sub;
	}

	public long getStatusId() {
		return this.statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

    /**
     * <p>
     * Gets the prize list. Each value in the list is prize amount, while value index (or position) is the prize placement. That
     * is first place prize would be at index 0, while 2nd place prize would be at index 1 and so on.
     * </p>
     * 
     * @return prize list.
     */
    public List<Double> getPrizes() {
        return this.prizes;
    }

    /**
     * <p>
     * Sets the prize list. Each value in the list is prize amount, while value index (or position) is the prize placement. That
     * is first place prize would be at index 0, while 2nd place prize would be at index 1 and so on.
     * </p>
     * 
     * @param prizes prize list.
     */
    public void setPrizes(List<Double> prizes) {
        this.prizes = prizes;
    }

    /**
     * <p>
     * Gets the contest type.
     * </p>
     * 
     * @return the contestType
     * 
     * @since Cockpit Submission Viewer Widget Enhancement Part 1.
     */
    public String getContestType() {
        return this.contestType;
    }

    /**
     * <p>
     * Sets the contest type.
     * </p>
     * 
     * @param contestType the contestType to set
     * 
     * @since Cockpit Submission Viewer Widget Enhancement Part 1.
     */
    public void setContestType(String contestType) {
        this.contestType = contestType;
    }

    /**
     * <p>
     * Gets the permission.
     * </p>
     * 
     * 
     * @since BUGR-1797.
     */
	public String getPermission() {
		return permission;
	}

	/**
     * <p>
     * Sets the permission.
     * </p>
     * 
     * @param permission user permission to set
     * 
     * @since BUGR-1797.
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * Gets the value of the multi round flag
     *
     * @return true if the contest is a multi round format, false otherwise.
     *
     * @since 1.1
     */
    public boolean isMultiRound() {
        return multiRound;
    }

     /**
     * Gets the value of the multi round flag
     *
     * @return true if the contest is a multi round format, false otherwise.
     *
     * @since 1.1
     */
    public boolean getMultiRound() {
        return multiRound;
    }

    /**
     * Sets the value of the multi round flag
     *
     * @param multiRound the new value for the multi round flag
     *
     * @since 1.1
     */
    public void setMultiRound(boolean multiRound) {
        this.multiRound = multiRound;
    }

    /**
     * Gets the value of the milestone prize amount attribute.
     *
     * @return the value of the milestone prize amount attribute
     *
     * @since 1.1
     */
    public Double getMilestonePrizeAmount() {
        return this.milestonePrizeAmount;
    }

    /**
     * Sets the value of the milestone prize amount attribute.
     *
     * @param milestonePrizeAmount the new value for the milestone prize amount attribute
     *
     * @since 1.1
     */
    public void setMilestonePrizeAmount(Double milestonePrizeAmount) {
        this.milestonePrizeAmount = milestonePrizeAmount;
    }

    /**
     * Gets the value of the number of milestone prizes
     *
     * @return the value of the number of milestone prizes
     *
     * @since 1.1
     */
    public Integer getNumberOfMilestonePrizes() {
        return this.numberOfMilestonePrizes;
    }

    /**
     * Sets the value of the number of milestone prizes
     *
     * @param numberOfMilestonePrizes the new value for the number milestone prizes
     *
     * @since 1.1
     */
    public void setNumberOfMilestonePrizes(Integer numberOfMilestonePrizes) {
        this.numberOfMilestonePrizes = numberOfMilestonePrizes;
    }

    /**
     * Gets the value of the milestone date attribute.
     *
     * @return the value of the milestone date attribute
     *
     * @since 1.1
     */
    public XMLGregorianCalendar getMilestoneDate() {
        return this.milestoneDate;
    }

    /**
     * Sets the value of the milestone date attribute.
     *
     * @param milestoneDate the new value for the milestone date attribute
     *
     * @since 1.1
     */
    public void setMilestoneDate(XMLGregorianCalendar milestoneDate) {
        this.milestoneDate = milestoneDate;
    }
}
