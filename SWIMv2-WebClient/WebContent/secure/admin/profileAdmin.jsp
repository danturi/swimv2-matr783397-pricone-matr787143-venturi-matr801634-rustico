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
		String email = request.getParameter("user");
		if(email==null && request.getAttribute("LastProfileUserView")!=null) {
			email = request.getAttribute("LastProfileUserView").toString();
			request.setAttribute("LastProfileUserView", null);
		}
		User usr = null;
		
	
	%>


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

				<%
					if (email != null) {
						usr = userBean.find(email);

						if (usr != null) {
							out.write("<div class=\"top\">");
							if (!usr.getEmail().equals(request.getUserPrincipal().getName())) {
								
								out.write("<h1><strong>Profilo utente selezionato</strong></h1>");
								
							} else {
								out.write("<h1><strong>Il mio profilo</strong></h1>");
							}
							out.write("<p>&nbsp;</p>");
							if(usr.getPictureId()!=null){
								
								SwimResponse getPicRsp = userBean.retrievePicture(usr.getEmail());
								
								if(getPicRsp.getStatus()==SwimResponse.SUCCESS){
									String picPath = (String) getPicRsp.getData();
									
									out.write("<h1><img src=\""+request.getContextPath()+"/"+picPath+"\" alt=\"user_picture\" alt=\"user_picture\" width=\"210\" height=\"210\" hspace=\"20\" vspace=\"0\" align=\"absmiddle\" />"+ usr.getFirstname()+ " "+ usr.getLastname()+ "</h1>");


								} else {
									out.write("<h1><img src=\"/SWIMv2-WebClient/images/GIMP-file/utente_incognito.png\" alt=\"omino_incognito\" width=\"205\" height=\"148\" hspace=\"20\" vspace=\"0\" align=\"absmiddle\" />"+ usr.getFirstname()+ " "+ usr.getLastname()+ "</h1>");
								}
								
							} else {
							
								out.write("<h1><img src=\"/SWIMv2-WebClient/images/GIMP-file/utente_incognito.png\" alt=\"omino_incognito\" width=\"205\" height=\"148\" hspace=\"20\" vspace=\"0\" align=\"absmiddle\" />"+ usr.getFirstname()+ " "+ usr.getLastname()+ "</h1>");
						
							}
							
							if (!usr.getEmail().equals(request.getUserPrincipal().getName())) {
								out.write("<p><a href=\"/SWIMv2-WebClient/Control?actionType=removeUser&user="+usr.getEmail()+"\"><img src=\"/SWIMv2-WebClient/images/removeuser.jpg\" alt=\"removeuser\" align=\"absmiddle\" /></a></p>");

							}
							out.write("</div>");
							out.write("<div class=\"middle\">");
							out.write("<div class=\"middleleft\">");
							out.write("<h2>Informazioni</h2>");
							out.write("<table width=\"327\" border=\"0\" cellpadding=\"2\" cellspacing=\"8\">");
							out.write("<tr>");
							out.write("<td width=\"60\"><h3> Email:</h3></td>");
							out.write("<td class=\"formLabel\">"+usr.getEmail()+"</td>");
							out.write("</tr>");
							out.write("<tr>");
							out.write("<td width=\"60\"><h3> Genere:</h3></td>");
							if (usr.getSex() != null) {
								out.write("<td class=\"formLabel\">" + usr.getSex()
										+ "</td>");
							} else {
								out.write("<td class=\"formLabel\">Non specificato.</td>");
							}
							out.write("</tr>");
							out.write("<tr>");
							out.write("<td><h3>Et&agrave;:</h3></td>");
							if (usr.getAge() != null) {
								out.write("<td class=\"formLabel\">" + usr.getAge()
										+ "</td>");
							} else {
								out.write("<td class=\"formLabel\">Non specificata.</td>");
							}
							out.write("</tr>");
							out.write("<tr>");
							out.write("<td><h3>Residenza:</h3></td>");
							if (usr.getCity() != null) {
								out.write("<td class=\"formLabel\">" + usr.getCity()
										+ "</td>");
							} else {
								out.write("<td class=\"formLabel\">Non specificata.</td>");
							}
							out.write("</tr>");
							out.write("<tr>");
							out.write("<td><h3>Professione:</h3></td>");
							if (usr.getOccupation() != null) {
								out.write("<td class=\"formLabel\">"
										+ usr.getOccupation() + "</td>");
							} else {
								out.write("<td class=\"formLabel\">Non specificata.</td>");
							}
							out.write("</tr>");
							out.write("</table>");
							out.write("<p>&nbsp;</p>");
							
							if(usr.getRating()!=null){
								out.write("<h2>Rating</h2>");
								out.write("<h1><span style=\"color: green;\">"+String.valueOf(usr.getRating().floatValue()).substring(0, 3)+" <span><a href=\""+request.getContextPath()+"/secure/admin/showFeedbackAdmin.jsp?user="+usr.getEmail()+"\"> <p>Visualizza feedback</p></a></h1>");
							}
							
							out.write("</div>");
							out.write("<div class=\"middleright\">");
							if (!usr.getEmail().equals(request.getUserPrincipal().getName())) {
							out.write("<h2>Competenze professionali</h2>");
							SwimResponse swimRsp2 = userBean.getAbilityList(email);
							if (swimRsp2.getStatus() == SwimResponse.SUCCESS) {
								List<Ability> abilityList = (List<Ability>) swimRsp2.getData();
								if (!abilityList.isEmpty()) {
									for (Ability ab : abilityList) {
										out.write("<p>" + ab.getDescription() + "</p>");
									}

								} else {
									out.write("<p>Nessuna.</p>");
								}
							} else {
								out.write("<p>Nessuna.</p>");
							}
							} else {
								out.write("<h2>Competenze professionali</h2>");
								out.write("<p>Amministratore di Swim</p>");
							}
							out.write("</div>");
							out.write("</div>");

						} else {

							out.write("<h1>Profilo utente richiesto non esistente.</h1>");

						}
					} else {
						out.write("<h1>Profilo utente richiesto non esistente.</h1>");
					}
				%>
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
