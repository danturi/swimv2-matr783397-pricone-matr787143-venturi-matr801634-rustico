<%@ page import="sessionbeans.logic.UserBeanRemote" %>
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
	<!-- TemplateEndEditable -->
	<meta name="Description" content="Designed and developed by Codify Design Studio - codifydesign.com" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/images/stylesheet.css" />
		
		<link href="<%=request.getContextPath()%>/SpryAssets/SpryMenuBarHorizontal.css" rel="stylesheet" type="text/css" />
		<!-- TemplateBeginEditable name="head" -->
		<!-- TemplateEndEditable -->
</head>
	<body>
	
		<div class="bannerArea">
			<div class="container"><!-- TemplateBeginEditable name="banner_menï¿½" -->
			  <div class="bannernav"><a href="#" >Priv
			    <script type="text/javascript" src="<%=request.getContextPath()%>/list.min.js"></script>
		      acy Policy</a> &bull; <a href="#" >Contact Us</a> &bull; <a href="#" >Site Map</a></div>
			<!-- TemplateEndEditable -->
			  <div class="toplogo"><a href="#"><a href="#"></a><img src="<%=request.getContextPath()%>/images/GIMP-file/swim-titolo_b.png" width="223" height="51" alt="titolo" /></div>
              <div style="clear:both;"></div>
          </div>
		</div>
		<div class="topnavigationArea">
			<div class="container"><!-- TemplateBeginEditable name="menï¿½" -->
			  <div class="topnavigationgroup">
			    <ul id="MenuBar1" class="MenuBarHorizontal">
			      <li class="MenuBarHorizontal"><a href="homeUser.jsp" title="home" target="_parent">HOME</a></li>
		        </ul>
		    </div>
			<!-- TemplateEndEditable -->
			  <div style="clear:both;"></div>
			</div>
		</div>
		<div class="contentArea">
			<div class="container"><!-- TemplateBeginEditable name="contentLeft" -->
			  <div class="contentleft">
              	<div class="middle">
			    <h1>Lista Utenti</h1>
<p>&nbsp;</p>
			  <!-- <p>Ecco chi ha le carratteristiche che cercavi:</p> -->
              	
			    <div class="wrapper">
			      <ul class="immagini">
			        <li>Nome Utente<img src="<%=request.getContextPath()%>/images/GIMP-file/utente_incognito.png" alt="..." height="100" width="120"/></li>
			        <li>Nome Utente<img src="<%=request.getContextPath()%>/images/GIMP-file/utente_incognito.png" alt="..." height="100" width="120"/></li>
			        <li>Nome Utente<img src="<%=request.getContextPath()%>/images/GIMP-file/utente_incognito.png" alt="..." height="100" width="120"/></li>
			        <li>Nome Utente<img src="<%=request.getContextPath()%>/images/GIMP-file/utente_incognito.png" alt="..." height="100" width="120"/></li>
			        <li>Nome Utente<img src="<%=request.getContextPath()%>/images/GIMP-file/utente_incognito.png" alt="..." height="100" width="120"/></li>
			        <li>Nome Utente<img src="<%=request.getContextPath()%>/images/GIMP-file/utente_incognito.png" alt="..." height="100" width="120"/></li>
			        <li>Nome Utente<img src="<%=request.getContextPath()%>/images/GIMP-file/utente_incognito.png" alt="..." height="100" width="120"/></li>
			        <li>Nome Utente<img src="<%=request.getContextPath()%>/images/GIMP-file/utente_incognito.png" alt="..." height="100" width="120"/></li>
		          </ul>
			    </div>
              </div>
		    </div>
			<!-- TemplateEndEditable --><!-- TemplateBeginEditable name="contentRight" -->
			<div class="contentright">
			  <h2>&nbsp;</h2>
			  <h2>Le tue notifiche:</h2>
			  <p>&nbsp;</p>
			  <p>Richieste di aiuto</p>
			  <p>&nbsp;</p>
			  <p>Richieste di amicizia</p>
			  <p>&nbsp;</p>
			  <p>Richieste abilit&agrave;<img src="images/omino_msg.jpg" alt="omino_msg" width="158" height="165" align="right" /></p>
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
		<!--
		var MenuBar1 = new Spry.Widget.MenuBar("MenuBar1", {imgDown:"SpryAssets/SpryMenuBarDownHover.gif", imgRight:"SpryAssets/SpryMenuBarRightHover.gif"});
		//-->
		</script>
	</body>
</html>
