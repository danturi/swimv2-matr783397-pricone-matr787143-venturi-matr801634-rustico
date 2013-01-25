<%@ page import="sessionbeans.logic.UserBeanRemote"%>
<%@ page import="sessionbeans.logic.SwimResponse"%>
<%@ page import="entities.User"%>
<%@ page import="entities.AbilityRequest"%>
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
	SwimResponse swimResponse = userBean.getNewAbilityReqList();
	List<AbilityRequest> newAbilityReqList = null;
	if(swimResponse!=null){
		if(swimResponse.getStatus()==SwimResponse.SUCCESS){
			newAbilityReqList = (List<AbilityRequest>) swimResponse.getData();
		}
	}
	
	
	%>


	<div class="bannerArea">
		<div class="container">
		<div class="bannernav">Pannello di amministrazione di SWIMv2</div>

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
        <li style="border-right-style: solid;"><a id="logoutLink" href="<%=request.getContextPath()%>/services/auth/logout">LOGOUT</a></li>
        <li><a href="#">RICERCA UTENTI</a></li>
        <li><a href="<%=request.getContextPath()%>/secure/admin/abilityAdmin.jsp">ABILITA'</a></li>
        <li><a href="<%=request.getContextPath()%>/secure/admin/homeAdmin.jsp">HOME</a></li>
      </ul>
      </div>
			<div style="clear:both;"></div>
			</div>
		</div>
	<div class="contentArea">
		<div class="container">
			<div class="contentleft">

				<div class="middle">

					<%
      				
      		if(!newAbilityReqList.isEmpty()){
  			out.write("<h2>Sono state ricevute alcune richieste di abilitazione:</h2>");
  			out.write("<p>&nbsp;</p>");
  
      			for(AbilityRequest abr: newAbilityReqList){
      				
      				out.write("<div class=\"scroll\">");
      				out.write("<h2>Email utente: "+abr.getUser().getEmail()+"</h2>");
      				//out.write("<p>&nbsp;</p>");
      				out.write("<h2>Abilità richiesta: "+abr.getAbilityId().getDescription()+"</h2>");
      				//out.write("<p>&nbsp;</p>");
      				out.write("<h2>Descrizione:</h2>");
      				//out.write("<p>&nbsp;</p>");
      				out.write("<p>"+abr.getDescription()+"</p>");
      				out.write("</div>");
      				out.write("<p><a href=\"/SWIMv2-WebClient/Control?actionType=replyToAbilityReq&toUser="+abr.getUser().getEmail()+"&abilityId="+abr.getAbilityId().getAbilityId()+"&value=approve\"><img src=\"/SWIMv2-WebClient/images/accetta2.jpg\" alt=\"accetta\"/></a>");
      				out.write("<a href=\"/SWIMv2-WebClient/Control?actionType=replyToAbilityReq&toUser="+abr.getUser().getEmail()+"&abilityId="+abr.getAbilityId().getAbilityId()+"&value=decline\"><img src=\"/SWIMv2-WebClient/images/rifiuta2.jpg\" alt=\"rifiuta\"/></a></p>");
      				out.write("<p>&nbsp;</p>");
      	  			out.write("<p>&nbsp;</p>");
      				
      			}
      			
      		} else {
      			out.write("<h2>Non è stata ricevuta nessuna richiesta.</h2>");
      		}
      		%>
      		<h3>&nbsp;</h3>
					<%if(request.getAttribute("ReplyResult")!=null){
                  if(request.getAttribute("ReplyResult").equals("ok")){
                	  out.write("<h2><span style=\"color: red;\">La risposta alla richiesta di abilitazione è stata gestita con successo!</span><h2>");
                	 
                  } else if(request.getAttribute("ReplyResult").equals("urlfail")){
                	  out.write("<h2><span style=\"color: red;\">Si è verificato un problema. Utente o abilità non corretti nell'URL.</span><h2>");
                  } else {
                	  System.out.println("*****"+session.getAttribute("ReplyResult"));
                	  out.write("<h2><span style=\"color: red;\">Si è verificato un problema.La risposta alla richiesta di abilitazione NON è avvenuta correttamente.</span><h2>");
                  }
                  request.setAttribute("ReplyResult", "null");
                  }  %>


				</div>


			</div>
			<div class="contentright">
				<h2>&nbsp;</h2>
				<h2>Le tue notifiche:</h2>
				<p>
					<a
						href="<%=request.getContextPath()%>/secure/admin/showAbilityReqAdmin.jsp">Richieste
						aggiunta abilit&agrave;</a>
				</p>
				<h2>&nbsp;</h2>
				<h2>
					<img src="<%=request.getContextPath()%>/images/omino_msg.jpg"
						alt="omino_msg" width="121" height="159" align="right" />
				</h2>
				
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