<%@ page errorPage="/public/error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/paging" prefix="p" %>
<%@ taglib uri="/orpheus_auction" prefix="auction" %>
<fmt:setLocale value="en_US"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
        <title>The Ball :: Bid Confirmation</title>
        <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
        <script type="text/javascript" src="${ctx}/js/forms.js"></script>
</head>
	
<body id="page">
	<div id="container">
        <c:set var="subbar" value="auctions" scope="page"/>
        <%@ include file="header.jsp" %>
		<div id="wrap">
			<h1><img src="${ctx}/i/h/title_bidconfirmation.gif" alt="Bid Confirmation" /></h1>
				<div id="breadcrumb">
                    &raquo; 1. View Games with Available Timeslots &nbsp; &raquo; 2. Bid on a Timeslot &nbsp;
                    <span class="active">&raquo; 3. Bid Confirmation</span>
                </div>

				<div id="table-details">
					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<th colspan="2">Auction #${auction.id}</th>
						</tr>
						<tr>
							<td class="bold" width="55%">Game:</td>
							<td width="45%">${game.name}</td>
						</tr>
						<tr>
							<td class="bold">Block:</td>
							<td>${auction:getMaxDuration(block)}</td>
						</tr>
						<tr>
							<td class="bold">Current Bid Range:</td>
							<td>
                                <c:if test="${not empty leaders}">
                                    $<fmt:formatNumber value="${auction:getMinimumLeadingBid(leaders).effectiveAmount}"
                                                       pattern="#,##0.00"/>
                                    -
                                    $<fmt:formatNumber value="${auction:getMaximumLeadingBid(leaders).effectiveAmount}"
                                                       pattern="#,##0.00"/>
                                </c:if>&nbsp;
                            </td>
						</tr>
						<tr>
							<td class="bold">Your Bid:</td>
							<td>$<fmt:formatNumber value="${auction:toLong(param['maxBid'])}" pattern="#,##0.00"/></td>
						</tr>
						<tr>
							<td class="bold">Your Domain:</td>
							<td><a href="#">${domain.domainName}</a></td>
						</tr>
                        <tr>
                            <td colspan="2">
                                <div class="buttons">
                                    <a href="${ctx}/server/sponsor/openAuctions.do" title="Cancel Bid">
                                        <img src="${ctx}/i/b/btn_cancel.gif" width="50" height="15" alt="Cancel"/>
                                    </a>
                                    <a href="#" title="Confirm Bid" onclick="submitForm(document.BidForm);return false;">
                                        <img src="${ctx}/i/b/btn_confirmbid.gif" width="71" height="15" alt="Confirm Bid"/>
                                    </a>
                                </div>
                            </td>
                        </tr>
						<tr>
							<td class="bold">Your Image:</td>
							<td>
                                <img src="${ctx}/server/sponsor/getImage.do?downloadId=${image.downloadId}" border="0"
                                     alt="${image.description}"/>
                            </td>
						</tr>
					</table>
                    <c:if test="${update_bid eq false}">
                        <form action="${ctx}/server/sponsor/placeBid.do" name="BidForm" id="BidForm" method="POST">
                            <input type="hidden" name="maxBid" value="${param['maxBid']}"/>
                            <input type="hidden" name="auctionId" value="${param['auctionId']}"/>
                            <input type="hidden" name="imageId" value="${param['imageId']}"/>
                            <input type="hidden" name="gameId" value="${param['gameId']}"/>
                        </form>
                    </c:if>
                    <c:if test="${update_bid eq true}">
                        <form action="${ctx}/server/sponsor/updateBid.do" name="BidForm" id="BidForm" method="POST">
                            <input type="hidden" name="maxBid" value="${param['maxBid']}"/>
                            <input type="hidden" name="auctionId" value="${param['auctionId']}"/>
                            <input type="hidden" name="bidId" value="${param['bidId']}"/>
                            <input type="hidden" name="gameId" value="${param['gameId']}"/>
                        </form>
                    </c:if>
                </div>
		</div>
	</div>
    <%@ include file="/includes/footer.jsp" %>
</body>
</html>