<%@ page import="sessionbeans.logic.UserBeanRemote"%>
<%@ page import="sessionbeans.logic.SwimResponse"%>
<%@ page import="entities.User"%>
<%@ page import="entities.HelpRequest"%>
<%@ page import="java.util.List"%>
<%@ page import="javax.naming.InitialContext"%>
<%@ page import="javax.naming.Context"%>
<%@page import="java.security.Principal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%><%@ taglib
	uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<html>
<head>
<!-- TemplateBeginEditable name="doctitle" -->
<title>SWIMv2</title>

<meta name="description"
	content="Designed and developed by Codify Design Studio - codifydesign.com" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/images/stylesheet.css" />
<link
	href="<%=request.getContextPath()%>/SpryAssets/SpryMenuBarHorizontal.css"
	rel="stylesheet" type="text/css" />
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
<style type="text/css">
<!--
div.scroll {
	height: 200px;
	width: 500px;
	overflow: auto;
	border: 1px solid #666;
	background-color: #D1FFFF;
	padding: 8px;
}
-->
</style>
</head>
<body>
	<%UserBeanRemote userBean;
	 Context context = new
	InitialContext(); userBean = (UserBeanRemote)
	context.lookup(UserBeanRemote.class.getName());
	SwimResponse incomingRsp = userBean.getHelpReqList(request.getUserPrincipal().getName());
	SwimResponse outgoingRsp = userBean.getSentHelpReqList(request.getUserPrincipal().getName());
	
	List<HelpRequest> incomingList = null;
	List<HelpRequest> outgoingList = null;
	if(incomingRsp!=null && outgoingRsp!=null){
		if(incomingRsp.getStatus()==SwimResponse.SUCCESS && outgoingRsp.getStatus()==SwimResponse.SUCCESS){
			incomingList = (List<HelpRequest>) incomingRsp.getData();
			outgoingList = (List<HelpRequest>) outgoingRsp.getData();
		}
	}
	
	
	%>


	<div class="bannerArea">
		<div class="container">
			<div class="bannernav">
				Sei loggato come
				<%=request.getUserPrincipal().getName() %>.
			</div>
			<div class="toplogo">
				<a href="#"></a><a href="#"></a><a href="#"><a href="#"><a
						href="#"><a href="#"></a><img
							src="<%=request.getContextPath()%>/images/GIMP-file/swim-titolo_b.png"
							width="223" height="51" alt="titolo" />
			</div>
			<div style="clear: both;"></div>
		</div>
	</div>
	<div class="topnavigationArea">
		<div class="container">
			<div class="topnavigationgroup">
				<ul id="MenuBar1" class="MenuBarHorizontal">
					<li style="border-right-style: solid;"><a id="logoutLink"
						href="<%=request.getContextPath()%>/services/auth/logout">LOGOUT</a></li>
					<li><a href="#">AMICI</a></li>
					<li><a
						href="<%=request.getContextPath()%>/secure/profile.jsp?user=<%=request.getUserPrincipal().getName()%>">PROFILO</a></li>
					<li><a href="<%=request.getContextPath()%>/secure/helpSearch.jsp">CERCA UTENTI</a></li>
					<li class="MenuBarHorizontal"><a href="<%=request.getContextPath()%>/secure/homeUser.jsp"
						title="home" target="_parent">HOME</a></li>
				</ul>
			</div>
			<div style="clear: both;"></div>
		</div>
	</div>
	<div class="contentArea">
		<div class="container">
			<div class="contentleft">

				<div class="middle">
				<%if(request.getAttribute("ReplyResult")!=null){
                  if(request.getAttribute("ReplyResult").equals("ok")){
                	  out.write("<h2><span style=\"color: red;\">La risposta alla richiesta di aiuto è stata gestita con successo!</span><h2>");
                  } else if(request.getAttribute("ReplyResult").equals("reqAlreadyEvaluated")){
                	  out.write("<h2><span style=\"color: red;\">Si è verificato un problema. Hai già risposto a questa richiesta di aiuto.</span><h2>");
                  } else if(request.getAttribute("ReplyResult").equals("noValidUser")){
                	  out.write("<h2><span style=\"color: red;\">Si è verificato un problema. Utente non corretto.</span><h2>");
                  } else if(request.getAttribute("ReplyResult").equals("noSuchRequestFound")){
                	  out.write("<h2><span style=\"color: red;\">Si è verificato un problema. Impossibile trovare la richiesta d'aiuto selezionata.</span><h2>");
                  } else if(request.getAttribute("ReplyResult").equals("noValidReq")){
                	  out.write("<h2><span style=\"color: red;\">Si è verificato un problema. Impossibile trovare la richiesta d'aiuto selezionata.</span><h2>");
                  } else if(request.getAttribute("ReplyResult").equals("urlfail")){
                	  out.write("<h2><span style=\"color: red;\">Si è verificato un problema. Utente o richiesta non corretti nell'URL.</span><h2>");
                  } else {
                	  System.out.println("*****"+session.getAttribute("ReplyResult"));
                	  out.write("<h2><span style=\"color: red;\">Si è verificato un problema. Impossibile rispondere alla richiesta d'aiuto.</span><h2>");
                  }
                  request.setAttribute("ReplyResult", "null");
                  }  
			 
                  %>
				

		<%
		if(incomingList!=null && outgoingList!=null){
      		if(!incomingList.isEmpty()){
  			out.write("<h1>Richieste di aiuto ricevute:</h1>");
  			out.write("<p>&nbsp;</p>");
  
      			for(HelpRequest helpReq: incomingList){
      				
      				out.write("<div class=\"scroll\">");
      				out.write("<h2>Email utente: "+helpReq.getFromUser().getEmail()+"</h2>");
      				if(helpReq.getIsEvaluated().booleanValue()){
      					if(helpReq.getAcceptanceStatus()==true){
          					out.write("<h2>Stato: <span style=\"color: red;\">Hai accettato.</span></h2>");
      					} else {
          					out.write("<h2>Stato: <span style=\"color: red;\">Hai rifiutato.</span></h2>");

      					}
      				} else {
      					out.write("<h2>Stato: <span style=\"color: red;\">da valutare</span></h2>");
      				}
      				out.write("<h2>Data e ora: "+helpReq.getDatetime()+"</h2>");
      				out.write("<h2>Abilità richiesta: "+helpReq.getAbilityId().getDescription()+"</h2>");
      				//out.write("<p>&nbsp;</p>");
      				out.write("<h2>Descrizione:</h2>");
      				//out.write("<p>&nbsp;</p>");
      				out.write("<p>"+helpReq.getDescription()+"</p>");
      				out.write("</div>");
      				if(!helpReq.getIsEvaluated().booleanValue()){
          				out.write("<p><a href=\"/SWIMv2-WebClient/Control?actionType=replyToHelpReq&toUser="+helpReq.getFromUser().getEmail()+"&reqId="+helpReq.getHelpReqId()+"&value=approve\"><img src=\"/SWIMv2-WebClient/images/accetta2.jpg\" alt=\"accetta\"/></a>");
          				out.write("<a href=\"/SWIMv2-WebClient/Control?actionType=replyToHelpReq&toUser="+helpReq.getFromUser().getEmail()+"&reqId="+helpReq.getHelpReqId()+"&value=decline\"><img src=\"/SWIMv2-WebClient/images/rifiuta2.jpg\" alt=\"rifiuta\"/></a></p>");
          				out.write("<p>&nbsp;</p>");
      				} else {
      					out.write("<p>&nbsp;</p>");
      					out.write("<p>&nbsp;</p>");
      				}
      			}
      		} else {
      			out.write("<h2>Non è stata ricevuta nessuna richiesta di aiuto.</h2>");
      			out.write("<p>&nbsp;</p>");
      		}
      		out.write("<p>&nbsp;</p>");
      		if(!outgoingList.isEmpty()){
      			out.write("<h1>Richieste d'aiuto inviate:</h1>");
      			out.write("<p>&nbsp;</p>");
      
          			for(HelpRequest helpReq: outgoingList){
          				
          				out.write("<div class=\"scroll\">");
          				out.write("<h2>Email utente: "+helpReq.getToUser().getEmail()+"</h2>");
          				if(helpReq.getIsEvaluated().booleanValue()){
          					if(helpReq.getAcceptanceStatus()==true){
              					out.write("<h2>Stato: <span style=\"color: red;\">Accettata.</span></h2>");
          					} else {
          						out.write("<h2>Stato: <span style=\"color: red;\">Rifiutata.</span></h2>");
          					}
          				} else {
          					out.write("<h2>Stato: <span style=\"color: red;\">non ancora valutata</span></h2>");
          				}
          				out.write("<h2>Data e ora: "+helpReq.getDatetime()+"</h2>");
          				out.write("<h2>Abilità richiesta: "+helpReq.getAbilityId().getDescription()+"</h2>");
          				//out.write("<p>&nbsp;</p>");
          				out.write("<h2>Descrizione:</h2>");
          				//out.write("<p>&nbsp;</p>");
          				out.write("<p>"+helpReq.getDescription()+"</p>");
          				out.write("</div>");
          				out.write("<p>&nbsp;</p>");
          				out.write("<p>&nbsp;</p>");
          			}
          		} else {
          			out.write("<h2>Non hai ancora inviato nessuna richiesta di aiuto.</h2>");
          		}
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