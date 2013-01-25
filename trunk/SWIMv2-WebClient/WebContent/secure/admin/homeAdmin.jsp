<%@page import="java.security.Principal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%><%@ taglib
	uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	<title>SWIMv2</title>
	<meta name="Description" content="Designed and developed by Codify Design Studio - codifydesign.com" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/images/stylesheet.css" />
		
		<link href="<%=request.getContextPath()%>/SpryAssets/SpryMenuBarHorizontal.css" rel="stylesheet" type="text/css" />
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
	
		<div class="bannerArea">
			<div class="container">
<div class="bannernav">Pannello di amministrazione di SWIMv2</div>
			<div class="toplogo"><a href="#"></a><img src="<%=request.getContextPath()%>/images/GIMP-file/swim-titolo_b.png" width="223" height="51" alt="titolo" /></div>
              <div style="clear:both;"></div>
          </div>
		</div>
		<div class="topnavigationArea">
			<div class="container">
<div class="topnavigationgroup">
      <ul id="MenuBar1" class="MenuBarHorizontal">
        <li style="border-right-style: solid;"><a id="logoutLink" href="<%=request.getContextPath()%>/services/auth/logout">LOGOUT</a></li>
        <li><a href="<%=request.getContextPath()%>/secure/admin/userSearchAdmin.jsp">RICERCA UTENTI</a></li>
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
      <h1>&nbsp;</h1>
      <h1>Pannello di amministrazione</h1>
<p>&nbsp;</p>
<p>Definisci l'insieme delle abilit&agrave; disponibili in Swim e gestisci gli utenti del Social Network e le loro richieste di aggiunta abilit&agrave; dal men&ugrave;.</p>
<p>&nbsp;</p>
<p><img src="<%=request.getContextPath()%>/images/omino_amministratore.jpg" alt="omino_admin" width="168" height="168" align="left" /></p>
<p>&nbsp;</p>
<p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      </div>
			<div class="contentright">
			  <h2>&nbsp;</h2>
			  <h2>Le tue notifiche:</h2>
			  <p><a href="<%=request.getContextPath()%>/secure/admin/showAbilityReqAdmin.jsp">Richieste aggiunta abilit&agrave;</a></p>
				<h2>&nbsp;</h2>
				<h2><img src="<%=request.getContextPath()%>/images/omino_msg.jpg" alt="omino_msg" width="121" height="159" align="right" /></h2>
				<p>&nbsp;</p>
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
