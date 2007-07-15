<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/paging" prefix="p" %>
<%@ taglib uri="/orpheus_admin" prefix="admin" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="gameId" value="${param['gameId']}" scope="page"/>
<c:set var="slot" value="${requestScope['slot']}" scope="page"/>
<c:set var="replacementError" value="${param['replacementError']}" scope="page"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: View Domain Targets</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <script type="text/javascript" xml:space="preserve">
        var domainName = '${slot.domain.domainName}';
        function confirmDelete(link) {
            if (confirm('Are you sure you want to delete selected target?')) {
                window.location = link.href;
            }
        }
        function confirmRegen(link) {
            if (confirm('Are you sure you want to re-generate selected target?')) {
                window.location = link.href;
            }
        }
        function showReplaceForm(index, seq, text, path) {
            var textCell = document.getElementById('Text' + index);
            var urlCell = document.getElementById('URL' + index);
            var buttonsCell = document.getElementById('ButtonsCell' + index);
            addInput(textCell, text);
            addInput(urlCell, path);
            clearElement(buttonsCell);
            addLink(buttonsCell, 'Save', 'replaceTarget(' + index + ', ' + seq + ')');
            buttonsCell.appendChild(document.createTextNode(' '));
            addLink(buttonsCell, 'Cancel', "cancelTarget(" + index + ", " + seq +  ", '" + text + "', '" + path + "')");
        }
        function addInput(cell, inputValue) {
            clearElement(cell);
            var input = document.createElement("input");
            input.type = 'text';
            input.setAttribute("type", "text")
            input.value = inputValue;
            cell.appendChild(input);
        }
        function addLink(cell, text, func) {
            var link = document.createElement("a");
            link.href = '#';
            link.onclick = new Function(func);
            link.appendChild(document.createTextNode(text));
            cell.appendChild(link);
        }
        function clearElement(e) {
            var childNodesCount = e.childNodes.length;
            for (var i = 0; i < childNodesCount; i++) {
                e.removeChild(e.firstChild);
            }
        }
        function replaceTarget(index, seq) {
            var textCell = document.getElementById('Text' + index);
            var urlCell = document.getElementById('URL' + index);
            var newText = textCell.getElementsByTagName('input')[0].value;
            var newUrl = urlCell.getElementsByTagName('input')[0].value;
            if (newText.length == 0) {
                alert('Target text is required!');
                return;
            }
            if (newUrl.length == 0) {
                alert('Target url is required!');
                return;
            }
            if (newUrl.substring(0, domainName.length + 7) != 'http://' + domainName) {
                alert('New URL is not from same domain as original URL');
                return;
            }
            if (confirm('Are you sure you want to replace the specified target?')) {
                var form = document.getElementById('ReplaceTargetForm');
                form.target.value = seq;
                form.text.value = newText;
                form.url.value = newUrl;
                form.submit();
            }
        }
        function cancelTarget(index, seq, text, url) {
            var textCell = document.getElementById('Text' + index);
            clearElement(textCell);
            textCell.appendChild(document.createTextNode('"' + text + '"'));

            var link = document.createElement("a");
            link.href = url;
            link.target = 'OrpheusAdminTarget';
            link.appendChild(document.createTextNode(url));
            var urlCell = document.getElementById('URL' + index);
            clearElement(urlCell);
            urlCell.appendChild(link);

            var buttonsCell = document.getElementById('ButtonsCell' + index);
            clearElement(buttonsCell);
            addLink(buttonsCell, 'Replace', 'showReplaceForm(' + index +', ' + seq + ', "' + text + '", "' + url + '")');
        }
        function addNewTarget() {
            var form = document.getElementById('ReplaceTargetForm');
            var newText = form.newText.value;
            var newUrl = form.newUrl.value;
            if (newText.length == 0) {
                alert('Target text is required!');
                return;
            }
            if (newUrl.length == 0) {
                alert('Target url is required!');
                return;
            }
            if (newUrl.substring(0, domainName.length + 7) != 'http://' + domainName) {
                alert('New URL is not from same domain as original URL');
                return;
            }
            if (confirm('Are you sure you want to add the specified target?')) {
                form.text.value = newText;
                form.url.value = newUrl;
                form.action = '${ctx}/server/admin/addTarget.do';
                form.submit();
            }
            return false;
        }
    </script>

</head>

<body>
<form action="${ctx}/server/admin/replaceTarget.do" method="POST" name="ReplaceTargetForm" id="ReplaceTargetForm">
        <input type="hidden" name="gameId" value="${gameId}"/>
        <input type="hidden" name="slotId" value="${slot.id}"/>
        <input type="hidden" name="target" value=""/>
        <input type="hidden" name="text" value=""/>
        <input type="hidden" name="url" value=""/>
    <table>
        <c:if test="${not empty replacementError}">
            <tr>
                <th align="center" colspan="7">
                        <span class="fBold cRed">
                            <c:if test="${replacementError eq 'targetUnverified'}">
                                The new target '${param['text']}' was not verified for validity as the process failed
                                to retrieve content from URL <br/>${param['url']}
                            </c:if>
                            <c:if test="${replacementError eq 'invalidTarget'}">
                                The new target '${param['text']}' is not valid for URL <br/>${param['url']}
                            </c:if>
                        </span>
                </th>
            </tr>
        </c:if>
        <tr>
            <th align="center">Seq. Num</th>
            <th align="center">Text</th>
            <th align="center">URL</th>
            <th align="center">Hash</th>
            <th align="center">Clue Image</th>
            <th align="center">&nbsp;</th>
            <th align="center">&nbsp;</th>
        </tr>
        <c:forEach items="${slot.domainTargets}" var="target" varStatus="index">
            <tr>
                <td>${target.sequenceNumber}</td>
                <td id="Text${index.index}">"${target.identifierText}"</td>
                <td id="URL${index.index}">
                    <a href="${target.uriPath}" target="OrpheusAdminTarget">${target.uriPath}</a>
                </td>
                <td>${target.identifierHash}</td>
                <td>
                    <a href="${ctx}/server/admin/viewClueImage.do?imageId=${target.clueImageId}&gameId=${gameId}&slotId=${slot.id}&target=${target.sequenceNumber}" target="OrpheusClueImage">
                        View
                    </a>
                </td>
                <td>
                    <a href="${ctx}/server/admin/deleteTarget.do?gameId=${gameId}&slotId=${slot.id}&target=${target.sequenceNumber}"
                        onclick="confirmDelete(this);return false;">
                        Delete
                    </a>
                </td>
                <td>
                    <a href="${ctx}/server/admin/regenTarget.do?gameId=${gameId}&slotId=${slot.id}&target=${target.sequenceNumber}"
                        onclick="confirmRegen(this);return false;">
                        Regenerate
                    </a>
                </td>
                <td id="ButtonsCell${index.index}">
                    <a href="#"
                       onclick="return showReplaceForm(${index.index}, ${target.sequenceNumber}, '${target.identifierText}', '${target.uriPath}');"
                       id="TargetLink${index.index}">
                        Replace
                    </a>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td>NEW</td>
            <td>
                <input type="text" name="newText" size="20" maxlength="20"/>
            </td>
            <td colspan="2">
                <input type="text" name="newUrl" size="40" maxlength="255"/>
            </td>
            <td colspan="4">
                <a href="#" onclick="return addNewTarget();">
                    Add New Target
                </a>
            </td>
        </tr>
    </table>
    </form>
</body>
</html>