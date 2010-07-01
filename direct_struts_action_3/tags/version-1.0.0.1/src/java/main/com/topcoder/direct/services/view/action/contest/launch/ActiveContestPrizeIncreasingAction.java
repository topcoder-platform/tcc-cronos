/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import java.util.List;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.direct.ContestPrize;

/**
 * <p>
 * This action increases the active contest prize.
 * </p>
 *
 * <p>
 * <strong>An example of how to configure action in struts.xml:</strong>
 * <pre>
 * &lt;action name="activeContestPrizeIncreasingAction" class="activeContestPrizeIncreasingAction"&gt;
 *     &lt;result name="input"&gt;/input.jsp&lt;/result&gt;
 *     &lt;result name="success"&gt;/success.jsp&lt;/result&gt;
 * &lt;/action&gt;
 * </pre>
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> Mutable and not thread safe, however will be used thread-safely in Struts framework.
 * </p>
 *
 * @author Urmass, TCSDEVELOPER
 * @version 1.0
 */
public class ActiveContestPrizeIncreasingAction extends BaseDirectStrutsAction {

    /**
     * Represents the prefix to use for the validation error keys.
     */
    private static final String KEY_PREFIX = "i18n.activeContestPrizeIncreasingAction.";

    /**
     * Represents the maximum number of allowed decimal places in a prize amount.
     */
    private static final int MAX_DECIMAL_PLACES_FOR_PRIZE = 2;

    /**
     * A decimal amount representing the minimum percentage of first place prize that second place prize must be.
     */
    private static final double PERCENTAGE_FOR_SECOND_PLACE_PRIZE = .2;

    /**
     * A string representing the minimum percentage of first place prize that second place prize must be.
     */
    private static final String PERCENTAGE_FOR_SECOND_PLACE_PRIZE_STRING = "20%";

    /**
     * <p>
     * Represents the contest ID used to retrieve the needed contest details.
     * </p>
     *
     * <p>
     * Initialized in corresponding setter, accessed in getter/setter and executeAction method.
     * Must be positive.
     * </p>
     */
    private long contestId;

    /**
     * <p>
     * Specifies whether the project is a studio contest.
     * </p>
     *
     * <p>
     * Initialized in corresponding setter, accessed in getter/setter and executeAction method.
     * </p>
     */
    private boolean studio;

    /**
     * <p>
     * Represents the contest prizes.
     * </p>
     *
     * <p>
     * Initialized in corresponding setter, accessed in getter/setter and executeAction method.
     * Should not be null and should not contain null elements. Each value should be greater than
     * or equal to 0, and each value can contain at most 2 decimal places.
     * </p>
     *
     * <p>
     * Must consist of at least first and second place prize, and second place prize
     * must be at least 20% of first place prize.
     * </p>
     */
    private List<Double> contestPrizes;

    /**
     * <p>
     * Represents the milestone prizes.
     * </p>
     *
     * <p>
     * Initialized in corresponding setter, accessed in getter/setter and executeAction method.
     * Should not be null and should not contain null elements. Each value should be greater than
     * or equal to 0, and each value can contain at most 2 decimal places.
     * </p>
     */
    private List<Double> milestonePrizes;

    /**
     * Default constructor, creates new instance.
     */
    public ActiveContestPrizeIncreasingAction() {
    }

