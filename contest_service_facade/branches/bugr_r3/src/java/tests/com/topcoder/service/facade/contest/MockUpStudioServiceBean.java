package com.topcoder.service.facade.contest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.PersistenceException;
import com.topcoder.service.studio.SubmissionData;
import com.topcoder.service.studio.ejb.StudioServiceBean;

@RunAs("Cockpit Administrator")
@RolesAllowed("Cockpit User")
@DeclareRoles( { "Cockpit User", "Cockpit Administrator" })
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Stateless
public class MockUpStudioServiceBean extends StudioServiceBean {
    /**
     * Retrieves the list of data for contests for which the user with the given
     * name is a resource. Returns an empty list if no contests are found.
     * 
     * Parameters: username - the name of the user
     * 
     * Returns: the list of found contests data (empty list of none found)
     * 
     * Throws: IllegalArgumentException if username is null or empty
     * PersistenceException when any other error occurs
     * 
     * @param username
     * @param Return
     * @return
     * @since 1.3
     */
    public List<ContestData> getUserContests(String username)
        throws PersistenceException {
        List<ContestData> contests = new ArrayList<ContestData>();
        XMLGregorianCalendar calendar = null;
        try {
            calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        } catch (DatatypeConfigurationException e) {
            throw new PersistenceException(e.getMessage(), e.getMessage());
        }
        if (username.equals("developer1")) {
            ContestData contest1 = new ContestData();
            contest1.setContestId(1L);
            contest1.setLaunchDateAndTime(calendar);
            contest1.setContestAdministrationFee(100);
            ContestData contest2 = new ContestData();
            contest2.setContestId(2L);
            contest2.setLaunchDateAndTime(calendar);
            contest2.setContestAdministrationFee(200);
            contests.add(contest1);
            contests.add(contest2);
        } else if (username.equals("developer2")) {
            ContestData contest3 = new ContestData();
            contest3.setContestId(3L);
            contests.add(contest3);
            contest3.setLaunchDateAndTime(calendar);
            contest3.setContestAdministrationFee(200);
        } else {
            throw new PersistenceException("The mock up method throw exception",
                "The mock up method throw exception");
        }

        return contests;
    }

    /**
     * Gets the milestone submissions for the given contest. Returns an empty
     * list if none found.
     * 
     * Parameters: submissionId - the ID of the submission payment to get return
     * - the list of retrieved submission data, an empty list if none found
     * 
     * Throws: PersistenceException if any error occurs during the retrieval
     * 
     * @param contestId
     * @param Return
     * @return
     * @since 1.3
     */
    public List < SubmissionData > getMilestoneSubmissionsForContest(
            long contestId) throws PersistenceException {
        List < SubmissionData > submissions = new ArrayList < SubmissionData >();

        if (contestId == 1L) {
            SubmissionData sm1 = new SubmissionData();
            sm1.setSubmissionId(1L);

            SubmissionData sm2 = new SubmissionData();
            sm2.setSubmissionId(2L);
            submissions.add(sm1);
            submissions.add(sm2);
        } else if (contestId == 2L) {
            SubmissionData sm3 = new SubmissionData();
            sm3.setSubmissionId(3L);
            submissions.add(sm3);
        } else {
            throw new PersistenceException(
                    "The mock up method throw exception",
                    "The mock up method throw exception");
        }

        return submissions;
    }

    /**
     * Gets the final submissions for the given contest. Returns an empty list
     * if none found.
     * 
     * Parameters: contestId - the id of the contest for which submission data
     * should be retrieved return - the list of retrieved submission data, an
     * empty list if none found
     * 
     * Throws: PersistenceException if any error occurs during the retrieval
     * 
     * @param contestId
     * @param Return
     * @return
     * @since 1.3
     */
    public List < SubmissionData > getFinalSubmissionsForContest(
            long contestId) throws PersistenceException {
        List < SubmissionData > submissions = new ArrayList < SubmissionData >();

        if (contestId == 1L) {
            SubmissionData sm1 = new SubmissionData();
            sm1.setSubmissionId(1L);

            SubmissionData sm2 = new SubmissionData();
            sm2.setSubmissionId(2L);
            submissions.add(sm1);
            submissions.add(sm2);
        } else if (contestId == 2L) {
            SubmissionData sm3 = new SubmissionData();
            sm3.setSubmissionId(3L);
            submissions.add(sm3);
        } else {
            throw new PersistenceException(
                    "The mock up method throw exception",
                    "The mock up method throw exception");
        }

        return submissions;
    }

    /**
     * Sets the specified milestone prize for submission with the given ID.
     * 
     * Parameters: submissionId - the ID of the submission milestonePrizeId -
     * the ID of the milestone prize
     * 
     * Throws: PersistenceException if any error occurs
     * 
     * @param Return
     * @param milestonePrizeId
     * @param submissionId
     * @since 1.3
     */
    public void setSubmissionMilestonePrize(long submissionId,
            long milestonePrizeId) throws PersistenceException {
        if (submissionId == 1L) {
            if (milestonePrizeId == 1L) {
                // passed
            } else if (milestonePrizeId == 2L) {
                throw new PersistenceException(
                        "The mock up method throw exception",
                        "submisson is not in milestonepirze phase");
            } else if (milestonePrizeId == 3L) {
                throw new PersistenceException(
                        "The mock up method throw exception",
                        "milestoneprize is not found");
            } else if (milestonePrizeId == 4L) {
                throw new PersistenceException(
                        "The mock up method throw exception",
                        "MilestonePrize could not be associated more submission");
            }
        } else {
            throw new PersistenceException(
                    "The mock up method throw exception",
                    "The submission is not found");
        }
    }
}
