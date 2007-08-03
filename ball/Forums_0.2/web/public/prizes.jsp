<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: How To Play :: Prizes And Payouts</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="howto" scope="page"/>
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <h1><img src="${ctx}/i/h/title_prizesandpayouts.gif" width="199" height="16" alt="Prizes And Payouts"/></h1>

        <p><span class="fBold">Terms &amp; Conditions</span><br/>
            To win, you must be aged 18 years or older and meet all other eligibility criteria. You must also agree to
            abide by our dramatic and exciting <a href="${ctx}/public/tos.jsp">Terms &amp; Conditions of Use.</a></p>

        <p><span class="fBold">Privacy</span><br/>
            We promise not to hassle you, or give your email address away, or to send you long boring letters over the
            holidays bragging about how well our children are doing in school. It's all (mostly) in our<a
            href="${ctx}/public/privacy-policy.jsp"> Privacy Policy.</a></p>

        <p><span class="fBold">Prizes</span><br/>
<!--
            The number of winners and payout percentages for each winner may vary by competition at TopCoder's sole
            discretion. Please refer to the information posted for each competition for the specific number of eligible
            winners and payout information for each competition.<br/>
            <br/>
            In general, the first six eligible players to unlock the ball and solve the subsequent puzzle within the
            required time limit will share in the total jackpot for that game according to the following percentages:
            <br/>
            <br/>
            1st player to complete the competition - 25% of total jackpot<br/>
            2nd player to complete the competition - 20% of total jackpot<br/>
            3rd player to complete the competition - 17.5% of total jackpot<br/>
            4th player to complete the competition - 15% of total jackpot<br/>
            5th player to complete the competition - 12.5% of total jackpot<br/>
            6th player to complete the competition - 10% of total jackpot<br/>
            <br/>
-->
            The number of winners and payout percentages for each winner may vary by competition at TopCoder's sole
            discretion. Please refer to the information posted for each competition for the specific number of eligible
            winners and payout information for each competition.<br/>
            <br/>
            In general, the first five eligible players to unlock the ball and solve the subsequent puzzle within the
            required time limit will win the following prizes (in US dollars):
            <br/>
            <br/>
            1st player to complete the competition - $125.00<br/>
            2nd player to complete the competition - $112.50<br/>
            3rd player to complete the competition - $100.00<br/>
            4th player to complete the competition - $87.50<br/>
            5th player to complete the competition - $75.00<br/>
            <br/>
            Prizes will be paid within 60 days of the end of the game, pending receipt of your
            <a href="${ctx}/public/affidavits.jsp">affidavit</a>. Prizes will be paid via check, sent via U.S. Postal
            Service mail, to the address in your profile. To ensure accurate delivery, be sure to update your address if
            you move. Tax liabilities, or any other obligations related to winning, are the sole responsibility of the
            winner, though common tax forms can be found <a href="${ctx}/public/tax.jsp">here</a>.
            </p>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>