<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/registration" prefix="reg" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="phase" value="${param.phase}"/>
<c:set var="nextPhase" value="${param.nextPhase}"/>
<c:set var="registrationManager" value="${orpheus:getRegistrationManager()}"/>

<c:if test="${not empty phase and not empty nextPhase}">
    <reg:registration registrationManager="${registrationManager}" phase="${phase}" nextPhase="${nextPhase}">
        <reg:doAfterPhase type="Start">
            <reg:success>
                <jsp:forward page="${nextPage}"/>
            </reg:success>
            <reg:error>
                <c:set var="exception" value="${exception}" scope="request"/>
                <jsp:forward page="${errorPage}"/>
            </reg:error>
        </reg:doAfterPhase>
        <reg:doAfterPhase type="NextQuestionsPage">
            <reg:success>
                <jsp:forward page="${nextPage}"/>
            </reg:success>
            <reg:error>
                <c:set var="exception" value="${exception}" scope="request"/>
                <jsp:forward page="${errorPage}"/>
            </reg:error>
        </reg:doAfterPhase>
        <reg:doAfterPhase type="EmailValidationRequest">
            <reg:success>
                <jsp:forward page="${nextPage}"/>
            </reg:success>
            <reg:error>
                <c:set var="exception" value="${exception}" scope="request"/>
                <jsp:forward page="${errorPage}"/>
            </reg:error>
        </reg:doAfterPhase>
        <reg:doAfterPhase type="EmailConfirmationRequest">
            <reg:success>
                <jsp:forward page="${nextPage}"/>
            </reg:success>
            <reg:error>
                <c:set var="exception" value="${exception}" scope="request"/>
                <jsp:forward page="${errorPage}"/>
            </reg:error>
        </reg:doAfterPhase>
    </reg:registration>
</c:if>

<c:if test="${empty phase or empty nextPhase}">
    <reg:registration registrationManager="${registrationManager}">
        <reg:doAfterPhase type="Start">
            <reg:success>
                <jsp:forward page="${nextPage}"/>
            </reg:success>
            <reg:error>
                <c:set var="exception" value="${exception}" scope="request"/>
                <jsp:forward page="${errorPage}"/>
            </reg:error>
        </reg:doAfterPhase>
        <reg:doAfterPhase type="NextQuestionsPage">
            <reg:success>
                <jsp:forward page="${nextPage}"/>
            </reg:success>
            <reg:error>
                <c:set var="exception" value="${exception}" scope="request"/>
                <jsp:forward page="${errorPage}"/>
            </reg:error>
        </reg:doAfterPhase>
        <reg:doAfterPhase type="EmailValidationRequest">
            <reg:success>
                <jsp:forward page="${nextPage}"/>
            </reg:success>
            <reg:error>
                <c:set var="exception" value="${exception}" scope="request"/>
                <jsp:forward page="${errorPage}"/>
            </reg:error>
        </reg:doAfterPhase>
        <reg:doAfterPhase type="EmailConfirmationRequest">
            <reg:success>
                <jsp:forward page="${nextPage}"/>
            </reg:success>
            <reg:error>
                <c:set var="exception" value="${exception}" scope="request"/>
                <jsp:forward page="${errorPage}"/>
            </reg:error>
        </reg:doAfterPhase>
    </reg:registration>
</c:if>