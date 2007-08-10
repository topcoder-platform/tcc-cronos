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
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <p><img src="${ctx}/i/help/img_auction_bidding.gif" alt="How to Play" width="320" height="18"/></p>
        <ol start="1" type="1">
            <li>Once you have been registered for an account, a member of the Ball&rsquo;s Team will contact you to
                verify that you are authorized to bid on behalf of the domains on your list within ten business days.
            </li>
        </ol>
        <ol start="2" type="1">
            <li>Once your account is approved, you may bid in any of our open auctions by logging into the website at
                http://www.theball.com.&nbsp; </li>
        </ol>
        <p align="center"><img src="${ctx}/i/help/auction_01.jpg" width="350" height="270" alt=""/></p>
        <blockquote>&nbsp;</blockquote>
        <ol start="3">
            <li>Logging in with your sponsor account information will automatically direct you to the Auction Bidding
                page on the website. Note:
            </li>
        </ol>
        <ul>
            <li>Each auction is made up of Blocks and Slots.&nbsp; </li>
            <li>Each slot within a block represents a portion of time for which a site will be hosting the
                ball.&nbsp; </li>
            <li>You may bid to win up to half the amounts of slots within one block, on behalf of the domains for which
                are you the authorized sponsor.&nbsp; </li>
            <li>You may place bids for slots in as many blocks as you would like.</li>
        </ul>
        <blockquote>
            <p align="center"><img src="${ctx}/i/help/auction_02.jpg" width="350" height="270" alt=""/></p>
        </blockquote>
        <ol start="4">
            <li>When you place a bid for a particular slot, you can see all the competing bids for that slot.&nbsp; We
                have a proxy bid system in place, which allows you to place a &ldquo;max bid&rdquo; &ndash; this is the
                maximum amount you are willing to pay for that slot and we will bid on your behalf in a similar fashion
                to EBay.&nbsp; Note: because of the max bid feature, the competing bids that you see may not be a true
                picture of the bid you need to enter to become one of the winners for a slot within a block.
            </li>
        </ol>
        <blockquote>
            <p align="center"><img src="${ctx}/i/help/auction_03.jpg" width="350" height="270" alt=""/></p>
        </blockquote>
        <ol start="5">
            <li> You will be required to confirm your bid.</li>
        </ol>
        <blockquote>
            <p align="center"><img src="${ctx}/i/help/auction_04.jpg" width="350" height="270" alt=""/></p>
        </blockquote>
        <ol start="6">
            <li>If your bid is successful, you will notified accordingly:</li>
        </ol>
        <blockquote>
            <p align="center"><img src="${ctx}/i/help/auction_05.jpg" width="350" height="270" alt=""/></p>
        </blockquote>
        <p align="center">&nbsp;</p>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>