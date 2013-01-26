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
			<div class="bannernav">Ciao, <%=request.getUserPrincipal().getName() %>.</div>
			<div class="toplogo">
				<a href="#"></a><a href="#"></a><a href="#"><a href="#"><a href="#"><a href="#"></a><img src="<%=request.getContextPath()%>/images/GIMP-file/swim-titolo_b.png"
							width="223" height="51" alt="titolo" />
			</div>
			<div style="clear: both;"></div>
		</div>
	</div>
	<div class="topnavigationArea">
			<div class="container"><!-- TemplateBeginEditable name="menï¿½" -->
			  <div class="topnavigationgroup">
			    <ul id="MenuBar1" class="MenuBarHorizontal">
							<li style="border-right-style: solid;"><a id="logoutLink"
						href="<%=request.getContextPath()%>/services/auth/logout">LOGOUT</a></li>
					<li><a href="<%=request.getContextPath()%>/secure/friendsList.jsp">AMICI</a></li>
					<li><a href="<%=request.getContextPath()%>/secure/profile.jsp?user=<%=request.getUserPrincipal().getName()%>">PROFILO</a></li>
					<li><a href="<%=request.getContextPath()%>/secure/helpSearch.jsp">CERCA UTENTI</a></li>
					<li class="MenuBarHorizontal"><a href="<%=request.getContextPath()%>/secure/homeUser.jsp"
						title="home" target="_parent">HOME</a></li>
				</ul>
		    </div>
			<!-- TemplateEndEditable -->
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
								User usrPrincipal = userBean.find(request.getUserPrincipal().getName());
								if (usrPrincipal != null) {
									SwimResponse friendListRsp = userBean.getFriendsList(request.getUserPrincipal().getName());
									SwimResponse revFriendListRsp = userBean.getReversedFriendsList(request.getUserPrincipal().getName());
									if (friendListRsp.getStatus() == SwimResponse.SUCCESS && revFriendListRsp.getStatus() == SwimResponse.SUCCESS) {
										List<User> friendList = (List<User>) friendListRsp.getData();
										List<User> revFriendList = (List<User>) revFriendListRsp.getData();
										if(friendList.contains(usr) || revFriendList.contains(usr)){
											out.write("<img src=\"/SWIMv2-WebClient/images/alreadyfriendship.png\" alt=\"gia_amici\" align=\"absmiddle\"/>");
										} else{
											SwimResponse userPrincipalSentFriendReqRsp = userBean.getSentFriendshipReqList(request.getUserPrincipal().getName());
											SwimResponse userPrincipalReceivedFriendReqRsp = userBean.getFriendshipReqList(request.getUserPrincipal().getName());
											if (userPrincipalSentFriendReqRsp.getStatus() == SwimResponse.SUCCESS && userPrincipalReceivedFriendReqRsp.getStatus() == SwimResponse.SUCCESS) {
												List<FriendshipRequest> userPrincipalSentFriendReqList = (List<FriendshipRequest>) userPrincipalSentFriendReqRsp.getData();
												List<FriendshipRequest> userPrincipalReceivedFriendReqList = (List<FriendshipRequest>) userPrincipalReceivedFriendReqRsp.getData();
												boolean existSentFriendReq = false;
												boolean existReceivedFriendReq = false;
												for (FriendshipRequest freq: userPrincipalSentFriendReqList){
													if(freq.getAcceptanceStatus()==false && freq.getToUser().equals(usr)){
														existSentFriendReq = true;
													}
												}
												for (FriendshipRequest freq: userPrincipalReceivedFriendReqList){
													if(freq.getAcceptanceStatus()==false && freq.getFromUser().equals(usr)){
														existReceivedFriendReq = true;
													}
												}
												if(existSentFriendReq && !existReceivedFriendReq){
													out.write("<img src=\"/SWIMv2-WebClient/images/friendreqhasbeensent.jpg\" alt=\"gia_amici\" align=\"absmiddle\"/>");
												} else if (!existSentFriendReq && existReceivedFriendReq){
													out.write("<a href=\""+request.getContextPath()+"/secure/friendReq.jsp\"><img src=\"/SWIMv2-WebClient/images/friendreqalreadysent.jpg\" alt=\"gia_amici\" align=\"absmiddle\"/>");
												} else {
													out.write("<a href=\"/SWIMv2-WebClient/Control?actionType=sendFriendReq&toUser="+usr.getEmail()+"\"><img src=\"/SWIMv2-WebClient/images/askforfriendship.jpg\" alt=\"gia_amici\" align=\"absmiddle\"/></a>");
												}
											} else {
												out.write("<h2>Si è verificato un errore. Utente non correttamente identificato.</h2>");
											}
										}
									} else {
										out.write("<h2>Si è verificato un errore. Utente non correttamente identificato.</h2>");
									}
								} else {
									out.write("<h2>Si è verificato un errore. Utente non correttamente identificato.</h2>");
								}

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
								List<Ability> usrAbilityList = (List<Ability>) userBean.getAbilityList(usr.getEmail()).getData();
								if(usrAbilityList!=null){
									if(!usrAbilityList.isEmpty()){
										out.write("<p><a href=\"/SWIMv2-WebClient/secure/askforhelp.jsp?toUser="+usr.getEmail()+"\"><img src=\"/SWIMv2-WebClient/images/askforhelp.jpg\" alt=\"askforhelp\" align=\"absmiddle\" /></a></p>");
									}
								}
							} else {
								
								out.write("<form method=\"post\" enctype=\"multipart/form-data\" action=\""+request.getContextPath()+"/FileUploadServlet\">");
								out.write("<input type=\"file\" id=\"selectedFile\" name=\"selectedFile\" accept=\"image/*\" value=\"Scegli foto\" /><br />");
								out.write("<input type=\"submit\" value=\"Carica\" />");
								out.write("</form>");
								
								}
							if (request.getAttribute("FileUpload") != null) {
		                		if (request.getAttribute("FileUpload").equals("ok")) {

		                			out.write("<h3><span style=\"color: red;\">Foto caricata con successo.</span><h3>");
		                		} else if(request.getAttribute("FileUpload").equals("noFile")){
		                			out.write("<h3><span style=\"color: red;\">Nessun file selezionato.</span><h3>");
		                		} else {
		                			out.write("<h3><span style=\"color: red;\">Si è verificatio un errore nel sistema. Foto non caricata.</span><h3>");
		                		}
		                	}
		                	request.setAttribute("AbilityReqSent", null);
							out.write("</div>");
							out.write("<div class=\"middle\">");
							out.write("<div class=\"middleleft\">");
							out.write("<h2>Informazioni</h2>");
							out.write("<table width=\"327\" border=\"0\" cellpadding=\"2\" cellspacing=\"8\">");
							out.write("<tr>");
							out.write("<td width=\"60\"><h3> Email:</h3></td>");
							if (!usr.getEmail().equals(request.getUserPrincipal().getName())) {
								out.write("<td class=\"formLabel\">"+usr.getEmail()+"</td>");
							} else {
								out.write("<td class=\"formLabel\">"+request.getUserPrincipal().getName()+"</td>");
							}
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
							if (usr.getEmail().equals(
									request.getUserPrincipal().getName())) {
								out.write("<p>&nbsp;</p>");
								out.write("<p><a href=\"/SWIMv2-WebClient/secure/changeInfo.jsp\"><strong>Modifica informazioni</strong></a></p>");
								out.write("<p>&nbsp;</p>");
							}
							
							out.write("<p>&nbsp;</p>");
							
							if(usr.getRating()!=null){
								out.write("<h2>Rating</h2>");
								out.write("<h1><span style=\"color: green;\">"+String.valueOf(usr.getRating().floatValue()).substring(0, 3)+" <span><a href=\""+request.getContextPath()+"/secure/showFeedback.jsp?user="+usr.getEmail()+"\"> <p>Visualizza feedback</p></a></h1>");
							}
							
							out.write("</div>");
							out.write("<div class=\"middleright\">");
							out.write("<h2>Competente professionali</h2>");
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
							if (usr.getEmail().equals(
									request.getUserPrincipal().getName())) {
								out.write("<p>&nbsp;</p>");
								out.write("<p><a href=\"/SWIMv2-WebClient/secure/changeAbility.jsp\"><strong>Modifica competenze</strong></a></p>");
								out.write("<p>&nbsp;</p>");
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