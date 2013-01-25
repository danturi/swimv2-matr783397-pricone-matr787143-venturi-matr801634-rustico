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
SwimResponse abilitySetRsp = userBean.getAbilitySet();	
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

<%if(request.getAttribute("RemoveUser")!=null){
                  if(request.getAttribute("RemoveUser").equals("ok")){
                	  out.write("<h2><span style=\"color: red;\">L'utente selezionato è stato rimosso con successo dal sistema.</span><h2>");
                  } else {
                	  out.write("<h2><span style=\"color: red;\">Si è verificato un problema. Utente selezionato non rimosso.</span><h2>");
                  }
                  request.setAttribute("RemoveUser", null);
                  }  %>
	  <h1>Ricerca utenti</h1>
<p>&nbsp;</p>
	  <p>Inserisci i dati della persona che stai cercando</p>
<p>&nbsp;</p>
<h6>Non &egrave; obbligatorio compilare tutti i campi</h6>
<p><img src="<%=request.getContextPath()%>/images/omino_search.gif" alt="omino_search" width="305" height="307" align="right" /></p>
<form name="queryform" action="<%=request.getContextPath()%>/secure/admin/resultAdmin.jsp" method="post">
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
        <th width="54" scope="row">&nbsp;</th>
          <td><input name="helpSearchButton" type="submit" id="helpSearchButton" value="CERCA" /></td>
        </tr>
      </table>
      </form>
<p>&nbsp;</p>
<p>oppure <a href="<%=request.getContextPath()%>/secure/admin/userListAdmin.jsp">Lista di tutti gli utenti iscritti</a></p>

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
			<!-- TemplateEndEditable --></div>
		</div>
			<div style="clear:both;"></div>
			</div>
		</div>
	
        
	</body>
</html>
