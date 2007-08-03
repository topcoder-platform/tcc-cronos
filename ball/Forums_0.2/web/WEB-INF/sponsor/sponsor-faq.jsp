<%@ page errorPage="/public/error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Auctions :: FAQ</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="faq" scope="page"/>
    <%@ include file="header.jsp" %>

    <div id="wrap">
        <h1><img src="${ctx}/i/h/title_biddingfaq.gif" alt="Bidding FAQ"/></h1>
        <ol>
            <li><a href="#1">How does the Sponsorship Auction work? </a></li>
            <li><a href="#2">How do I start the bidding process to host the ball?</a></li>
            <li><a href="#3">What is the difference between Blocks and Slots? </a></li>
            <li><a href="#4">What is a max bid?</a></li>
            <li><a href="#5">How come I was outbid even though I had an apparent winning bid?</a></li>
            <li><a href="#6">Can I re-bid or raise my original bid?</a></li>
            <li><a href="#7">What are my payment options?</a></li>
        </ol>

        <hr class="grayline"/>
        <ol>
            <li><a name="1"></a><span class="faq">How does the Sponsorship Auction work?</span><br/>
                Sponsorship Auctions follow a standard auction format. During the course of the auction, you may
                manually enter bids (with a minimum bid of $5 or with a bid that is at least $1 higher than the current
                winning bid) or you may set a maximum bid and allow the system to automatically raise your bid as
                appropriate, based on rival sponsor bids. Note: other sponsors may have maximum bids in the system, and
                as we will proxy bid on their behalf as well, the minimum bid stated on the Bid Details page may appear
                to be sufficient to win a slot, however after proxy bidding on all sponsors’ behalf, a seemingly winning
                bid may still be out-bid.  Please check the "Open Auctions" page after you have placed a bid to ensure
                that you have a winning bid after all of the proxy bidding.
                <br/>&nbsp;</li>

            <li><a name="2"></a><span class="faq">How do I start the bidding process to host the ball?</span><br/>
                Once you have registered for an account, you must be approved by a game administrator before you are
                able to bid to host the ball. Once you have been approved and contacted by an administrator, you may
                begin bidding by logging into the Ball’s website. You do not need to specify that you are a sponsor when
                logging into our website, your credentials will provide sufficient authorization and you will be
                automatically directed to any auctions that are in progress for which are eligible to bid.
                <br/>&nbsp;</li>

            <li><a name="3"></a><span class="faq">What is the difference between Blocks and Slots? </span><br/>
                Each auction is made up of blocks and slots.  Blocks are simply a way of grouping slots together for
                administrative purposes. Each slot within a block represents a portion of time for which a site will be
                hosting the ball. You may bid to win up to half the number of slots within a block if there are an even
                number of slots, or half the number of slots +1 if there are an odd number of slots (e.g. if a block has
                4 slots, you can bid to win up to 2 slots; if a block has 5 slots, you can bid to win up to 3 slots).
                You may place bids in as many blocks and as many games as you like.  Note: different games and blocks
                may have different start and end times. Once you have identified a particular game and block for which
                you would like to place a bid, simply click on the orange "Bid" button associated with your desired
                block and you will be taken to the bidding details screen where you can enter a bid amount.
                <br/>&nbsp;</li>

            <li><a name="4"></a><span class="faq">What is a max bid?</span><br/>
                Once you place a bid for a particular block, you will see the competing bids for that block. We have a
                proxy bidding system in place that allows a sponsor to place a "maximum bid". This is the maximum amount
                a sponsor is willing to pay for a slot within that block and we will bid on a sponsor’s behalf up to
                their specified maximum.
                <br/>&nbsp;</li>

            <li><a name="5"></a><span class="faq">How come I was outbid even though I had an apparent winning bid?</span><br/>
                The competing bids shown on a page may not entirely reflect how high a bid a sponsor needs to place to
                win a slot within a particular block since we have an automated proxy bidding system in place, and rival
                sponsors may have a much higher, hidden "max" bid. Once you place a bid in the system, you bid may cause
                another sponsor to lose their winning slot within a block. However if that sponsor has a higher max bid
                in place, our system will bid on the latter’s behalf and may "knock out" you bid if it is too low, once
                the proxy bidding process on behalf of all sponsors is complete. You will need to experiment a little to
                try to "outguess" rival sponsors, and even if you are outbid on a slot, don’t worry – you can always
                re-bid to re-capture the slot! Always check the "Open Auctions" page after you have placed a bid or
                re-bid for a slot to ensure that you have not been outbid by proxy bidders.
                <br/>&nbsp;</li>

            <li><a name="6"></a><span class="faq">Can I re-bid or raise my original bid?</span><br/>
                Once you have placed a bid, you may re-bid or raise you maximum bid at any time. Simply click the
                raise-bid button (blue button with $+ icon on it) and you will be taken to the raise bid details screen.
                Your previous maximum bid will be automatically pre-populated in the "Specify Max Bids" field. Note: as
                with placing an original bid, sponsors will be required to confirm the re-bid before the new bid amount
                is finalized. Sponsors may not place a lower bid than their previous highest max bid for that slot.
                <br/>&nbsp;</li>

            <li><a name="7"></a><span class="faq">What are my payment options?</span><br/>
                Please contact us for payment options and details.
                <br/>&nbsp;</li>

        </ol>
    </div>
</div>
<%@ include file="/includes/footer.jsp" %>
</body>
</html>