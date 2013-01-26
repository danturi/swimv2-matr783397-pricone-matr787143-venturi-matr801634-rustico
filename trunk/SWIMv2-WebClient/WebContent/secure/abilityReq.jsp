<%@ page import="sessionbeans.logic.UserBeanRemote" %> 
<%@ page import="sessionbeans.logic.SwimResponse" %>
<%@ page import="entities.User" %>
<%@ page import="entities.AbilityRequest" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.naming.InitialContext" %> 
<%@ page import="javax.naming.Context" %> 
<%@page import="java.security.Principal"%> <%@page contentType="text/html"pageEncoding="UTF-8"%><%@ taglib uri='http://java.sun.com/jsp/jstl/core'prefix='c'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<html>
	<head>
	<!-- TemplateBeginEditable name="doctitle" -->
	<title>SWIMv2</title>
	
	<meta name="description"
	content="Designed and developed by Codify Design Studio - codifydesign.com" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/images/stylesheet.css" />
<link href="<%=request.getContextPath()%>/SpryAssets/SpryMenuBarHorizontal.css" rel="stylesheet"
	type="text/css" />
	<script src="<%=request.getContextPath()%>/js/json2.js"
	type="text/javascript"></script>

<%@ include file="/WEB-INF/includes/head/jquery.jsp"%>
	<script type="text/javascript">
        $(function(){
            "use strict";
            $('#logoutLink').click(function(){
                 
                var destinationUrl = this.href;
                 
                $.ajax({
                    url: destinationUrl,
                    type: "GET",
                    cache: false,
                    dataType: "json",
                         
                    success: function (data, textStatus, jqXHR){
                        //alert("success");
                        if (data.status == "SUCCESS" ){
                            //redirect to welcome page
                            window.location.replace("http://"+window.location.host+"<%=request.getContextPath()%>/index.jsp");
                        }else{
                            alert("failed");
                        }
                    },
                         
                    error: function (jqXHR, textStatus, errorThrown){
                        alert("Errore di logout. HTTP STATUS: "+jqXHR.status);
                    },
                         
                    complete: function(jqXHR, textStatus){
                        //alert("complete");
                    }                    
                });
                 
                return false;
            });
        });
        </script>
</head>
<body>
	<%UserBeanRemote userBean;
	 Context context = new
	InitialContext(); userBean = (UserBeanRemote)
	context.lookup(UserBeanRemote.class.getName());
	
	%>
	
		<div class="bannerArea">
		<div class="container">
		<%
		if(request.getUserPrincipal().getName()!=null){
			User usrPr = userBean.find(request.getUserPrincipal().getName());
			if(usrPr!=null){
				out.write("<div class=\"bannernav\">Ciao, "+usrPr.getFirstname()+".</div>");
			}
		}
		%>
			
			<div class="toplogo">
				<img src="<%=request.getContextPath()%>/images/GIMP-file/swim-titolo_b.png"
							width="223" height="51" alt="titolo" />
			</div>
			<div style="clear: both;"></div>
		</div>
	</div>
		<div class="topnavigationArea">
			<div class="container"><!-- TemplateBeginEditable name="menï¿½" -->
			  <div class="topnavigationgroup">
			    <ul id="MenuBar1" class="MenuBarHorizontal">
							<li style="border-right-style: solid;"><a id="logoutLink"
						href="<%=request.getContextPath()%>/services/auth/logout">LOGOUT</a></li>
					<li><a href="<%=request.getContextPath()%>/secure/friendsList.jsp">AMICI</a></li>
					<li><a href="<%=request.getContextPath()%>/secure/profile.jsp?user=<%=request.getUserPrincipal().getName()%>">PROFILO</a></li>
					<li><a href="<%=request.getContextPath()%>/secure/helpSearch.jsp">CERCA UTENTI</a></li>
					<li class="MenuBarHorizontal"><a href="<%=request.getContextPath()%>/secure/homeUser.jsp"
						title="home" target="_parent">HOME</a></li>
				</ul>
		    </div>
			<!-- TemplateEndEditable -->
			  <div style="clear:both;"></div>
			</div>
		</div>
		<div class="contentArea">
			<div class="container">
<div class="contentleft">
      
      <div class="middle">
      		
      		<%
      		      			if (request.getUserPrincipal().getName() != null) {
      		      				User usr = userBean.find(request.getUserPrincipal().getName());
      		      				if (usr != null) {
      		      					boolean newRequest = false;
									List<AbilityRequest> abilityReqList = (List<AbilityRequest>) userBean.getAbilityReqList(usr.getEmail()).getData();
      		      					if(abilityReqList!=null){
      		      						if(!abilityReqList.isEmpty()){
      		      						out.write("<h2>Stato delle tue richieste di abilitazione:</h2>");

      		      						for (AbilityRequest areq : abilityReqList) {
      		      							
      		      								out.write("<table width=\"100%\" border=\"\" cellpadding=\"8\" cellspacing=\"5\">");
      		      								out.write("<tr>");
      		      								out.write("<td width=\"50%\" align=\"center\"><h1>"+areq.getAbilityId().getDescription()+"</h1></td>");
      		      								if(areq.getIsEvaluated()){
      		      									if(areq.getAcceptanceStatus()==true){
          		      									out.write("<td width=\"50%\" align=\"center\"><h1><span style=\"color: green;\">Accettata</h1></td>");
      		      									} else {
              		      								out.write("<td width=\"50%\" align=\"center\"><h1><span style=\"color: red;\">Rifiutata</h1></td>");

      		      									}
      		      								} else {
          		      								out.write("<td width=\"50%\" align=\"center\"><h1><span style=\"color: black;\">Non ancora valutata</h1></td>");

      		      								}
      		      								out.write("</tr>");
      		      								out.write("</table>");
      		      								out.write("<h3>&nbsp;</h3>");
      		      							
      		      						}
      		      						} else {
      		                          	out.write("<h2>Non hai ancora richiesto nessuna abilitazione.<h2>");

      		      						}
      		      						
      		      					} else {
          		      					out.write("<h2><span style=\"color: red;\">Si è verificato un problema. Utente non valido.</span><h2>");
      		      					}
      		      				} else {
      		      					out.write("<h2><span style=\"color: red;\">Si è verificato un problema. Utente non valido.</span><h2>");

      		      				}
      		      			} else {
      		      				out.write("<h2><span style=\"color: red;\">Si è verificato un problema. Utente non valido.</span><h2>");
      		      			}
      		      		%>
   
                  <h3>&nbsp;</h3>
                  
      </div>
     
</div>
			<div class="contentright">
				<h2>&nbsp;</h2>
				<h2>Le tue notifiche:</h2>
				<p>&nbsp;</p>
				<p>
					<a href="<%=request.getContextPath()%>/secure/showHelpReq.jsp">Richieste di aiuto</a>
				</p>
				<p>&nbsp;</p>
				<p><a href="<%=request.getContextPath()%>/secure/friendReq.jsp">Richieste di amicizia</a></p>
				<p>&nbsp;</p>
				<p><a href="<%=request.getContextPath()%>/secure/abilityReq.jsp">
					Richieste abilit&agrave;</a><img src="<%=request.getContextPath()%>/images/omino_msg.jpg"
						alt="omino_msg" width="158" height="165" align="right" />
				</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
			</div>
			<div style="clear:both;"></div>
		  </div>
		</div>
<div class="footerArea">
			<div class="container">
<div class="copyright">&copy; 2013 SWIMv2 - Social Network by Marco Pricone,Venturi Davide,Rustico Sebastiano.  All rights reserved.</div>
</div>
		</div>
		
		
	</body>
</html>
