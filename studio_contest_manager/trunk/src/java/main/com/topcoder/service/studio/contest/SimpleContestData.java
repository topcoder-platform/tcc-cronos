package com.topcoder.service.studio.contest;

import java.util.Date;
/**
 * <p>
 * Represents the entity class for contest info for contestmonitor widget.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author will.xie
 * @version 1.0
 */
public class SimpleContestData {
	
	/**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -6991488651979864256L;
    
    
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
    private Date startDate;

    /**
     * Represents the end date.
     */
    private Date endDate;
    
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
     * Default constructor.
     */
    public SimpleContestData() {
        // empty
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
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Updates the startDate with the specified value.
     *
     * @param startDate
     *            the startDate to set.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Returns the endDate.
     *
     * @return the endDate.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Updates the endDate with the specified value.
     *
     * @param endDate
     *            the endDate to set.
     */
    public void setEndDate(Date endDate) {
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
     * @return the num_reg.
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
     * @return the num_sub.
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

}
