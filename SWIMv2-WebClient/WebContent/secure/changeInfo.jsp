<%@ page import="sessionbeans.logic.UserBeanRemote" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.Context" %>
<%@page import="java.security.Principal"%>
<%@ page import="sessionbeans.logic.SwimResponse"%>
<%@ page import="java.util.List"%>
<%@ page import="entities.User"%>
<%@ page import="entities.Ability"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%><%@ taglib
	uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:ice="http://ns.adobe.com/incontextediting">
	<head>
	<title>SWIMv2</title>
	<meta name="description" content="Designed and developed by Codify Design Studio - codifydesign.com" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/images/stylesheet.css" />
<link href="<%=request.getContextPath()%>/SpryAssets/SpryMenuBarHorizontal.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/js/json2.js" type="text/javascript"></script>

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
<%
UserBeanRemote userBean;
Context context = new InitialContext();
userBean = (UserBeanRemote) context.lookup(UserBeanRemote.class.getName());
User userPrincipal = userBean.find(request.getUserPrincipal().getName());
if(request.getParameter("sendinfoform")!=null){
	if(request.getParameter("sendinfoform").equals("true")){
		request.setAttribute("SendInfoForm", "sending");
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/Control");
		dispatcher.forward(request, response);
	}
}

%>
	
		<div class="bannerArea">
			<div class="container">
<div class="bannernav">Sei loggato come <%=request.getUserPrincipal().getName() %>.</div>
			<div class="toplogo"><a href="#"></a><img src="<%=request.getContextPath()%>/images/GIMP-file/swim-titolo_b.png" width="223" height="51" alt="titolo" /></div>
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
					<li><a href="profile.jsp?user=<%=request.getUserPrincipal().getName()%>">PROFILO</a></li>
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
	  <h1>Modifica informazioni profilo</h1>
<p>&nbsp;</p>
<%
if(request.getAttribute("SendInfoForm")!=null){
	if(request.getAttribute("SendInfoForm").equals("ok")){
		out.write("<h2><span style=\"color: red;\">I tuoi dati sono stati modificati correttamente.</span><h2>");
	} else {
		out.write("<h2><span style=\"color: red;\">Si è verificato un problema. Dati non inseriti.</span><h2>");

	}
	
}
%>
<p>&nbsp;</p>
<h6>Non &egrave; obbligatorio compilare tutti i campi</h6>
<p>&nbsp;</p>
<form name="changeinfoform" action="<%=request.getContextPath()%>/secure/changeInfo.jsp?sendinfoform=true" method="post">
<table width="254" border="0">
        <tr>
          <th width="54" class="formStyle" scope="row">Cognome</th>
          <td width="190"><input name="lastname" type="text" class="formLabel" id="lastname" value="<%=userPrincipal.getLastname() %>" size="30" /></td>
        </tr>
        <tr>
          <th width="54" class="formStyle" scope="row">Nome</th>
          <td><input name="firstname" type="text" class="formLabel" id="firstname" value="<%=userPrincipal.getFirstname() %>" size="30" /></td>
        </tr>
        <tr>
          <th width="54" class="formStyle" scope="row">Città</th>
          <td><input name="place" type="text" class="formLabel" id="place" size="30" /></td>
        </tr>
        <tr>
          <th width="54" class="formStyle" scope="row">&nbsp;</th>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <th width="54" class="formStyle" scope="row">Sesso</th>
          <td><input name="sex" type="radio" value="Maschio" class="formLabel" id="sex" size="30" checked="checked"/>Maschio
         		 <input name="sex" type="radio" value="Femmina" class="formLabel" id="sex" size="30" />Femmina</td>
        </tr>
        <tr>
          <th width="54" class="formStyle" scope="row">&nbsp;</th>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <th width="54" class="formStyle" scope="row">Età</th>
          <td><select name="age" class="formLabel" id="age">
          <%for(int i=13; i<120;i++){
        	  out.write("<option value=\""+i+"\">"+i+"</option>");
          }
 
          %>
				</select></td>
        </tr>
        <tr>
          <th width="54" class="formStyle" scope="row">&nbsp;</th>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <th width="54" class="formStyle" scope="row">Occupazione</th>
          <td><input name="job" type="text" class="formLabel" id="job" size="30" /></td>
        </tr>
        <tr>
          <th width="54" class="formStyle" scope="row">Telefono <%=request.getParameter("QT")%></p></th>
          <td><input name="tel" type="text" class="formLabel" id="tel" size="30" /></td>
        </tr>
        
        <tr>
          <th width="54" class="formStyle" scope="row">&nbsp;</th>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <th width="54" scope="row">&nbsp;</th>
          <td><input name="changeinfoButton" type="submit" id="changeinfoButton" value="Salva" /></td>
        </tr>
      </table>
      </form>

</div>
			<div class="contentright">
				<h2>&nbsp;</h2>
				<h2>Le tue notifiche:</h2>
				<p>&nbsp;</p>
				<p>Richieste di aiuto</p>
				<p>&nbsp;</p>
				<p><a href="friendReq.jsp">Richieste di amicizia</a></p>
				<p>&nbsp;</p>
				<p>
					Richieste abilit&agrave;<img
						src="<%=request.getContextPath()%>/images/omino_msg.jpg"
						alt="omino_msg" width="158" height="165" align="right" />
				</p>
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
        <script type="text/javascript">
<!--
var MenuBar1 = new Spry.Widget.MenuBar("MenuBar1", {imgDown:"<%=request.getContextPath()%>/SpryAssets/SpryMenuBarDownHover.gif", imgRight:"<%=request.getContextPath()%>/SpryAssets/SpryMenuBarRightHover.gif"});
//-->
        </script>
	</body>
</html>
