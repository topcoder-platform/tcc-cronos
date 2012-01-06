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
							<h2 class="groupTitle">Groups</h2>
						</div>
						<!-- End #wholeAreaHeader -->
						
						<!-- .topFilterBox --> 
						<div class="topFilterBox">
							
							<!-- .filterTitle -->
							<div class="filterTitle">
								<div class="filterTitleRight">
									<div class="filterTitleCenter">
										<a href="javascript:;" class="expanded"></a>
										<h4>Search</h4>
									</div><!-- End .filterTitleCenter -->
								</div><!-- End .filterTitleRight -->
							</div>
							<!-- End .filterTitle -->
							
							<form id="filter_form" action="<s:url action="searchGroup"/>" method="post">
							<!-- .filterContainer -->
							<div class="filterContainer">
								<dl class="filterUserGroup">
									<dd>
										<div class="leftSide">
											<span class="label">Group Name:</span>
											<input type="text" name="criteria.groupName" class="text" />
										</div>
										
										<div class="rightSide">
											<span class="label">Access Rights:</span>
											<div class="secondColumn">
												<input type="radio" name="radio1" id="readRadio" />
												<label for="readRadio">Read</label>
												<input type="radio" name="radio1" id="writeRadio" />
												<label for="writeRadio">Write</label>
												<input type="radio" name="radio1" id="fullRadio" />
												<label for="fullRadio">Full</label>
											</div>
										</div>
										<div class="clearFixed"></div>
									</dd>
									<dd>
										<div class="leftSide">
											<span class="label">Customer Name:</span>
											<input type="text" name="criteria.clientName" class="text" />
										</div>
										
										<div class="rightSide">
											<span class="label">Account Name:</span>
											<input type="text" name="criteria.billingAccountName" class="text" />
										</div>
										<div class="clearFixed"></div>
									</dd>
									<dd>
										<div class="leftSide">
											<span class="label">Project Name:</span>
											<input type="text" name="criteria.projectName" class="text" />
										</div>
										
										<div class="rightSide">
											<span class="label">Group Member Name:</span>
											<input type="text" name="criteria.groupMemberHandle" class="text" />
										</div>
										
										<div class="clearFixed"></div>
									</dd>
								</dl>
								<div class="filterContainerCommand">
									<a class="button6" href="javascript:$('#filter_form').submit()"><span class="left"><span class="right">SEARCH</span></span></a>
								</div>
							</div>
							<!-- End .filterContainer -->
							</form>
							
						</div>
						<!-- End .topFolderBox -->
						
						<div class="mainContent">
							<div class="tableControlBox tableControlBoxTop">
								<div class="leftSide">
									<a class="button7 createNewGroup" href="<s:url action="createGroup"/>"><span class="left">Create New Group</span></a>
								</div>
								<div class="rightSide">
									<a class="button7" href="<s:url action="viewGroup"/>"><span class="left">View Group</span></a>
									<a class="button7" href="<s:url action="updateGroup"/>"><span class="left">Update Group</span></a>
									<a class="button7 grayButton7 triggerModal" href="javascript:;" rel="#deleteGroupModal"><span class="left">Remove Group</span></a>
								</div>
							</div>
							<!-- End .tableControlBox -->
							
							<div class="tableGroupWrapper">
								<table cellpadding="0" cellspacing="0" class="normalTableList">
									<colgroup>
										<col width="3%" />
										<col width="15%" />
										<col width="10%" />
										<col width="10%" />
										<col width="13%" />
										<col width="22%" />
										<col width="15%" />
										<col width="12%" />
									</colgroup>
									<thead>
										<tr>
											<th>&nbsp;</th>
											<th>Group Name</th>
											<th>Access Rights</th>
											<th>Customer</th>
											<th>Account</th>
											<th>Project</th>
											<th>Resource Restrictions</th>
											<th>Group Members</th>
										</tr>
									</thead>
									<tbody>
										<s:iterator value="groups.values" status="status">
											<tr <s:if test="#status.first">class="firstRow"</s:if>>
											<td><input type="radio" name="userGroupSelectRadio" value="<s:property value="id"/>" <s:if test="#status.first">checked="checked"</s:if> /></td>
											<td><a href="<s:url action="viewGroup"><s:param name="groupId" value="id"/></s:url>"><s:property value="name"/></a></td>
											<td><s:property value="defaultPermission"/></td>
											<td><s:property value="client.name"/></td>
											<td>
											<s:iterator value="billingAccounts" status="status">
												<s:property value="name"/>
												<s:if test="!#status.last">,</s:if>
											</s:iterator>
											</td>
											<td>
											<s:iterator value="directProjects" status="status">
												<s:property value="name"/>
												<s:if test="!#status.last">,</s:if>
											</s:iterator>
											</td>
											<td>
											<s:iterator value="restrictions" status="status">
												<s:property/>
												<s:if test="!#status.last">,</s:if>
											</s:iterator>
											</td>
											<td>
											<s:iterator value="groupMembers" status="status">
												<s:property value="userId"/>
												<s:if test="!#status.last"><br/></s:if>
											</s:iterator>
											</td>
											</tr>
										</s:iterator>
									</tbody>
								</table>
							</div>
							
							<div class="tableControlBox tableControlBoxBottom">
								<div class="leftSide">
									<a class="button7 createNewGroup" href="<s:url action="createGroup"/>"><span class="left">Create New Group</span></a>
								</div>
								<div class="rightSide">
									<a class="button7" href="<s:url action="viewGroup"/>"><span class="left">View Group</span></a>
									<a class="button7" href="<s:url action="updateGroup"/>"><span class="left">Update Group</span></a>
									<a class="button7 grayButton7 triggerModal" href="javascript:;" rel="#deleteGroupModal"><span class="left">Remove Group</span></a>
								</div>
							</div>
							<!-- End .tableControlBox -->
							
							<div class="tableControlPage">
								<div class="leftSide">Showing <strong>1</strong> to <strong>10</strong> of <strong><s:property value="groups.total"/></strong> entries</div>
								<div class="rightSide">
									<strong class="label">Show:</strong>
									<select name="pageSize">
										<option value="10">10</option>
										<option value="25">25</option>
										<option value="50">50</option>
										<option value="100">All</option>
									</select>
									<span class="label">per Page</span>
									<ul>
										<li><a href="javascript:;" class="prevPage disable">Prev</a></li>
										<li><strong>1</strong></li>
										<li><a href="javascript:;">2</a></li>
										<li><a href="javascript:;" class="nextPage">Next</a></li>
									</ul>
								</div>
								<span class="corner bl"></span>
								<span class="corner br"></span>
							</div>
							<!-- End .tableControlPage -->
							
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
    			<a href="javascript:;" class="newButton1 closeModal"><span class="btnR"><span class="btnC">OK</span></span></a>
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
	
</div>
<!-- end modal -->



</body>
<!-- End #page -->
</html>
<s:debug/>
