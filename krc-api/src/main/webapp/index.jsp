
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List"%>
<%@ page isELIgnored ="false" %>



<html xmlns="http://www.w3.org/1999/xhtml" xmlns:ipp="">
  <head><meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
     <title>My Connect Page</title>
     <% List customerList = (List)session.getAttribute("customerList");%>
     <script type="text/javascript" src="https://appcenter.intuit.com/Content/IA/intuit.ipp.anywhere-1.3.2.js">
        </script>
     <script>intuit.ipp.anywhere.setup({
          menuProxy: '',
          grantUrl: 'http://localhost:8080/api/RequestToken'});
     </script>
     <script type="text/javascript">
     function changeSyncStatus(id){
     var all = document.getElementById(id)
     var companyId= document.getElementById(id).id;
        
    if(all.checked == true){
        all.checked = true;
        document.location.href="${pageContext.request.contextPath}/AccessToken?action=changeSyncStatus&syncStatus=true&companyId="+companyId;
    }else{
        all.checked = false;
         document.location.href="${pageContext.request.contextPath}/AccessToken?action=changeSyncStatus&syncStatus=false&&companyId="+companyId;
   		
    }
    
     
     }
     
      </script>
   </head>
  <b><body><h3 style=style="float:right">Customer Connection Details</h3></b>
  
  <%if(request.getSession(true).getAttribute("Message")!=null){%>

<Span style="color:red;"><%=request.getSession(true).getAttribute("Message")%> </span>


<%}%>
<hr size="4" color="gray"/>
<table border="1" style="width:70%">


   <tr>
   <th style="width:45%">Shop Domain Name</th>
   <th style="width:25%">Connection Status</th>
    <th style="width:25%">Sync Settings</th>
    <th style="width:25%">Sync Status</th>
 	</tr>

    <c:forEach items="${customerList}" var="customer">
    
        <tr>
            <td style="width:45%"><c:out value="${customer.shopDomain}"/></td>
             
           
             
               <c:if test="${customer.tokenValidationMessage =='NotConnected'}">
                <td style="width:25%"><ipp:connectToIntuit></ipp:connectToIntuit></td>
               </c:if>
              
               <c:if test="${customer.tokenValidationMessage =='Connected'}">
                <td style="width:25%"><c:out value="${customer.tokenValidationMessage}"/></td>
               </c:if>
             <td>
              <c:if test="${customer.syncStatus}">
             <form action="${pageContext.request.contextPath}/AccessToken" method="post">
             <input type="hidden" value="cofigureBatch" name="action"/>
             <input type="hidden" value="${customer.qbCompanyId}" name="companyId">
    		 <input type="submit" name="Cofigure Batch" value="Cofigure Batch" />
				</form>
				</c:if> 
				<c:if test="${!customer.syncStatus}">
			 <input type="submit" name="Cofigure Batch" value="Cofigure Batch" disabled="true" />
			</c:if>  
			</td>
			
			  
			<td>
			<c:if test="${customer.syncStatus}">
			<input type="checkBox" id="${customer.qbCompanyId}" onchange="changeSyncStatus(this.id);" checked="checked" value="${customer.qbCompanyId}"/>
			
			</c:if>
			<c:if test="${!customer.syncStatus}">
			<input type="checkBox" id="${customer.qbCompanyId}" onchange="changeSyncStatus(this.id);" value="${customer.qbCompanyId}"/>
			
			</c:if>
			
			</td>
			      
        </tr>
    </c:forEach>
</table>
     <!--  <ipp:connectToIntuit></ipp:connectToIntuit> -->
     
    
   </body>
</html>
