<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/paging" prefix="p" %>
<%@ taglib uri="/orpheus_admin" prefix="admin" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Reject Image</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/tabs.css"/>
    <script type="text/javascript" src="${ctx}/js/forms.js"></script>
</head>

<body id="page">
<div id="container">
    <c:set var="bar" value="sponsor" scope="page"/>
    <%@ include file="header.jsp" %>
    <div id="wrap">
        <div id="breadcrumb-admin">
            &raquo; Approve Website / Images &nbsp;
            <span class="active"> &raquo; Reject Image</span>
        </div>

        <p>Please state your reason for rejecting this image. Your message will be emailed to the sponsor.</p>

        <table border="0" width="313" cellspacing="0" cellpadding="0" id="table1" align="center">
            <tr>
                <td>
                    <ul id="tablistBlue">
                        <li id="current"><a href="#">Reject Image</a></li>
                    </ul>

                    <div style="clear:both;"></div></td>
            </tr>
            <tr>
                <td>
                    <div id="table-form">
                        <form action="${ctx}/server/admin/rejectImage.do"
                              name="RejectImageForm" id="RejectImageForm" method="POST">
                            <input type="hidden" name="imageId" value="${param['imageId']}"/>
                            <input type="hidden" name="domainId" value="${param['domainId']}"/>
                            <input type="hidden" name="sponsorId" value="${param['sponsorId']}"/>
                            <table border="0" cellpadding="0" cellspacing="0" width="360">
                                <tr>
                                    <td class="tdTop" colspan="2"></td>
                                </tr>
                                ${admin:error('rejectReason', validationErrors, 2)}
                                <tr>
                                    <td width="25%">Reason code:</td>
                                    <td width="75%">
                                        <select name="rejectReason" class="inputBox" style="width:250px">
                                            <option>001 - Adult Material</option>
                                            <option>002 - Poor Quality</option>
                                            <option>003 - Image is Too Small</option>
                                            <option>004 - Wrong File Type</option>
                                            <option>005 - Copyright issues</option>
                                        </select>
                                   </td>
                                </tr>
                                ${admin:error('rejectMessage', validationErrors, 2)}
                                <tr>
                                    <td colspan="2">
                                        Message to Sponsor:<br/>
                                        <textarea name="rejectMessage" class="textbox" style="width:335px">${param['rejectMessage']}</textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap>&nbsp;</td>
                                    <td class="buttons">
                                        <a href="#" onclick="submitForm(document.RejectImageForm);return false;">
                                            <img src="${ctx}/i/b/btn_admin_reject.gif" class="btn" alt="Reject"/>
                                        </a>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </td>
            </tr>
        </table>
        <br/>
    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>
