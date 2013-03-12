<%@ page session="true" language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.sumilux.ssi.client.Idme,com.sumilux.ssi.client.json.JSONObject"%>
<%
	String stat = request.getParameter("stat") == null ? null : (String) request.getParameter("stat");
	JSONObject jo = null;
	JSONObject joFromAuthSource = null;
	String errorMsg = null;
	String authSource = "";
	if (stat != null && "ok".equals(stat)) {
		String token = request.getParameter("ssi_token");
		request.getSession().setAttribute("ssi_token", token);
		Idme idme = new Idme(token);
		jo = idme.getUserProfile();
		System.out.println(jo.toString());
		jo = idme.getUserProfile();
		authSource = "dummy";
	} else {
		errorMsg = request.getParameter("errMsg");
	}
	if(stat == null) {
		System.out.println("stat is null");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet" href="http://ssi.sumilux.com/ssi/download/ssi.css">
<script type="text/javascript">
window.SSI={
    tokenUrl:"http://dev95.sumilux.com:8090/ssi/index.jsp", // replace __TOKEN_URL__ with your own callback URL
    appName:"gnt",
    sig:"99d7805cc0aefbb688b11d40485fe50a",
    owaUrl:"http://dev95.sumilux.com:8670/smx/owa",
    v:"v0.7.15",
    style:"small-icon",
    options:{}
};
(function(){
    var f=document.createElement("script");
    f.type="text/javascript"; f.src="http://dev95.sumilux.com:8670/smx/js/ssi.js";
    var h=document.getElementsByTagName("script")[0];
    h.parentNode.insertBefore(f, h);
})();
</script>
</head>
<body>
<h1>Social-Sign-In Sample</h1>
    <%if(stat == null) {%>
<div id="smx_ssi"></div>
	<%
		} else if ("ok".equals(stat)) {
	%>
	</BR> Welcome <%=jo.getString("displayName")%>, Verified by <%= authSource%></BR>
	<a href="profile.jsp">Identity Info</a></br>

	<%
		} else {
	%>
	Sign In Failed:
	</br> error message:<%=errorMsg%></br>
	<%
		}
	%>

</body>
</html>
