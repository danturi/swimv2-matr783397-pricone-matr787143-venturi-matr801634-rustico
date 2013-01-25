<%@ page import="sessionbeans.logic.UserBeanRemote" %>
<%@ page import="sessionbeans.logic.SwimResponse" %>
<%@ page import="entities.User" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.Context" %>
<%@page import="java.security.Principal"%>
<%@page import="entities.HelpRequest"%>
<%@page import="entities.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%><%@ taglib
	uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	<!-- TemplateBeginEditable name="doctitle" -->
	<title>SWIMv2</title>
	
	<meta name="description"
	content="Designed and developed by Codify Design Studio - codifydesign.com" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/images/starFeedback.js"></script>
    <link href="<%=request.getContextPath()%>/images/star.css" rel="stylesheet" type="text/css" />
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
	User usr = null;
	
	 %>
	
		<div class="bannerArea">
		<div class="container">
			<div class="bannernav">
				Sei loggato come <%=request.getUserPrincipal().getName()%>.
			</div>
			<div class="toplogo">
				<img src="<%=request.getContextPath()%>/images/GIMP-file/swim-titolo_b.png" width="223" height="51" alt="titolo" />
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
			<div class="container"><!-- TemplateBeginEditable name="contentLeft" -->
			  <div class="contentleft">
			  
			  <%
			
			if(request.getParameter("toUser")!=null && request.getParameter("reqId")!=null){
				usr = userBean.find(request.getParameter("toUser"));
				if(usr!=null){
					if(!usr.getEmail().equals(request.getUserPrincipal().getName())){
						
							out.write("<div class=\"middle\">");
							out.write("<h1>Lascia il tuo feedback</h1>");
							out.write("<p>&nbsp;</p>");
							out.write("<p>Dai una valutazione sull'aiuto ricevuto e fornisci una breve descrizione</p>");
							out.write("<p>&nbsp;</p>");
							out.write("<p>Vota:</p>");
							if(request.getParameter("vote")!=null){
						    	
						    		out.write("<p><strong><span style=\"color: blue ;\">Hai votato " + request.getParameter("vote")+" stelle!</span></strong></p><p>&nbsp;</p></span>");
						    		out.write("<p>Descrizione:</p>");
									out.write("<form id=\"makefeed\" name=\"makefeed\" method=\"post\" action=\""+request.getContextPath()+"/secure/feedbacksent.jsp?toUser="+request.getParameter("toUser")+"&reqId="+request.getParameter("reqId")+"&vote="+request.getParameter("vote")+"\">");
									out.write("<p>");
									out.write("<label>");
									out.write("<textarea name=\"comments\" cols=\"50\" rows=\"5\" class=\"formLabel\" id=\"comments\"></textarea>");
									out.write("</label>");
									out.write("</p>");
									/*out.write("<p>");
									out.write("<label>");
									out.write("<input name=\"vote\" type=\"hidden\" id=\"vote\" />");
									out.write("</label>");
									out.write("</p>");*/
									out.write("<p>");
					      	        out.write("<label>");
					      		    out.write("<input type=\"submit\" name=\"sendFeed\" id=\"sendFeed\" value=\"Invia\" />");
					      	     	out.write("</label>");
									out.write("</p>");
									out.write("</form>");
									out.write("</div>");
							
							}else {
					    		out.write("<div id=\"STAR_RATING\"><p><script type=\"text/javascript\">star(0);</script></p><p>&nbsp;</p></div>");
					    		out.write("</div>");
							}
					} else {
						out.write("<h2><span style=\"color: red;\">Utente non valido. Non è possibile inviare la richiesta.</span><h2>");
					}
				} else {
					out.write("<h2><span style=\"color: red;\">Utente non valido. Non è possibile inviare la richiesta.</span><h2>");
				}
			} else {
				out.write("<h2><span style=\"color: red;\">URL non valido. Non è possibile inviare la richiesta.</span><h2>");
			}
			%>
			  

			  <!-- da qua -->
   
              	<!-- fin qua -->
              	
              	
              	
              	
		    </div>
			<!-- TemplateEndEditable --><!-- TemplateBeginEditable name="contentRight" -->
			<div class="contentright">
				<h2>&nbsp;</h2>
				<h2>Le tue notifiche:</h2>
				<p>&nbsp;</p>
				<p>
					<a href="<%=request.getContextPath()%>/secure/showHelpReq.jsp">Richieste di aiuto</a>
				</p>
				<p>&nbsp;</p>
				<p>
					<a href="<%=request.getContextPath()%>/secure/friendReq.jsp">Richieste di amicizia</a>
				</p>
				<p>&nbsp;</p>
				<p>
					Richieste abilit&agrave;<img
						src="<%=request.getContextPath()%>/images/omino_msg.jpg"
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
			<div class="copyright">&copy; 2013 SWIMv2 - Social Network by
				Marco Pricone,Venturi Davide,Rustico Sebastiano. All rights
				reserved.</div>
		</div>
	</div>


</body>
</html>