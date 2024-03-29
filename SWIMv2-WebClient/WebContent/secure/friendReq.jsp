<%@ page import="sessionbeans.logic.UserBeanRemote" %> 
<%@ page import="sessionbeans.logic.SwimResponse" %>
<%@ page import="entities.User" %>
<%@ page import="entities.FriendshipRequest" %>
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
	User user = userBean.find(request.getUserPrincipal().getName());
	SwimResponse swimResponse = userBean.getFriendshipReqList(request.getUserPrincipal().getName());
	List<FriendshipRequest> friendReqList = null;
	if(swimResponse.getStatus()==SwimResponse.SUCCESS){
		friendReqList = (List<FriendshipRequest>) swimResponse.getData();
	}
	
	
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
<div class="contentleft">
      
      <div class="middle">
      		
      		<% boolean newRequest = false;
      		
      		for(FriendshipRequest freq: friendReqList){
      			if(freq.getAcceptanceStatus()==false) newRequest=true;	
      		}
      		if(newRequest){
      		System.out.println(friendReqList);
  			out.write("<h2>Hai ricevuto alcune richieste d'amicizia!</h2>");
  			
      			for(FriendshipRequest freq: friendReqList){
      				if(freq.getAcceptanceStatus()==false){
      				out.write("<table width=\"100%\" border=\"\" cellpadding=\"8\" cellspacing=\"5\">");
      				out.write("<tr>");
      				out.write("<td width=\"60%\" align=\"center\"><a href=\"/SWIMv2-WebClient/secure/profile.jsp?user="+freq.getFromUser().getEmail()+"\"><h1>"+freq.getFromUser().getFirstname()+" "+freq.getFromUser().getLastname()+"</h1></a></td>");
      				out.write("<td width=\"20%\" align=\"center\" valign=\"middle\">");
      				out.write("<a href=\"/SWIMv2-WebClient/Control?actionType=replyToFriendReq&toUser="+freq.getFromUser().getEmail()+"&value=approve\"><img src=\"/SWIMv2-WebClient/images/accetta.jpg\" alt=\"accetta\" align=\"absmiddle\" /></a></td>");
      				out.write("<td width=\"20%\" align=\"center\" valign=\"middle\">");
      				out.write("<a href=\"/SWIMv2-WebClient/Control?actionType=replyToFriendReq&toUser="+freq.getFromUser().getEmail()+"&value=decline\"><img src=\"/SWIMv2-WebClient/images/rifiuta.jpg\" alt=\"rifiuta\" align=\"absmiddle\" /></a></td>");
      				out.write("</tr>");
      				out.write("</table>");
      				out.write("<h3>&nbsp;</h3>");
      				}
      			}
      			
      		} else {
      			out.write("<h2>Non è stata ricevuta nessuna richiesta d'amicizia.</h2>");
      		}
      		%>
   
                  <h3>&nbsp;</h3>
                  <%if(request.getAttribute("ReplyResult")!=null){
                  if(request.getAttribute("ReplyResult").equals("ok")){
                	  out.write("<h2><span style=\"color: red;\">La risposta alla richiesta d'amicizia è stata gestita con successo!</span><h2>");
                	  request.setAttribute("ReplyResult", "null");
                  } else {
                	  System.out.println("*****"+session.getAttribute("ReplyResult"));
                	  out.write("<h2>La risposta alla richiesta d'amicizia NON è stata gestita correttamente.<h2>");
                  }}  %>
         

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