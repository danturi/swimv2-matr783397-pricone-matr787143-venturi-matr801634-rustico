<%@page contentType="text/html" pageEncoding="UTF-8"%><%@ taglib
uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:ice="http://ns.adobe.com/incontextediting">
<head>
<title>SWIMv2</title>
<script src="<%=request.getContextPath()%>/js/json2.js"
	type="text/javascript"></script>

<%@ include file="/WEB-INF/includes/head/jquery.jsp"%>


<meta name="description"
	content="Designed and developed by Codify Design Studio - codifydesign.com" />
	
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/images/stylesheet.css" />
<link href="<%=request.getContextPath()%>/SpryAssets/SpryMenuBarHorizontal.css" rel="stylesheet"
	type="text/css" />
	
</head>
	<body>
	
		<div class="bannerArea">
			<div class="container">
			<div class="bannernav">
				Hai tentato di accedere a una indirizzo vietato.
			</div>
			<div class="toplogo"><a href="#"></a><img src="<%=request.getContextPath()%>/images/GIMP-file/swim-titolo_b.png" width="223" height="51" alt="titolo" /></div>
              <div style="clear:both;"></div>
          </div>
		</div>
		<div class="topnavigationArea">
			<div class="container">
<div class="topnavigationgroup">
      <ul id="MenuBar1" class="MenuBarHorizontal">
        <li style="border-right-style: solid;"><a href="<%=request.getContextPath()%>/index.jsp" title="home" target="_parent">HOME</a></li>
      </ul>
      </div>
			<div style="clear:both;"></div>
			</div>
		</div>
		<div class="contentArea">
			<div class="container">
<div class="middle">
 	<div class="alert"> 
  		<h1 id="title">STOP!</h1>
      	<h1>Non hai i privilegi per accedere a questa pagina</h1>
		<img src="<%=request.getContextPath()%>/images/omino-stop.png" alt="omino_stop" width="294" height="335" hspace="120" vspace="40" />	
		</div>
</div>
<div style="clear:both;"></div>
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
