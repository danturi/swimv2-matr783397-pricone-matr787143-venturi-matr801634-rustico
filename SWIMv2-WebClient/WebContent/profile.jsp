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
<c:if test="${pageContext.request.userPrincipal!=null}">
	<c:redirect url="/secure/homeUser.jsp" />
	<!-- this will redirect if user is already logged in -->
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:ice="http://ns.adobe.com/incontextediting">
<head>
<title>SWIMv2</title>
<script src="<%=request.getContextPath()%>/js/json2.js"
	type="text/javascript"></script>

<%@ include file="/WEB-INF/includes/head/jquery.jsp"%>

<script type="text/javascript">
            $(function(){
                "use strict";
             
                $(document.forms['registerForm']).submit(function(event){
                    var data = {
                        fname: this.fname.value,
                        lname: this.lname.value,
                        email: this.email.value,
                        password1: this.password1.value,
                        password2: this.password2.value
                    }; 
                    var destinationUrl = this.action;
                 
                    $.ajax({
                        url: destinationUrl,
                        type: "POST",
                        //data: data,
                        data: JSON.stringify(data),
                        contentType: "application/json",
                        cache: false,
                        dataType: "json",
                     
                        success: function (data, textStatus, jqXHR){
                            //alert("success");
                            if (data.status == "SUCCESS" ){
                                //redirect to secured page
                                window.location.replace("http://"+window.location.host+"<%=request.getContextPath()%>/secure/homeUser.jsp");
                            }else if (data.status == "CREATEUSER_FAILED"){
                            	alert("Utente già esistente o errore nel server. Registrazione fallita.");
                            }
                            else
                            {
                                alert("Account utente creato ma si è verificatio in errore nel server. Registrazione fallita.");
                            }
                        },
                     
                        error: function (jqXHR, textStatus, errorThrown){
                            alert("Errore nella fase di registrazione. HTTP STATUS: "+jqXHR.status);
                         
                        },
                     
                        complete: function(jqXHR, textStatus){
                            //alert("complete");
                            //i.e. hide loading spinner
                        },
                     
                        statusCode: {
                            404: function() {
                                alert("page not found");
                            }
                        }
                     
                 
                    });
                 
                    //event.preventDefault();
                    return false;
                });
             
                $(document.forms['loginForm']).submit(function(event){
                 
                    var data = {
                        email: this.email.value,
                        password: this.password.value
                    }; 
                    var destinationUrl = this.action;
                     
                    $.ajax({
                        url: destinationUrl,
                        type: "POST",
                        data: data,
                        cache: false,
                        dataType: "json",
                         
                        success: function (data, textStatus, jqXHR){
                            //alert("success");
                            if (data.status == "SUCCESS" ){
                                //redirect to secured page
                                window.location.replace("http://"+window.location.host+"<%=request.getContextPath()%>/secure/homeUser.jsp");
											} else {
												alert("Login fallito.");
											}
										},

										error : function(jqXHR, textStatus,
												errorThrown) {
											alert("Errore nella fase di Login. HTTP STATUS: "
													+ jqXHR.status);
										},

										complete : function(jqXHR, textStatus) {
											//alert("complete");
										}
									});

							//event.preventDefault();
							return false;
						});
	});
</script>
<meta name="description"
	content="Designed and developed by Codify Design Studio - codifydesign.com" />
	
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/images/stylesheet.css" />
<link href="<%=request.getContextPath()%>/SpryAssets/SpryMenuBarHorizontal.css" rel="stylesheet"
	type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/banner.js"></script>
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
<div class="bannernav">Benvenuto in Swim!</div>
			<div class="toplogo"><a href="#"></a><img src="<%=request.getContextPath()%>/images/GIMP-file/swim-titolo_b.png" width="223" height="51" alt="titolo" /></div>
              <div style="clear:both;"></div>
          </div>
		</div>
		<div class="topnavigationArea">
			<div class="container">
			  <div class="topnavigationgroup">
			    <ul id="MenuBar1" class="MenuBarHorizontal">
			      <li style="border-right-style:solid"><a href="<%=request.getContextPath()%>/index.jsp" title="home" target="_parent">HOME</a></li>
		        </ul>
</div>
			  <div style="clear:both;"></div>
			</div>
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

							out.write("<h1><strong>Profilo utente selezionato</strong></h1>");

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
							out.write("</div>");
							
							out.write("<div class=\"middle\">");
							out.write("<div class=\"middleleft\">");
							out.write("<h2>Informazioni</h2>");
							out.write("<table width=\"327\" border=\"0\" cellpadding=\"2\" cellspacing=\"8\">");
							out.write("<tr>");
							out.write("<td width=\"60\"><h3> Email:</h3></td>");
							out.write("<td class=\"formLabel\">" + usr.getEmail()
									+ "</td>");
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

							if (usr.getRating() != null) {
								out.write("<h2>Rating</h2>");
								out.write("<h1><span style=\"color: green;\">"
										+ String.valueOf(usr.getRating().floatValue())
												.substring(0, 3)
										+ " <span><a href=\"feedback.jsp\"> <p>Visualizza feedback</p></a></h1>");
							}

							out.write("</div>");
							out.write("<div class=\"middleright\">");

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
			<div class="login">

						<!-- did we already try to login and it failed? -->
						<c:if test="false">
							<div class="authError">
								<h3>
									<strong>Invalid User Name or Password. Please try
										again.</strong>
								</h3>
							</div>
						</c:if>
						
						<form id="login" name="login" method="post"
							action="j_security_check">
							
									<td><table width="230" border="0" cellspacing="2"
											class="imageright">
											<tr></tr>
											<tr>
												<th class="formStyle" scope="row"></th>
												<td><h2>
														<strong>Accedi a SWIM</strong>
													</h2></td>
											</tr>
											
											<tr>
												<td class="formStyle" scope="row">Email</td>
												<td><input name="j_username" type="text"
													class="formLabel" id="j_username" size="25" maxlength="40" /></td>
											</tr>
											<tr>
												<td class="formStyle" scope="row">Password</td>
												<td><input name="j_password" type="password"
													class="formLabel" id="j_password" size="25" maxlength="20" />
												</td>
											</tr>
											<tr>
												<th class="formStyle" scope="row">&nbsp;</th>
												<td><p>
														<input type="submit" name="login2" id="login2"
															value="Login" />
													</p>
													</td>
											</tr>
										</table>
						</form>
					</div>

			</div>
		</div>
		<div style="clear: both;"></div>
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
