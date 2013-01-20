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
      <div class="top">
        <h1><img src="<%=request.getContextPath()%>/images/GIMP-file/utente_incognito.png" alt="omino_incognito" width="205" height="148" hspace="20" vspace="0" align="absmiddle" />Nome utente</h1>
      </div>
      <div class="middle">
        <div class="middleleft">
              <h2>Informazioni</h2>
                  <table width="327" border="1" cellpadding="2" cellspacing="0">
                    <tr>
                      <td width="60"><h3> Genere:</h3></td>
                      <td width="257" class="formLabel">&nbsp;</td>
                    </tr>
                    <tr>
                      <td><h3>Et&agrave;:</h3></td>
                      <td class="formLabel">&nbsp;</td>
                    </tr>
                    <tr>
                      <td><h3>Residenza:</h3></td>
                      <td class="formLabel">&nbsp;</td>
                    </tr>
                    <tr>
                      <td><h3>Luogo:</h3></td>
                      <td class="formLabel">&nbsp;</td>
                    </tr>
                    <tr>
                      <td><h3>Professione:</h3></td>
                      <td class="formLabel">&nbsp;</td>
                    </tr>
                    <tr>
                      <td><h3>Email:</h3></td>
                      <td class="formLabel">&nbsp;</td>
                    </tr>
                  </table>
                  <p>&nbsp;</p>
                  <h2>Rating</h2>
          <table width="178" border="1" cellpadding="2" cellspacing="0">
                    <tr>
                      <td width="114"><h3>Il tuo punteggio &egrave;:</h3></td>
                      <td width="50" class="formLabel">&nbsp;</td>
                    </tr>
          </table>
          <p>&nbsp;</p>
                  <p><a href="feedback.jsp"><strong>Visualizza tutti i miei Feedback</strong><strong></strong></a></p>
                  <h3>&nbsp;</h3>
                  <h3>&nbsp;</h3>
</div>
		<div class="middleright">
<h2>Abilit&agrave;</h2>
                  <p>abilit�1</p>
  		  <p>abilit�2</p>
                  <p>abilit�3</p>
                  <p>&nbsp;</p>
                  <p><a href="changeAbility.jsp"><strong>Modifica abilit&agrave;</strong></a></p>
                  <p>&nbsp;</p>
		</div>
      </div>
</div>
			<div class="contentright">
			  <h2>&nbsp;</h2>
			  <h2>Le tue notifiche:</h2>
			  <p>&nbsp;</p>
			  <p>Richieste di aiuto</p>
			  <p>&nbsp;</p>
			  <p><a href="friendReq.jsp">Richieste di amicizia</a></p>
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
