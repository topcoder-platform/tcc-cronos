<%@ taglib prefix="s" uri="/struts-tags" %>
<div id="header"> <a href="javascript:;" class="logo">Groups</a>
					<div id="tabs0">
						<!-- the left tabs -->
						<ul>
							<li> <a href="javascript:;"><span>Dashboard</span></a> </li>
							<li> <a href="javascript:;"><span>Projects</span></a> </li>
							<li> <a href="javascript:;"><span>CoPilots</span></a> </li>
							<li class="on"> <a href="javascript:;"><span>Groups</span></a> </li>
						</ul>
					</div>
					<!-- End #tabs0 -->
					<div class="helloUser">
						<ul>
							<li> <strong>Hello</strong> <a href="javascript:;" class="coderTextGreen">lunarkid</a> | </li>
							<li class="helloUserLink"> <a href="javascript:;">Logout</a>| </li>
							<li class="helloUserLink"> <a href="javascript:;" class="helloUserLinkTarget" >Help</a> </li>
						</ul>
					</div>
					<!-- End .helloUSer -->
					<div>
						<!-- TC Logo -->
						<a href="http://community.topcoder.com" class="TCLogo"><img src="images/tc-logo.png" alt="TopCoder"></a>
						<p class="lookCP dashBoardLookCP">Looking for Community Portal? <a href="javascript:;" onclick="window.open('http://topcoder.com/home/');"><strong>Go There Now</strong></a></p>
					</div>
					<div id="tabs1">
						<ul>
							<li class="${param.tabLoc == 'first' ? 'on' : ''} firstTabs"> <a href="<s:url action="searchGroup"/>"><span class="dashboardSpan">Groups</span></a> </li>
							<li class="${param.tabLoc == 'second' ? 'on' : ''} secondTabs"><a href="todo_administrator-send-group-invitation.html"><span class="dashboardSpan">Send Group Invitation</span></a> </li>
							<li class="${param.tabLoc == 'third' ? 'on' : ''} thirdTabs"> <a href="todo_administrator-view-pending-approval.html"><span class="dashboardSpan">Pending Approvals</span></a> </li>
							<li class="${param.tabLoc == 'forth' ? 'on' : ''} forthTabs"> <a href="todo_administrator-create-new-administrator.html"><span class="dashboardSpan">Create New Administrator User</span></a> </li>
							<li class="${param.tabLoc == 'fifth' ? 'on' : ''} fifthTabs"> <a href="todo_administrator-acc-auditing-information.html"><span class="dashboardSpan">Audit Information</span></a> </li>
							<li class="${param.tabLoc == 'sixth' ? 'on' : ''} sixthTabs"> <a href="todo_administrator-fetch-registration-url.html"><span class="dashboardSpan">Fetch Registration URL</span></a> </li>
						</ul>
					</div>
                </div>
				<!-- End #header -->
