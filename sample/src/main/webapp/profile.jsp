<%@ page session="true" language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.sumilux.ssi.client.Idme,com.sumilux.ssi.client.json.JSONObject"%>
<%
	String token = request.getSession().getAttribute("ssi_token") == null ? null : (String) request.getSession().getAttribute("ssi_token");
    JSONObject jo = new JSONObject();
    if(token != null) {
		Idme idme = new Idme(token);
		jo = idme.getUserProfile().getJSONObject("data");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
</head>
<body>
<h1>User Profile</h1>
<%=jo.toString() %></br>
<a href="index.jsp?stat=ok&ssi_token=<%=token%>">Go Back</a></br>
</body>
</html>
