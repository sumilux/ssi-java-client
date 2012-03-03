<%@ page session="true" language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.sumilux.idme.sdk.Idme,com.sumilux.idme.json.JSONObject"%>
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
		System.out.println(idme.getUserProfile().toString());
		jo = idme.getUserProfile().getJSONObject("data");
		authSource = idme.getMyAuthSource().getString("data");
	} else {
		errorMsg = request.getParameter("errMsg");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet" href="http://demo.sumilux.com/ssi/download/ssi.css">
<script type="text/javascript">
window.SSI={
    tokenUrl:"http://172.25.1.96:8090/ssi/index.jsp", // replace __TOKEN_URL__ with your own callback URL
    appName:"WidgetSample",
    sig:"d2e6116e892af6a970167f9a3dcf0ea9",
    owaUrl:"http://idmedemo.sumilux.com/smx/owa",
    v:"v0.7.15",
    style:"small-icon",
    options:{}
};
(function(){
    var f=document.createElement("script");
    f.type="text/javascript"; f.src="http://demo.sumilux.com/ssi/download/ssi.js";
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
	<a href="photo.jsp?authsource=<%=authSource%>" >Photo Info</a></br>

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
