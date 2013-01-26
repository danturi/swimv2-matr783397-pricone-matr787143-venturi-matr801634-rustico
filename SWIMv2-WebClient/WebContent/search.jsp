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
	

</head>
<body>
<%
UserBeanRemote userBean;
Context context = new InitialContext();
userBean = (UserBeanRemote) context.lookup(UserBeanRemote.class.getName());
SwimResponse abilitySetRsp = userBean.getAbilitySet();	

%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/banner.js"></script>
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
	  <h1>Ricerca utenti</h1>
<p>&nbsp;</p>
	  <p>Cerca tra  chi fa gi&agrave; parte della nostra community e si ti piace socializzare che aspetti..Iscriviti anche tu!</p>
<p>&nbsp;</p>
<h6>Non &egrave; obbligatorio compilare tutti i campi</h6>
<p><img src="<%=request.getContextPath()%>/images/omino_search.gif" alt="omino_search" width="305" height="307" align="right" /></p>
<form name="queryform" action="<%=request.getContextPath()%>/result.jsp" method="post">
<table width="254" border="0">
        <tr>
          <th width="54" class="formStyle" scope="row">Cognome</th>
          <td width="190"><input name="lastname" type="text" class="formLabel" id="lastname" value="" size="30" /></td>
        </tr>
        <tr>
          <th width="54" class="formStyle" scope="row">Nome</th>
          <td><input name="firstname" type="text" class="formLabel" id="firstname" size="30" /></td>
        </tr>
        <tr>
          <th width="54" class="formStyle" scope="row">Luogo</th>
          <td><input name="place" type="text" class="formLabel" id="place" size="30" /></td>
        </tr>
        <tr>
          <th class="formStyle" scope="row">&nbsp;</th>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <th width="54" class="formStyle" scope="row">&nbsp;</th>
          <td><span class="formStyle">Abilit&agrave; richieste</span></td>
        </tr>
        <tr>
          <th width="54" class="formStyle" scope="row">Prima</th>
          <td>
          <select name="ability" class="formLabel" id="ability">
            <option value="0">--Non richiesta--</option>
            <%
            if(abilitySetRsp!=null){
            	if(abilitySetRsp.getStatus()==SwimResponse.SUCCESS){
           			List<Ability> abilitySet = (List<Ability>) abilitySetRsp.getData();
           			for(Ability ability: abilitySet){
        				out.write("<option value=\""+ability.getAbilityId()+"\">"+ability.getDescription()+"</option>");
        				
       				 }
            	}
            }
            %>
          </select></td>
        </tr>
        <tr>
          <th width="54" class="formStyle" scope="row">Seconda</th>
          <td><select name="ability2" class="formLabel" id="ability2">
            <option value="0">--Non richiesta--</option>
             <%
            if(abilitySetRsp!=null){
            	if(abilitySetRsp.getStatus()==SwimResponse.SUCCESS){
           			List<Ability> abilitySet = (List<Ability>) abilitySetRsp.getData();
        			for(Ability ability: abilitySet){
        				out.write("<option value=\""+ability.getAbilityId()+"\">"+ability.getDescription()+"</option>");
        				
       				 }
            	}
            }
            %>
          </select></td>
        </tr>
        <tr>
          <th width="54" class="formStyle" scope="row">&nbsp;</th>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <th width="54" scope="row">&nbsp;</th>
          <td><input name="helpSearchButton" type="submit" id="helpSearchButton" value="CERCA" /></td>
        </tr>
      </table>
      </form>
<p>&nbsp;</p>
<p>oppure <a href="<%=request.getContextPath()%>/userList.jsp">Lista di tutti gli utenti iscritti</a></p>

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

				<h2>Ecco i nostri iscritti!</h2>
				<div id="slideCont"
					style="position: relative; z-index: 1; width: 220px; height: 50px; left: 0px; overflow: hidden;">
					<div id="slideA"
						style="position: absolute; z-index: 1; top: 10px; left: 0 px; width: 200px; overflow: hidden;">
						<%
							List<User> userList = (List<User>) userBean.findAll().getData();
							int n = 0;			
							for(User usr: userList){
								
								if(usr.getPictureId()!=null && n<10){
									
									SwimResponse getPicRsp = userBean.retrievePicture(usr.getEmail());
									
									if(getPicRsp.getStatus()==SwimResponse.SUCCESS){
										String picPath = (String) getPicRsp.getData();
										
										out.write("<img src=\""+request.getContextPath()+"/pictures/picture_id_"+usr.getPictureId().getPictureId()+".jpg\" width=\"200\" height=\"150\" />");
										n++;
									}
					
								}
							}%>
							
							<div id="slideB"
									style="position: relative; z-index: 1; top: 0px; left: 0 px; width: 200px; overflow: hidden;">
						
						
						
						<%
							
							int m = 0;			
							for(User usr: userList){
								
								if(usr.getPictureId()!=null && m<10){
									
									SwimResponse getPicRsp = userBean.retrievePicture(usr.getEmail());
									
									if(getPicRsp.getStatus()==SwimResponse.SUCCESS){
										String picPath = (String) getPicRsp.getData();
										
										out.write("<img src=\""+request.getContextPath()+"/pictures/picture_id_"+usr.getPictureId().getPictureId()+".jpg\" width=\"200\" height=\"150\" />");
										m++;
									}
					
								}
							}%>
						
				<!--  	<img src="/images/facce1.jpg" width="200" height="150" /> <img
							src="/images/facce2.jpeg" width="200" height="150" /> <img
							src="th()%>/images/facce5.jpg" width="200" height="150" /> <img
							src="/images/facce6.jpg" width="200" height="150" /> <img
							src="/images/facce4.jpg" width="200" height="150" />
						<div id="slideB"
							style="position: relative; z-index: 1; top: 0px; left: 0 px; width: 200px; overflow: hidden;">
							<img src="/images/facce1.jpg" width="200" height="150" /> <img
								src="/images/facce2.jpeg" width="200" height="150" /> <img
								src="/images/facce5.jpg" width="200" height="150" /> <img
								src="/images/facce6.jpg" width="200" height="150" /> <img
								src="/images/facce4.jpg" width="200" height="150" /> -->	
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
