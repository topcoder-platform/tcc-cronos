/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.DatatypeConfigurationException;

import com.topcoder.management.resource.Resource;
import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.ContestStatusData;
import com.topcoder.service.studio.PrizeData;

/**
 * <p>
 * It is the DTO class which is used to transfer studio competition data. The information can be null or can be empty,
 * therefore this check is not present in the setters. It's the related to the equivalent Contest entity.
 * </p>
 * <p>
 * This class is not thread safe because it's highly mutable
 * </p>
 * @author FireIce
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "studioCompetition",
        propOrder = {"id", "contestData", "type", "resources", "clientApproval", "clientName", "reviewPayment",
		"specificationReviewPayment", "contestFee", "status",
	    "category", "confidence", "pricingApproval", "hasWikiSpecification", "passedSpecReview", "hasDependentCompetitions",
	    "wasReposted", "notes"})
public class StudioCompetition extends Competition {

    /**
     * <p>
     * Represents the ContestData.
     * </p>
     */
    private ContestData contestData;


    /**
     * <p>
     * Represents the id.
     * </p>
     */
    private long id;

    /**
     * <p>
     * Represents the competition type.
     * </p>
     */
    private CompetionType type;
    /**
     * <p>Represents the contest status data.</p>
    */
    private ContestStatusData status;
    /**
     * <p>Represents the contest category data.</p>
    */
    private String category;
    /**
     * <p>Represents the resource array.</p>
    */
    private Resource [] resources;
    /**
     * <p>Represents the flag for client approval.</p>
     */
    private boolean clientApproval;

    /**
     * <p>Represents the name of the client.</p>
     */
    private String clientName;

    /**
     * <p>Represents the review payment.</p>
    */
    private double reviewPayment;

    /**
     * <p>Represents the specification review payment.</p>
    */
    private double specificationReviewPayment;

    /**
     * <p>Represents the contest fee.</p>
    */
    private double contestFee;


    /**
     * <p>Represents the confidence.</p>
    */
    private int confidence;

    /**
     * <p>Represents the pricing approval flag.</p>
    */
    private boolean pricingApproval;

    /**
     * <p>Represents has wiki specification flag.</p>
    */
    private boolean hasWikiSpecification;

    /**
     * <p>Represents the passed specification review flag.</p>
    */
    private boolean passedSpecReview;

    /**
     * <p>Represents the has dependent competition flag.</p>
    */
    private boolean hasDependentCompetitions;

    /**
     * <p>Represents the was reposted flag.</p>
    */
    private boolean wasReposted;

    /**
     * <p>Represents the notes.</p>
    */
    private String notes;

    /**
     * <p>
     * Default no-arg constructor.
     * </p>
     */
    public StudioCompetition() {
        contestData = new ContestData();
    }

    /**
     * <p>
     * Constructs a <code>StudioCompetition</code> instance with given <code>ContestData</code>.
     * </p>
     * @param contestData
     *            the contest data.
     * @throws IllegalArgumentException
     *             if the contestData is null.
     */
    public StudioCompetition(ContestData contestData) {
        if (contestData == null) {
            throw new IllegalArgumentException("The given contestData is null.");
        }

        this.contestData = contestData;
    }

	/**
     * <p>
     * Returns the Contest data.
     * </p>
     * @return the Contest data..
     */
    public ContestData getContestData() {
        return contestData;
    }


    /**
     * <p>
     * Returns the admin fee for the competition.
     * </p>
     * @return the admin fee for the competition.
     */
    @Override
    public Double getAdminFee() {
        return contestData.getContestAdministrationFee();
    }

    /**
     * <p>
     * Returns the creator user id of the competition.
     * </p>
     * @return the creator user id of the competition.
     */
    @Override
    public long getCreatorUserId() {
        return contestData.getCreatorUserId();
    }

    /**
     * <p>
     * Returns the digital run points for the competition.
     * </p>
     * @return the digital run points for the competition.
     */
    @Override
    public double getDrPoints() {
        return contestData.getDrPoints();
    }

    /**
     * <p>
     * Returns the eligibility of the competition.
     * </p>
     * @return the eligibility of the competition.
     */
    @Override
    public String getEligibility() {
        return contestData.getEligibility();
    }

    /**
     * <p>
     * Returns the end time of the competition.
     * </p>
     * @return the end time of the competition.
     */
    @Override
    public XMLGregorianCalendar getEndTime() {
        Date startDate = getDate(contestData.getLaunchDateAndTime());
        Date endDate = new Date((long) (startDate.getTime() + 60l * 60 * 1000 * contestData.getDurationInHours()));
        return getXMLGregorianCalendar(endDate);

        // BUGR- 1312
        // return contestData.getWinnerAnnoucementDeadline();
    }

    /**
     * <p>
     * Returns the id.
     * </p>
     * @return the id.
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Returns the prizes for the competition.
     * </p>
     * @return the prizes for the competition.
     */
    @Override
    public List<CompetitionPrize> getPrizes() {
        List<CompetitionPrize> results = new ArrayList<CompetitionPrize>();

        for (PrizeData prize : contestData.getPrizes()) {
            CompetitionPrize competitionPrize = null;
            if (prize != null) {
                competitionPrize = new CompetitionPrize();
                competitionPrize.setAmount(prize.getAmount());
                competitionPrize.setPlace(prize.getPlace());
            }

            results.add(competitionPrize);
        }
        return results;
    }

    /**
     * <p>
     * Returns the short summary of the competition.
     * </p>
     * @return the short summary of the competition.
     */
    @Override
    public String getShortSummary() {
        return contestData.getShortSummary();
    }

    /**
     * <p>
     * Returns the start time of the competition.
     * </p>
     * @return the start time of the competition.
     */
    @Override
    public XMLGregorianCalendar getStartTime() {
        return contestData.getLaunchDateAndTime();
    }

    /**
     * <p>
     * Returns the competition type.
     * </p>
     * @return the competition type.
     */
    @Override
    public CompetionType getType() {
        return type;
    }

    /**
     * <p>
     * Sets the creator user id of the competition.
     * </p>
     * @param creatorUserId
     *            the creator user id of the competition.
     */
    @Override
    public void setCreatorUserId(long creatorUserId) {
        contestData.setCreatorUserId(creatorUserId);
    }

    /**
     * <p>
     * Sets the digital run points for the competition.
     * </p>
     * @param drPoints
     *            the digital run points for the competition.
     */
    @Override
    public void setDrPoints(double drPoints) {
        contestData.setDrPoints(drPoints);
    }

    /**
     * <p>
     * Sets the eligibility of the competition.
     * </p>
     * @param eligibility
     *            the eligibility of the competition.
     */
    @Override
    public void setEligibility(String eligibility) {
        contestData.setEligibility(eligibility);
    }

    /**
     * <p>
     * Sets the end time of the competition.
     * </p>
     * @return the end time of the competition.
     */
    @Override
    public void setEndTime(XMLGregorianCalendar endTime) {
        Date endDate = getDate(endTime);
        Date startDate = getDate(contestData.getLaunchDateAndTime());
        double durationInHours = (endDate.getTime() - startDate.getTime()) / (double)(60l * 60 * 1000);
        Date winnerAnnouncementDeadlineDate;

        if(durationInHours <= 24) {
            winnerAnnouncementDeadlineDate = new Date((long) (endDate.getTime() + 60l * 60 * 1000 * 24));

        } else {
            winnerAnnouncementDeadlineDate = new Date((long) (endDate.getTime() + 60l * 60 * 1000 * durationInHours));
        }

        contestData.setDurationInHours(durationInHours);

        // BUGR- 1312
        // contestData.setWinnerAnnoucementDeadline(endTime);
    }

    /**
     * <p>
     * Sets the id.
     * </p>
     * @param the
     *            id.
     */
    @Override
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>
     * Sets the prizes for the competition.
     * </p>
     * @param prizes
     *            the prizes for the competition.
     */
    @Override
    public void setPrizes(List<CompetitionPrize> prizes) {
        List<PrizeData> results = new ArrayList<PrizeData>();
        for (CompetitionPrize competitionPrize : prizes) {
            PrizeData prizeData = null;
            if (competitionPrize != null) {
                prizeData = new PrizeData();
                prizeData.setAmount(competitionPrize.getAmount());
                prizeData.setPlace(competitionPrize.getPlace());
            }

            results.add(prizeData);
        }
        contestData.setPrizes(results);
    }

    /**
     * <p>
     * Sets the short summary of the competition.
     * </p>
     * @param shortSummary
     *            the short summary of the competition.
     */
    @Override
    public void setShortSummary(String shortSummary) {
        contestData.setShortSummary(shortSummary);
    }

    /**
     * <p>
     * Sets the start time of the competition.
     * </p>
     * @param startTime
     *            the start time of the competition.
     */
    @Override
    public void setStartTime(XMLGregorianCalendar startTime) {
        contestData.setLaunchDateAndTime(startTime);
    }

	 /**
     * <p>
     * Returns the admin fee for the competition.
     * </p>
     * @return the admin fee for the competition.
     */
    @Override
    public void setAdminFee(Double fee) {
        contestData.setContestAdministrationFee(fee);
    }

    /**
     * <p>
     * Sets the competition type.
     * </p>
     * @param type
     *            the competition type.
     */
    @Override
    public void setType(CompetionType type) {
        this.type = type;
    }

    /**
     * <p>Converts specified <code>XMLGregorianCalendar</code> instance into <code>Date</code> instance.</p>
     *
     * @param calendar an <code>XMLGregorianCalendar</code> representing the date to be converted.
     * @return a <code>Date</code> providing the converted value of specified calendar or <code>null</code> if specified
     *         <code>calendar</code> is <code>null</code>.
     */
    private static Date getDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }

    /**
     * <p>
     * Converts specified <code>Date</code> instance into
     * <code>XMLGregorianCalendar</code> instance.
     * </p>
     *
     * @param date
     *            a <code>Date</code> representing the date to be converted.
     * @return a <code>XMLGregorianCalendar</code> providing the converted value
     *         of specified date or <code>null</code> if specified
     *         <code>date</code> is <code>null</code> or if it can't be
     *         converted to calendar.
     */
    private static XMLGregorianCalendar getXMLGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException ex) {
            return null;
        }
    }
    /**
     * <p>
     * Gets the client approval flag.
     * </p>
     * @param true if it is client approval competition
     */
    @Override
    public boolean getClientApproval() {
        return this.clientApproval;
    }

    @Override
    public String getClientName() {
        return clientName;
    }

    @Override
    public int getConfidence() {
        return this.confidence;
    }

    @Override
    public double getContestFee() {
        return this.contestFee;
    }

    @Override
    public boolean getHasDependentCompetitions() {
        return hasDependentCompetitions;
    }

    @Override
    public boolean getHasWikiSpecification() {
        return hasWikiSpecification;
    }

    @Override
    public String getNotes() {
        return notes;
    }

    @Override
    public boolean getPassedSpecReview() {
        return passedSpecReview;
    }

    @Override
    public boolean getPricingApproval() {
        return this.pricingApproval;
    }

    @Override
    public Resource[] getResources() {
        return resources;
    }

    @Override
    public double getReviewPayment() {
        return this.reviewPayment;
    }

    @Override
    public double getSpecificationReviewPayment() {
        return this.specificationReviewPayment;
    }

    @Override
    public boolean getWasReposted() {
        return this.wasReposted;
    }

    @Override
    public void setClientApproval(boolean clientApproval) {
        this.clientApproval = clientApproval;
    }

    @Override
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }

    @Override
    public void setContestFee(double contestFee) {
        this.contestFee = contestFee;
    }

    @Override
    public void setHasDependentCompetitions(boolean hasDependentCompetitions) {
        this.hasDependentCompetitions = hasDependentCompetitions;
    }

    @Override
    public void setHasWikiSpecification(boolean hasWikiSpecification) {
        this.hasWikiSpecification = hasWikiSpecification;
    }

    @Override
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public void setPassedSpecReview(boolean passedSpecReview) {
        this.passedSpecReview = passedSpecReview;
    }

    @Override
    public void setPricingApproval(boolean pricingApproval) {
    	this.pricingApproval = pricingApproval;
    }

    @Override
    public void setResources(Resource[] resources) {
        this.resources = resources;
    }

    @Override
    public void setReviewPayment(double reviewPayment) {
        this.reviewPayment = reviewPayment;
    }

    @Override
    public void setSpecificationReviewPayment(double specificationReviewPayment) {
        this.specificationReviewPayment = specificationReviewPayment;
    }

    @Override
    public void setWasReposted(boolean wasReposted) {
    	this.wasReposted = wasReposted;
    }

	/**
	 * Returns the value of status.
	 * @return the status
	 */
	public ContestStatusData getStatus() {
		return status;
	}

	/**
	 * Set the value to  status field.
	 * @param status the status to set
	 */
	public void setStatus(ContestStatusData status) {
		this.status = status;
	}
    /**
     * @return return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }
}
