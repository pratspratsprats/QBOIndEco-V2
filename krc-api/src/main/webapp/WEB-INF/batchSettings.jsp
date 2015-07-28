<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored ="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Connected to Intuit</title>
<script type="text/javascript">
		  window.close();
		 window.onunload = refreshParent;
		 function refreshParent() {
		    window.opener.location.reload();
		}


</script>
</head>
  <b><body><h3 style=style="float:right">Customer Connection Details</h3></b>
<hr size="4" color="gray"/>
<% String companyId=request.getParameter("companyId");
%>

<form action="${pageContext.request.contextPath}/AccessToken?action=saveBatchSettings" method="post">
Sync Frequency (in Minutes)::<br>
<input type="text" name="repeatinterval" >
<br>
Stat Delay (in Minutes):<br>
<input type="text" name="startDelay"><br>

<br><br>
<input type="submit" value="Submit">
<input type="hidden" value="<%=companyId%>" name="companyId">
</form>
</body>
</html>

