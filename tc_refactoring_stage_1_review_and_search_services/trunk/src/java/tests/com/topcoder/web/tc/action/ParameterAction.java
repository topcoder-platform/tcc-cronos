/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.action;

import com.opensymphony.xwork2.ActionSupport;

/**
 * <p>
 * This class is used to receive searching criteria, composite them into a JSON string, and passing the JSON string to
 * the target action.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
public class ParameterAction extends ActionSupport {

    /**
     * Represents the serial version id of this class.
     */
    private static final long serialVersionUID = -2120328094226508124L;

    /**
     * Represents the target action.
     */
    private String target;

    /**
     * Represents the JSON string parameter.
     */
    private String parameter;

    /**
     * Represents the contest name.
     */
    private String contestName;

    /**
     * Represents the catalog.
     */
    private String catalog;

    /**
     * Represents the type.
     */
    private String type;

    /**
     * Represents the subtype.
     */
    private String subtype;

    /**
     * Represents the registration start date type.
     */
    private String registStartType;

    /**
     * Represents the registration start first date.
     */
    private String registStartDate1;

    /**
     * Represents the registration start second date.
     */
    private String registStartDate2;

    /**
     * Represents the submission end date type.
     */
    private String submitEndType;

    /**
     * Represents the submission end first date.
     */
    private String submitEndDate1;

    /**
     * Represents the submission end second date.
     */
    private String submitEndDate2;

    /**
     * Represents the payment start.
     */
    private String paymentStart;

    /**
     * Represents the payment end.
     */
    private String paymentEnd;

    /**
     * Represents the review end date type.
     */
    private String reviewEndType;

    /**
     * Represents the review end first date.
     */
    private String reviewEndDate1;

    /**
     * Represents the review end second date.
     */
    private String reviewEndDate2;

    /**
     * Represents the contest finalization date type.
     */
    private String contestFinalizationType;

    /**
     * Represents the contest finalization first date.
     */
    private String contestFinalizationDate1;

    /**
     * Represents the contest finalization second date.
     */
    private String contestFinalizationDate2;

    /**
     * Represents the winner handle.
     */
    private String winnerHandle;

    /**
     * Represents the prize start.
     */
    private String prizeStart;

    /**
     * Represents the prize end.
     */
    private String prizeEnd;

    /**
     * Represents the sorting column name.
     */
    private String columnName;

    /**
     * Represents the sorting order.
     */
    private String sortingOrder;

    /**
     * Represents the page number.
     */
    private String pageNumber;

    /**
     * Represents the page size.
     */
    private String pageSize;

    /**
     * <p>
     * Creates an instance of this class. It does nothing.
     * </p>
     */
    public ParameterAction() {
    }

    /**
     * <p>
     * Compiles all searching criteria into a JSON string and set it into parameter.
     * </p>
     * @return SUCCESS.
     */
    @Override
    public String execute() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"filter\":{");
        if (contestName != null && contestName.trim().length() != 0) {
            builder.append("\"contestName\":\"" + contestName + "\",");
        }
        if (catalog != null && catalog.trim().length() != 0) {
            builder.append("\"catalog\":[" + convertList(catalog) + "],");
        }
        if (type != null && type.trim().length() != 0) {
            builder.append("\"type\":[" + convertList(type) + "],");
        }
        if (subtype != null && subtype.trim().length() != 0) {
            builder.append("\"subtype\":[" + convertList(subtype) + "],");
        }
        if (registStartType != null && !registStartType.equals("NONE")) {
            builder.append("\"registrationStart\":{" + convertDate(registStartType, registStartDate1, registStartDate2)
                    + "},");
        }
        if (submitEndType != null && !submitEndType.equals("NONE")) {
            builder.append("\"submissionEnd\":{" + convertDate(submitEndType, submitEndDate1, submitEndDate2) + "},");
        }
        if (paymentStart != null && paymentStart.trim().length() != 0) {
            builder.append("\"paymentStart\":" + paymentStart + ",");
        }
        if (paymentEnd != null && paymentEnd.trim().length() != 0) {
            builder.append("\"paymentEnd\":" + paymentEnd + ",");
        }
        if (reviewEndType != null && !reviewEndType.equals("NONE")) {
            builder.append("\"reviewEndDate\":{" + convertDate(reviewEndType, reviewEndDate1, reviewEndDate2) + "},");
        }
        if (contestFinalizationType != null && !contestFinalizationType.equals("NONE")) {
            builder.append("\"contestFinalizationType\":{"
                    + convertDate(contestFinalizationType, contestFinalizationDate1, contestFinalizationDate2) + "},");
        }
        if (winnerHandle != null && winnerHandle.trim().length() != 0) {
            builder.append("\"winnerHandle\":\"" + winnerHandle + "\",");
        }
        if (prizeStart != null && prizeStart.trim().length() != 0) {
            builder.append("\"prizeStart\":" + prizeStart + ",");
        }
        if (prizeEnd != null && prizeEnd.trim().length() != 0) {
            builder.append("\"prizeEnd\":" + prizeEnd + ",");
        }
        builder.deleteCharAt(builder.length() - 1).append("},");
        if (columnName != null && columnName.trim().length() != 0) {
            builder.append("\"columnName\":\"" + columnName + "\",");
        }
        if (!sortingOrder.equals("NONE")) {
            builder.append("\"sortingOrder\":\"" + sortingOrder + "\",");
        }
        if (pageNumber != null && pageNumber.trim().length() != 0) {
            builder.append("\"pageNumber\":" + pageNumber + ",");
        }
        if (pageSize != null && pageSize.trim().length() != 0) {
            builder.append("\"pageSize\":" + pageSize + ",");
        }
        parameter = builder.deleteCharAt(builder.length() - 1).append("}").toString();
        return SUCCESS;
    }

    /**
     * <p>
     * Converts a comma-separated string into a JSON string.
     * </p>
     * @param value
     *            the comma-separated string to convert.
     * @return the result JSON string.
     */
    private String convertList(String value) {
        String[] list = value.split(",");
        StringBuilder builder = new StringBuilder();
        for (String item : list) {
            builder.append("\"" + item.trim() + "\",");
        }
        return builder.substring(0, builder.length() - 1);
    }

    /**
     * <p>
     * Converts a date criteria into a JSON string.
     * </p>
     * @param dateType
     *            the date interval type.
     * @param firstDate
     *            the first date.
     * @param secondDate
     *            the second date.
     * @return the JSON string converted from given criteria.
     */
    private String convertDate(String dateType, String firstDate, String secondDate) {
        if (firstDate != null && firstDate.trim().length() != 0) {
            firstDate = ",\"firstDate\":\"" + firstDate + "\"";
        } else {
            firstDate = "";
        }
        if (secondDate != null && secondDate.trim().length() != 0) {
            secondDate = ",\"secondDate\":\"" + secondDate + "\"";
        } else {
            secondDate = "";
        }
        return "\"intervalType\":\"" + dateType + "\"" + firstDate + secondDate;
    }

    /**
     * <p>
     * Gets the target action.
     * </p>
     * @return the target action.
     */
    public String getTarget() {
        return target;
    }

    /**
     * <p>
     * Sets the target action.
     * </p>
     * @param target
     *            the target action to set.
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * <p>
     * Gets the JSON parameter.
     * </p>
     * @return the JSON parameter.
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * <p>
     * Sets the contest name.
     * </p>
     * @param contestName
     *            the contest name to set.
     */
    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    /**
     * <p>
     * Sets the catalog.
     * </p>
     * @param catalog
     *            the catalog to set.
     */
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    /**
     * <p>
     * Sets the type.
     * </p>
     * @param type
     *            the type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * <p>
     * Sets the subtype.
     * </p>
     * @param subtype
     *            the subtype to set.
     */
    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    /**
     * <p>
     * Sets the registration start date type.
     * </p>
     * @param registStartType
     *            the registration start date type to set.
     */
    public void setRegistStartType(String registStartType) {
        this.registStartType = registStartType;
    }

    /**
     * <p>
     * Sets the registration start first date.
     * </p>
     * @param registStartDate1
     *            the registration start first date to set.
     */
    public void setRegistStartDate1(String registStartDate1) {
        this.registStartDate1 = registStartDate1;
    }

    /**
     * <p>
     * Sets the registration start second date.
     * </p>
     * @param registStartDate2
     *            the registration start second date to set.
     */
    public void setRegistStartDate2(String registStartDate2) {
        this.registStartDate2 = registStartDate2;
    }

    /**
     * <p>
     * Sets the submission end date type.
     * </p>
     * @param submitEndType
     *            the submission end date type to set.
     */
    public void setSubmitEndType(String submitEndType) {
        this.submitEndType = submitEndType;
    }

    /**
     * <p>
     * Sets the submission end first date.
     * </p>
     * @param submitEndDate1
     *            the submission end first date to set.
     */
    public void setSubmitEndDate1(String submitEndDate1) {
        this.submitEndDate1 = submitEndDate1;
    }

    /**
     * <p>
     * Sets the submission end second date.
     * </p>
     * @param submitEndDate2
     *            the submission end second date to set.
     */
    public void setSubmitEndDate2(String submitEndDate2) {
        this.submitEndDate2 = submitEndDate2;
    }

    /**
     * <p>
     * Sets the payment start.
     * </p>
     * @param paymentStart
     *            the payment start to set.
     */
    public void setPaymentStart(String paymentStart) {
        this.paymentStart = paymentStart;
    }

    /**
     * <p>
     * Sets the payment end.
     * </p>
     * @param paymentEnd
     *            the payment end to set.
     */
    public void setPaymentEnd(String paymentEnd) {
        this.paymentEnd = paymentEnd;
    }

    /**
     * <p>
     * Sets the review end date type.
     * </p>
     * @param reviewEndType
     *            the review end date type to set.
     */
    public void setReviewEndType(String reviewEndType) {
        this.reviewEndType = reviewEndType;
    }

    /**
     * <p>
     * Sets the review end first date.
     * </p>
     * @param reviewEndDate1
     *            the review end first date to set.
     */
    public void setReviewEndDate1(String reviewEndDate1) {
        this.reviewEndDate1 = reviewEndDate1;
    }

    /**
     * <p>
     * Sets the review end second date.
     * </p>
     * @param reviewEndDate2
     *            the review end second date to set.
     */
    public void setReviewEndDate2(String reviewEndDate2) {
        this.reviewEndDate2 = reviewEndDate2;
    }

    /**
     * <p>
     * Sets the contest finalization date type.
     * </p>
     * @param contestFinalizationType
     *            the contest finalization date type to set.
     */
    public void setContestFinalizationType(String contestFinalizationType) {
        this.contestFinalizationType = contestFinalizationType;
    }

    /**
     * <p>
     * Sets the contest finalization first date.
     * </p>
     * @param contestFinalizationDate1
     *            the contest finalization first date to set.
     */
    public void setContestFinalizationDate1(String contestFinalizationDate1) {
        this.contestFinalizationDate1 = contestFinalizationDate1;
    }

    /**
     * <p>
     * Sets the contest finalization second date.
     * </p>
     * @param contestFinalizationDate2
     *            the contest finalization second date to set.
     */
    public void setContestFinalizationDate2(String contestFinalizationDate2) {
        this.contestFinalizationDate2 = contestFinalizationDate2;
    }

    /**
     * <p>
     * Sets the winner handle.
     * </p>
     * @param winnerHandle
     *            the winner handle to set.
     */
    public void setWinnerHandle(String winnerHandle) {
        this.winnerHandle = winnerHandle;
    }

    /**
     * <p>
     * Sets the prize start.
     * </p>
     * @param prizeStart
     *            the prize start to set.
     */
    public void setPrizeStart(String prizeStart) {
        this.prizeStart = prizeStart;
    }

    /**
     * <p>
     * Sets the prize end.
     * </p>
     * @param prizeEnd
     *            the prize end to set.
     */
    public void setPrizeEnd(String prizeEnd) {
        this.prizeEnd = prizeEnd;
    }

    /**
     * <p>
     * Sets the sorting column name.
     * </p>
     * @param columnName
     *            the sorting column name to set.
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * <p>
     * Sets the sorting order.
     * </p>
     * @param sortingOrder
     *            the sorting order to set.
     */
    public void setSortingOrder(String sortingOrder) {
        this.sortingOrder = sortingOrder;
    }

    /**
     * <p>
     * Sets the page number.
     * </p>
     * @param pageNumber
     *            the page number to set.
     */
    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * <p>
     * Sets the page size.
     * </p>
     * @param pageSize
     *            the page size to set.
     */
    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
