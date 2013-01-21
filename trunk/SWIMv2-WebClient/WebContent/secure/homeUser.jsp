<%@ page import="sessionbeans.logic.UserBeanRemote" %> <%@ page
import="javax.naming.InitialContext" %> <%@ page
import="javax.naming.Context" %> <%@page
import="java.security.Principal"%> <%@page contentType="text/html"
pageEncoding="UTF-8"%><%@ taglib uri='http://java.sun.com/jsp/jstl/core'
prefix='c'%>
<c:if test="${pageContext.request.userPrincipal.name=='ad'}">
	<c:redirect url="/secure/admin/homeAdmin.jsp" />
	<!-- this will redirect if user is already logged in -->
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<!-- TemplateBeginEditable name="doctitle" -->
<title>SWIMv2</title>
<!-- TemplateEndEditable -->
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
         
        $(function(){
           $("#getTimeStampButton").click(function(){
               $.ajax({
                   url: "<%=request.getContextPath()%>/services/secure/timestamp/now",
                   type: "GET",
                   cache: false,
                   dataType: "json",
                        
                   success: function (data, textStatus, jqXHR){
                       //alert("success");
                       if (data.status == "SUCCESS" ){
                           $("#timeStampContent").html("Timestamp: "+data.data);
                       }else{
                           alert("failed");
                       }
                   },
                        
                   error: function (jqXHR, textStatus, errorThrown){
                       //alert("error - HTTP STATUS: "+jqXHR.status);
                       if (textStatus == "parsererror"){
                           alert("You session has timed out");
                           //forward to welcomde page
                           window.location.replace("http://"+window.location.host+"<%=request.getContextPath()%>/index.jsp");
                       }
                   },
                        
                   complete: function(jqXHR, textStatus){
                       //alert("complete");
                   }                    
               });
           }); 
        });
        </script>
</head>
<body>
	<%!UserBeanRemote userBean;%> <% Context context = new
	InitialContext(); userBean = (UserBeanRemote)
	context.lookup(UserBeanRemote.class.getName());
	System.out.println("****** QUI EJB CALL DA JSP ******");
	//userBean.sendFriendshipReq("aa", "bb"); %>

	<div class="bannerArea">
		<div class="container">
			<!-- TemplateBeginEditable name="banner_men�" -->
			<div class="bannernav">
				<a href="#">Privacy Policy</a> &bull; <a href="#">Contact Us</a>
				&bull; <a href="#">Site Map</a>
			</div>
			<!-- TemplateEndEditable -->
			<div class="toplogo">
				<a href="#"><a href="#"><img
						src="<%=request.getContextPath()%>/images/GIMP-file/swim-titolo_b.png" width="223" height="51"
						alt="titolo" /></a>
			</div>
			<div style="clear: both;"></div>
		</div>
	</div>
	<div class="topnavigationArea">
		<div class="container">
			<!-- TemplateBeginEditable name="men�" -->
			<div class="topnavigationgroup">
				<ul id="MenuBar1" class="MenuBarHorizontal">
							<li style="border-right-style: solid;"><a id="logoutLink"
						href="<%=request.getContextPath()%>/services/auth/logout">LOGOUT</a></li>
					<li><a href="#">AMICI</a></li>
					<li><a href="profile.jsp?user=<%=request.getUserPrincipal().getName()%>">PROFILO</a></li>
					<li><a href="helpSearch.jsp">CERCA UTENTI</a></li>
					<li class="MenuBarHorizontal"><a href="homeUser.jsp"
						title="home" target="_parent">HOME</a></li>
				</ul>
			</div>
			<!-- TemplateEndEditable -->
			<div style="clear: both;"></div>
		</div>
	</div>
	<div class="contentArea">
		<div class="container">
			<!-- TemplateBeginEditable name="contentLeft" -->
			<div class="contentleft">
				<div class="middle">
					<h1>
						<img src="<%=request.getContextPath()%>/images/GIMP-file/utente_incognito.jpg"
							alt="omino_incognito" width="179" height="137" align="right" />
					</h1>
					<h1>Benvenuto Nome Utente</h1>
					<p>&nbsp;</p>
					<p>Peri iniziare subito la ricerca di aiuto spingi il bottone
						&quot;Help&quot; qui in basso e ti ritroverai nella pi&ugrave;
						grande e disponibile community del web!</p>
					<img src="<%=request.getContextPath()%>/images/GIMP-file/help-sm.jpg" alt="helpHome" width="294"
						height="317" border="0" align="left" usemap="#help" />
					<map name="help" id="help">
						<area shape="poly" coords="147,224" href="#" />
						<area shape="poly" coords="221,163" href="#" />
						<area shape="poly" coords="211,183" href="#" />
						<area shape="poly" coords="245,152" href="#" />
					</map>
					<%
						Principal p = request.getUserPrincipal();
						out.write("<br/><br/>");
						if (p == null) {
							//if you get here the something is really wrong, because
							//you can only see that page if you have been authenticated 
							//and therefore there is a principal available
							out.write("<div>Principal = NULL</div>");
						} else {
							out.write("<div>Principal.getName()                 = "
									+ p.getName() + "</div>");
							out.write("<div>request.getRemoteUser()             = "
									+ request.getRemoteUser() + "</div>");
							out.write("<div>request.getAuthType()               = "
									+ request.getAuthType() + "</div>");
							out.write("<div>request.isUserInRole(ADMINISTRATOR) = "
									+ request.isUserInRole("ADMINISTRATOR") + "</div>");
							out.write("<div>request.isUserInRole(USER)          = "
									+ request.isUserInRole("USER") + "</div>");
							out.write("<div>request.isUserInRole(DEFAULT)       = "
									+ request.isUserInRole("DEFAULT") + "</div>");
							out.write("<div>request.isUserInRole(CUSTOMER)      = "
									+ request.isUserInRole("CUSTOMER") + "</div>");
						}
					%>
				</div>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
			</div>
			<!-- TemplateEndEditable -->
			<!-- TemplateBeginEditable name="contentRight" -->
			<div class="contentright">
				<h2>&nbsp;</h2>
				<h2>Le tue notifiche:</h2>
				<p>&nbsp;</p>
				<p>Richieste di aiuto</p>
				<p>&nbsp;</p>
				<p><a href="friendReq.jsp">Richieste di amicizia</a></p>
				<p>&nbsp;</p>
				<p>
					Richieste abilit&agrave;<img src="<%=request.getContextPath()%>/images/omino_msg.jpg"
						alt="omino_msg" width="158" height="165" align="right" />
				</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
			</div>
			<!-- TemplateEndEditable -->
			<div style="clear: both;"></div>
		</div>
	</div>
	<div class="footerArea">
		<div class="container">
			<!-- TemplateBeginEditable name="footer" -->
			<div class="copyright">&copy; 2013 SWIMv2 - Social Network by
				Marco Pricone,Venturi Davide,Rustico Sebastiano. All rights
				reserved.</div>
			<!-- TemplateEndEditable -->
		</div>
	</div>


</body>
</html>
