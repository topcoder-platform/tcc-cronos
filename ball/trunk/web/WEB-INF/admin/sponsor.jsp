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
    <title>The Ball :: Sponsor Approval</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>

<body id="page">
<div id="container">
<c:set var="subbar" value="sponsor" scope="page"/>
<%@ include file="header.jsp" %>
<div id="wrap">
<div id="breadcrumb-admin"><span class="active"> &raquo; Sponsor Approval &nbsp;</span></div>

<p>Select a row to view Ball progress and alter the hosting Order of a game.</p>

<p:dataPaging pageSize="10" data="${admin:convertToList(pendingSponsors)}" id="pager"
              requestURL="${ctx}/server/admin/pendingSponsors.do">
    <p:page>
        <div id="data-table-admin">
            <p:table border="0" cellpadding="0" cellspacing="0"
                     comparator="${admin:getAdminAllGamesComparator()}">
                <p:header>
                    <p:column index="1" name="Sponsor"/>
                    <p:column index="2" name="Contact"/>
                    <th><a href="#">Details</a></th>
                    <th><a href="#">Domain Approval</a></th>
                    <th><a href="#">Sponsor Approval</a></th>
                </p:header>
                <p:rowData id="item" rowId="row" oddRowStyle="alt"
                           rowType="com.orpheus.administration.entities.SponsorDomain"
                           renderTR="true">
                    <td>${admin:getHandle(row.sponsor)}</td>
                    <td>${admin:getSponsorContactName(row.sponsor)}<br/>
                        <a href="mailto:${admin:getEmailAddress(row.sponsor)}">
                        ${admin:getEmailAddress(row.sponsor)}
                        </a>
                    </td>
                    <td class="center">
                        <a class="popup" href="#">
                            <img border="0" src="${ctx}/i/b/btn_admin_view.gif" width="36" height="15" class="btn"
                                 alt="View"/>
                            <span>${admin:getAddress1(row.sponsor)}<br/>${admin:getAddress2(row.sponsor)}<br/>
                                ${admin:getCity(row.sponsor)}, ${admin:getState(row.sponsor)} ${admin:getPostalCode(row.sponsor)},
                                ${admin:getCountry(row.sponsor)}<br/>
                                Phone: ${admin:getPhone(row.sponsor)}<br/>Fax: ${admin:getFax(row.sponsor)}</span>
                        </a>
                    </td>
                    <td>
                        <li class="domain-block">
                            <table border="0" cellpadding="0" cellspacing="0" width="400">
                                <c:forEach items="${row.domains}" var="domain">
                                    <c:set var="domainStatus" value="${admin:getDomainApprovalStatus(domain)}"
                                           scope="page"/>
                                    <tr style="cursor: pointer;" onClick="window.location='${ctx}/server/admin/pendingDomain.do?domainId=${domain.id}'"
                                        <c:if test="${domainStatus eq 'unapproved'}">
                                            class="active"
                                            onmouseover="this.style.backgroundColor='#FEF1C4';"
                                            onmouseout="this.style.backgroundColor='#f5dd89';"
                                        </c:if>
                                        <c:if test="${domainStatus eq 'rejected'}">
                                            class="strike"
                                        </c:if>
                                        <c:if test="${domainStatus eq 'approved'}">
                                            onmouseover="this.style.backgroundColor='#FEF1C4';"
                                            onmouseout="this.style.backgroundColor='#ffffff';"
                                        </c:if>>
                                        <td class="center">
                                            <c:if test="${domainStatus eq 'unapproved'}">
                                                <img border="0" src="${ctx}/i/error.gif" width="16" height="16" alt=""/>
                                            </c:if>
                                            <c:if test="${domainStatus eq 'approved'}">
                                                <img border="0" src="${ctx}/i/approved.gif" width="16" height="16" alt=""/>
                                            </c:if>
                                            <c:if test="${domainStatus eq 'rejected'}">
                                                <img border="0" src="${ctx}/i/rejected.gif" width="16" height="16" alt=""/>
                                            </c:if>
                                        </td>
                                        <td><a href="${domain.domainName}">${domain.domainName}</a></td>
                                        <td>${domainStatus}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </li>
                    </td>
                    <td class="center">
                        <a href="${ctx}/server/admin/approveSponsor.do?sponsorId=${row.sponsor.identifier}">
                            <img border="0" src="${ctx}/i/b/btn_admin_approve.gif" width="50" height="15" alt="Approve"/>
                        </a>
                        <a href="${ctx}/server/admin/showRejectSponsorForm.do?sponsorId=${row.sponsor.identifier}">
                            <img border="0" src="${ctx}/i/b/btn_admin_reject.gif" width="41" height="15" alt="Reject"
                                 class="btn"/>
                        </a>
                    </td>
                </p:rowData>
            </p:table>
        </div>

        <div class="pagination">
            <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td>Showing Sponsors ${admin:getDataPagingFooterMessage(pager)}</td>
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
<%@ include file="footer.jsp" %>
</body>
</html>