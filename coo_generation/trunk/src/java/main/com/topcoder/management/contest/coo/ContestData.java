/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * This class represents a contest data. This class is used by
 * <code>COOReport</code> to represents the contest data.
 * </p>
 * <p>
 * <strong> Thread safety:</strong> This class is not thread safe since it is
 * mutable.
 * </p>
 *
 * @author bramandia, TCSDEVELOPER
 * @version 1.1
 */
public class ContestData {
    /**
     * <p>
     * Represents the component name.
     * </p>
     * <p>
     * Initialized to default value of the data type, has getter and setter,
     * once set: will not be null nor empty.
     * </p>
     */
    private String componentName;
    /**
     * <p>
     * Represents the contest end date.
     * </p>
     * <p>
     * Initialized to default value of the data type, has getter and setter,
     * once set: will not be null.
     * </p>
     */
    private Date contestEndDate;
    /**
     * <p>
     * Represents the design project id.
     * </p>
     * <p>
     * Initialized to default value of the data type, has getter and setter,
     * once set: will not be empty.
     * </p>
     */
    private String designProjectId;
    /**
     * <p>
     * Represents the winning designer.
     * </p>
     * <p>
     * Initialized to default value of the data type, has getter and setter,
     * once set: will not be empty.
     * </p>
     */
    private String designWinner;
    /**
     * <p>
     * Represents the 2nd place designer.
     * </p>
     * <p>
     * Initialized to default value of the data type, has getter and setter,
     * once set: will not be empty.
     * </p>
     */
    private String designSecondPlace;
    /**
     * <p>
     * Represents the design reviewers.
     * </p>
     * <p>
     * Initialized to default value of the data type, has getter and setter,
     * once set: will be null or empty not contain null/empty.
     * </p>
     */
    private List<String> designReviewers;
    /**
     * <p>
     * Represents the development project id.
     * </p>
     * <p>
     * Initialized to default value of the data type, has getter and setter,
     * once set: will not be empty.
     * </p>
     */
    private String developmentProjectId;
    /**
     * <p>
     * Represents the development winner.
     * </p>
     * <p>
     * Initialized to default value of the data type, has getter and setter,
     * once set: will not be empty. can be null to indicate no winner.
     * </p>
     */
    private String developmentWinner;
    /**
     * <p>
     * Represents the development 2nd place winner.
     * </p>
     * <p>
     * Initialized to default value of the data type, has getter and setter,
     * once set: will not be empty. can be null to indicate no winner.
     * </p>
     */
    private String developmentSecondPlace;
    /**
     * <p>
     * Represents the development reviewers.
     * </p>
     * <p>
     * Initialized to default value of the data type, has getter and setter,
     * once set: will not contain null element. can be null to indicate no
     * reviewer.
     * </p>
     */
    private List<String> developmentReviewers;
    /**
     * <p>
     * Represents the SVN path of the project.
     * </p>
     * <p>
     * Initialized to default value of the data type, has getter and setter,
     * once set: will not be null nor empty.
     * </p>
     */
    private String svnPath;
    /**
     * <p>
     * Represents the category of the project. This could be for instance "Java"
     * or ".NET".
     * </p>
     * <p>
     * Initialized to default value of the data type, has getter and setter,
     * once set: will not be null nor empty.
     * </p>
     */
    private String category;

    /**
     * Empty constructor.
     */
    public ContestData() {
    }

    /**
     * Getter for the component name.
     *
     * @return The value of the component name.
     */
    public String getComponentName() {
        return componentName;
    }

    /**
     * Setter for the component name.
     *
     * @param componentName The component name. must not be null or empty.
     * @throws IllegalArgumentException if any argument is invalid.
     *
     */
    public void setComponentName(String componentName) {
        Helper.checkString("componentName", componentName);
        this.componentName = componentName;
    }

    /**
     * Getter for the contest end date.
     *
     * @return The value of the contest end date.
     */
    public Date getContestEndDate() {
        return contestEndDate;
    }

    /**
     * Setter for the contest end date.
     *
     * @param contestEndDate The contest end date. must not be null.
     * @throws IllegalArgumentException if any argument is invalid.
     *
     */
    public void setContestEndDate(Date contestEndDate) {
        Helper.checkNull("contestEndDate", contestEndDate);
        this.contestEndDate = contestEndDate;
    }

    /**
     * Getter for the design project id.
     *
     * @return The value of the design project id.
     */
    public String getDesignProjectId() {
        return designProjectId;
    }

    /**
     * Setter for the design project id.
     *
     * @param designProjectId The design project id. must not be null.
     * @throws IllegalArgumentException if any argument is invalid.
     *
     */
    public void setDesignProjectId(String designProjectId) {
        Helper.checkNull("designProjectId", designProjectId);
        this.designProjectId = designProjectId;
    }

    /**
     * Getter for the design winner.
     *
     * @return The value of the design winner.
     *
     */
    public String getDesignWinner() {
        return designWinner;
    }

