<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>TopCoder Cockpit</title>
<!-- Meta Tags -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- External CSS -->
<link rel="stylesheet" href="css/layout.css" media="all" type="text/css" />
<link rel="stylesheet" href="css/layout-groups.css" media="all" type="text/css" />

<!-- External javascript -->
<script type="text/javascript" src="scripts/jquery-1.7.min.js"></script>
<script type="text/javascript" src="scripts/script-groups.js"></script>

</head>


<body id="page" class="dashboardPage">
<div id="wrapper">
	<div id="wrapperInner">
		<div id="container">
			<div id="content">
			<jsp:include page="includes/header.jsp">
                    <jsp:param name="tabLoc" value="first" />
                </jsp:include>
				<div id="wholeContent">
					<div id="wholeArea">
					
						<div id="wholeAreaHeader">
							<h2 class="groupDetailsTitle">View Group - &lt;<s:property value="group.name"/>&gt;</h2>
							<a class="button7" href="<s:url action="searchGroup"/>"><span class="left">Back to Groups</span></a>
						</div>
						<!-- End #wholeAreaHeader -->
						
						<div class="groupDetailsTable">
							<!-- .filterTitle -->
							<div class="filterTitle">
								<div class="filterTitleRight">
									<div class="filterTitleCenter">
										<h3 class="title">Group Details</h3>
									</div><!-- End .filterTitleCenter -->
								</div><!-- End .filterTitleRight -->
							</div>
							<!-- End .filterTitle -->
							
							<div class="groupDetailsTableContainer">
								<table cellpadding="0" cellspacing="0">
									<colgroup>
										<col width="25%" />
										<col width="25%" />
										<col width="25%" />
										<col width="25%" />
									</colgroup>
									<tbody>
										<tr class="firstRow">
											<td class="firstColumn">Group Name:</td>
											<td><s:property value="group.name"/></td>
											<td class="firstColumn">Access Rights:</td>
											<td><s:property value="group.defaultPermission"/></td>
										</tr>
										<tr>
											<td class="firstColumn">Group Authorization Attributes:</td>
											<td>Customer Name</td>
											<td class="firstColumn">Billing Accounts:</td>
											<td>
											<s:iterator value="group.billingAccounts" status="status">
											<s:property value="name"/>
											<s:if test="!#status.last">,</s:if>
											</s:iterator>
											</td>
										</tr>
										<tr>
											<td class="firstColumn">Customer Name:</td>
											<td>Project 1, Project 2, Project 3</td>
											<td class="firstColumn">Resource Restriction:</td>
											<td>Projects</td>
										</tr>
									</tbody>
								</table>
							</div>
							
						</div>
						
						<div class="groupMembersTable">
							<!-- .filterTitle -->
							<div class="filterTitle">
								<div class="filterTitleRight">
									<div class="filterTitleCenter">
										<h3 class="title">Group Members</h3>
									</div><!-- End .filterTitleCenter -->
								</div><!-- End .filterTitleRight -->
							</div>
							<!-- End .filterTitle -->
							
							<div class="groupMembersTableContainer">
								<table cellpadding="0" cellspacing="0" class="normalTableList detailsGroupTable">
									<colgroup>
										<col width="50%" />
										<col width="50%" />
									</colgroup>
									<thead>
										<tr>
											<th>Handle</th>
											<th>Access Type</th>
										</tr>
									</thead>
									<tbody>
										<s:iterator value="group.groupMembers">
										<tr>
											<td>handle<s:property value="userId"/></td>
											<td>Full</td>
										</tr>
										</s:iterator>
									</tbody>
								</table>
							</div>
							
							<div class="groupDetailsButton">
								<a class="newButton1 newButtonGray triggerModal" href="javascript:;" rel="#deleteGroupModal"><span class="btnR"><span class="btnC">DELETE GROUP</span></span></a>
								<a class="newButton1 updateGroupButton" href=
								"
								<s:url action='enterGroupDetailsInUpdate'>
									<s:param name="groupId" value="groupId"/>
								</s:url>
								"
								><span class="btnR"><span class="btnC">UPDATE GROUP</span></span></a>
							</div>
							
						</div>
						
					</div>
				</div>

				<!-- End #mainContent -->
				<div id="footer" class="dashboardFooter">
					<!--Update footer-->
					<div class="socialNetwork">
						<span>Follow us on :</span>
						<a href="http://www.twitter.com/TopCoder" class="twitterIcon" title="Follow us on Twitter"></a>
						<a href="http://www.linkedin.com/company/topcoder" class="linkedInIcon" title="Follow us on LinkedIn"></a>
						<a href="http://www.facebook.com/TopCoderInc" class="facebookIcon" title="Follow us on Facebook"></a>
					</div>
					<!--End socialNetwork-->
					<div class="copyright"> 
						<span>Copyright TopCoder, Inc. 2001-2011</span>
						<a href="http://www.topcoder.com/tc?module=Static" title="Terms of Use">Terms of Use</a>
						<a href="http://www.topcoder.com/tc?module=Static" title="Privacy Policy">Privacy Policy</a>
					</div>
					<!--End copyright-->
				</div>
			
			</div>
			<!-- End #content -->
		</div>
		<!-- End #container -->
	</div>
</div>
<!-- End #wrapper -->

