<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/paging" prefix="p" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Unlocked Domains</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/plugin.css"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Expires" content="Wed, 09 Aug 2000 08:21:57 GMT"/>
    <script type="text/javascript" src="${ctx}/js/pluginSupport.js"></script>
</head>

<body id="pagePlugin">
<div id="containerPlugin">

    <div id="mastHead"><img src="${ctx}/i/plugin_stripes.jpg" width="668" height="62" alt=""/></div>

    <div id="pluginTitle">UNLOCKED <span class="main">WEBSITES FOR ${game.name} </span></div>

    <div id="wrapPlugin">
        <p>"Unlocked Sites" shows any keys that you have found as well as sites where other players have found keys.
            Sites are listed in reverse chronological order as compared to when the site hosted the ball, however please
            note that there may be sites in-between those listed here that have not yet been unlocked by any player.</p>
        <div class="tabletop"></div>
        <c:set var="completedSlots" value="${orpheus:reverseSlots(completedSlots)}" scope="request"/>
        <p:dataPaging pageSize="10" data="${orpheus:convertToList(orpheus:reverseDomains(unlockedDomains))}" id="pager"
                      requestURL="${ctx}/server/plugin/unlockedDomains.do">
            <p:page>
                <div id="plugin-table">
                    <ul>
                        <li class="auction-block">
                            <p:table border="0" cellpadding="0" cellspacing="0">
                                <tr class="altHeader">
                                    <th width="245">Site</th>
                                    <th width="191">Key</th>
                                    <th width="209">Date Unlocked </th>
                                </tr>
                                <p:rowData oddRowStyle="alt2" evenRowStyle="alt" id="item" rowId="domain"
                                           rowType="com.orpheus.game.persistence.Domain">
                                    <td class="link">
                                        <a href="${orpheus:buildDomainUrl(domain.domainName)}"
                                           onclick="openInParentWindow(this);return false;">
                                                ${domain.domainName}
                                        </a>
                                    </td>
                                    <td class="alt">${unlockedDomainsDetails[domain.id][completedSlots[item.rowNumberFromStart].id].keyText}</td>
                                    <td>
                                        <fmt:formatDate value="${unlockedDomainsDetails[domain.id][completedSlots[item.rowNumberFromStart].id].timestamp}"
                                                        pattern="MM/dd/yyyy hh:mm aa"/>
                                    </td>
                                </p:rowData>
                            </p:table>
                        </li>
                    </ul>
                </div>

                <div class="pagination">
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td>Showing Domains ${orpheus:getDataPagingFooterMessage(pager)}</td>
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

        <div class="tablebot"></div>
    </div>
</div>
<script type="text/javascript" xml:space="preserve">
    <!--
    window.focus();
    -->
</script>
</body>
</html>