    /**
     * Setter for the design winner.
     *
     * @param designWinner The winning designer. must not be empty.
     * @throws IllegalArgumentException if any argument is invalid.
     *
     */
    public void setDesignWinner(String designWinner) {
        Helper.checkStringNotEmpty("designWinner", designWinner);
        this.designWinner = designWinner;
    }

    /**
     * Getter for the design second place.
     *
     * @return The value of the design second place.
     *
     */
    public String getDesignSecondPlace() {
        return designSecondPlace;
    }

    /**
     * Setter for the design second place.
     *
     * @param designSecondPlace The 2nd place designer. must not be empty
     * @throws IllegalArgumentException if any argument is invalid.
     *
     */
    public void setDesignSecondPlace(String designSecondPlace) {
        Helper.checkStringNotEmpty("designSecondPlace", designSecondPlace);
        this.designSecondPlace = designSecondPlace;
    }

    /**
     * Getter for the design reviewers.
     *
     * @return The value of the design reviewers.
     *
     */
    public List<String> getDesignReviewers() {
        return designReviewers;
    }

    /**
     * Setter for the design reviewers.
     *
     * @param designReviewers The list of design reviewers. must not contain
     *            null/empty element,can be null or empty.
     * @throws IllegalArgumentException if any argument is invalid.
     *
     */
    public void setDesignReviewers(List<String> designReviewers) {
        if (designReviewers != null) {
            for (String reviewer : designReviewers) {
                Helper.checkString(null, "element in designReviewers", reviewer);
            }
        }
        this.designReviewers = designReviewers;
    }

    /**
     * Getter for the development project id.
     *
     * @return The value of the development project id.
     */
    public String getDevelopmentProjectId() {
        return developmentProjectId;
    }

    /**
     * Setter for the development project id.
     *
     * @param developmentProjectId The development project id. must not be null.
     * @throws IllegalArgumentException if any argument is invalid.
     *
     */
    public void setDevelopmentProjectId(String developmentProjectId) {
        Helper.checkNull("developmentProjectId", developmentProjectId);
        this.developmentProjectId = developmentProjectId;
    }

    /**
     * Getter for the development winner.
     *
     * @return The value of the development winner.
     *
     */
    public String getDevelopmentWinner() {
        return developmentWinner;
    }

    /**
     * Setter for the development winner.
     *
     * @param developmentWinner The winning developer. must not be empty.
     * @throws IllegalArgumentException if any argument is invalid.
     *
     */
    public void setDevelopmentWinner(String developmentWinner) {
        Helper.checkStringNotEmpty("developmentWinner", developmentWinner);
        this.developmentWinner = developmentWinner;
    }

    /**
     * Getter for the development second place.
     *
     * @return The value of the development second place.
     *
     */
    public String getDevelopmentSecondPlace() {
        return developmentSecondPlace;
    }

    /**
     * Setter for the development second place.
     *
     * @param developmentSecondPlace The 2nd place developer. must not be empty.
     * @throws IllegalArgumentException if any argument is invalid.
     *
     */
    public void setDevelopmentSecondPlace(String developmentSecondPlace) {
        Helper.checkStringNotEmpty("developmentSecondPlace", developmentSecondPlace);
        this.developmentSecondPlace = developmentSecondPlace;
    }

    /**
     * Getter for the development reviewers.
     *
     * @return The value of the development reviewers.
     *
     */
    public List<String> getDevelopmentReviewers() {
        return developmentReviewers;
    }

    /**
     * Setter for the development reviewers.
     *
     * @param developmentReviewers The list of development reviewers. must not
     *            contain null element
     * @throws IllegalArgumentException if any argument is invalid.
     *
     */
    public void setDevelopmentReviewers(List<String> developmentReviewers) {
        if (developmentReviewers != null) {
            for (String reviewer : developmentReviewers) {
                Helper.checkNull(null, "element in developmentReviewers", reviewer);
            }
        }
        this.developmentReviewers = developmentReviewers;
    }

    /**
     * Getter for the svn path.
     *
     * @return The value of the svn path.
     *
     */
    public String getSvnPath() {
        return svnPath;
    }

    /**
     * Setter for the svn path.
     *
     * @param svnPath The svn path. must not be null or empty.
     * @throws IllegalArgumentException if any argument is invalid.
     *
     */
    public void setSvnPath(String svnPath) {
        Helper.checkString("svnPath", svnPath);
        this.svnPath = svnPath;
    }

    /**
     * Getter for the category.
     *
     * @return The value of the category.
     *
     */
    public String getCategory() {
        return category;
    }

    /**
     * Setter for the category.
     *
     * @param category The category. must not be null or empty.
     * @throws IllegalArgumentException if any argument is invalid.
     *
     */
    public void setCategory(String category) {
        Helper.checkString("category", category);
        this.category = category;
    }
}
