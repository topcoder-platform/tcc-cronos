<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Help</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="sponsor" scope="page"/>
    <c:set var="subbar2" value="help-sponsor" scope="page"/>
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap2">
        <p><img src="${ctx}/i/help/img_create.gif" alt="How to Play" width="320" height="18"/></p>

        <p>Note: You will need at least one domain on whose behalf you will be bidding and at least one image for each
            domain (of size approximately 240-300 x 300-400 pixels), in order to set up a sponsor account.</p>
        <ol start="1" type="1">
            <li>Go to <a href="sponsor.jsp">http://www.theball.com/public/sponsor.jsp</a>
                to start the registration process to become a sponsor.&nbsp; Hint: Do not use the back button at any
                time. If you make an error during registration, please re-start the registration process with a
                different email address or contact <a href="mailto:${orpheus:getContactEmailAddress()}">us</a> for
                further assistance.
            </li>
        </ol>
        <blockquote>
            <p>You may create as many sponsor accounts as you wish, however each must have a unique email address.&nbsp;
                Each sponsor account can bid on behalf of up to five (5) different domains.</p>

            <p align="center"><img src="${ctx}/i/help/create_01.jpg" width="350" height="270" alt=""/></p>
        </blockquote>
        <ol start="2">
            <li>Click on &ldquo;Get in the Game&rdquo; or the &ldquo;Sponsor A Game&rdquo; tab at the top</li>
        </ol>
        <blockquote>
            <p align="center"><img src="${ctx}/i/help/create_02.jpg" width="350" height="270" alt=""/></p>
        </blockquote>
        <ol start="3">
            <li>Read through the Terms &amp; Conditions and click on the &ldquo;I Agree&rdquo; button to continue.</li>
        </ol>
        <blockquote>
            <p align="center"><img src="${ctx}/i/help/create_03.jpg" width="350" height="270" alt=""/></p>
        </blockquote>
        <ol start="4">
            <li>You will be presented with a form to fill in.&nbsp; Please use a unique email address when filling in
                the form Click &ldquo;Continue&rdquo; when done.
            </li>
        </ol>
        <blockquote>
            <p align="center"><img src="${ctx}/i/help/create_04.jpg" width="350" height="270" alt=""/></p>
        </blockquote>
        <ol start="5">
            <li>Enter the domain(s) on whose behalf you would like to bid and at least one image for each domain (size
                between 250-300 x 300-400 pixels).&nbsp; Note: use <a href="http://www.theball.com/">www.theball.com</a>
                format ie. no &ldquo;http//&rdquo; (not <a href="http://www.theball.com/">http://www.theball.com</a>) .
                You may enter up to five domains,
            </li>
        </ol>
        <blockquote>
            <p align="center"><img src="${ctx}/i/help/create_05.jpg" width="350" height="270" alt=""/></p>
        </blockquote>
        <ol start="6">
            <li>Click save and finish when you are done</li>
        </ol>
        <blockquote>
            <p align="center"><img src="${ctx}/i/help/create_06.jpg" width="350" height="270" alt=""/></p>
        </blockquote>
        <p align="center">&nbsp;</p>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>