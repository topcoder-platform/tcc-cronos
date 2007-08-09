<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: How To Play :: Affidavits</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>
<body id="page">
<div id="container">
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <p><img src="${ctx}/i/h/affidavits.gif" alt="Installation FAQ" width="201" height="16"/></p>
        <div>
            <table class="wrap" cellspacing="0" cellpadding="0" border="0">
                <tr>
                    <td id="column-center">
                        <div id="help-wrap">
                            <div class="help">
                                <p>When a player is approved as a game winner for The Ball, they will be emailed an
                                    affidavit to complete.&nbsp; To be paid for winning any Ball game, winners must
                                    first submit to The Ball&rsquo;s Accounting Department ONE notarized affidavit.&nbsp;
                                    Once a player has a notarized affidavit on file with us, subsequent affidavits can
                                    simply be signed and returned to The Ball&rsquo;s Accounting Department.</p>
                                <ul>
                                    <li>A notarized affidavit is one that has been signed and stamped by a Notary Public
                                        (or similar official person in other countries). A Notary Public is defined as
                                        &quot;A person legally empowered to witness and certify the validity of
                                        documents and to take affidavits and depositions.&quot; </li>
                                    <li>In the US and Canada, you can find a Notary Public at most post offices, banks
                                        and college/university registrar offices.
                                    </li>
                                    <li>Outside the US and Canada, you can get your affidavit notarized at any US
                                        Embassy.
                                    </li>
                                    <li>Once you have a notarized affidavit on file with TopCoder, you can affirm online
                                        all future affidavits. You must affirm affidavits within 60 days; otherwise they
                                        will expire and you will not receive payment for the associated contest.
                                    </li>
                                    <li><strong>Note:</strong> Getting an affidavit notarized is a ONE TIME issue. While
                                        you may incur costs to obtain a notarized affidavit, know that you will not have
                                        to do it again in the future. TopCoder will not pay for any costs related to the
                                        notarization of affidavits.
                                    </li>
                                </ul>
                                <p>
                                    Please email any other questions to
                                    <a href="mailto:service@theball.com">service@theball.com</a>
                                </p>

                                <div class="components"></div>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>
