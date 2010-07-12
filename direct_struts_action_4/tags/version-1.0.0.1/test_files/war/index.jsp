<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<html>
  <head>
    <sx:head/>
  </head>
  <body>
    <h1>Welcome to Direct Struts Action 4 Demo</h1>
    <hr>
    <h5>ContestRepostingAction</h5>
    <s:form action="ContestRepostingAction">
          <s:textfield key="contestId"/>
          <s:submit/>
    </s:form>
    
    <hr>
    <h5>ContestDeletingAction</h5>
    <s:form action="ContestDeletingAction">
        <s:textfield key="contestId"/>
        <s:textfield key="studio" value="true"/>
        <s:submit/>
    </s:form>
    
    <hr>
    <h5>ContestLinksRetrievalAction</h5>
        <s:form action="ContestLinksRetrievalAction">
          <s:textfield key="contestId"/>
          <s:submit/>
    </s:form>
    
    <hr>
    <h5>ContestsLinkingAction</h5>
    <s:form action="ContestsLinkingAction">
          <s:textfield key="contestId"/>
          <s:textfield key="parentProjectIds" value="-1"/>
          <s:textfield key="childProjectIds"/>
          <s:submit/>
    </s:form>
        
    <hr>
    <h5>NewComponentVersionCreationAction</h5>
        <s:form action="NewComponentVersionCreationAction">
          <s:textfield key="contestId"/>
          <s:submit/>
    </s:form>
    
    <hr>
    <h5>ProjectBudgetModifyingAction</h5>
        <s:form action="ProjectBudgetModifyingAction">
          <s:textfield key="billingProjectId" />
          <s:textfield key="changedAmount" value="1.0"/>
          <s:submit/>
    </s:form>
    
    <hr>
    <h5>SpecificationReviewResultRetrievalAction</h5>
        <s:form action="SpecificationReviewResultRetrievalAction">
          <s:textfield key="contestId"/>
          <s:submit/>
    </s:form>
    
    <hr>
    <h5>SpecificationReviewStartingAction</h5>
        <s:form action="SpecificationReviewStartingAction">
          <s:textfield key="contestId"/>
          <s:submit/>
    </s:form>
    
    
    
  </body>
</html>


