package com.topcoder.service.studio.contest;

import java.util.Date;

public class SimpleProjectContestData {
	
	/**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -6991488651979864256L;
    
    
    /**
     * Represents the contest id.
     */
    private Long contestId;
    
    /**
     * Represents the project id.
     */
    private Long projectId;
    
    /**
     * Represents the project name.
     */
    private String pname;

    /**
     * Represents the contest name.
     */
    private String cname;
    
    /**
     * Represents the start date.
     */
    private Date startDate;

    /**
     * Represents the end date.
     */
    private Date endDate;
    
    private int num_reg;
    
    private int num_sub;
    
    private int num_for;
    
    private String sname;


	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Long getContestId() {
		return contestId;
	}

	public void setContestId(Long contestId) {
		this.contestId = contestId;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getNum_for() {
		return num_for;
	}

	public void setNum_for(int num_for) {
		this.num_for = num_for;
	}

	public int getNum_reg() {
		return num_reg;
	}

	public void setNum_reg(int num_reg) {
		this.num_reg = num_reg;
	}

	public int getNum_sub() {
		return num_sub;
	}

	public void setNum_sub(int num_sub) {
		this.num_sub = num_sub;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

}