    /**
     * <p>
     * This executeAction method is responsible for updating active contest prize.
     * No result is stored in the <code>AggregateDataModel</code>.
     * </p>
     *
     * <p>
     * For both contest and milestone prizes the new prize amounts are expected to be greater than the old prize
     * amounts, and the number of old and new prizes should match. If any validation fails, the error is stored
     * in the model as a field error.
     * </p>
     *
     * <p>
     * All the action's required variables should be set before the execution. All exceptions will be caught and
     * stored in the <code>AggregateDataModel</code> and then rethrown.
     * </p>
     *
     * @throws IllegalStateException if <code>DirectServiceFacade</code> instance has not been injected
     * @throws Exception if some other error occurs when executing this action
     */
    protected void executeAction() throws Exception {
        try {
            ActionHelper.checkInjectedFieldNull(getDirectServiceFacade(), "directServiceFacade");
            TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();

            // get the old prize amounts
            ContestPrize prize = getDirectServiceFacade().getContestPrize(tcSubject, contestId, studio);

            // convert the prizes to arrays, and make sure there are the same number of old and new prizes and that
            // new prizes are greater than old ones (if returned array from validatePrizeAmountsIncreased is
            // null, it means that the validation failed)
            boolean[] allEqual = new boolean[1];
            double[] contestPrizesArray = validatePrizeAmountsIncreased(prize.getContestPrizes(), contestPrizes,
                "contestPrizes", allEqual);

            if (contestPrizesArray != null) {
                double[] milestonePrizesArray = validatePrizeAmountsIncreased(prize.getMilestonePrizes(),
                    milestonePrizes, "milestonePrizes", allEqual);

                if (milestonePrizesArray != null) {

                    // set the new prize information
                    prize = new ContestPrize();
                    prize.setContestId(contestId);
                    prize.setStudio(studio);
                    prize.setContestPrizes(contestPrizesArray);
                    prize.setMilestonePrizes(milestonePrizesArray);

                    // set the value to indicate whether all milestone prizes are equal
                    prize.setEqualMilestonePrize(allEqual[0]);

                    // update the active contest prize
                    getDirectServiceFacade().updateActiveContestPrize(tcSubject, prize);
                }
            }

        } catch (Exception e) {
            // store exception in model and rethrow it
            setResult(e);
            throw e;
        }
    }

    /**
     * Getter for contest ID.
     *
     * @return the contest ID
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Setter for contest ID. Struts 2 validation is used to check that the argument is greater than 0.
     *
     * @param contestId the contest ID
     */
    @FieldExpressionValidator(
        key = KEY_PREFIX + "contestIdValues",
        fieldName = "contestId", expression = "contestId > 0",
        message = ActionHelper.GREATER_THAN_ZERO)
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Getter for studio boolean value.
     *
     * @return the boolean value for studio
     */
    public boolean isStudio() {
        return studio;
    }

    /**
     * Setter for studio boolean value.
     *
     * @param studio the boolean value for studio
     */
    public void setStudio(boolean studio) {
        this.studio = studio;
    }

    /**
     * Getter for contest prizes.
     *
     * @return the contest prizes
     */
    public List<Double> getContestPrizes() {
        return contestPrizes;
    }

    /**
     * <p>
     * Setter for contest prizes.
     * </p>
     *
     * <p>
     * Should not be null and should not contain null elements. Each value should be greater than
     * or equal to 0, and each value can contain at most 2 decimal places.
     * Must consist of at least first and second place prize, and second place prize
     * must be at least 20% of first place prize.
     * </p>
     *
     * @param contestPrizes the contest prizes
     */
    public void setContestPrizes(List<Double> contestPrizes) {
        validatePrizeAmounts(contestPrizes, "contestPrizes");
        this.contestPrizes = contestPrizes;
    }

    /**
     * Getter for milestone prizes.
     *
     * @return the milestone prizes
     */
    public List<Double> getMilestonePrizes() {
        return milestonePrizes;
    }

    /**
     * <p>
     * Setter for milestone prizes.
     * </p>
     *
     * <p>
     * Should not be null and should not contain null elements. Each value should be greater than
     * or equal to 0, and each value can contain at most 2 decimal places.
     * </p>
     *
     * @param milestonePrizes the milestone prizes
     */
    public void setMilestonePrizes(List<Double> milestonePrizes) {
        validatePrizeAmounts(milestonePrizes, "milestonePrizes");
        this.milestonePrizes = milestonePrizes;
    }

