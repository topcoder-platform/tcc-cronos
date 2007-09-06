<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Sponsor A Game :: FAQ</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="sponsor" scope="page"/>
    <c:set var="subbar2" value="help-sponsor" scope="page"/>
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <h1><img src="${ctx}/i/h/title_biddingfaq.gif" alt="Bidding FAQ"/></h1>
        <ol>
            <li><a href="#1">What are the benefits of sponsorship?</a></li>
            <li><a href="#2">How do I create a Sponsor account?</a></li>
            <li><a href="#3">What does it take to get verified as a Sponsor?</a></li>
            <li><a href="#4">How do I bid to host the ball</a></li>
            <li><a href="#5">How does the Sponsorship Auction work?</a></li>
        </ol>

        <hr class="grayline"/>
        <ol>
            <li><a name="1"></a><span class="faq">What are the benefits of sponsorship?</span><br/>
                The primary benefit of hosting The Ball is increased traffic to your Web site. And unlike other forms of
                Web advertising, hosting The Ball guarantees that visitors will actually pay attention to your content.
                Simply put, they have to -- to win, players need to identify specific keywords and phrases on your site
                in order to progress to the next clue.  As well, if a player finds the ball on your site, the puzzle
                that is then presented to the player incorporates an image that you submit to us when you sign-up for
                your account. We suggest that company or product logos (approximately 200 x 200 pixels) are ideal images
                to use, because it provides additional branding opportunities.<br/>
                In addition, your site gets the positive, brand-building buzz of association with a new, innovative and
                fun way to explore the Web.
                <br/>&nbsp;</li>

            <li><a name="2"></a><span class="faq">How do I create a Sponsor account?</span><br/>
                You can create a sponsor account
                <a href="${ctx}/public/sponsorRegistration.jsp?phase=start&nextPhase=step1">here</a>. There are a few
                rules to observe when creating sponsor accounts:
                <ul>
                    <li>Do <i>not</i> use the back button at any time. If you make an error during registration, please
                        start the process over or <a href="mailto:${orpheus:getContactEmailAddress()}">contact us</a>
                        for further instructions.</li>
                    <li>Do not use http:// when entering domains on whose behalf you would like to bid (e.g. Do not
                        enter http://www.topcoder.com, but enter www.topcoder.com instead)</li>
                    <li> Each domain that you submit to us should have at least one (1) associated image which may be
                        used during puzzle creation and display to players. Ideal image size should be approximately
                        200 x 200 pixels.</li>
                    <li>Images and domain will be vetted for content, size, suitability, etc and may be rejected at the
                        discretion of the administrator.</li>
                    <li>You may create more than one sponsor account, but each account requires a unique email address
                        and will be verified independently.</li>
                </ul>

                <br/>&nbsp;</li>

            <li><a name="3"></a><span class="faq">What does it take to get verified as a Sponsor?</span><br/>
                Before you can sponsor The Ball on your Web site, we will verify that you are either the owner of the
                domain on whose behalf you are bidding, or that you are bidding on behalf of (and with the permission
                of) that owner. We may need to contact you to finalize this verification process - to help this process
                go as smoothly as possible, please ensure that the contact information in your profile is up-to-date and
                accurate. 
                <br/>&nbsp;</li>

            <li><a name="4"></a><span class="faq">How do I create a Sponsor account?</span><br/>
                Once you have registered for an account, you must be approved by a game administrator before you are
                able to bid to host the ball. Once you have been contacted and approved by an administrator you may
                begin bidding by logging into the Ball’s website. You do not need to specify that you are a sponsor when
                logging into our website, your credentials will provide sufficient authorization and you will be
                automatically directed to any auctions that in progress and for which you are eligible to bid.
                <br/>&nbsp;</li>

            <li><a name="5"></a><span class="faq">How does the Sponsorship Auction work?</span><br/>
                Sponsorship Auctions follow a standard auction format. During the course of the auction, you can
                manually enter bids (with a minimum bid of $5 or with a bid that is at least $1 higher than the current
                winning bid) or you may set a maximum bid and allow the system to automatically raise your bids as
                appropriate, based on other sponsor’s bids. Note: other rival sponsors may have maximum bids in the
                system, and as we will proxy bid on their behalf as well, the minimum bid stated on the Bid Details page
                may not be sufficient to guarantee a winning slot. Furthermore, once you have placed an apparent winning
                bid, it is possible to be shortly thereafter outbid by a rival sponsor who has a higher max bid in the
                system.
                <br/>&nbsp;</li>
        </ol>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>