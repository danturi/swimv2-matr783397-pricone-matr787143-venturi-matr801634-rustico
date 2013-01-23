<%@ page import="sessionbeans.logic.UserBeanRemote"%>
<%@ page import="javax.naming.InitialContext"%>
<%@ page import="javax.naming.Context"%>
<%@page import="java.security.Principal"%>
<%@ page import="sessionbeans.logic.SwimResponse"%>
<%@ page import="java.util.List"%>
<%@ page import="entities.User"%>
<%@ page import="entities.Ability"%>
<%@ page import="entities.FriendshipRequest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
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
											} else {
												alert("failed");
											}
										},

										error : function(jqXHR, textStatus,
												errorThrown) {
											alert("Errore di logout. HTTP STATUS: "
													+ jqXHR.status);
										},

										complete : function(jqXHR, textStatus) {
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
		User usr = null;
		SwimResponse abilitySetRsp = userBean.getAbilitySet();	
		if(request.getParameter("toUser")!=null){
			usr = userBean.find(request.getParameter("toUser"));
			if(usr!=null){
				
			}
		}
	
	%>


	<div class="bannerArea">
		<div class="container">
			<div class="bannernav">Sei loggato come <%=request.getUserPrincipal().getName() %>.
			</div>
			<div class="toplogo">
				<a href="#"></a><a href="#"></a></a><img src="<%=request.getContextPath()%>/images/GIMP-file/swim-titolo_b.png"
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
			<%
			
			if(request.getParameter("toUser")!=null){
				usr = userBean.find(request.getParameter("toUser"));
				if(usr!=null){
					SwimResponse usrAbilityListRsp = userBean.getAbilityList(usr.getEmail());
					if(usrAbilityListRsp.getStatus()==SwimResponse.SUCCESS){
					List<Ability> usrAbilityList = (List<Ability>) usrAbilityListRsp.getData();
					if(!usrAbilityList.isEmpty()){
			
						out.write("<div class=\"middle\">");
						out.write("<form name=\"askforhelpform\" action="+request.getContextPath()+"/secure/abilityreqsent.jsp\" method=\"post\">");
						out.write("<table width=\"254\" border=\"0\">");
						out.write("<tr>");
						out.write("<th class=\"formStyle\" scope=\"row\">&nbsp;</th>");
						out.write("<td>&nbsp;</td>");
						out.write("</tr>");
						out.write("<tr>");
						out.write("<td width=\"54\" class=\"formStyle\" scope=\"row\"><h2>Scegli la competenza di cui hai bisogno</h2></td>");
						out.write("</tr>");
						out.write("<tr>");
						out.write("<td>");
						out.write("<select name=\"helprequested\" class=\"formLabel\" id=\"ability\">");
						
			           	int n = 1;
			            for(Ability ability: usrAbilityList){
			           		out.write("<option value=\""+n+"\">"+ability.getDescription()+"</option>");
		 		           		n++;
			            }			 
			  
						out.write("</select></td>");
						out.write("</tr>");
						out.write("<tr>");
						out.write("<th class=\"formStyle\" scope=\"row\">&nbsp;</th>");
						out.write("<td>&nbsp;</td>");
						out.write("</tr>");
						out.write("<tr>");
						out.write("<td><textarea name=\"comments\" cols=\"60\" rows=\"10\" maxlength=\"10000\">Descrivi all'utente la tua situazione e ciò di cui hai bisogno.</textarea></td>");
						out.write("</tr>");
						out.write("<tr>");
						out.write("<th width=\"54\" class=\"formStyle\" scope=\"row\">&nbsp;</th>");
						out.write("<td>&nbsp;</td>");
						out.write("</tr>");
						out.write("<tr>");
						out.write("<th width=\"54\" scope=\"row\">&nbsp;</th>");
						out.write("<td><input name=\"askforhelpButton\" type=\"submit\" id=\"sendabilityrequestButton\" value=\"Invia richiesta\" /></td>");
						out.write("</tr>");
						out.write("</table>");
						out.write("</form>");
						out.write("</div>");
						} else {
							out.write("<h2><span style=\"color: red;\">L'utente selezionato non è abilitato a nessuna competenza.</span><h2>");
						}
					} else {
						out.write("<h2><span style=\"color: red;\">Si è verificato in problema. Non è possibile inviare la richiesta.</span><h2>");
					}
				} else {
					out.write("<h2><span style=\"color: red;\">Utente non valido. Non è possibile inviare la richiesta.</span><h2>");
				}
			} else {
				out.write("<h2><span style=\"color: red;\">Utente non valido. Non è possibile inviare la richiesta.</span><h2>");
			}
			%>

			</div>
			<div class="contentright">
				<h2>&nbsp;</h2>
				<h2>Le tue notifiche:</h2>
				<p>&nbsp;</p>
				<p>Richieste di aiuto</p>
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