    /**
     * <p>
     * Validates that new prize amounts are greater than old ones and if so, returns the new
     * prizes in an array.
     * </p>
     *
     * <p>
     * Any errors are added as field errors to model.
     * </p>
     *
     * @param oldPrizeAmounts the old prize amounts
     * @param newPrizeAmounts the new prize amounts
     * @param prizeType type the type of prize (contestPrizes or milestonePrizes)
     * @param allEqual a single element boolean array which will indicate whether all the new prizes are equal
     * @return an array containing the new prize amounts, or null if validation failed
     */
    private double[] validatePrizeAmountsIncreased(double[] oldPrizeAmounts, List<Double> newPrizeAmounts,
        String prizeType, boolean[] allEqual) {

        // make sure number of prize amounts match
        if (oldPrizeAmounts == null || oldPrizeAmounts.length != newPrizeAmounts.size()) {
            addFieldError(prizeType,
                "The old and new " + prizeType + " must have same number of elements");
            return null;
        }

        // prepare the array to return
        double[] retArray = new double[newPrizeAmounts.size()];

        allEqual[0] = true;

        for (int i = 0; i < oldPrizeAmounts.length; ++i) {
            // make sure new prize is greater than old prize
            if (newPrizeAmounts.get(i) <= oldPrizeAmounts[i]) {
                addFieldError(prizeType,
                    "The new " + prizeType + " amounts must be greater than the old ones");
                return null;
            }

            // store the prize in the array to return
            retArray[i] = newPrizeAmounts.get(i);

            // keep track of whether all the prizes are equal
            if (i > 0 && retArray[i] != retArray[i - 1]) {
                allEqual[0] = false;
            }
        }
        return retArray;
    }

    /**
     * <p>
     * Validates the prize amounts when setting. Validates that prizeAmounts argument isn't null and doesn't contain
     * null elements or negative/NaN elements, and that each prize amount contains at most 2 decimal places.
     * </p>
     *
     * <p>
     * For contest prizes, additional validation is performed to ensure second place prize is at least 20%
     * of first place prize, and that first and second place prizes are provided.
     * </p>
     *
     * <p>
     * Any errors are added as field errors to model.
     * </p>
     *
     * @param prizeAmounts the prize amounts
     * @param prizeType type the type of prize (contestPrizes or milestonePrizes)
     * @return boolean indicating whether prize amounts are valid
     */
    private boolean validatePrizeAmounts(List<Double> prizeAmounts, String prizeType) {
        if (prizeAmounts == null || prizeAmounts.contains(null)) {
            addFieldError(prizeType, prizeType + " cannot be null, cannot contain null elements");
            return false;
        }

        // for contest prizes, 1st and 2nd place prizes are required
        if (prizeType.equals("contestPrizes") && prizeAmounts.size() < 2) {
            addFieldError(prizeType, "1st and 2nd place prize are required for contestPrizes");
            return false;
        }

        // make sure all prize amounts are greater than or equal to 0
        // IMPORTANT: we have to check for NaN separately, otherwise the validation will succeed
        for (Double contestPrize : prizeAmounts) {
            if (contestPrize.equals(Double.NaN) || contestPrize < 0) {
                addFieldError(prizeType, "All prize amounts must be >= 0");
                return false;
            }

            // make sure at most 2 decimal places of cents were specified for each prize
            String str = contestPrize.toString();
            if (str.substring(str.indexOf(".") + 1).length() > MAX_DECIMAL_PLACES_FOR_PRIZE) {
                addFieldError(prizeType, "All prize amounts must have at most " + MAX_DECIMAL_PLACES_FOR_PRIZE
                    + " decimal places for cents");
                return false;
            }
        }

        if (prizeType.equals("contestPrizes")) {
            // make sure 2nd place prize is at least PERCENTAGE_FOR_SECOND_PRIZE percent of first place prize
            if (prizeAmounts.get(1) < prizeAmounts.get(0) * PERCENTAGE_FOR_SECOND_PLACE_PRIZE) {
                addFieldError("contestPrizes", "The second place prize must be at least "
                    + PERCENTAGE_FOR_SECOND_PLACE_PRIZE_STRING + " of first place prize");
                return false;
            }
        }

        return true;
    }
}