<!-- modal -->
<div id="modalBackground"></div>
<div id="new-modal">

	<!-- #deleteGroupModal -->
	<div id="deleteGroupModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Delete Group
					<a href="javascript:;" class="closeModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="iconNotice"><img src="images/icon-question.png" alt="question" /></div>
			<div class="noticeContent">Are you sure you want to delete group &lt;Group Name&gt; ?</div>
			
			<div class="modalCommandBox">
    			<a href="javascript:;" class="newButton1 triggerNoPreloaderModal" rel="#deleteGroupConfirmModal"><span class="btnR"><span class="btnC">YES</span></span></a>
    			<a href="javascript:;" class="newButton1 newButtonGray closeModal"><span class="btnR"><span class="btnC">NO</span></span></a>
			</div>
		</div>
		<!-- end .modalBody -->
		
		<div class="modalFooter">
			<div class="modalFooterRight">
				<div class="modalFooterCenter"></div>
			</div>
		</div>
		<!-- end .modalFooter -->
	</div>
	<!-- end #deleteGroupModal -->
	
	<!-- #deleteGroupConfirmModal -->
	<div id="deleteGroupConfirmModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Delete Group
					<a href="javascript:;" class="closeModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="iconNotice"><img src="images/icon-question.png" alt="question" /></div>
			<div class="noticeContent">&lt;Group Name&gt; has been deleted.</div>
			
			<div class="modalCommandBox">
    			<a href="<s:url action="searchGroup"/>" class="newButton1"><span class="btnR"><span class="btnC">OK</span></span></a>
			</div>
		</div>
		<!-- end .modalBody -->
		
		<div class="modalFooter">
			<div class="modalFooterRight">
				<div class="modalFooterCenter"></div>
			</div>
		</div>
		<!-- end .modalFooter -->
	</div>
	<!-- end #deleteGroupConfirmModal -->
	
	<!-- #searchModal -->
	<div id="searchModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Search User Handle
					<a href="javascript:;" class="closeModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="searchInputDiv">
				<label>Handle Name:</label>
				<input type="text" class="text" value="Name" />
			</div>
			
			<div class="modalCommandBox">
    			<a href="javascript:;" class="newButton1 searchButton triggerNoPreloaderModal" rel="#searchListModal"><span class="btnR"><span class="btnC">SEARCH</span></span></a>
				<a href="javascript:;" class="newButton1 newButtonGray closeModal"><span class="btnR"><span class="btnC">CANCEL</span></span></a>
			</div>
		</div>
		<!-- end .modalBody -->
		
		<div class="modalFooter">
			<div class="modalFooterRight">
				<div class="modalFooterCenter"></div>
			</div>
		</div>
		<!-- end .modalFooter -->
	</div>
	<!-- end #searchModal -->
	
	<!-- #searchListModal -->
	<div id="searchListModal" class="outLay">
		<div class="modalHeader">
			<div class="modalHeaderRight">
				<div class="modalHeaderCenter">
					Search User Handle
					<a href="javascript:;" class="closeModal" title="Close">Close</a>
				</div>
			</div>
		</div>
		<!-- end .modalHeader -->
		
		<div class="modalBody">
			<div class="searchListDiv">
				<p>10 User Handles Found:</p>
				<table border="0" cellpadding="0" cellspacing="0">
					<colgroup>
						<col width="30" />
						<col width="316" />
					</colgroup>
					<tbody>
						<tr>
							<td class="firstColumn"><input type="checkbox" checked="checked" /></td>
							<td>Handle Name</td>
						</tr>
						<tr>
							<td class="firstColumn"><input type="checkbox" /></td>
							<td>Handle Name</td>
						</tr>
						<tr>
							<td class="firstColumn"><input type="checkbox" /></td>
							<td>Handle Name</td>
						</tr>
						<tr>
							<td class="firstColumn"><input type="checkbox" /></td>
							<td>Handle Name</td>
						</tr>
						<tr>
							<td class="firstColumn"><input type="checkbox" /></td>
							<td>Handle Name</td>
						</tr>
						<tr>
							<td class="firstColumn"><input type="checkbox" /></td>
							<td>Handle Name</td>
						</tr>
						<tr>
							<td class="firstColumn"><input type="checkbox" /></td>
							<td>Handle Name</td>
						</tr>
						<tr>
							<td class="firstColumn"><input type="checkbox" /></td>
							<td>Handle Name</td>
						</tr>
						<tr>
							<td class="firstColumn"><input type="checkbox" /></td>
							<td>Handle Name</td>
						</tr>
						<tr>
							<td class="firstColumn"><input type="checkbox" /></td>
							<td>Handle Name</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<div class="modalCommandBox">
    			<a href="javascript:;" class="newButton1 searchButton closeModal"><span class="btnR"><span class="btnC">ADD USER TO GROUP</span></span></a>
				<a href="javascript:;" class="newButton1 searchButton triggerNoPreloaderModal" rel="#searchModal"><span class="btnR"><span class="btnC">SEARCH AGAIN</span></span></a>
				<a href="javascript:;" class="newButton1 newButtonGray closeModal"><span class="btnR"><span class="btnC">CANCEL</span></span></a>
			</div>
		</div>
		<!-- end .modalBody -->
		
		<div class="modalFooter">
			<div class="modalFooterRight">
				<div class="modalFooterCenter"></div>
			</div>
		</div>
		<!-- end .modalFooter -->
	</div>
	<!-- end #searchListModal -->
	
</div>
<!-- end modal -->



</body>
<!-- End #page -->
</html>
