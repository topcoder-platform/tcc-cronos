<%--
  - Author: isv
  - Date: 19 Dec 2008
  - Version: 1.0
  - Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
  -
  - Description: This page is used ot handle the requests from index.jsp for invoking the selected web service operation
  - with provided parameters and displaying the results of the call. In fact, this page acts like a web service client
  - demonstrating the code which could be used for calling the web service.
--%>
<%@ page import="javax.xml.namespace.QName" %>
<%@ page import="java.net.URL" %>
<%@ page import="javax.xml.ws.Service" %>
<%@ page import="com.topcoder.service.facade.contest.ContestServiceFacade" %>
<%@ page import="com.topcoder.service.facade.contest.ContestServiceException" %>
<%@ page import="org.jboss.ws.core.StubExt" %>
<%@ page import="javax.xml.ws.BindingProvider" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.StringWriter" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.topcoder.service.project.Competition" %>
<%@ page import="com.topcoder.service.studio.ContestTypeData" %>
<%@ page import="com.topcoder.service.studio.MediumData" %>
<%@ page import="com.topcoder.service.studio.ContestStatusData" %>
<%@ page import="com.topcoder.service.project.StudioCompetition" %>
<%@ page import="com.topcoder.service.studio.UploadedDocument" %>
<%@ page import="com.topcoder.service.studio.SubmissionData" %>
<%@ page import="com.topcoder.service.studio.ContestPaymentData" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.topcoder.service.studio.ChangeHistoryData" %>
<%@ page import="javax.xml.datatype.DatatypeFactory" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.topcoder.service.facade.contest.ContestServiceFilter" %>
<%@ page import="com.topcoder.search.builder.filter.LikeFilter" %>
<%@ page import="com.topcoder.service.project.CompetionType" %>
<%@ page import="com.topcoder.security.auth.module.UserProfilePrincipal" %>
<%@ page import="com.topcoder.service.project.CompetitionPrize" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="com.topcoder.service.studio.ContestData" %>
<%@ page import="com.topcoder.service.studio.contest.DocumentType" %>
<%@ page import="javax.print.attribute.standard.Media" %>
<%@ page import="com.topcoder.service.studio.permission.Permission" %>
<%@ page import="com.topcoder.service.studio.permission.PermissionType" %>
<%@ page import="com.topcoder.catalog.entity.Category" %>
<%@ page import="com.topcoder.catalog.entity.Technology" %>
<%@ page import="com.topcoder.catalog.entity.Phase" %>
<%@ page import="com.topcoder.service.project.SoftwareCompetition" %>
<%@ page import="com.topcoder.catalog.service.AssetDTO" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="com.topcoder.catalog.entity.CompDocumentation" %>
<%@ page import="com.topcoder.management.project.Project" %>
<%@ page import="com.topcoder.management.resource.Resource" %>
<%@ page import="com.topcoder.management.resource.ResourceRole" %>
<%@ page import="com.topcoder.management.project.ProjectType" %>
<%@ page import="com.topcoder.management.project.ProjectCategory" %>
<%@ page import="com.topcoder.management.project.ProjectStatus" %>
<%@ page import="com.topcoder.date.workdays.DefaultWorkdaysFactory" %>
<%@ page import="com.topcoder.date.workdays.DefaultWorkdays" %>
<%@ page import="com.topcoder.project.phases.PhaseType" %>
<%@ page import="com.topcoder.project.phases.PhaseStatus" %>
<%@ page import="javax.activation.DataHandler" %>
<%@ page import="javax.activation.FileDataSource" %>
<%@ page import="com.topcoder.service.payment.CreditCardPaymentData" %>
<%@ page import="com.topcoder.service.payment.TCPurhcaseOrderPaymentData" %>
<%@ page import="com.topcoder.service.payment.PaymentResult" %>
<%@ page import="com.topcoder.project.service.ContestSaleData" %>
<%@ page import="com.topcoder.service.facade.contest.CommonProjectContestData" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
SoftwareCompetition generateSoftwareCompetition(ContestServiceFacade port) throws ContestServiceException {
    Category javaCategory = null;
    Category ejb3Category = null;

    List<Category> categories = port.getActiveCategories();
    for (Category category : categories) {
        if (javaCategory == null) {
            javaCategory = category;
        } else if (ejb3Category == null) {
            ejb3Category = category;
        }
    }

    Technology java15Technology = null;
    Technology informixTechnology = null;

    List<Technology> technologies = port.getActiveTechnologies();
    for (Technology technology : technologies) {
        if (java15Technology == null) {
            java15Technology = technology;
        } else if (informixTechnology == null) {
            informixTechnology = technology;
        }
    }            
    
    AssetDTO newAsset = new AssetDTO();
    newAsset.setName("Catalog Services");
    newAsset.setVersionText("1.0");
    newAsset.setShortDescription("short");
    newAsset.setDetailedDescription("detailed");
    newAsset.setFunctionalDescription("functional");
    // set the root category
    newAsset.setRootCategory(javaCategory);

    // assign categories which this asset belongs to
    newAsset.setCategories(Arrays.asList(ejb3Category));

    newAsset.setTechnologies(Arrays.asList(
        java15Technology,
        informixTechnology
    ));
    newAsset.setDocumentation(new ArrayList<CompDocumentation>());

    ProjectType projectType = new ProjectType(1, "Component");
    ProjectCategory projectCategory = new ProjectCategory(1, "Design", projectType);
    ProjectStatus projectStatus = new ProjectStatus(1, "projectStatus");
    com.topcoder.management.project.Project projectHeader = new com.topcoder.management.project.Project(
        projectCategory, projectStatus);
    projectHeader.setTcDirectProjectId(1);
    projectHeader.setProperty("Payments", "1000");

    com.topcoder.project.phases.Project projectPhases = new com.topcoder.project.phases.Project(new Date(),
            (DefaultWorkdays)new DefaultWorkdaysFactory().createWorkdaysInstance());
    PhaseType phaseTypeOne = new PhaseType(1, "one");
    com.topcoder.project.phases.Phase phaseOne = new com.topcoder.project.phases.Phase(projectPhases, 1);
    phaseOne.setPhaseType(phaseTypeOne);
    phaseOne.setFixedStartDate(new Date());
    phaseOne.setPhaseStatus(PhaseStatus.SCHEDULED);
    phaseOne.setProject(null);
    phaseOne.setScheduledStartDate(new Date());
    phaseOne.setScheduledEndDate(new Date());

    Resource resource = new Resource();
    ResourceRole resourceRole = new ResourceRole(13);
    resource.setResourceRole(resourceRole);
    Resource[] projectResources = new Resource[] {resource};

    SoftwareCompetition contest = new SoftwareCompetition();
    contest.setAssetDTO(newAsset);
    contest.setProjectHeader(projectHeader);
    contest.setProjectPhases(projectPhases);
    contest.setProjectResources(projectResources);
    contest.setType(CompetionType.SOFTWARE);

    return contest;
}
%>
<%
    String calledOperation = null;
    Object callResult = null;
    Throwable error = null;

    System.out.println("UserPrincipal: " + request.getUserPrincipal().getName());
    try {
        String operation = request.getParameter("operation");
        calledOperation = operation;
        URL wsdlLocation = new URL(getServletConfig().getServletContext().getInitParameter("facade_wsdl"));
        QName serviceName = new QName("http://ejb.contest.facade.service.topcoder.com/",
                                      "ContestServiceFacadeBeanService");
        Service service = Service.create(wsdlLocation, serviceName);
        ContestServiceFacade port = service.getPort(ContestServiceFacade.class);
        ((StubExt) port).setConfigName("Standard WSSecurity Client");
        ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
                                                         request.getUserPrincipal().getName());
        ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "password");
        if ("getAllContestTypes".equals(operation)) {
            StringBuilder b = new StringBuilder();
            List<ContestTypeData> types = port.getAllContestTypes();
            for (ContestTypeData t : types) {
                b.append("    ContestType: ID = ").append(t.getContestTypeId()).append(", desc = ").append(t.getDescription()).
                        append("<br/>");
            }
            callResult = "Retrieved contest types:<br/>" + b.toString();
        } else if ("getAllMediums".equals(operation)) {
            StringBuilder b = new StringBuilder();
            List<MediumData> types = port.getAllMediums();
            for (MediumData t : types) {
                b.append("    MediumData: ID = ").append(t.getMediumId()).append(", desc = ").append(t.getDescription()).
                        append("<br/>");
            }
            callResult = "Retrieved mediums:<br/>" + b.toString();
        } else if ("getStatusList".equals(operation)) {
            StringBuilder b = new StringBuilder();
            List<ContestStatusData> types = port.getStatusList();
            for (ContestStatusData t : types) {
                b.append("    ContestStatusData: ID = ").append(t.getStatusId()).append(", name = ").append(t.getName()).
                        append(", desc = ").append(t.getDescription()).append("<br/>");
            }
            callResult = "Retrieved contest types:<br/>" + b.toString();
        } else if ("getSubmissionFileTypes".equals(operation)) {
            String types = port.getSubmissionFileTypes();
            callResult = "Retrieved submission file types:<br/>" + types;
        } else if ("getAllContestHeaders".equals(operation)) {
            StringBuilder b = new StringBuilder();
            List<StudioCompetition> contests = port.getAllContestHeaders();
            for (StudioCompetition c : contests) {
                b.append("    Contest: ID = ").append(c.getId()).
                        append(", start = ").append(c.getStartTime()).
                        append(", end = ").append(c.getEndTime()).append("<br/>");
            }
            callResult = "Retrieved contest headers:<br/>" + b.toString();
        } else if ("getAllContests".equals(operation)) {
            StringBuilder b = new StringBuilder();
            List<StudioCompetition> contests = port.getAllContests();
            for (Competition c : contests) {
                b.append("    Contest: ID = ").append(c.getId()).
                        append(", start = ").append(c.getStartTime()).
                        append(", end = ").append(c.getEndTime()).append("<br/>");
            }
            callResult = "Retrieved contests:<br/>" + b.toString();
        } else if ("createContest".equals(operation)) {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(new Date(System.currentTimeMillis() + 10 * 24 * 3600 * 1000L));
            GregorianCalendar calendar2 = new GregorianCalendar();
            calendar2.setTime(new Date(System.currentTimeMillis() + 20 * 24 * 3600 * 1000L));
            ContestData data = new ContestData();
            StudioCompetition newContest = new StudioCompetition(data);
            newContest.setAdminFee(100);
            newContest.setCompetitionId(-1L);
            newContest.setCreatorUserId(((UserProfilePrincipal) request.getUserPrincipal()).getUserId());
            newContest.setDrPoints(50);
            newContest.setEligibility(request.getParameter("ce16"));
            newContest.setEndTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
            newContest.setId(-1);
            newContest.setPrizes(new ArrayList<CompetitionPrize>());
            newContest.setProject(null);
            newContest.setShortSummary(request.getParameter("cs16"));
            newContest.setStartTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
            newContest.setType(CompetionType.STUDIO);
            data.setContestChannelId(1);
            data.setContestDescriptionAndRequirements("Desc");
            data.setContestId(-1);
            data.setContestTypeId(1);
            data.setCreatorUserId(((UserProfilePrincipal) request.getUserPrincipal()).getUserId());
            data.setDocumentationUploads(new ArrayList<UploadedDocument>());
            data.setDurationInHours(96);
            data.setFinalFileFormat("doc");
            data.setForumId(-1);
            data.setForumPostCount(0);
            data.setLaunchDateAndTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
            data.setMaximumSubmissions(10);
            data.setMedia(new ArrayList<MediumData>());
            data.setName("New Contest");
            data.setNotesOnWinnerSelection("Notes on winner");
            data.setNumberOfRegistrants(100);
            data.setOtherFileFormats("Other");
            data.setOtherRequirementsOrRestrictions("Other reqs");
            data.setPrizeDescription("Pirze desc");
            data.setProjectId(Long.parseLong(request.getParameter("pid16")));
            data.setRequiredOrRestrictedColors("colros");
            data.setRequiredOrRestrictedFonts("Fonts");
            data.setSizeRequirements("Size");
            data.setStatusId(15);
            data.setSubmissionCount(0);
            data.setTcDirectProjectId(Long.parseLong(request.getParameter("pid16")));
            data.setWinnerAnnoucementDeadline(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar2));

            newContest = port.createContest(newContest, Long.parseLong(request.getParameter("pid16")));
            callResult = "Created new contest: ID = " + newContest.getId();
        } else if ("updateContest".equals(operation)) {
            String cid = request.getParameter("cid17");
            StudioCompetition contest = port.getContest(Long.parseLong(cid));
            contest.setEligibility(request.getParameter("ce17"));
            contest.setShortSummary(request.getParameter("cs17"));
            port.updateContest(contest);
            callResult = "Updated contest succesfully";
        } else if ("getContest".equals(operation)) {
            String cid = request.getParameter("cid1");
            StudioCompetition contest = port.getContest(Long.parseLong(cid));
            callResult = "Retrieved contest: ID = " + contest.getId() + ", start = " + contest.getStartTime()
                         + ", end = " + contest.getEndTime();
        } else if ("getContestsForProject".equals(operation)) {
            String pid = request.getParameter("pid1");
            StringBuilder b = new StringBuilder();
            List<StudioCompetition> contests = port.getContestsForProject(Long.parseLong(pid));
            for (Competition c : contests) {
                b.append("    Contest: ID = ").append(c.getId()).append(", start = ").
                        append(c.getStartTime()).
                        append(", end = ").append(c.getEndTime()).append("<br/>");
            }
            callResult = "Retrieved contests for project :<br/>" + b.toString();
        } else if ("updateContestStatus".equals(operation)) {
            String cid = request.getParameter("cid2");
            String sid = request.getParameter("sid1");
            port.updateContestStatus(Long.parseLong(cid), Long.parseLong(sid));
            callResult = "Updated contests status successfully";
        } else if ("uploadDocumentForContest".equals(operation)) {
            UploadedDocument doc = new UploadedDocument();
            doc.setContestId(Long.parseLong(request.getParameter("dcid1")));
            doc.setDescription(request.getParameter("ddesc1"));
            doc.setDocumentId(-1);
            doc.setDocumentTypeId(Long.parseLong(request.getParameter("dtid1")));
            doc.setFile(request.getParameter("dfile1").getBytes());
            doc.setFileName(request.getParameter("dfilename1"));
            doc.setMimeTypeId(Long.parseLong(request.getParameter("dmtid1")));
            doc.setPath(request.getParameter("dpath1"));
            doc = port.uploadDocumentForContest(doc);
            callResult = "Uploaded document for contest: <br/> Document: ID = " + doc.getDocumentId()
                         + ", desc = " + doc.getDescription() + ", file = " + doc.getFileName();
        } else if ("uploadDocument".equals(operation)) {
            UploadedDocument doc = new UploadedDocument();
            doc.setContestId(-1);
            doc.setDescription(request.getParameter("ddesc2"));
            doc.setDocumentId(-1);
            doc.setDocumentTypeId(Long.parseLong(request.getParameter("dtid2")));
            doc.setFile(request.getParameter("dfile2").getBytes());
            doc.setFileName(request.getParameter("dfilename2"));
            doc.setMimeTypeId(Long.parseLong(request.getParameter("dmtid2")));
            doc.setPath(request.getParameter("dpath2"));
            doc = port.uploadDocument(doc);
            callResult = "Uploaded document: <br/> Document: ID = " + doc.getDocumentId()
                         + ", desc = " + doc.getDescription() + ", file = " + doc.getFileName();
        } else if ("addDocumentToContest".equals(operation)) {
            String did = request.getParameter("did1");
            String cid = request.getParameter("cid3");
            port.addDocumentToContest(Long.parseLong(did), Long.parseLong(cid));
            callResult = "Added document to contest successfully";
        } else if ("removeDocumentFromContest".equals(operation)) {
            String did = request.getParameter("did4");
            String cid = request.getParameter("cid4");
            UploadedDocument doc = new UploadedDocument();
            doc.setContestId(Long.parseLong(cid));
            doc.setDocumentId(Long.parseLong(did));
            port.removeDocumentFromContest(doc);
            callResult = "Removed document from contest successfully";
        } else if ("retrieveSubmissionsForContest".equals(operation)) {
            String cid = request.getParameter("cid5");
            List<SubmissionData> submissions = port.retrieveSubmissionsForContest(Long.parseLong(cid));
            StringBuilder b = new StringBuilder();
            for (SubmissionData sc : submissions) {
                b.append("    Submission: ID = ").append(sc.getSubmissionId()).append(", date = ").
                        append(sc.getSubmittedDate()).append("<br/>");
            }
            callResult = "Retrieved submissions for contest :<br/>" + b.toString();
        } else if ("retrieveAllSubmissionsByMember".equals(operation)) {
            String uid = request.getParameter("uid1");
            List<SubmissionData> submissions = port.retrieveAllSubmissionsByMember(Long.parseLong(uid));
            StringBuilder b = new StringBuilder();
            for (SubmissionData sc : submissions) {
                b.append("    Submission: ID = ").append(sc.getSubmissionId()).append(", date = ").
                        append(sc.getSubmittedDate()).append("<br/>");
            }
            callResult = "Retrieved submissions for user :<br/>" + b.toString();
        } else if ("removeSubmission".equals(operation)) {
            String sid = request.getParameter("sbmid1");
            port.removeSubmission(Long.parseLong(sid));
            callResult = "Removed submission successfully";
        } else if ("getContestsForClient".equals(operation)) {
            String uid = request.getParameter("uid2");
            StringBuilder b = new StringBuilder();
            List<StudioCompetition> contests = port.getContestsForClient(Long.parseLong(uid));
            for (Competition c : contests) {
                b.append("    Contest: ID = ").append(c.getId()).
                        append(", start = ").append(c.getStartTime()).
                        append(", end = ").append(c.getEndTime()).append("<br/>");
            }
            callResult = "Retrieved contest for client:<br/>" + b.toString();
        } else if ("retrieveSubmission".equals(operation)) {
            String sid = request.getParameter("sbmid2");
            SubmissionData s = port.retrieveSubmission(Long.parseLong(sid));
            StringBuilder b = new StringBuilder();
            b.append("    Submission: ID = ").append(s.getSubmissionId()).append(", date = ").
                    append(s.getSubmittedDate());
            callResult = "Retrieved submission: <br/>" + b;
        } else if ("removeDocument".equals(operation)) {
            String did = request.getParameter("did5");
            port.removeDocument(Long.parseLong(did));
            callResult = "Removed document successfully";
        } else if ("getMimeTypeId".equals(operation)) {
            String ct = request.getParameter("ct1");
            long mimeTypeId = port.getMimeTypeId(ct);
            callResult = "Retrieved mime type ID for content type: " + mimeTypeId;
        //} else if ("purchaseSubmission".equals(operation)) {
        //    String sid = request.getParameter("sbmid3");
        //    String ppo = request.getParameter("ppo3");
        //    String st = request.getParameter("st3");
        //    port.purchaseSubmission(Long.parseLong(sid), ppo, st);
        //    callResult = "Purchase submission successfully";
        } else if ("createContestPayment".equals(operation)) {
            ContestPaymentData p = new ContestPaymentData();
            String cid = request.getParameter("cid6");
            String ppo = request.getParameter("ppo6");
            String psid = request.getParameter("psid6");
            String price = request.getParameter("price6");
            String st = request.getParameter("st6");
            p.setContestId(Long.parseLong(cid));
            p.setCreateDate(new Date());
            p.setPaymentStatusId(Long.parseLong(psid));
            p.setPaypalOrderId(ppo);
            p.setPrice(Double.parseDouble(price));
            p = port.createContestPayment(p, st);
            callResult = "Created contest payment:<br/> Payment: contest ID = " + p.getContestId()
                         + ", date = " + p.getCreateDate() + ", status = " + p.getPaymentStatusId()
                         + ", price = " + p.getPrice() + ", PayPal Order ID = " + p.getPaypalOrderId();
            ;
        } else if ("getContestPayment".equals(operation)) {
            String cid = request.getParameter("cid7");
            List<ContestPaymentData> pl = port.getContestPayments(Long.parseLong(cid));
			ContestPaymentData p = (ContestPaymentData)pl.get(1);
            callResult = "Retrieved contest payment details: <br/> Payment: contest ID = " + p.getContestId()
                         + ", date = " + p.getCreateDate() + ", status = " + p.getPaymentStatusId()
                         + ", price = " + p.getPrice() + ", PayPal Order ID = " + p.getPaypalOrderId();
        } else if ("editContestPayment".equals(operation)) {
            ContestPaymentData p = new ContestPaymentData();
            String cid = request.getParameter("cid8");
            String ppo = request.getParameter("ppo8");
            String psid = request.getParameter("psid8");
            String price = request.getParameter("price8");
            p.setContestId(Long.parseLong(cid));
            p.setCreateDate(new Date());
            p.setPaymentStatusId(Long.parseLong(psid));
            p.setPaypalOrderId(ppo);
            p.setPrice(Double.parseDouble(price));
            port.editContestPayment(p);
            callResult = "Updated contest payment successfully";
        } else if ("removeContestPayment".equals(operation)) {
            String cid = request.getParameter("cid9");
            boolean result = port.removeContestPayment(Long.parseLong(cid));
            callResult = "Removed contest payment. Deleted = " + result;
        } else if ("setSubmissionPlacement".equals(operation)) {
            String sid = request.getParameter("sbmid4");
            String place = request.getParameter("place4");
            port.setSubmissionPlacement(Long.parseLong(sid), Integer.parseInt(place));
            callResult = "Set submission placement successfully";
        } else if ("setSubmissionPrize".equals(operation)) {
            String sid = request.getParameter("sbmid5");
            String prid = request.getParameter("prid5");
            port.setSubmissionPrize(Long.parseLong(sid), Long.parseLong(prid));
            callResult = "Set submission prize successfully";
        } else if ("markForPurchase".equals(operation)) {
            String sid = request.getParameter("sbmid6");
            port.markForPurchase(Long.parseLong(sid));
            callResult = "Marked submission for purchase successfully";
        } else if ("getChangeHistory".equals(operation)) {
            String cid = request.getParameter("cid10");
            List<ChangeHistoryData> history = port.getChangeHistory(Long.parseLong(cid));
            StringBuilder b = new StringBuilder();
            for (ChangeHistoryData historyData : history) {
                b.append("    History: field = ").append(historyData.getFieldName()).append(", old = ").
                        append(historyData.getOldData()).append(", new = ").append(historyData.getNewData()).
                        append(", timestamp").append(historyData.getTimestamp()).append(", user = ").
                        append(historyData.getUserName()).append(", transaction = ").
                        append(historyData.getTransactionId()).append("<br/>");
            }
            callResult = "Retrieved history data for contest :<br/>" + b.toString();
        } else if ("getLatestChanges".equals(operation)) {
            String cid = request.getParameter("cid11");
            List<ChangeHistoryData> history = port.getLatestChanges(Long.parseLong(cid));
            StringBuilder b = new StringBuilder();
            for (ChangeHistoryData historyData : history) {
                b.append("    History: field = ").append(historyData.getFieldName()).append(", old = ").
                        append(historyData.getOldData()).append(", new = ").append(historyData.getNewData()).
                        append(", timestamp").append(historyData.getTimestamp()).append(", user = ").
                        append(historyData.getUserName()).append(", transaction = ").
                        append(historyData.getTransactionId()).append("<br/>");
            }
            callResult = "Retrieved latest changes for contest :<br/>" + b.toString();
        } else if ("deleteContest".equals(operation)) {
            String cid = request.getParameter("cid12");
            port.deleteContest(Long.parseLong(cid));
            callResult = "Deleted contest successfully";
        } else if ("processMissingPayments".equals(operation)) {
            String cid = request.getParameter("cid13");
            port.processMissingPayments(Long.parseLong(cid));
            callResult = "Processed missing payments for contest successfully";
        } else if ("addChangeHistory".equals(operation)) {
            ChangeHistoryData h = new ChangeHistoryData();
            h.setContestId(Long.parseLong(request.getParameter("cid14")));
            h.setFieldName(request.getParameter("fn14"));
            h.setNewData(request.getParameter("nv14"));
            h.setOldData(request.getParameter("ov14"));
            h.setTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
            h.setTransactionId(Long.parseLong(request.getParameter("trid14")));
            h.setUserAdmin(request.isUserInRole("Cockpit Administrator"));
            h.setUserName(request.getUserPrincipal().getName());
            List<ChangeHistoryData> list = new ArrayList<ChangeHistoryData>();
            list.add(h);
            port.addChangeHistory(list);
            callResult = "Added change history records for contest successfully";
        } else if ("updateSubmission".equals(operation)) {
            SubmissionData s = new SubmissionData();
            s.setContestId(Long.parseLong(request.getParameter("cid15")));
            s.setMarkedForPurchase(false);
            s.setPaidFor(true);
            s.setPassedScreening(false);
            s.setPlacement(1);
            s.setRemoved(false);
            s.setSubmissionId(Long.parseLong(request.getParameter("sbmid15")));
            s.setSubmittedDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
            s.setSubmitterId(Long.parseLong(request.getParameter("uid15")));
            port.updateSubmission(s);
            callResult = "Updated submission successfully";
        } else if ("searchContests".equals(operation)) {
            String name = request.getParameter("cname1");
            com.topcoder.search.builder.filter.Filter d = new LikeFilter("name", name);
            ContestServiceFilter filter = new ContestServiceFilter(d);
            List<StudioCompetition> contests = port.searchContests(filter);
            StringBuilder b = new StringBuilder();
            for (Competition c : contests) {
                b.append("    Contest: ID = ").append(c.getId()).
                        append(", start = ").append(c.getStartTime()).
                        append(", end = ").append(c.getEndTime()).append("<br/>");
            }
            callResult = "Found contests by name:<br/>" + b.toString();
        } else if ("getAllDocumentTypes".equals(operation)) {
            StringBuilder b = new StringBuilder();
            List<DocumentType> types = port.getAllDocumentTypes();
            for (DocumentType t : types) {
                b.append("    DocumentType: ID = ").append(t.getDocumentTypeId()).append(", desc = ").append(t.getDescription()).
                        append("<br/>");
            }
            callResult = "Retrieved contest types:<br/>" + b.toString();
        } else if ("getActiveCategories".equals(operation)) {
            StringBuilder b = new StringBuilder();
            List<Category> categories = port.getActiveCategories();
            for (Category category : categories) {
                b.append("    Category: ID = ").append(category.getId()).append(", name = ").append(category.getName()).
                        append(", description = ").append(category.getDescription()).
                        append(", status = ").append(category.getStatus()).
                        append(", viewable = ").append(category.isViewable()).
                        append(", catalogName = ").append(category.getCatalogName()).
                        append("<br/>");
            }
            callResult = "Retrieved categories:<br/>" + b.toString();
        } else if ("getActiveTechnologies".equals(operation)) {
            StringBuilder b = new StringBuilder();
            List<Technology> technologies = port.getActiveTechnologies();
            for (Technology technology : technologies) {
                b.append("    Technology: ID = ").append(technology.getId()).append(", name = ").append(technology.getName()).
                        append(", description = ").append(technology.getDescription()).
                        append(", status = ").append(technology.getStatus()).
                        append("<br/>");
            }
            callResult = "Retrieved technologies:<br/>" + b.toString();
        } else if ("getPhases".equals(operation)) {
            StringBuilder b = new StringBuilder();
            List<Phase> phases = port.getPhases();
            for (Phase phase : phases) {
                b.append("    Phase: ID = ").append(phase.getId()).append(", description = ").append(phase.getDescription()).
                        append("<br/>");
            }
            callResult = "Retrieved phases:<br/>" + b.toString();
        } else if ("assignUserToAsset".equals(operation)) {
            String userId = request.getParameter("cid11");
            String assetId = request.getParameter("cid12");

            port.assignUserToAsset(Long.parseLong(userId), Long.parseLong(assetId));

            callResult = "assignUserToAsset successfully";
        } else if ("removeUserFromAsset".equals(operation)) {
            String userId = request.getParameter("cid21");
            String assetId = request.getParameter("cid22");

            port.removeUserFromAsset(Long.parseLong(userId), Long.parseLong(assetId));

            callResult = "removeUserFromAsset successfully";
        } else if ("findAllTcDirectProjects".equals(operation)) {
            StringBuilder b = new StringBuilder();
            SoftwareCompetition[] contests = port.findAllTcDirectProjects();
            for (int i = 0; i < contests.length; i++) {
                SoftwareCompetition contest = contests[i];
                b.append("    Project Header: ID = ").append(contest.getProjectData().getProjectHeader().getId()).
                        append("<br/>");
            }
            callResult = "Retrieved projects:<br/>" + b.toString();
        } else if ("findAllTcDirectProjectsForUser".equals(operation)) {
            String operator = request.getParameter("cid31");

            StringBuilder b = new StringBuilder();
            SoftwareCompetition[] contests = port.findAllTcDirectProjectsForUser(operator);
            for (int i = 0; i < contests.length; i++) {
                SoftwareCompetition contest = contests[i];
                b.append("    Project Header: ID = ").append(contest.getProjectData().getProjectHeader().getId()).
                        append("<br/>");
            }
            callResult = "Retrieved projects:<br/>" + b.toString();
        } else if ("getFullProjectData".equals(operation)) {
            String projectId = request.getParameter("cid41");

            StringBuilder b = new StringBuilder();
            SoftwareCompetition contest = port.getFullProjectData(Long.parseLong(projectId));
            b.append("    Project Header: ID = ").append(contest.getProjectData().getProjectHeader().getId()).
                    append("<br/>");
            b.append("    Resources: ").append(contest.getProjectData().getResources()).
                    append("<br/>");
            b.append("    Teams: ").append(contest.getProjectData().getTeams()).
                    append("<br/>");
            b.append("    Technologies: ").append(contest.getProjectData().getTechnologies()).
                    append("<br/>");
            b.append("    ContestSales: ");

            List<ContestSaleData> contestSales = contest.getProjectData().getContestSales();
            for (ContestSaleData contestSale : contestSales) {
                b.append("    ContestSale: ID = ").append(contestSale.getContestSaleId()).append(", contestId = ").append(contestSale.getContestId()).
                        append(", saleStatusId = ").append(contestSale.getSaleStatusId()).
                        append(", paypalOrderId = ").append(contestSale.getPaypalOrderId()).
                        append(", price = ").append(contestSale.getPrice()).
                        append(", createDate = ").append(contestSale.getCreateDate()).
                        append(", saleTypeId = ").append(contestSale.getSaleTypeId()).
                        append(", saleReferenceId = ").append(contestSale.getSaleReferenceId()).
                        append("<br/>");
            }

            callResult = "Retrieved projects:<br/>" + b.toString();
        } else if ("createSoftwareContest".equals(operation)) {
            String tcDirectProjectId = request.getParameter("cid51");

            Category javaCategory = null;
            Category ejb3Category = null;

            List<Category> categories = port.getActiveCategories();
            for (Category category : categories) {
                if (javaCategory == null) {
                    javaCategory = category;
                } else if (ejb3Category == null) {
                    ejb3Category = category;
                }
            }

            Technology java15Technology = null;
            Technology informixTechnology = null;

            List<Technology> technologies = port.getActiveTechnologies();
            for (Technology technology : technologies) {
                if (java15Technology == null) {
                    java15Technology = technology;
                } else if (informixTechnology == null) {
                    informixTechnology = technology;
                }
            }            
            
            AssetDTO newAsset = new AssetDTO();
            newAsset.setName("Shannon Services");
            newAsset.setVersionText("1.0");
            newAsset.setShortDescription("short");
            newAsset.setDetailedDescription("detailed");
            newAsset.setFunctionalDescription("functional");
            // set the root category
            newAsset.setRootCategory(javaCategory);

            // assign categories which this asset belongs to
            newAsset.setCategories(Arrays.asList(ejb3Category));
    
            newAsset.setTechnologies(Arrays.asList(
                java15Technology,
                informixTechnology
            ));
            newAsset.setDocumentation(new ArrayList<CompDocumentation>());

            ProjectType projectType = new ProjectType(1, "Component");
            ProjectCategory projectCategory = new ProjectCategory(1, "Design", projectType);
            ProjectStatus projectStatus = new ProjectStatus(1, "projectStatus");
            com.topcoder.management.project.Project projectHeader = new com.topcoder.management.project.Project(
                projectCategory, projectStatus);

            com.topcoder.project.phases.Project projectPhases = new com.topcoder.project.phases.Project(new Date(),
                    (DefaultWorkdays)new DefaultWorkdaysFactory().createWorkdaysInstance());
           // PhaseType phaseTypeOne = new PhaseType(1, "one");
           // com.topcoder.project.phases.Phase phaseOne = new com.topcoder.project.phases.Phase(projectPhases, 1);
           // phaseOne.setPhaseType(phaseTypeOne);
         //   phaseOne.setFixedStartDate(new Date());
          //  phaseOne.setPhaseStatus(PhaseStatus.SCHEDULED);
          //  phaseOne.setProject(null);
          //  phaseOne.setScheduledStartDate(new Date());
          //  phaseOne.setScheduledEndDate(new Date());

            Resource resource = new Resource();
            ResourceRole resourceRole = new ResourceRole(13);
            resource.setResourceRole(resourceRole);
            Resource[] projectResources = new Resource[] {resource};

            SoftwareCompetition contest = new SoftwareCompetition();
            contest.setAssetDTO(newAsset);
            contest.setProjectHeader(projectHeader);
            contest.setProjectPhases(projectPhases);
            contest.setProjectResources(projectResources);
            contest.setType(CompetionType.SOFTWARE);
            port.createSoftwareContest(contest, Long.parseLong(tcDirectProjectId));

            callResult = "createSoftwareContest successfully";
        } else if ("updateSoftwareContest".equals(operation)) {
            String tcDirectProjectId = request.getParameter("cid51");

            Category javaCategory = null;
            Category ejb3Category = null;

            List<Category> categories = port.getActiveCategories();
            for (Category category : categories) {
                if (javaCategory == null) {
                    javaCategory = category;
                } else if (ejb3Category == null) {
                    ejb3Category = category;
                }
            }

            Technology java15Technology = null;
            Technology informixTechnology = null;

            List<Technology> technologies = port.getActiveTechnologies();
            for (Technology technology : technologies) {
                if (java15Technology == null) {
                    java15Technology = technology;
                } else if (informixTechnology == null) {
                    informixTechnology = technology;
                }
            }            
            
            AssetDTO newAsset = new AssetDTO();
            newAsset.setName("Catalog Services");
            newAsset.setVersionText("1.0");
            newAsset.setShortDescription("short");
            newAsset.setDetailedDescription("detailed");
            newAsset.setFunctionalDescription("functional");
            // set the root category
            newAsset.setRootCategory(javaCategory);

            // assign categories which this asset belongs to
            newAsset.setCategories(Arrays.asList(ejb3Category));
    
            newAsset.setTechnologies(Arrays.asList(
                java15Technology,
                informixTechnology
            ));
            newAsset.setDocumentation(new ArrayList<CompDocumentation>());

            ProjectType projectType = new ProjectType(1, "projectType");
            ProjectCategory projectCategory = new ProjectCategory(1, "projectCategory", projectType);
            ProjectStatus projectStatus = new ProjectStatus(1, "projectStatus");
            com.topcoder.management.project.Project projectHeader = new com.topcoder.management.project.Project(
                projectCategory, projectStatus);

            com.topcoder.project.phases.Project projectPhases = new com.topcoder.project.phases.Project(new Date(),
                    (DefaultWorkdays)new DefaultWorkdaysFactory().createWorkdaysInstance());
            /*PhaseType phaseTypeOne = new PhaseType(1, "one");
            com.topcoder.project.phases.Phase phaseOne = new com.topcoder.project.phases.Phase(projectPhases, 1);
            phaseOne.setPhaseType(phaseTypeOne);
            phaseOne.setFixedStartDate(new Date());
            phaseOne.setPhaseStatus(PhaseStatus.SCHEDULED);
            phaseOne.setProject(null);
            phaseOne.setScheduledStartDate(new Date());
            phaseOne.setScheduledEndDate(new Date());*/

            Resource resource = new Resource();
            ResourceRole resourceRole = new ResourceRole(13);
            resource.setResourceRole(resourceRole);
            Resource[] projectResources = new Resource[] {resource};

            SoftwareCompetition contest = new SoftwareCompetition();
            contest.setAssetDTO(newAsset);
            contest.setProjectHeader(projectHeader);
            contest.setProjectPhases(projectPhases);
            contest.setProjectResources(projectResources);
            contest.setType(CompetionType.COMPONENT_DESIGN);
            contest = port.createSoftwareContest(contest, Long.parseLong(tcDirectProjectId));

            contest.setProjectHeaderReason("projectHeaderReason");
            port.updateSoftwareContest(contest, Long.parseLong(tcDirectProjectId));

            callResult = "updateSoftwareContest successfully";
        } else if ("uploadSubmission".equals(operation)) {
            String projectId = request.getParameter("cid11");
            FileDataSource dataSource = new FileDataSource("D:/temp/sample.jar");
            DataHandler dataHandler = new DataHandler(dataSource);

            long submissionId = port.uploadSubmission(Long.parseLong(projectId), "newFile", dataHandler);

            callResult = "Newly created submission id: " + submissionId;
        } else if ("uploadFinalFix".equals(operation)) {
            String projectId = request.getParameter("cid21");
            FileDataSource dataSource = new FileDataSource("D:/temp/sample.jar");
            DataHandler dataHandler = new DataHandler(dataSource);

            long submissionId = port.uploadFinalFix(Long.parseLong(projectId), "newFile", dataHandler);

            callResult = "Newly created submission id: " + submissionId;
        } else if ("uploadTestCases".equals(operation)) {
            String projectId = request.getParameter("cid31");
            FileDataSource dataSource = new FileDataSource("D:/temp/sample.jar");
            DataHandler dataHandler = new DataHandler(dataSource);

            long submissionId = port.uploadTestCases(Long.parseLong(projectId), "newFile", dataHandler);

            callResult = "Newly created submission id: " + submissionId;
        } else if ("setSubmissionStatus".equals(operation)) {
            String submissionId = request.getParameter("cid41");
            String submissionStatusId = request.getParameter("cid42");
            String operator = request.getParameter("cid43");

            port.setSubmissionStatus(Long.parseLong(submissionId), Long.parseLong(submissionStatusId), operator);

            callResult = "setSubmissionStatus successfully";
        }  else if ("addSubmitter".equals(operation)) {
            String projectId = request.getParameter("cid51");
            String userId = request.getParameter("cid52");

            port.addSubmitter(Long.parseLong(projectId), Long.parseLong(userId));

            callResult = "addSubmitter successfully";
        } else if ("processContestCreditCardSale".equals(operation)) {
            SoftwareCompetition contest = generateSoftwareCompetition(port);

            CreditCardPaymentData paymentData = new CreditCardPaymentData();
            paymentData.setCardNumber("4111111111111111");
            paymentData.setCardType("visa");
            paymentData.setCardExpiryMonth("12");
            paymentData.setCardExpiryYear("2010");
            PaymentResult result = port.processContestCreditCardSale(contest, paymentData);

            callResult = "processContestCreditCardSale successfully: <br/> Payment result: referenceNumber = " + result.getReferenceNumber();
        } else if ("processContestPurchaseOrderSale".equals(operation)) {
            SoftwareCompetition contest = generateSoftwareCompetition(port);

            TCPurhcaseOrderPaymentData paymentData = new TCPurhcaseOrderPaymentData();
            paymentData.setPoNumber("PoNumber");
            PaymentResult result = port.processContestPurchaseOrderSale(contest, paymentData);

            callResult = "processContestCreditCardSale successfully: <br/> Payment result: referenceNumber = " + result.getReferenceNumber();
        }
        else if ("getSimpleProjectContestDatapid".equals(operation)) {
        	String pid = request.getParameter("apid3");
            StringBuilder b = new StringBuilder();
            List<CommonProjectContestData> cs = port.getCommonProjectContestDataByPID(Long.parseLong(pid));
            for (CommonProjectContestData c : cs) {
                b.append("    ProjectId: ID = ").append(c.getProjectId()).append(", pname= ").append(c.getPname()).append(", cname = ").append(c.getCname()).
                append(", status = ").append(c.getSname()).append(", startTime = ").append(c.getStartDate()).    
                append(", forumId = ").append(c.getForumId()).    
                append(", desc = ").append(c.getDescription()).    
                append("<br/>");
            }
            callResult = "Retrieved mediums:<br/>" + b.toString();
        } 
        else if ("getSimpleProjectContestData".equals(operation)) {
            StringBuilder b = new StringBuilder();
            List<CommonProjectContestData> cs = port.getCommonProjectContestData();
            for (CommonProjectContestData c : cs) {
                b.append("    ProjectId: ID = ").append(c.getProjectId()).append(", cname = ").append(c.getCname()).
                append(", startTime = ").append(c.getStartDate()).    
                append(", forumId = ").append(c.getForumId()).    
                append(", desc = ").append(c.getDescription()).    
                append("<br/>");
            }
            callResult = "Retrieved mediums:<br/>" + b.toString();
        } 

		else if ("getPermissionsByUser".equals(operation)) {
            String userId = request.getParameter("cid11");
            List<Permission> permissions = port.getPermissionsByUser(Long.parseLong(userId));
            StringBuilder b = new StringBuilder();
            for (Permission permission : permissions) {
                b.append("    Permission: ID = ").append(permission.getPermissionId()).append(", userId = ").append(permission.getUserId()).
                        append(", projectId = ").append(permission.getProjectId()).
                        append(", permissionTypeId = ").append(permission.getPermissionType().getPermissionTypeId()).
                        append(", permissionTypeName = ").append(permission.getPermissionType().getName()).
                        append("<br/>");
            }
            callResult = "Retrieved permissions:<br/>" + b.toString();
        } else if ("getPermissions".equals(operation)) {
            String userId = request.getParameter("cid21");
            String projectId = request.getParameter("cid22");
            List<Permission> permissions = port.getPermissions(Long.parseLong(userId), Long.parseLong(projectId));
            StringBuilder b = new StringBuilder();
            for (Permission permission : permissions) {
                b.append("    Permission: ID = ").append(permission.getPermissionId()).append(", userId = ").append(permission.getUserId()).
                        append(", projectId = ").append(permission.getProjectId()).
                        append(", permissionTypeId = ").append(permission.getPermissionType().getPermissionTypeId()).
                        append(", permissionTypeName = ").append(permission.getPermissionType().getName()).
                        append("<br/>");
            }
            callResult = "Retrieved permissions:<br/>" + b.toString();
        } else if ("getAllPermissionType".equals(operation)) {
            List<PermissionType> types = port.getAllPermissionType();
            StringBuilder b = new StringBuilder();
            for (PermissionType type : types) {
                b.append("    PermissionType: ID = ").append(type.getPermissionTypeId()).append(", name = ").append(type.getName()).
                        append("<br/>");
            }
            callResult = "Retrieved permission types:<br/>" + b.toString();
        } else if ("addPermissionType".equals(operation)) {
            PermissionType type = new PermissionType();
            String name = request.getParameter("cid31");
            type.setName(name);
            type = port.addPermissionType(type);
            callResult = "Created permission type:<br/> PermissionType: permission type ID = " + type.getPermissionTypeId()
                         + ", name = " + type.getName();
        } else if ("addPermission".equals(operation)) {
            Permission permission = new Permission();
            String userId = request.getParameter("cid41");
            String projectId = request.getParameter("cid42");
            String permissionTypeId = request.getParameter("cid43");
            permission.setUserId(Long.parseLong(userId));
            permission.setProjectId(Long.parseLong(projectId));

            List<PermissionType> types = port.getAllPermissionType();
            for (PermissionType type : types) {
                if (type.getPermissionTypeId().equals(Long.parseLong(permissionTypeId))) {
                    permission.setPermissionType(type);
                }
            }

            permission = port.addPermission(permission);
            callResult = "Created permission:<br/> Permission: permission ID = " + permission.getPermissionId()
                         + ", userId = " + permission.getUserId() + ", projectId = " + permission.getProjectId()
                         + ", permissionTypeId = " + permission.getPermissionType().getPermissionTypeId()
                         + ", permissionTypeName = " + permission.getPermissionType().getName();
        } else if ("updatePermissionType".equals(operation)) {
            PermissionType type = new PermissionType();
            String permissionTypeId = request.getParameter("cid51");
            String name = request.getParameter("cid52");
            type.setPermissionTypeId(Long.parseLong(permissionTypeId));
            type.setName(name);
            port.updatePermissionType(type);
            callResult = "Update Permission Type successfully";
        } else if ("updatePermission".equals(operation)) {
            Permission permission = new Permission();
            String permissionId = request.getParameter("cid61");
            String userId = request.getParameter("cid62");
            String projectId = request.getParameter("cid63");
            String permissionTypeId = request.getParameter("cid64");
            permission.setPermissionId(Long.parseLong(permissionId));
            permission.setUserId(Long.parseLong(userId));
            permission.setProjectId(Long.parseLong(projectId));

            List<PermissionType> types = port.getAllPermissionType();
            for (PermissionType type : types) {
                if (type.getPermissionTypeId().equals(Long.parseLong(permissionTypeId))) {
                    permission.setPermissionType(type);
                }
            }
            port.updatePermission(permission);
            callResult = "Update Permission successfully";
        } else if ("deletePermissionType".equals(operation)) {
            String permissionTypeId = request.getParameter("cid71");
            boolean result = port.deletePermissionType(Long.parseLong(permissionTypeId));
            callResult = "Removed Permission Type. Deleted = " + result;
        } else if ("deletePermission".equals(operation)) {
            String permissionId = request.getParameter("cid81");
            boolean result = port.deletePermission(Long.parseLong(permissionId));
            callResult = "Removed Permission. Deleted = " + result;
        }

    } catch (Throwable e) {
        error = e;
    }
    if (error != null) {
        StringWriter sw = new StringWriter();
        error.printStackTrace(new PrintWriter(sw));
        callResult = "ERROR!<br/>" + sw.getBuffer().toString().replaceAll("\\n", "<br/>");
    }
%>
<html>
  <head>
      <title>Contest Service Facade Demo</title>
  </head>
  <body>
      <p>Called Contest Service Facade operation: <%=calledOperation%></p>
      <p>Resulf of the call: <%=callResult%></p>
      <a href="index.jsp">Back to list of available operations</a>
  </body>
</html>
