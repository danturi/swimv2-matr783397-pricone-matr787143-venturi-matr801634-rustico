<%@ page import="sessionbeans.logic.UserBeanRemote" %>
<%@ page import="sessionbeans.logic.SwimResponse" %>
<%@ page import="entities.User" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.Context" %>
<%@page import="java.security.Principal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%><%@ taglib
	uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
        <script type="text/javascript" src="<%=request.getContextPath()%>/list.js"></script>
	
</head>
	<body>
	<%UserBeanRemote userBean;%> 
	<% Context context = new
	InitialContext(); userBean = (UserBeanRemote)
	context.lookup(UserBeanRemote.class.getName());
	SwimResponse friendListRsp = userBean.getFullFriendsList(request.getUserPrincipal().getName());
	List<User> userList = null;
	if(friendListRsp.getStatus()==SwimResponse.SUCCESS){
		userList = (List<User>) friendListRsp.getData();
	}
	
	System.out.println("****** QUI EJB CALL DA JSP ******");
	//userBean.sendFriendshipReq("aa", "bb"); %>
	
		<div class="bannerArea">
			<div class="container">
			<div class="bannernav">Sei loggato come <%=request.getUserPrincipal().getName() %>.</div>
			  <div class="toplogo"><a href="#"><a href="#"></a><img src="<%=request.getContextPath()%>/images/GIMP-file/swim-titolo_b.png" width="223" height="51" alt="titolo" /></div>
              <div style="clear:both;"></div>
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
           
								<% if(userList!=null){
									if(!userList.isEmpty()){
										
										out.write("<div class=\"middle\">");
										out.write("<h1>Ecco i tuoi amici</h1>");
										out.write("<p>&nbsp;</p>");
										out.write("<div id=\"user-list\">");
										out.write("<input class=\"search\" size=\"30\" placeholder=\"Cerca utente\" />");
										out.write("<ul class=\"sort-by\">");
										out.write("<li class=\"sort btn\" data-sort=\"name\">Ordina per nome (A/Z - Z/A)</li>");
										out.write("</ul>");
										out.write("<ul class=\"filter\">");
										out.write("</ul>");
										out.write("<div class=\"wrapper\">");
										out.write("<ul class=\"list\">");
									
										for(User u: userList){
										
											out.write("<li><span class=\"name\"><a href=\"/SWIMv2-WebClient/secure/profile.jsp?user="+u.getEmail()+"\">"+u.getFirstname()+" "+u.getLastname()+"</span><img src=\"/SWIMv2-WebClient/images/GIMP-file/utente_incognito.png\" alt=\"...\" height=\"100\" width=\"120\"/></a></li>");
										}
										out.write(" </ul>");
										out.write(" </div>");
										out.write("</div>");
										out.write("</div>");
						
									} else {
										out.write("<div class=\"middle\">");
										out.write("<h1>Non hai ancora amici.</h1>");
										out.write("</div>");
									}
								} else {
									out.write("<div class=\"middle\">");
									out.write("<h2><span style=\"color: red;\">Si è verificato un problema. Nessun utente amico trovato nel sistema.</span></h2>");
									out.write("</div>");
								}
								%>
 
		    </div>
	
			<div class="contentright">
			  <h2>&nbsp;</h2>
			  <h2>Le tue notifiche:</h2>
			  <p>&nbsp;</p>
			  <p>
					<a href="<%=request.getContextPath()%>/secure/showHelpReq.jsp">Richieste di aiuto</a>
				</p>
			  <p>&nbsp;</p>
			  <p><a href="friendReq.jsp">Richieste di amicizia</a></p>
			  <p>&nbsp;</p>
			  <p>Richieste abilit&agrave;<img src="<%=request.getContextPath()%>/images/omino_msg.jpg" alt="omino_msg" width="158" height="165" align="right" /></p>
			  <p>&nbsp;</p>
			  <p>&nbsp;</p>
			  </div>
			<!-- TemplateEndEditable -->
			<div style="clear:both;"></div>
			</div>
		</div>
		<div class="footerArea">
			<div class="container"><!-- TemplateBeginEditable name="footer" -->
			  <div class="copyright">&copy; 2013 SWIMv2 - Social Network by Marco Pricone,Venturi Davide,Rustico Sebastiano.  All rights reserved.</div>
			<!-- TemplateEndEditable --></div>
		</div>
		
		
        <script type="text/javascript">
		
			var options = {
				valueNames: [ 'name' ]
			};
		
			var featureList = new List('user-list', options);
			
			/*filtro uomini
			$('#filter-men').click(function() {
				featureList.filter(function(item) {
					if(item.values().gender == "uomo") {
						return true;
					} else {
						return false;
					}
				});
				return false;
			});
			*/
			
		
		</script>
	</body>
</html>
