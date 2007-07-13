<%@ page import="com.topcoder.user.profile.UserProfile,
				 com.orpheus.user.persistence.UserConstants" %>

<%  String state = (String)profile.getProperty(UserConstants.ADDRESS_STATE); %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${not empty requestScope['state.error']}">
  <span class="fBold cRed">${requestScope['state.error']}</span><br/>
</c:if>
<select name="state" id="state" class="dropDown" style="width:150px;">
    <option value=""></option>
    <option value="AL" <% if ("AL".equals(state)) { %>selected<% } %>>Alabama</option>
    <option value="AK" <% if ("AK".equals(state)) { %>selected<% } %>>Alaska</option>
    <option value="AZ" <% if ("AZ".equals(state)) { %>selected<% } %>>Arizona</option>
    <option value="AR" <% if ("AR".equals(state)) { %>selected<% } %>>Arkansas</option>
    <option value="CA" <% if ("CA".equals(state)) { %>selected<% } %>>California</option>
    <option value="CO" <% if ("CO".equals(state)) { %>selected<% } %>>Colorado</option>
    <option value="CT" <% if ("CT".equals(state)) { %>selected<% } %>>Connecticut</option>
    <option value="DE" <% if ("DE".equals(state)) { %>selected<% } %>>Delaware</option>
    <option value="DC" <% if ("DC".equals(state)) { %>selected<% } %>>Dist of Columbia</option>
    <option value="FL" <% if ("FL".equals(state)) { %>selected<% } %>>Florida</option>
    <option value="GA" <% if ("GA".equals(state)) { %>selected<% } %>>Georgia</option>
    <option value="HI" <% if ("HI".equals(state)) { %>selected<% } %>>Hawaii</option>
    <option value="ID" <% if ("ID".equals(state)) { %>selected<% } %>>Idaho</option>
    <option value="IL" <% if ("IL".equals(state)) { %>selected<% } %>>Illinois</option>
    <option value="IN" <% if ("IN".equals(state)) { %>selected<% } %>>Indiana</option>
    <option value="IA" <% if ("IA".equals(state)) { %>selected<% } %>>Iowa</option>
    <option value="KS" <% if ("KS".equals(state)) { %>selected<% } %>>Kansas</option>
    <option value="KY" <% if ("KY".equals(state)) { %>selected<% } %>>Kentucky</option>
    <option value="LA" <% if ("LA".equals(state)) { %>selected<% } %>>Louisiana</option>
    <option value="ME" <% if ("ME".equals(state)) { %>selected<% } %>>Maine</option>
    <option value="MD" <% if ("MD".equals(state)) { %>selected<% } %>>Maryland</option>
    <option value="MA" <% if ("MA".equals(state)) { %>selected<% } %>>Massachusetts</option>
    <option value="MI" <% if ("MI".equals(state)) { %>selected<% } %>>Michigan</option>
    <option value="MN" <% if ("MN".equals(state)) { %>selected<% } %>>Minnesota</option>
    <option value="MS" <% if ("MS".equals(state)) { %>selected<% } %>>Mississippi</option>
    <option value="MO" <% if ("MO".equals(state)) { %>selected<% } %>>Missouri</option>
    <option value="MT" <% if ("MT".equals(state)) { %>selected<% } %>>Montana</option>
    <option value="NE" <% if ("NE".equals(state)) { %>selected<% } %>>Nebraska</option>
    <option value="NV" <% if ("NV".equals(state)) { %>selected<% } %>>Nevada</option>
    <option value="NH" <% if ("NH".equals(state)) { %>selected<% } %>>New Hampshire</option>
    <option value="NJ" <% if ("NJ".equals(state)) { %>selected<% } %>>New Jersey</option>
    <option value="NM" <% if ("NM".equals(state)) { %>selected<% } %>>New Mexico</option>
    <option value="NY" <% if ("NY".equals(state)) { %>selected<% } %>>New York</option>
    <option value="NC" <% if ("NC".equals(state)) { %>selected<% } %>>North Carolina</option>
    <option value="ND" <% if ("ND".equals(state)) { %>selected<% } %>>North Dakota</option>
    <option value="OH" <% if ("OH".equals(state)) { %>selected<% } %>>Ohio</option>
    <option value="OK" <% if ("OK".equals(state)) { %>selected<% } %>>Oklahoma</option>
    <option value="OR" <% if ("OR".equals(state)) { %>selected<% } %>>Oregon</option>
    <option value="PA" <% if ("PA".equals(state)) { %>selected<% } %>>Pennsylvania</option>
    <option value="RI" <% if ("RI".equals(state)) { %>selected<% } %>>Rhode Island</option>
    <option value="SC" <% if ("SC".equals(state)) { %>selected<% } %>>South Carolina</option>
    <option value="SD" <% if ("SD".equals(state)) { %>selected<% } %>>South Dakota</option>
    <option value="TN" <% if ("TN".equals(state)) { %>selected<% } %>>Tennessee</option>
    <option value="TX" <% if ("TX".equals(state)) { %>selected<% } %>>Texas</option>
    <option value="UT" <% if ("UT".equals(state)) { %>selected<% } %>>Utah</option>
    <option value="VT" <% if ("VT".equals(state)) { %>selected<% } %>>Vermont</option>
    <option value="VA" <% if ("VA".equals(state)) { %>selected<% } %>>Virginia</option>
    <option value="WA" <% if ("WA".equals(state)) { %>selected<% } %>>Washington</option>
    <option value="WV" <% if ("WV".equals(state)) { %>selected<% } %>>West Virginia</option>
    <option value="WI" <% if ("WI".equals(state)) { %>selected<% } %>>Wisconsin</option>
    <option value="WY" <% if ("WY".equals(state)) { %>selected<% } %>>Wyoming</option>
    <%-- Canadian provinces --%>
    <option value="Alberta" <% if ("Alberta".equals(state)) { %>selected<% } %>>Alberta</option>
    <option value="British Columbia" <% if ("British Columbia".equals(state)) { %>selected<% } %>>British Columbia</option>
    <option value="Manitoba" <% if ("Manitoba".equals(state)) { %>selected<% } %>>Manitoba</option>
    <option value="New Brunswick" <% if ("New Brunswick".equals(state)) { %>selected<% } %>>New Brunswick</option>
    <option value="Newfoundland and Labrador" <% if ("Newfoundland and Labrador".equals(state)) { %>selected<% } %>>Newfoundland and Labrador</option>
    <option value="Nova Scotia" <% if ("Nova Scotia".equals(state)) { %>selected<% } %>>Nova Scotia</option>
    <option value="Ontario" <% if ("Ontario".equals(state)) { %>selected<% } %>>Ontario</option>
    <option value="Prince Edward Island" <% if ("Prince Edward Island".equals(state)) { %>selected<% } %>>Prince Edward Island</option>
    <option value="Quebec" <% if ("Quebec".equals(state)) { %>selected<% } %>>Quebec</option>
    <option value="Saskatchewan" <% if ("Saskatchewan".equals(state)) { %>selected<% } %>>Saskatchewan</option>
    <option value="NorthWest Territories" <% if ("NorthWest Territories".equals(state)) { %>selected<% } %>>NorthWest Territories</option>
    <option value="Nunavut" <% if ("Nunavut".equals(state)) { %>selected<% } %>>Nunavut</option>
    <option value="Yukon Territory" <% if ("Yukon Territory".equals(state)) { %>selected<% } %>>Yukon Territory</option>
</select>
