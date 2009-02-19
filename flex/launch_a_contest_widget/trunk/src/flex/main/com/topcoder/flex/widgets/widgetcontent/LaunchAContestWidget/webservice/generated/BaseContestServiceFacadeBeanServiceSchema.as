package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.generated
{
	 import mx.rpc.xml.Schema
	 public class BaseContestServiceFacadeBeanServiceSchema
	{
		 public var schemas:Array = new Array();
		 public var targetNamespaces:Array = new Array();
		 public function BaseContestServiceFacadeBeanServiceSchema():void
{		
			 var xsdXML0:XML = <xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns="http://schemas.xmlsoap.org/wsdl/" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tns="http://ejb.contest.facade.service.topcoder.com/" xmlns:xs="http://www.w3.org/2001/XMLSchema" attributeFormDefault="unqualified" elementFormDefault="unqualified" targetNamespace="http://ejb.contest.facade.service.topcoder.com/">
    <xsd:element name="PaymentException" type="tns:PaymentException"/>
    <xsd:element name="addChangeHistory" type="tns:addChangeHistory"/>
    <xsd:element name="addChangeHistoryResponse" type="tns:addChangeHistoryResponse"/>
    <xsd:element name="addDocumentToContest" type="tns:addDocumentToContest"/>
    <xsd:element name="addDocumentToContestResponse" type="tns:addDocumentToContestResponse"/>
    <xsd:element name="contest_not_found_fault">
        <xsd:complexType>
            <xsd:sequence>
                <xsd:element name="contest_not_found" type="xs:long"/>
            </xsd:sequence>
        </xsd:complexType>
    </xsd:element>
    <xsd:element name="createContest" type="tns:createContest"/>
    <xsd:element name="createContestPayment" type="tns:createContestPayment"/>
    <xsd:element name="createContestPaymentResponse" type="tns:createContestPaymentResponse"/>
    <xsd:element name="createContestResponse" type="tns:createContestResponse"/>
    <xsd:element name="deleteContest" type="tns:deleteContest"/>
    <xsd:element name="deleteContestResponse" type="tns:deleteContestResponse"/>
    <xsd:element name="do_1" type="tns:do_1"/>
    <xsd:element name="do_1Response" type="tns:do_1Response"/>
    <xsd:element name="do_2" type="tns:do_2"/>
    <xsd:element name="do_2Response" type="tns:do_2Response"/>
    <xsd:element name="do_3" type="tns:do_3"/>
    <xsd:element name="do_3Response" type="tns:do_3Response"/>
    <xsd:element name="document_not_found_fault">
        <xsd:complexType>
            <xsd:sequence>
                <xsd:element name="document_not_found" type="xs:long"/>
            </xsd:sequence>
        </xsd:complexType>
    </xsd:element>
    <xsd:element name="editContestPayment" type="tns:editContestPayment"/>
    <xsd:element name="editContestPaymentResponse" type="tns:editContestPaymentResponse"/>
    <xsd:element name="getAllContestHeaders" type="tns:getAllContestHeaders"/>
    <xsd:element name="getAllContestHeadersResponse" type="tns:getAllContestHeadersResponse"/>
    <xsd:element name="getAllContestTypes" type="tns:getAllContestTypes"/>
    <xsd:element name="getAllContestTypesResponse" type="tns:getAllContestTypesResponse"/>
    <xsd:element name="getAllContests" type="tns:getAllContests"/>
    <xsd:element name="getAllContestsResponse" type="tns:getAllContestsResponse"/>
    <xsd:element name="getAllDocumentTypes" type="tns:getAllDocumentTypes"/>
    <xsd:element name="getAllDocumentTypesResponse" type="tns:getAllDocumentTypesResponse"/>
    <xsd:element name="getAllMediums" type="tns:getAllMediums"/>
    <xsd:element name="getAllMediumsResponse" type="tns:getAllMediumsResponse"/>
    <xsd:element name="getAllStudioFileTypes" type="tns:getAllStudioFileTypes"/>
    <xsd:element name="getAllStudioFileTypesResponse" type="tns:getAllStudioFileTypesResponse"/>
    <xsd:element name="getChangeHistory" type="tns:getChangeHistory"/>
    <xsd:element name="getChangeHistoryResponse" type="tns:getChangeHistoryResponse"/>
    <xsd:element name="getContest" type="tns:getContest"/>
    <xsd:element name="getContestDataOnly" type="tns:getContestDataOnly"/>
    <xsd:element name="getContestDataOnlyByPID" type="tns:getContestDataOnlyByPID"/>
    <xsd:element name="getContestDataOnlyByPIDResponse" type="tns:getContestDataOnlyByPIDResponse"/>
    <xsd:element name="getContestDataOnlyResponse" type="tns:getContestDataOnlyResponse"/>
    <xsd:element name="getContestPayment" type="tns:getContestPayment"/>
    <xsd:element name="getContestPaymentResponse" type="tns:getContestPaymentResponse"/>
    <xsd:element name="getContestResponse" type="tns:getContestResponse"/>
    <xsd:element name="getContestsForClient" type="tns:getContestsForClient"/>
    <xsd:element name="getContestsForClientResponse" type="tns:getContestsForClientResponse"/>
    <xsd:element name="getContestsForProject" type="tns:getContestsForProject"/>
    <xsd:element name="getContestsForProjectResponse" type="tns:getContestsForProjectResponse"/>
    <xsd:element name="getLatestChanges" type="tns:getLatestChanges"/>
    <xsd:element name="getLatestChangesResponse" type="tns:getLatestChangesResponse"/>
    <xsd:element name="getMimeTypeId" type="tns:getMimeTypeId"/>
    <xsd:element name="getMimeTypeIdResponse" type="tns:getMimeTypeIdResponse"/>
    <xsd:element name="getSimpleContestData" type="tns:getSimpleContestData"/>
    <xsd:element name="getSimpleContestDataByPID" type="tns:getSimpleContestDataByPID"/>
    <xsd:element name="getSimpleContestDataByPIDResponse" type="tns:getSimpleContestDataByPIDResponse"/>
    <xsd:element name="getSimpleContestDataResponse" type="tns:getSimpleContestDataResponse"/>
    <xsd:element name="getSimpleProjectContestData" type="tns:getSimpleProjectContestData"/>
    <xsd:element name="getSimpleProjectContestDataByPID" type="tns:getSimpleProjectContestDataByPID"/>
    <xsd:element name="getSimpleProjectContestDataByPIDResponse" type="tns:getSimpleProjectContestDataByPIDResponse"/>
    <xsd:element name="getSimpleProjectContestDataResponse" type="tns:getSimpleProjectContestDataResponse"/>
    <xsd:element name="getStatusList" type="tns:getStatusList"/>
    <xsd:element name="getStatusListResponse" type="tns:getStatusListResponse"/>
    <xsd:element name="getSubmissionFileTypes" type="tns:getSubmissionFileTypes"/>
    <xsd:element name="getSubmissionFileTypesResponse" type="tns:getSubmissionFileTypesResponse"/>
    <xsd:element name="markForPurchase" type="tns:markForPurchase"/>
    <xsd:element name="markForPurchaseResponse" type="tns:markForPurchaseResponse"/>
    <xsd:element name="persistence_fault">
        <xsd:complexType>
            <xsd:sequence>
                <xsd:element name="persistence_message" type="xs:string"/>
            </xsd:sequence>
        </xsd:complexType>
    </xsd:element>
    <xsd:element name="processContestPayment" type="tns:processContestPayment"/>
    <xsd:element name="processContestPaymentResponse" type="tns:processContestPaymentResponse"/>
    <xsd:element name="processContestPayment_old" type="tns:processContestPayment_old"/>
    <xsd:element name="processContestPayment_oldResponse" type="tns:processContestPayment_oldResponse"/>
    <xsd:element name="processMissingPayments" type="tns:processMissingPayments"/>
    <xsd:element name="processMissingPaymentsResponse" type="tns:processMissingPaymentsResponse"/>
    <xsd:element name="processSubmissionPayment" type="tns:processSubmissionPayment"/>
    <xsd:element name="processSubmissionPaymentResponse" type="tns:processSubmissionPaymentResponse"/>
    <xsd:element name="project_not_found_fault">
        <xsd:complexType>
            <xsd:sequence>
                <xsd:element name="project_not_found" type="xs:long"/>
            </xsd:sequence>
        </xsd:complexType>
    </xsd:element>
    <xsd:element name="purchaseSubmission" type="tns:purchaseSubmission"/>
    <xsd:element name="purchaseSubmissionResponse" type="tns:purchaseSubmissionResponse"/>
    <xsd:element name="removeContestPayment" type="tns:removeContestPayment"/>
    <xsd:element name="removeContestPaymentResponse" type="tns:removeContestPaymentResponse"/>
    <xsd:element name="removeDocument" type="tns:removeDocument"/>
    <xsd:element name="removeDocumentFromContest" type="tns:removeDocumentFromContest"/>
    <xsd:element name="removeDocumentFromContestResponse" type="tns:removeDocumentFromContestResponse"/>
    <xsd:element name="removeDocumentResponse" type="tns:removeDocumentResponse"/>
    <xsd:element name="removeSubmission" type="tns:removeSubmission"/>
    <xsd:element name="removeSubmissionResponse" type="tns:removeSubmissionResponse"/>
    <xsd:element name="retrieveAllSubmissionsByMember" type="tns:retrieveAllSubmissionsByMember"/>
    <xsd:element name="retrieveAllSubmissionsByMemberResponse" type="tns:retrieveAllSubmissionsByMemberResponse"/>
    <xsd:element name="retrieveSubmission" type="tns:retrieveSubmission"/>
    <xsd:element name="retrieveSubmissionResponse" type="tns:retrieveSubmissionResponse"/>
    <xsd:element name="retrieveSubmissionsForContest" type="tns:retrieveSubmissionsForContest"/>
    <xsd:element name="retrieveSubmissionsForContestResponse" type="tns:retrieveSubmissionsForContestResponse"/>
    <xsd:element name="searchContests" type="tns:searchContests"/>
    <xsd:element name="searchContestsResponse" type="tns:searchContestsResponse"/>
    <xsd:element name="setSubmissionPlacement" type="tns:setSubmissionPlacement"/>
    <xsd:element name="setSubmissionPlacementResponse" type="tns:setSubmissionPlacementResponse"/>
    <xsd:element name="setSubmissionPrize" type="tns:setSubmissionPrize"/>
    <xsd:element name="setSubmissionPrizeResponse" type="tns:setSubmissionPrizeResponse"/>
    <xsd:element name="status_not_allowed_fault">
        <xsd:complexType>
            <xsd:sequence>
                <xsd:element name="status_not_found" type="xs:long"/>
            </xsd:sequence>
        </xsd:complexType>
    </xsd:element>
    <xsd:element name="status_not_found_fault">
        <xsd:complexType>
            <xsd:sequence>
                <xsd:element name="status_not_found" type="xs:long"/>
            </xsd:sequence>
        </xsd:complexType>
    </xsd:element>
    <xsd:element name="updateContest" type="tns:updateContest"/>
    <xsd:element name="updateContestResponse" type="tns:updateContestResponse"/>
    <xsd:element name="updateContestStatus" type="tns:updateContestStatus"/>
    <xsd:element name="updateContestStatusResponse" type="tns:updateContestStatusResponse"/>
    <xsd:element name="updateSubmission" type="tns:updateSubmission"/>
    <xsd:element name="updateSubmissionResponse" type="tns:updateSubmissionResponse"/>
    <xsd:element name="uploadDocument" type="tns:uploadDocument"/>
    <xsd:element name="uploadDocumentForContest" type="tns:uploadDocumentForContest"/>
    <xsd:element name="uploadDocumentForContestResponse" type="tns:uploadDocumentForContestResponse"/>
    <xsd:element name="uploadDocumentResponse" type="tns:uploadDocumentResponse"/>
    <xsd:element name="user_not_authorized_fault">
        <xsd:complexType>
            <xsd:sequence>
                <xsd:element name="user_not_authorized" type="xs:long"/>
            </xsd:sequence>
        </xsd:complexType>
    </xsd:element>
    <xsd:complexType name="createContest">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="arg0" type="tns:studioCompetition"/>
            <xsd:element name="arg1" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="studioCompetition">
        <xsd:complexContent>
            <xsd:extension base="tns:competition">
                <xsd:sequence>
                    <xsd:element name="id" type="xs:long"/>
                    <xsd:element minOccurs="0" name="contestData" type="tns:contestData"/>
                    <xsd:element minOccurs="0" name="type" type="tns:competionType"/>
                </xsd:sequence>
            </xsd:extension>
        </xsd:complexContent>
    </xsd:complexType>
    <xsd:complexType abstract="true" name="competition">
        <xsd:sequence>
            <xsd:element name="adminFee" type="xs:double"/>
            <xsd:element minOccurs="0" name="competitionId" type="xs:long"/>
            <xsd:element name="creatorUserId" type="xs:long"/>
            <xsd:element name="drPoints" type="xs:double"/>
            <xsd:element minOccurs="0" name="eligibility" type="xs:string"/>
            <xsd:element minOccurs="0" name="endTime" type="xs:anySimpleType"/>
            <xsd:element name="id" type="xs:long"/>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="prizes" type="tns:competitionPrize"/>
            <xsd:element minOccurs="0" name="project" type="tns:project"/>
            <xsd:element minOccurs="0" name="shortSummary" type="xs:string"/>
            <xsd:element minOccurs="0" name="startTime" type="xs:anySimpleType"/>
            <xsd:element minOccurs="0" name="type" type="tns:competionType"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="contestData">
        <xsd:sequence>
            <xsd:element name="contestId" type="xs:long"/>
            <xsd:element minOccurs="0" name="name" type="xs:string"/>
            <xsd:element name="projectId" type="xs:long"/>
            <xsd:element name="tcDirectProjectId" type="xs:long"/>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="prizes" type="tns:prizeData"/>
            <xsd:element minOccurs="0" name="launchDateAndTime" type="xs:anySimpleType"/>
            <xsd:element minOccurs="0" name="winnerAnnoucementDeadline" type="xs:anySimpleType"/>
            <xsd:element name="durationInHours" type="xs:double"/>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="documentationUploads" type="tns:uploadedDocument"/>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="contestPayloads" type="tns:contestPayload"/>
            <xsd:element minOccurs="0" name="shortSummary" type="xs:string"/>
            <xsd:element minOccurs="0" name="contestDescriptionAndRequirements" type="xs:string"/>
            <xsd:element minOccurs="0" name="requiredOrRestrictedColors" type="xs:string"/>
            <xsd:element minOccurs="0" name="requiredOrRestrictedFonts" type="xs:string"/>
            <xsd:element minOccurs="0" name="sizeRequirements" type="xs:string"/>
            <xsd:element minOccurs="0" name="otherRequirementsOrRestrictions" type="xs:string"/>
            <xsd:element name="creatorUserId" type="xs:long"/>
            <xsd:element minOccurs="0" name="finalFileFormat" type="xs:string"/>
            <xsd:element minOccurs="0" name="otherFileFormats" type="xs:string"/>
            <xsd:element name="statusId" type="xs:long"/>
            <xsd:element name="detailedStatusId" type="xs:long"/>
            <xsd:element name="submissionCount" type="xs:long"/>
            <xsd:element name="contestTypeId" type="xs:long"/>
            <xsd:element name="contestChannelId" type="xs:long"/>
            <xsd:element minOccurs="0" name="eligibility" type="xs:string"/>
            <xsd:element minOccurs="0" name="notesOnWinnerSelection" type="xs:string"/>
            <xsd:element minOccurs="0" name="prizeDescription" type="xs:string"/>
            <xsd:element name="forumPostCount" type="xs:int"/>
            <xsd:element name="forumId" type="xs:long"/>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="media" type="tns:mediumData"/>
            <xsd:element name="drPoints" type="xs:double"/>
            <xsd:element name="contestAdministrationFee" type="xs:double"/>
            <xsd:element name="launchImmediately" type="xs:boolean"/>
            <xsd:element name="requiresPreviewImage" type="xs:boolean"/>
            <xsd:element name="requiresPreviewFile" type="xs:boolean"/>
            <xsd:element name="maximumSubmissions" type="xs:long"/>
            <xsd:element name="numberOfRegistrants" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="prizeData">
        <xsd:sequence>
            <xsd:element name="amount" type="xs:double"/>
            <xsd:element name="place" type="xs:int"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="uploadedDocument">
        <xsd:sequence>
            <xsd:element name="documentId" type="xs:long"/>
            <xsd:element name="contestId" type="xs:long"/>
            <xsd:element minOccurs="0" name="file" type="xs:base64Binary"/>
            <xsd:element minOccurs="0" name="description" type="xs:string"/>
            <xsd:element minOccurs="0" name="fileName" type="xs:string"/>
            <xsd:element name="documentTypeId" type="xs:long"/>
            <xsd:element name="mimeTypeId" type="xs:long"/>
            <xsd:element minOccurs="0" name="path" type="xs:string"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="contestPayload">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="name" type="xs:string"/>
            <xsd:element minOccurs="0" name="value" type="xs:string"/>
            <xsd:element name="required" type="xs:boolean"/>
            <xsd:element minOccurs="0" name="description" type="xs:string"/>
            <xsd:element name="contestTypeId" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="mediumData">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="mediumId" type="xs:long"/>
            <xsd:element minOccurs="0" name="description" type="xs:string"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="competitionPrize">
        <xsd:sequence>
            <xsd:element name="amount" type="xs:double"/>
            <xsd:element name="place" type="xs:int"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="project">
        <xsd:complexContent>
            <xsd:extension base="tns:projectData">
                <xsd:sequence>
                    <xsd:element maxOccurs="unbounded" minOccurs="0" name="competitions" type="tns:competition"/>
                    <xsd:element minOccurs="0" name="createDate" type="xs:dateTime"/>
                    <xsd:element minOccurs="0" name="modifyDate" type="xs:dateTime"/>
                    <xsd:element name="userId" type="xs:long"/>
                </xsd:sequence>
            </xsd:extension>
        </xsd:complexContent>
    </xsd:complexType>
    <xsd:complexType name="projectData">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="description" type="xs:string"/>
            <xsd:element minOccurs="0" name="name" type="xs:string"/>
            <xsd:element minOccurs="0" name="projectId" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="createContestResponse">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="return" type="tns:studioCompetition"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getContest">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getContestResponse">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="return" type="tns:studioCompetition"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getContestsForProject">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getContestsForProjectResponse">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="return" type="tns:studioCompetition"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="updateContest">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="arg0" type="tns:studioCompetition"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="updateContestResponse">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="updateContestStatus">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
            <xsd:element name="arg1" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="updateContestStatusResponse">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="removeDocument">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="removeDocumentResponse">
        <xsd:sequence>
            <xsd:element name="return" type="xs:boolean"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="addDocumentToContest">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
            <xsd:element name="arg1" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="addDocumentToContestResponse">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="removeDocumentFromContest">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="arg0" type="tns:uploadedDocument"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="removeDocumentFromContestResponse">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="getAllStudioFileTypes">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="getAllStudioFileTypesResponse">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="return" type="tns:studioFileType"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="studioFileType">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="description" type="xs:string"/>
            <xsd:element minOccurs="0" name="extension" type="xs:string"/>
            <xsd:element minOccurs="0" name="imageFile" type="xs:boolean"/>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="mimeTypes" type="tns:mimeType"/>
            <xsd:element minOccurs="0" name="sort" type="xs:int"/>
            <xsd:element name="studioFileType" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="mimeType">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="description" type="xs:string"/>
            <xsd:element minOccurs="0" name="mimeTypeId" type="xs:long"/>
            <xsd:element minOccurs="0" name="studioFileType" type="tns:studioFileType"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getAllDocumentTypes">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="getAllDocumentTypesResponse">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="return" type="tns:documentType"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="documentType">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="description" type="xs:string"/>
            <xsd:element minOccurs="0" name="documentTypeId" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getAllContests">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="getAllContestsResponse">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="return" type="tns:studioCompetition"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getSimpleContestData">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="getSimpleContestDataResponse">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="return" type="tns:simpleContestData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="simpleContestData">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="contestId" type="xs:long"/>
            <xsd:element minOccurs="0" name="endDate" type="xs:dateTime"/>
            <xsd:element minOccurs="0" name="name" type="xs:string"/>
            <xsd:element name="num_reg" type="xs:int"/>
            <xsd:element name="num_sub" type="xs:int"/>
            <xsd:element minOccurs="0" name="sname" type="xs:string"/>
            <xsd:element minOccurs="0" name="startDate" type="xs:dateTime"/>
            <xsd:element name="statusId" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getSimpleProjectContestData">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="getSimpleProjectContestDataResponse">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="return" type="tns:simpleProjectContestData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="simpleProjectContestData">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="cname" type="xs:string"/>
            <xsd:element minOccurs="0" name="contestId" type="xs:long"/>
            <xsd:element minOccurs="0" name="description" type="xs:string"/>
            <xsd:element minOccurs="0" name="endDate" type="xs:dateTime"/>
            <xsd:element minOccurs="0" name="forumId" type="xs:int"/>
            <xsd:element name="num_for" type="xs:int"/>
            <xsd:element name="num_reg" type="xs:int"/>
            <xsd:element name="num_sub" type="xs:int"/>
            <xsd:element minOccurs="0" name="pname" type="xs:string"/>
            <xsd:element minOccurs="0" name="projectId" type="xs:long"/>
            <xsd:element minOccurs="0" name="sname" type="xs:string"/>
            <xsd:element minOccurs="0" name="startDate" type="xs:dateTime"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getContestDataOnly">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="getContestDataOnlyResponse">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="return" type="tns:simpleContestData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="searchContests">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="arg0" type="tns:contestServiceFilter"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="contestServiceFilter">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="searchContestsResponse">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="return" type="tns:studioCompetition"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getAllContestTypes">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="getAllContestTypesResponse">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="return" type="tns:contestTypeData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="contestTypeData">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="contestTypeId" type="xs:long"/>
            <xsd:element minOccurs="0" name="description" type="xs:string"/>
            <xsd:element name="requirePreviewFile" type="xs:boolean"/>
            <xsd:element name="requirePreviewImage" type="xs:boolean"/>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="config" type="tns:contestPayload"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="createContestPayment">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="arg0" type="tns:contestPaymentData"/>
            <xsd:element minOccurs="0" name="arg1" type="xs:string"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="contestPaymentData">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="contestId" type="xs:long"/>
            <xsd:element minOccurs="0" name="paymentStatusId" type="xs:long"/>
            <xsd:element minOccurs="0" name="price" type="xs:double"/>
            <xsd:element minOccurs="0" name="paypalOrderId" type="xs:string"/>
            <xsd:element minOccurs="0" name="createDate" type="xs:dateTime"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="createContestPaymentResponse">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="return" type="tns:contestPaymentData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getContestPayment">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getContestPaymentResponse">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="return" type="tns:contestPaymentData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="editContestPayment">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="arg0" type="tns:contestPaymentData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="editContestPaymentResponse">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="removeContestPayment">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="removeContestPaymentResponse">
        <xsd:sequence>
            <xsd:element name="return" type="xs:boolean"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="addChangeHistory">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="arg0" type="tns:changeHistoryData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="changeHistoryData">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="contestId" type="xs:long"/>
            <xsd:element minOccurs="0" name="fieldName" type="xs:string"/>
            <xsd:element minOccurs="0" name="newData" type="xs:string"/>
            <xsd:element minOccurs="0" name="oldData" type="xs:string"/>
            <xsd:element minOccurs="0" name="timestamp" type="xs:anySimpleType"/>
            <xsd:element minOccurs="0" name="transactionId" type="xs:long"/>
            <xsd:element name="userAdmin" type="xs:boolean"/>
            <xsd:element minOccurs="0" name="userName" type="xs:string"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="addChangeHistoryResponse">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="getChangeHistory">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getChangeHistoryResponse">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="return" type="tns:changeHistoryData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="deleteContest">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="deleteContestResponse">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="uploadDocumentForContest">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="arg0" type="tns:uploadedDocument"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="uploadDocumentForContestResponse">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="return" type="tns:uploadedDocument"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="retrieveSubmissionsForContest">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="retrieveSubmissionsForContestResponse">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="return" type="tns:submissionData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="submissionData">
        <xsd:sequence>
            <xsd:element name="submissionId" type="xs:long"/>
            <xsd:element name="submitterId" type="xs:long"/>
            <xsd:element name="contestId" type="xs:long"/>
            <xsd:element minOccurs="0" name="submittedDate" type="xs:anySimpleType"/>
            <xsd:element minOccurs="0" name="submissionContent" type="xs:string"/>
            <xsd:element name="passedScreening" type="xs:boolean"/>
            <xsd:element name="placement" type="xs:int"/>
            <xsd:element name="paidFor" type="xs:boolean"/>
            <xsd:element name="price" type="xs:double"/>
            <xsd:element name="markedForPurchase" type="xs:boolean"/>
            <xsd:element minOccurs="0" name="rank" type="xs:int"/>
            <xsd:element name="removed" type="xs:boolean"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="retrieveAllSubmissionsByMember">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="retrieveAllSubmissionsByMemberResponse">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="return" type="tns:submissionData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="updateSubmission">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="arg0" type="tns:submissionData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="updateSubmissionResponse">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="removeSubmission">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="removeSubmissionResponse">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="getStatusList">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="getStatusListResponse">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="return" type="tns:contestStatusData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="contestStatusData">
        <xsd:sequence>
            <xsd:element name="statusId" type="xs:long"/>
            <xsd:element minOccurs="0" name="name" type="xs:string"/>
            <xsd:element minOccurs="0" name="description" type="xs:string"/>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="allowableNextStatus" type="xs:long"/>
            <xsd:element minOccurs="0" name="displayIcon" type="xs:string"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getSubmissionFileTypes">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="getSubmissionFileTypesResponse">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="return" type="xs:string"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="uploadDocument">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="arg0" type="tns:uploadedDocument"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="uploadDocumentResponse">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="return" type="tns:uploadedDocument"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getContestsForClient">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getContestsForClientResponse">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="return" type="tns:studioCompetition"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="retrieveSubmission">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="retrieveSubmissionResponse">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="return" type="tns:submissionData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getMimeTypeId">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="arg0" type="xs:string"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getMimeTypeIdResponse">
        <xsd:sequence>
            <xsd:element name="return" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="purchaseSubmission">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
            <xsd:element minOccurs="0" name="arg1" type="xs:string"/>
            <xsd:element minOccurs="0" name="arg2" type="xs:string"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="purchaseSubmissionResponse">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="getAllMediums">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="getAllMediumsResponse">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="return" type="tns:mediumData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="setSubmissionPlacement">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
            <xsd:element name="arg1" type="xs:int"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="setSubmissionPlacementResponse">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="markForPurchase">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="markForPurchaseResponse">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="getLatestChanges">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getLatestChangesResponse">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="return" type="tns:changeHistoryData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getAllContestHeaders">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="getAllContestHeadersResponse">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="return" type="tns:studioCompetition"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="processMissingPayments">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="processMissingPaymentsResponse">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="getSimpleProjectContestDataByPID">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getSimpleProjectContestDataByPIDResponse">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="return" type="tns:simpleProjectContestData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getSimpleContestDataByPID">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getSimpleContestDataByPIDResponse">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="return" type="tns:simpleContestData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getContestDataOnlyByPID">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="getContestDataOnlyByPIDResponse">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="return" type="tns:simpleContestData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="setSubmissionPrize">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
            <xsd:element name="arg1" type="xs:long"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="setSubmissionPrizeResponse">
        <xsd:sequence/>
    </xsd:complexType>
    <xsd:complexType name="processContestPayment_old">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="arg0" type="tns:contestData"/>
            <xsd:element minOccurs="0" name="arg1" type="tns:paymentData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType abstract="true" name="paymentData">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="type" type="tns:paymentType"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="creditCardPaymentData">
        <xsd:complexContent>
            <xsd:extension base="tns:paymentData">
                <xsd:sequence>
                    <xsd:element minOccurs="0" name="address" type="xs:string"/>
                    <xsd:element minOccurs="0" name="amount" type="xs:string"/>
                    <xsd:element minOccurs="0" name="cardExpiryMonth" type="xs:string"/>
                    <xsd:element minOccurs="0" name="cardExpiryYear" type="xs:string"/>
                    <xsd:element minOccurs="0" name="cardNumber" type="xs:string"/>
                    <xsd:element minOccurs="0" name="cardType" type="xs:string"/>
                    <xsd:element minOccurs="0" name="city" type="xs:string"/>
                    <xsd:element minOccurs="0" name="country" type="xs:string"/>
                    <xsd:element minOccurs="0" name="email" type="xs:string"/>
                    <xsd:element minOccurs="0" name="firstName" type="xs:string"/>
                    <xsd:element minOccurs="0" name="ipAddress" type="xs:string"/>
                    <xsd:element minOccurs="0" name="lastName" type="xs:string"/>
                    <xsd:element minOccurs="0" name="phone" type="xs:string"/>
                    <xsd:element minOccurs="0" name="sessionId" type="xs:string"/>
                    <xsd:element minOccurs="0" name="state" type="xs:string"/>
                    <xsd:element minOccurs="0" name="zipCode" type="xs:string"/>
                </xsd:sequence>
            </xsd:extension>
        </xsd:complexContent>
    </xsd:complexType>
    <xsd:complexType name="tcPurhcaseOrderPaymentData">
        <xsd:complexContent>
            <xsd:extension base="tns:paymentData">
                <xsd:sequence>
                    <xsd:element minOccurs="0" name="poNumber" type="xs:string"/>
                </xsd:sequence>
            </xsd:extension>
        </xsd:complexContent>
    </xsd:complexType>
    <xsd:complexType name="processContestPayment_oldResponse">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="return" type="tns:paymentResult"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="paymentResult">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="referenceNumber" type="xs:string"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="PaymentException">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="errorCode" type="xs:string"/>
            <xsd:element minOccurs="0" name="message" type="xs:string"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="processContestPayment">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="arg0" type="tns:studioCompetition"/>
            <xsd:element minOccurs="0" name="arg1" type="tns:paymentData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="processContestPaymentResponse">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="return" type="tns:paymentResult"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="processSubmissionPayment">
        <xsd:sequence>
            <xsd:element name="arg0" type="xs:long"/>
            <xsd:element minOccurs="0" name="arg1" type="tns:paymentData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="processSubmissionPaymentResponse">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="return" type="tns:paymentResult"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="do_1">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="arg0" type="tns:paymentData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="do_1Response">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="return" type="tns:paymentResult"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="do_2">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="arg0" type="tns:studioCompetition"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="do_2Response">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="return" type="tns:paymentResult"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="do_3">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="arg0" type="tns:contestData"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="do_3Response">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="return" type="tns:paymentResult"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:simpleType name="competionType">
        <xsd:restriction base="xs:string">
            <xsd:enumeration value="STUDIO"/>
        </xsd:restriction>
    </xsd:simpleType>
    <xsd:simpleType name="paymentType">
        <xsd:restriction base="xs:string">
            <xsd:enumeration value="PayPalCreditCard"/>
            <xsd:enumeration value="TCPurchaseOrder"/>
        </xsd:restriction>
    </xsd:simpleType>
</xsd:schema>
;
			 var xsdSchema0:Schema = new Schema(xsdXML0);
			schemas.push(xsdSchema0);
			targetNamespaces.push(new Namespace('','http://ejb.contest.facade.service.topcoder.com/'));
		}
	}
}