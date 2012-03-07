<%@ page session="true" language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.sumilux.ssi.client.Idme,com.sumilux.ssi.client.json.JSONObject,com.sumilux.ssi.client.json.JSONArray"%>
<%
	String token = request.getSession().getAttribute("ssi_token") == null ? null : (String) request.getSession().getAttribute("ssi_token");
    String authSource = request.getParameter("authsource");
	JSONObject joFromAuthSource = null;
    JSONObject jo = new JSONObject();
    String imageURL = "";
    JSONArray photoList = null;
    if(token != null) {
		Idme idme = new Idme(token);
		joFromAuthSource = idme.getIdentityAttr();
		if("sina".equals(authSource)) {
	    	imageURL = joFromAuthSource.getJSONObject("data").getString("profile_image_url");
	    } else if ("kaixin".equals(authSource)) {
	    	System.out.println(joFromAuthSource.toString());
	    	imageURL = joFromAuthSource.getString("logo50");
	    	if(idme.getUserAlbumList("YinQuDuo").getJSONObject("data").has("data")) {
	    	JSONArray joAlbumList = (JSONArray)idme.getUserAlbumList("YinQuDuo").getJSONObject("data").get("data");
	    	if(joAlbumList.length() > 0) {
	    		String albumid = ((JSONObject)joAlbumList.get(0)).get("albumid").toString();
	    		photoList = (JSONArray)idme.getUserPhotos("YinQuDuo", albumid, null, null).get("data");
	    	}
	    	}
	    } else if("renren".equals(authSource)) {
	    	imageURL = joFromAuthSource.getString("mainurl");
	    }
	   
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
</head>
<body>
<h1>User Photo</h1>
My Image:<img src=<%=imageURL%> alt='' border='none' /></br>

<p>Photo List</p>
<%if(photoList != null) {
	for(int i = 0; i < photoList.length(); i++) {
        String purl = ((JSONObject)photoList.get(0)).getString("pic");%>
        <img src=<%=purl%> alt='' border='none' /></br>
    <%} 
}%>
<a href="index.jsp?stat=ok&ssi_token=<%=token%>">Go Back</a></br>
</body>
</html>
