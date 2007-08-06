<%@ page errorPage="/public/error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/dropdown" prefix="dd" %>
<%@ taglib uri="/orpheus_auction" prefix="auction" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:if test="${empty param['maxBid']}">
    <c:set var="maxBidValue" value="${bid.effectiveAmount}"/>
</c:if>
<c:if test="${not empty param['maxBid']}">
    <c:set var="maxBidValue" value="${param['maxBid']}"/>
</c:if>
<fmt:setLocale value="en_US"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Auctions :: Update a Bid</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/tabs.css"/>
    <script type="text/javascript" src="${ctx}/js/forms.js"></script>
</head>

<body id="page">
<div id="container">
<c:set var="subbar" value="auctions" scope="page"/>
<%@ include file="header.jsp" %>
<div id="wrap">
<h1><img src="${ctx}/i/h/title_increaseyourbid.gif" alt="Increse your Bid"/></h1>

<div id="breadcrumb">
    &raquo; 1. View Games with Available Timeslots &nbsp; &raquo; 2. Bid on a Timeslot &nbsp;
    <span class="active">&raquo; 3. Bid Confirmation</span></div>

Winning Bidders will receive a timeslot where their website will be host to the Ball during this game. Because a ball
could end before the ball makes it to this timeslot, payment will not be collected until the ball has arrived at your
website. For more information on how the auctions work, visit our
<a href="${ctx}/server/sponsor/showFAQ.do">Bidding FAQ.</a><br/><br/>

<div id="table-details" style="width:313px">
    <form action="${ctx}/server/sponsor/confirmBidUpdate.do" name="BidForm" id="BidForm" method="POST">
        <input type="hidden" name="gameId" value="${game.id}"/>
        <input type="hidden" name="auctionId" value="${auction.id}"/>
        <input type="hidden" name="blockId" value="${block.id}"/>
        <input type="hidden" name="domainId" value="${domain.id}"/>
        <input type="hidden" name="imageId" value="${image.id}"/>
        <input type="hidden" name="bidId" value="${bid.id}"/>
        <table border="0" cellpadding="0" cellspacing="0" width="313">
            ${auction:error('auctionId', validationErrors)}
            <tr>
                <th colspan="2">Auction #${auction.id}</th>
            </tr>
            <tr>
                <td class="bold" width="55%">Game:</td>
                <td width="45%">${game.name}</td>
            </tr>
            <tr>
                <td class="bold">Block Length:</td>
                <td>${auction:getMaxDuration(block)} (maximum)</td>
            </tr>
            <tr>
                <td class="bold">Auction Ends:</td>
                <td>${auction:getAuctionTimeLeft('', auction)}</td>
            </tr>
            <tr>
                <td class="bold">Game Status:</td>
                <td>${auction:getGameStatus(game)}</td>
            </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" width="313">
            <tr>
                <th colspan="2">Place Your Bid</th>
            </tr>
            ${auction:error('domainId', validationErrors)}
            <tr>
                <td class="bold">Website: </td>
                <td>${domain.domainName}</td>
            </tr>
            ${auction:error('imageId', validationErrors)}
            <tr>
                <td class="bold">Image: </td>
                <td>${image.description}</td>
            </tr>
            ${auction:error('bidId', validationErrors)}
            ${auction:error('maxBid', validationErrors)}
            <tr>
                <td class="bold">*Specify Max Bid:</td>
                <td>
                    $<input name="maxBid" type="text" id="maxBid" class="inputBox" style="width:143px;" maxlength="9"
                           value="${maxBidValue}" onkeypress="return acceptDigit(event);"/> &nbsp;
                    <span class="fItalic subcontent"><br/>
                        (min. $<fmt:formatNumber value="${bid.maxAmount + 1}" pattern="###0"/>)
                    </span>
                </td>
            </tr>
        </table>
        <div class="buttons">
            <a href="#" onclick="submitForm(document.BidForm);return false;" title="Submit Bid">
                <img src="${ctx}/i/b/btn_submitbid.gif" width="71" height="15" alt="Submit Bid"/>
            </a>
        </div>

        <div class="subcontent">
            * Your bid will be automatically incremented up to your maximum bid until you have reached the minimum
            amount to secure a slot.
        </div>
    </form>
</div>

<div id="tabcontentcontainer">

    <!-- COMPETING BIDS TAB -->
    <div id="sc1" class="tabcontent">
        <ul id="tablist">
            <li id="current"><a href="#" onclick="return false;">Competing Bids</a></li>
        </ul>

        <div style="clear:both;"></div>

        <div id="table-tabs">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td>
                        <p>
                            There are ${auction.itemCount} slots available in this timeblock. You must bid at least as
                            high as the lowest competing bid to be eligible for a winning bid.
                        </p>
                    </td>
                </tr>
                <tr>
                    <td class="bold">COMPETING BIDS</td>
                </tr>
                <c:forEach items="${leaders}" var="leadingBid" varStatus="index">
                    <tr class="${index.index mod 2 eq 0 ? 'alt' : 'alt2'}">
                        <td>
                                ${index.index + 1}. $<fmt:formatNumber value="${leadingBid.effectiveAmount}"
                                                                       pattern="#,##0.00"/>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</div>


</div>
<%@ include file="/includes/footer.jsp" %>
</body>
</html>