<%@page contentType="text/html" pageEncoding="UTF-8"%><%@ taglib
uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
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
<!-- <link href="//images/stylesheet.css" rel="stylesheet" type="text/css"
	ice:classes="*" />-->

<!--<link href="//SpryAssets/SpryMenuBarHorizontal.css" rel="stylesheet"
	type="text/css" />-->
<link href="<%=request.getContextPath()%>//images/stylesheet.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>//SpryAssets/SpryMenuBarHorizontal.css" rel="stylesheet"
	type="text/css" />
</head>
<body>

	<script type="text/javascript" src="<%=request.getContextPath()%>/banner.js"></script>
	<div class="bannerArea">
		<div class="container">
			<div class="bannernav">
				<a href="#">Privacy Policy</a> &bull; <a href="#">Contact Us</a>
				&bull; <a href="#">Site Map</a>
			</div>
			<div class="toplogo">
				<a href="#"></a><img src="<%=request.getContextPath()%>/images/GIMP-file/swim-titolo_b.png"
					width="223" height="51" alt="titolo" />
			</div>
			<div style="clear: both;"></div>
		</div>
	</div>
	<div class="topnavigationArea">
		<div class="container">
			<div class="topnavigationgroup">
				<ul id="MenuBar1" class="MenuBarHorizontal">
					<li style="border-right-style: solid;"><a href="search.html"
						title="home" target="_parent">CERCA UTENTI</a></li>
					<li style="border-right-style: solid;"><a href="index.html"
						title="home" target="_parent">HOME</a></li>
				</ul>
			</div>
			<div style="clear: both;"></div>
		</div>
	</div>
	<div class="contentArea">
		<div class="container">
			<div class="contentleft">
				<h1>Hai bisogno di un aiuto?</h1>
				<p>&nbsp;</p>
				<p>Swim &egrave; un social network pensato per tutti quegli
					utenti che cercano e offrono aiuto ad altri utenti iscritti e che
					amano stare sempre in contatto con i propri amici.</p>
				<p>&nbsp;</p>
				<h3>Iscriviti subito!</h3>
				<div class="middle">
					<p>
						<img src="<%=request.getContextPath()%>/images/GIMP-file/omino_help.png" alt="omino_help"
							width="268" height="281" border="0" align="left" />
					</p>
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
							<table width="230" border="0" cellspacing="2" class="imageright">
								<tr>
									<td><table width="230" border="0" cellspacing="2"
											class="imageright">
											<tr></tr>
											<tr>
												<th class="formStyle" scope="row"></th>
												<td><h3>
														<strong>Accedi a SWIM</strong>
													</h3></td>
											</tr>
											<tr>
												<th class="formStyle" scope="row"></th>
												<td>&nbsp;</td>
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
															value="login" />
													</p>
													<p>
														oppure <a href="registration.html" title="registration"
															target="_parent">Iscriviti a Swim</a>
													</p></td>
											</tr>
										</table>
										<p>&nbsp;</p></td>
								</tr>
							</table>
						</form>
					</div>

				</div>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
			</div>
			<div class="contentright">
			<div class="register">
					<form id="registerForm" name="registerForm"
						action="<%=request.getContextPath()%>/services/auth/register"
						method="post">
						<fieldset>
							<legend>Registration</legend>

							<div>
								<label for="fname">First Name</label> <input type="text"
									id="fname" name="fname" />
							</div>
							<div>
								<label for="lname">Last Name</label> <input type="text"
									id="lname" name="lname" />
							</div>

							<div>
								<label for="email">Email</label> <input type="text" id="email"
									name="email" />
							</div>
							<div>
								<label for="password1">Password</label> <input type="password"
									id="password1" name="password1" />
							</div>
							<div>
								<label for="password2">Password (repeat)</label> <input
									type="password" id="password2" name="password2" />
							</div>

							<div class="buttonRow">
								<input type="submit" value="Register and Login" />
							</div>

						</fieldset>
					</form>
				</div>
				<h2>Ecco i nostri iscritti!</h2>
				<p>&nbsp;</p>
				<div id="slideCont"
					style="position: relative; z-index: 1; width: 220px; left: 0px; overflow: hidden;">
					<div id="slideA"
						style="position: absolute; z-index: 1; top: 10px; left: 0px; width: 200px; overflow: hidden;">
						<img src="<%=request.getContextPath()%>/images/facce1.jpg" width="200" height="150" /> <img
							src="<%=request.getContextPath()%>/images/facce2.jpeg" width="200" height="150" /> <img
							src="<%=request.getContextPath()%>/images/facce5.jpg" width="200" height="150" /> <img
							src="<%=request.getContextPath()%>/images/facce6.jpg" width="200" height="150" /> <img
							src="<%=request.getContextPath()%>/images/facce4.jpg" width="200" height="150" />
						<div id="slideB"
							style="position: relative; z-index: 1; top: 0px; left: 0px; width: 200px; overflow: hidden;">
							<img src="<%=request.getContextPath()%>/images/facce1.jpg" width="200" height="150" /> <img
								src="<%=request.getContextPath()%>/images/facce2.jpeg" width="200" height="150" /> <img
								src="<%=request.getContextPath()%>/images/facce5.jpg" width="200" height="150" /> <img
								src="<%=request.getContextPath()%>/images/facce6.jpg" width="200" height="150" /> <img
								src="<%=request.getContextPath()%>/images/facce4.jpg" width="200" height="150" />
						</div>
					</div>
				</div>

			</div>
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
