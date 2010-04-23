/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.stresstests;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.security.TCSubject;
import com.topcoder.service.actions.GetDocumentsContestAction;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.UploadedDocument;

/**
 * GetDocumentsContestActionTest.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GetDocumentsContestActionTest extends AbstractStressTest {

    /**
     * Test execute action.
     *
     * @throws Exception
     *             the exception occurs
     */
    @Test
    public void testExecuteAction() throws Exception {
        // set mock
        List < UploadedDocument > competitionDocuments = new ArrayList < UploadedDocument >();
        UploadedDocument ud = new UploadedDocument();
        ud.setFileName("aa.txt");
        ud.setPath("test_files/stress_files");
        ud.setDocumentId(1);
        competitionDocuments.add(ud);
        ContestData contestData = new ContestData();
        contestData.setDocumentationUploads(competitionDocuments);
        StudioCompetition competition = new StudioCompetition(contestData);
        TCSubject tc = null;
        EasyMock.expect(contestServiceFacade.getContest((TCSubject) EasyMock.eq(tc), EasyMock.eq(1L)))
            .andReturn(competition).times(StressHelper.EXCUTIMES);
        EasyMock.replay(contestServiceFacade);
        // test method
        stress.getStartTime();
        for (int i = 0; i < StressHelper.EXCUTIMES; i++) {
            request.setParameter("contestId", "1");
            ActionProxy proxy = getActionProxy("/documentsContest.action");
            GetDocumentsContestAction cate = (GetDocumentsContestAction) proxy.getAction();
            cate.setContestServiceFacade(contestServiceFacade);
            cate.setContestId(1L);
            proxy.execute();
            assertEquals(1, cate.getContestId());
        }
        stress.getEndTime();

        stress.printResultToFile("GetDocumentsContestAction", "executeAction", "");

        // verify mock
        control.verify();

    }

}
