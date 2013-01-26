<%@ page import="sessionbeans.logic.UserBeanRemote" %>
<%@ page import="sessionbeans.logic.SwimResponse" %>
<%@ page import="entities.User" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.Context" %>
<%@page import="java.security.Principal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%><%@ taglib
	uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
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
			<!-- TemplateBeginEditable name="contentLeft" -->
			<div class="contentleft">
				<div class="middle">
					<h1>
					<%
					if(request.getUserPrincipal().getName()!=null){
						User usr = userBean.find(request.getUserPrincipal().getName());
						if(usr!=null){
							
							if(usr.getPictureId()!=null){
								
								SwimResponse getPicRsp = userBean.retrievePicture(usr.getEmail());
								
								if(getPicRsp.getStatus()==SwimResponse.SUCCESS){
									String picPath = (String) getPicRsp.getData();
									
									out.write("<img src=\""+request.getContextPath()+"/"+picPath+"\" alt=\"user_picture\" width=\"179\" height=\"179\" align=\"right\" />");

								} else {
									out.write("<img src=\""+request.getContextPath()+"/images/GIMP-file/utente_incognito.jpg\" alt=\"omino_incognito\" width=\"179\" height=\"137\" align=\"right\" />");
								}
								
							} else {
							
								out.write("<img src=\""+request.getContextPath()+"/images/GIMP-file/utente_incognito.jpg\" alt=\"omino_incognito\" width=\"179\" height=\"137\" align=\"right\" />");
						
							}
						}
					}
			
					%>
					
					</h1>
					<%
					if(request.getUserPrincipal().getName()!=null){
						
						User usr = userBean.find(request.getUserPrincipal().getName());
						
						if(usr!=null){
							
							out.write("<h1>Benvenuto "+usr.getFirstname()+" "+usr.getFirstname()+ "</h1>");
							
						}
					}
					
					
					
					%>
					<p>&nbsp;</p>
					<p>Peri iniziare subito la ricerca di aiuto spingi il bottone
						&quot;Help&quot; qui in basso e ti ritroverai nella pi&ugrave;
						grande e disponibile community del web!</p>
					<img src="<%=request.getContextPath()%>/images/GIMP-file/help-sm.jpg" alt="helpHome" width="294"
						height="317" border="0" align="left" usemap="#help" />
			<!--  		<map name="help" id="help">
						<area shape="poly" coords="147,224" href="#" />
						<area shape="poly" coords="221,163" href="#" />
						<area shape="poly" coords="211,183" href="#" />
						<area shape="poly" coords="245,152" href="#" />
					</map>-->
					
					<p>&nbsp;</p>

        
        <%
                	if (request.getAttribute("FileUpload") != null) {
                		if (request.getAttribute("FileUpload").equals("ok")) {

                			out.write("<h3><span style=\"color: red;\">Foto caricata con successo.</span><h3>");

                		} else {
                			out.write("<h3><span style=\"color: red;\">Si è verificatio un errore nel sistema. Foto non caricata.</span><h3>");
                		}
                	}
                	request.setAttribute("AbilityReqSent", null);
                %>
				</div>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
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
