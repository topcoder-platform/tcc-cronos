<%@ page errorPage="/public/error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/paging" prefix="p" %>
<%@ taglib uri="/orpheus_auction" prefix="auction" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="en_US"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Open Auctions</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <script type="text/javascript" xml:space="preserve">
        function clickGame(gameNum) {
            var li = document.getElementById('gameAuction' + gameNum);
            var li2 = document.getElementById('gameHeader' + gameNum);
            if (li != null) {
                if (li.style.display == 'none') {
                    li.style.display = 'block';
                    li2.className = 'open';
                } else {
                    li.style.display = 'none';
                    li2.className = 'closed';
                }
            }
        }
    </script>
</head>

<body id="page">
<div id="container">
<c:set var="subbar" value="auctions" scope="page"/>
<%@ include file="header.jsp" %>

<div id="wrap">
<h1><img src="${ctx}/i/h/title_openauctions.gif" alt="Open Auctions"/></h1>

<div id="breadcrumb">
    <span class="active">&raquo; 1. View Games with Available Timeslots &nbsp;</span>
    <span class="next">&raquo; 2. Bid on a Timeslot &nbsp; &raquo; 3. Bid Confirmation</span>
</div>

<p>
    The following games are open for hosting The Ball. To bid, simply click the "Bid" button inline with the desired
    Block. Please visit the <a href="${ctx}/server/sponsor/showFAQ.do">Bidding FAQ</a> for more information on
    sponsoring The Ball and Bidding on available timeslots.
</p>
<br/><br/>

<c:set var="sponsor" value="${auction:getCurrentUser(pageContext.request)}" scope="page"/>
<p:dataPaging pageSize="10" data="${games}" id="pager"
              requestURL="${ctx}/server/player/activeGames.do">
<p:page>

