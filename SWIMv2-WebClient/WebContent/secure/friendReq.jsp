<%@ page import="sessionbeans.logic.UserBeanRemote" %> <%@ page
import="javax.naming.InitialContext" %> <%@ page
import="javax.naming.Context" %> <%@page
import="java.security.Principal"%> <%@page contentType="text/html"
pageEncoding="UTF-8"%><%@ taglib uri='http://java.sun.com/jsp/jstl/core'
prefix='c'%>
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
<%session.setAttribute("ReplyResult","null"); %>
	
		<div class="bannerArea">
			<div class="container">
<div class="bannernav"><a href="#" >Privacy Policy</a> &bull; <a href="#" >Contact Us</a> &bull; <a href="#" >Site Map</a></div>
			<div class="toplogo"><a href="#"></a><a href="#"></a><a href="#"><a href="#"><a href="#"><a href="#"></a><img src="<%=request.getContextPath()%>/images/GIMP-file/swim-titolo_b.png" width="223" height="51" alt="titolo" /></div>
              <div style="clear:both;"></div>
          </div>
		</div>
		<div class="topnavigationArea">
			<div class="container">
			  <div class="topnavigationgroup">
			    <ul id="MenuBar1" class="MenuBarHorizontal">
							<li style="border-right-style: solid;"><a id="logoutLink"
						href="<%=request.getContextPath()%>/services/auth/logout">LOGOUT</a></li>
					<li><a href="#">AMICI</a></li>
					<li><a href="profiloUser.jsp">PROFILO</a></li>
					<li><a href="helpSearch.jsp">CERCA UTENTI</a></li>
					<li class="MenuBarHorizontal"><a href="homeUser.jsp"
						title="home" target="_parent">HOME</a></li>
				</ul>
		      </div>
			  <div style="clear:both;"></div>
		  </div>
		</div>
		<div class="contentArea">
			<div class="container">
<div class="contentleft">
      
      <div class="middle">
   
              <h2>Hai ricevuto alcune richieste d'amicizia!</h2>
                  <table width="100%" border="" cellpadding="8" cellspacing="5">
                    <tr>
                      <td width="60%" class="formLabel" align="absmiddle"><h2> Genere</h2></td>
                      <td width="20%" align="center" valign="middle">
                      		<a href="<%=request.getContextPath()%>/Control?actionType=replyToFriendReq&toUser=bb&value=approve"><img src="<%=request.getContextPath()%>/images/accetta.png" alt="accetta" align="absmiddle" /></a></td>
                      		<p>&nbsp;</p>
                      		<td width="20%" align="center" valign="middle">
                      		<a href="#"><img src="<%=request.getContextPath()%>/images/rifiuta.png" alt="rifiuta" align="absmiddle" /></a>
                      	</td>
                    </tr>
                    <tr>
                     <td class="formLabel"><h3><strong>Genere:</strong> </h3></td>
                    </tr>
                    <tr>
                      <td class="formLabel"><h3> Genere:</h3></td>
                    </tr>
                    <tr>
                     <td class="formLabel"><h3> Genere:</h3></td>
                    </tr>
                    <tr>
                     <td class="formLabel"><h3> Genere:</h3></td>
                    </tr>
                    <tr>
                      <td class="formLabel"><h3> Genere:</h3></td>
                    </tr>
                  </table>
                  
                  
                  <h3>&nbsp;</h3>
                  <%if(session.getAttribute("ReplyResult").equals("ok")){
                	  out.write("<h3>La risposta alla richiesta d'amicizia è stata gestita con successo!<h3>");
                	 
                  } else {
                	  
                	  out.write("<h3>La risposta alla richiesta d'amicizia NON è stata gestita correttamente.<h3>");
                  }  %>
         

      </div>
</div>
			<div class="contentright">
			  <h2>&nbsp;</h2>
			  <h2>Le tue notifiche:</h2>
			  <p>&nbsp;</p>
			  <p>Richieste di aiuto</p>
			  <p>&nbsp;</p>
			  <p>Richieste di amicizia</p>
			  <p>&nbsp;</p>
			  <p>Richieste abilit&agrave;<img src="<%=request.getContextPath()%>/images/omino_msg.jpg" alt="omino_msg" width="158" height="165" align="right" /></p>
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
