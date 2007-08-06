<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Report A Bug</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>
<body id="page">
<div id="container">
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <p><strong>Report a Bug</strong> | <a href="${ctx}/public/bugs_known.jsp"><strong>Known Bugs</strong></a></p>

        <div>
            <table class="wrap" cellspacing="0" cellpadding="0" border="0">
                <tr>
                    <td id="column-center">
                        <div id="help-wrap">
                            <div class="help">
                                <p><br/>
                                    You're probably wondering, &quot;What is the Ball?&quot; Actually, a better question
                                    is, &quot;WHERE is the Ball?&quot;</p>

                                <p><a href="http://www.theball.com">The Ball</a> travels from site to site across the
                                    Web, and it's your job to find it. It's like an online treasure hunt - but unlike an
                                    &quot;X&quot; on a treasure map, the Ball doesn't stay still!</p>

                                <p>As you pursue it across the Internet, you're presented with puzzles and challenges.
                                    Solve them correctly and you receive clues to help you find the Ball, and the keys
                                    you'll need to unlock it when you do. Find the Ball, win the cash!</p>

                                <p>Right now the Ball is in beta testing. We're launching it first to our most reliable
                                    source of thinkers and code breakers: YOU, the TopCoder community. Before we let any
                                    random person play the game, we want to know what bugs there are and whether the
                                    game can be automated, broken or cheated. That's where you come in.</p>

                                <p>For several weeks, starting April 4, 2007, we'll be running a bug finding contest in
                                    parallel with the Ball game itself. Each TopCoder member who reports a novel* bug or
                                    a novel* method to automate game play will receive a share of a $1,500 prize pool.
                                    We've provided a simple bug report form for you to let us know when you've found a
                                    bug or a way to circumvent the intended game play.</p>

                                <p>The success of the Ball depends largely on what the TopCoder community finds. <a
                                    href="http://www.theball.com">Download the plug-in</a> now and start chasing the
                                    Ball!</p>

                                <p>&nbsp;</p>
                                <form action="https://www.topcoder.com/tc" method="POST" name="SubmitBugForm"
                                      id="SubmitBugForm">
                                    <input type="hidden" name="module" value="SubmitBallBug" />
                                    <table width="600" border="0" align="center" cellpadding="10" cellspacing="0">
                                        <tr>
                                            <td width="231">&nbsp;</td>
                                            <td width="369"><span class="cOrange"><strong>Report a Bug </strong></span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="right"><strong>System Configuration:</strong></td>
                                            <td>
                                                <label>
                                                    <textarea name="system" cols="50" rows="5"></textarea>
                                                </label>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="right"><strong>Bug description:</strong></td>
                                            <td><textarea name="desc" cols="50" rows="5"></textarea></td>
                                        </tr>
                                        <tr>
                                            <td align="right"><strong>How to reproduce bug:</strong></td>
                                            <td><textarea name="howto" cols="50" rows="5"></textarea></td>
                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td>
                                                <label>
                                                    <input type="submit" name="Submit" value="Submit"/>
                                                </label>
                                            </td>
                                        </tr>
                                    </table>
                                </form>
                                <p>
                                    <br/>
                                </p>
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