<div id="data-table">
    <ul>
        <p:table border="0" cellpadding="0" cellspacing="0" renderTable="false">
            <p:rowData id="item" rowId="row" rowType="com.orpheus.game.persistence.Game" renderTR="false">
                <li class="${item.rowNumberOnPage eq 0 ? 'open' : 'closed'}" id="gameHeader${item.rowNumberOnPage}">
                    <a href="#" onclick="javascript:clickGame(${item.rowNumberOnPage});return false;">
                        GAME: ${row.name}
                    </a>
                </li>
                <li class="auction-block" id="gameAuction${item.rowNumberOnPage}"
                    <c:if test="${item.rowNumberOnPage ne 0}">style="display:none;"</c:if>>
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <th width="10%"><a href="#" title="sort">
                                <font color="#FFFFFF">Block</font></a></th>
                            <th width="14%"><a href="#" title="sort">
                                <font color="#FFFFFF">Time Left</font></a></th>
                            <th width="5%"><a href="#" title="sort">
                                <font color="#FFFFFF">Slots</font></a></th>
                            <th width="20%"><a href="#" title="sort">
                                <font color="#FFFFFF">Bid Range</font></a></th>
                            <th width="21%"><a href="#" title="sort">
                                <font color="#FFFFFF">Status</font></a></th>
                            <th width="7%" nowrap><a href="#" title="sort">
                                <font color="#FFFFFF">Your Bids</font></a></th>
                            <th width="7%">&nbsp;</th>
                            <th width="16%">&nbsp;</th>
                        </tr>
                        <c:forEach items="${row.blocks}" var="block" varStatus="index">
                            <c:set var="auction" value="${auction:getAuction(block, auctions)}" scope="page"/>

                            <c:if test="${auction ne null}">
                            <c:set var="sponsorBids" value="${auction:getSponsorBids(sponsor, auction)}" scope="page"/>
                            <c:set var="sponsorMaxBid" value="${auction:getMaximumBid(sponsorBids)}" scope="page"/>
                            <c:set var="leadingBids" value="${auction:getLeadingBids(auction, leadingBidsMap)}" scope="page"/>
                            <tr ${index.index mod 2 == 1 ? 'class="alt"' : ''}>
                                <td>Block ${index.index + 1}</td>
                                <td class="spot">${auction:getAuctionTimeLeft('Ends in ', auction)}</td>
                                <td>${auction.itemCount}</td>
                                <td>
                                    <c:if test="${not empty leadingBids}">
                                    $<fmt:formatNumber value="${auction:getMinimumLeadingBid(leadingBids).effectiveAmount}"
                                                       pattern="#,##0.00"/>
                                    -
                                    $<fmt:formatNumber value="${auction:getMaximumLeadingBid(leadingBids).effectiveAmount}"
                                                       pattern="#,##0.00"/>
                                    </c:if>&nbsp;
                                </td>
                                <td>
                                    <c:forEach items="${sponsorBids}" var="bid" varStatus="bidStatusIndex">
                                        <c:set var="bidStatus" value="${auction:getBidStatus(bid, leadingBids)}"
                                               scope="page"/>
                                        <c:if test="${bidStatus eq 2}">
                                            <span class="spot">Outbid</span><br/>
                                        </c:if>
                                        <c:if test="${bidStatus eq 1}">
                                            Qualifying<br/>
                                        </c:if>
                                        <c:if test="${bidStatus eq 0}">
                                            <span class="bid">Highest Bidder</span><br/>
                                        </c:if>
                                    </c:forEach>&nbsp;
                                </td>
                                <td>
                                    <c:forEach items="${sponsorBids}" var="bid">
                                        <c:set var="bidStatus" value="${auction:getBidStatus(bid, leadingBids)}"
                                               scope="page"/>
                                        <c:if test="${bidStatus eq 2}">
                                        <span class="spot">
                                          $<fmt:formatNumber value="${bid.maxAmount}" pattern="#,##0.00"/>
                                        </span>
                                        </c:if>
                                        <c:if test="${bidStatus eq 1}">
                                            $<fmt:formatNumber value="${bid.effectiveAmount}" pattern="#,##0.00"/>
                                        </c:if>
                                        <c:if test="${bidStatus eq 0}">
                                        <span class="bid">
                                          $<fmt:formatNumber value="${bid.effectiveAmount}" pattern="#,##0.00"/>
                                         </span>
                                        </c:if>
                                    </c:forEach>&nbsp;
                                </td>
                                <td>
                                    <c:forEach items="${sponsorBids}" var="bid">
                                        <a href="${ctx}/server/sponsor/startBidUpdate.do?bidId=${bid.id}&auctionId=${auction.id}&gameId=${row.id}&blockId=${block.id}">
                                            <img border="0" src="${ctx}/i/b/btn_raisebid.gif" width="22" height="15"
                                                 alt="Raise Bid" class="btnRaiseBid"/>
                                        </a>
                                    </c:forEach>&nbsp;
                                </td>
                                <td align="center">
                                    <c:if test="${auction:getSize(sponsorBids) < auction:getMaximumBidsCount(auction.itemCount)}">
                                        <a href="${ctx}/server/sponsor/startBid.do?auctionId=${auction.id}&gameId=${row.id}&blockId=${block.id}"
                                           title="Bid">
                                            <img src="${ctx}/i/b/bid.gif" alt="Bid" width="27" height="15"/>
                                        </a>
                                    </c:if>
                                    <c:if test="${auction:getSize(sponsorBids) >= auction:getMaximumBidsCount(auction.itemCount)}">
                                        Max Bids Reached
                                    </c:if>
                                    &nbsp;
                                </td>
                            </tr>
                            </c:if>
                        </c:forEach>
                    </table>
                </li>
            </p:rowData>
        </p:table>
    </ul>
</div>

<div class="pagination">
    <table border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td>Showing Games ${auction:getDataPagingFooterMessage(pager)}</td>
            <td>
                <ul>Page:
                    <p:prevLink>&laquo;</p:prevLink>
                    <p:jumpLinks maxCount="5" prefix="<li>" suffix="</li>"/>
                    <p:nextLink>&raquo;</p:nextLink>
                </ul>
            </td>
        </tr>
    </table>
</div>
</p:page>
</p:dataPaging>
</div>
</div>
<%@ include file="/includes/footer.jsp" %>
</body>
</html>