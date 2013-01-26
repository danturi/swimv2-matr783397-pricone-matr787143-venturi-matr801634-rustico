<%@ page import="sessionbeans.logic.UserBeanRemote" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.Context" %>
<%@page import="java.security.Principal"%>
<%@ page import="sessionbeans.logic.SwimResponse"%>
<%@ page import="java.util.List"%>
<%@ page import="entities.User"%>
<%@ page import="entities.Ability"%>
<%@ page import="entities.HelpRequest"%>
<%@ page import="entities.Feedback"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%><%@ taglib
	uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:ice="http://ns.adobe.com/incontextediting">
	<head>
	<title>SWIMv2</title>
	<meta name="description" content="Designed and developed by Codify Design Studio - codifydesign.com" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/images/stylesheet.css" />
<link href="<%=request.getContextPath()%>/SpryAssets/SpryMenuBarHorizontal.css" rel="stylesheet" type="text/css" />
 <link href="<%=request.getContextPath()%>/images/star.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/js/json2.js" type="text/javascript"></script>

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
</head>
	<body>
	<%
UserBeanRemote userBean;
Context context = new InitialContext();
userBean = (UserBeanRemote) context.lookup(UserBeanRemote.class.getName());

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
			
			  <div style="clear:both;"></div>
		<div class="contentArea">
			<div class="container"><!-- TemplateBeginEditable name="contentLeft" -->
			  <div class="contentleft">
              	<div class="middle">
              	
              	<%
              	if(request.getParameter("user")!=null){
              		User usr = userBean.find(request.getParameter("user"));
              		
              		if(usr!=null){
              			
              			List<HelpRequest> helpReqList = (List<HelpRequest>) userBean.getHelpReqList(usr.getEmail()).getData();
              			boolean existFeed = false;
              			
              			for(HelpRequest helpReq: helpReqList){
              				
              				if(helpReq.getFeedbackId()!=null){
              					
              					if(!existFeed){
              						out.write("<h1>Feedback ricevuti dall'utente "+usr.getFirstname()+" "+usr.getLastname()+"</h1>");
              						out.write("<ul class=\"feedback\">");
              					}
              					existFeed=true;
              					
              					
              					out.write("<li id=\"fb\">");
              					out.write("<div id=\"STAR_RATING\">");
              					out.write("<ul>");
              					Float rating = helpReq.getFeedbackId().getRating();
              					int numStarsOn = Integer.valueOf(String.valueOf(rating.floatValue()).substring(0,1)).intValue();
              					int numStarsOff = 5 - numStarsOn;
              					for(int i=0; i<numStarsOn; i++){
              						
              						out.write("<li class=\"on\"></li>");
              						
              					}
								for(int i=0; i<numStarsOff; i++){
              						
              						out.write("<li></li>");
              						
              					}
              					
              					
              					out.write("<p> Competenza: "+helpReq.getAbilityId().getDescription()+"</p>");
              					out.write("</ul>");
              					out.write("<h3><strong>Commento lasciato da "+helpReq.getFromUser().getFirstname()+" "+helpReq.getFromUser().getLastname()+" in data: "+helpReq.getFeedbackId().getDatetime().toGMTString()+"</strong></h3>");
              					out.write("<h3>"+helpReq.getFeedbackId().getComment()+"</h3>");
              					out.write("</div>");
              					out.write("</li>");
              				
              					
              				}
              			
              	
              			} 
              			
              			if(existFeed){
         					 out.write("</ul>");
         				}
              			
              			if(!existFeed){
              				
                        	out.write("<h2>L'utente non ha ancora nessun feedback.<h2>");
              				
              			}
              			
              			
              			
              		} else {
              			
                		out.write("<h2><span style=\"color: red;\">Si è verificato un problema. Utente non valido.</span><h2>");
              		}
              		
              	} else {
            		out.write("<h2><span style=\"color: red;\">Si è verificato un problema. Utente non valido.</span><h2>");
              	}
              	
              	%>
               
              	</div>
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
