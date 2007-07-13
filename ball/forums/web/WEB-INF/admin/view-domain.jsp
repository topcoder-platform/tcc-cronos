<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/paging" prefix="p" %>
<%@ taglib uri="/orpheus_admin" prefix="admin" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="domainStatus" value="${admin:getApprovalStatusDomain(domain)}" scope="page"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Domain Approval</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/tabs.css"/>
    <script type="text/javascript" xml:space="preserve" src="${ctx}/js/popup.js">
    </script>
</head>

<body id="page">
<div id="container">
<c:set var="bar" value="sponsor" scope="page"/>
<%@ include file="header.jsp" %>
<div id="wrap">
<div id="breadcrumb-admin">
    &raquo; Sponsor Approval &nbsp;
    <span class="active"> &raquo; View Domain &nbsp;</span>
</div>

<p></p>

<div id="table-details">
    <table border="0" cellpadding="0" cellspacing="0" width="313">
        <tr>
            <th colspan="2" style="background-color:#697cc0">Domain Details:</th>
        </tr>
        <tr>
            <td class="bold" width="55%">Domain:</td>
            <td width="45%"><a href="${domain.domainName}">${domain.domainName}</a></td>
        </tr>
        <tr>
            <td class="bold">Sponsor:</td>
            <td>${admin:getHandle(sponsor)}</td>
        </tr>
        <tr>
            <td class="bold">Status:</td>
            <td>
                <c:if test="${domainStatus eq 0}">
                    Rejected
                </c:if>
                <c:if test="${domainStatus eq 1}">
                    Approved
                </c:if>
                <c:if test="${domainStatus eq 2}">
                    Awaiting Approval
                </c:if>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="background-color:#faf4e2">
                <p align="center">
                    <c:if test="${domainStatus eq 2}">
                        <span class="fBold cBlue"><br/>This website has not been approved.<br/></span><br/>
                    </c:if>
                    <c:if test="${domainStatus eq 2 or domainStatus eq 0}">
                        <a href="${ctx}/server/admin/approveDomain.do?domainId=${domain.id}&sponsorId=${sponsor.identifier}"
                           title="Approve Website">
                            <img src="${ctx}/i/b/btn_admin_approve.gif" align="absmiddle" class="btn" alt="Approve"/>
                        </a>
                    </c:if>
                    <c:if test="${domainStatus eq 2 or domainStatus eq 1}">
                        <a href="${ctx}/server/admin/showRejectDomainForm.do?domainId=${domain.id}&sponsorId=${sponsor.identifier}" title="Reject Website">
                            <img src="${ctx}/i/b/btn_admin_reject.gif" align="absmiddle" class="btn" alt="Reject"/>
                        </a>
                    </c:if>
                    <br/>
                    &nbsp;
                </p>
            </td>
        </tr>
    </table>
</div>

<div id="tabcontentcontainer">
    <table border="0" width="313" cellspacing="0" cellpadding="0" id="table1" align="center">
        <tr>
            <td>
                <ul id="tablistBlue">
                    <li id="current"><a href="#">Approve/Reject Images</a></li>
                </ul>

                <div style="clear:both;"></div>
            </td>
        </tr>
        <tr>
            <td>
                <div id="table-form">
                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                        <tr>
                            <th>&nbsp;</th>
                            <th>Status</th>
                            <th>Image</th>
                            <th>&nbsp;</th>
                        </tr>
                        <c:forEach items="${domain.images}" var="image">
                            <c:set var="imageStatus" value="${admin:getApprovalStatusImage(image)}" scope="page"/>
                            <c:set var="imageUrl" value="${ctx}/server/admin/getImage.do?downloadId=${image.downloadId}"
                                   scope="page"/>
                            <tr>
                                <c:if test="${imageStatus eq 2}">
                                    <td style="background-color:#faf4e2">
                                        <img src="${ctx}/i/alert.gif" width="34" height="34" alt="Alert"/>
                                    </td>
                                    <td style="background-color:#faf4e2">Awaiting Approval</td>
                                    <td style="background-color:#faf4e2">
                                        <a href="#"
                                           onclick="javascript:NewWindow('${imageUrl}', 'image', 600, 400, 'yes');return false;">
                                            <img src="${ctx}/i/fpo.jpg" alt="Click to view image"/>
                                        </a>
                                    </td>
                                    <td style="background-color:#faf4e2">
                                        <a href="${ctx}/server/admin/approveImage.do?domainId=${domain.id}&sponsorId=${sponsor.identifier}&imageId=${image.id}">
                                            <img src="${ctx}/i/b/btn_admin_approve.gif" class="btn" alt="Approve"/>
                                        </a>
                                        <a href="${ctx}/server/admin/showRejectImageForm.do?domainId=${domain.id}&sponsorId=${sponsor.identifier}&imageId=${image.id}" title="Reject Image">
                                            <img src="${ctx}/i/b/btn_admin_reject.gif" class="btn" alt="Reject"/>
                                        </a>
                                   </td>
                                </c:if>
                                <c:if test="${imageStatus eq 1}">
                                    <td>&nbsp;</td>
                                    <td>Approved</td>
                                    <td>
                                        <a href="#"
                                           onclick="javascript:NewWindow('${imageUrl}', 'image', 600, 400, 'yes');return false;">
                                            <img src="${ctx}/i/fpo.jpg" alt="Click to view image"/>
                                        </a>
                                    </td>
                                    <td>
                                        <a href="${ctx}/server/admin/showRejectImageForm.do?domainId=${domain.id}&sponsorId=${sponsor.identifier}&imageId=${image.id}" title="Reject Image">
                                            <img src="${ctx}/i/b/btn_admin_reject.gif" class="btn" alt="Reject"/>
                                        </a>
                                    </td>
                                </c:if>
                                <c:if test="${imageStatus eq 0}">
                                    <td style="background-color:#cccccc">&nbsp;</td>
                                    <td style="background-color:#cccccc">Rejected</td>
                                    <td style="background-color:#cccccc">
                                        <a href="#"
                                           onclick="javascript:NewWindow('${imageUrl}', 'image', 600, 400, 'yes');return false;">
                                            <img src="${ctx}/i/fpo.jpg" alt="Click to view image"/>
                                        </a>
                                    </td>
                                    <td style="background-color:#cccccc">
                                        <a href="${ctx}/server/admin/approveImage.do?domainId=${domain.id}&sponsorId=${sponsor.identifier}&imageId=${image.id}">
                                            <img src="${ctx}/i/b/btn_admin_approve.gif" class="btn" alt="Approve"/>
                                        </a>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </td>
        </tr>
    </table>
    <br/>
</div>
</div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>