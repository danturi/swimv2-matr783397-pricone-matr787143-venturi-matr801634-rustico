<%@page contentType="text/html" pageEncoding="UTF-8"
        %><%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' 
        %><c:if test="${pageContext.request.userPrincipal!=null}">
    <c:redirect url="/secure/index.jsp"/>
    <!-- this will redirect if user is already logged in -->
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html><!-- InstanceBegin template="/Templates/swim_template.dwt" codeOutsideHTMLIsLocked="false" -->
    <head>
        <!-- InstanceBeginEditable name="doctitle" -->
        <title>SWIMv2</title>
        <script src="<%=request.getContextPath()%>/js/json2.js" type="text/javascript"></script>

        <%@ include file="/WEB-INF/includes/head/jquery.jsp" %>

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
                                window.location.replace("http://"+window.location.host+"<%=request.getContextPath()%>/secure/index.jsp");
                            }else{
                                alert("Registrazione fallita.");
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
                                window.location.replace("http://"+window.location.host+"<%=request.getContextPath()%>/secure/index.jsp");
                            }else{
                                alert("Login fallito.");
                            }
                        },
                         
                        error: function (jqXHR, textStatus, errorThrown){
                            alert("Errore nella fase di Login. HTTP STATUS: "+jqXHR.status);
                        },
                         
                        complete: function(jqXHR, textStatus){
                            //alert("complete");
                        }                    
                    });
                 
                    //event.preventDefault();
                    return false;
                });
            });
        </script>
        <meta name="Description" content="Designed and developed by Codify Design Studio - codifydesign.com" />
        <!-- InstanceEndEditable -->
        <link rel="stylesheet" type="text/css" href="images/stylesheet.css" />
        <script src="SpryAssets/SpryMenuBar.js" type="text/javascript"></script>
        <link href="SpryAssets/SpryMenuBarHorizontal.css" rel="stylesheet" type="text/css" />
        <!-- InstanceBeginEditable name="head" -->
        <!-- InstanceEndEditable -->
    </head>
    <body>

        <div class="bannerArea">
            <div class="container">
                <div class="bannernav"><a href="#" >Privacy Policy</a> &bull; <a href="#" >Contact Us</a> &bull; <a href="#" >Site Map</a>			  </div>
                <div class="toplogo"><a href="#"><a href="#"><img src="images/GIMP-file/swim-titolo.jpg" width="223" height="51" alt="logo" /></a></div>
                <div style="clear:both;"></div>
            </div>
        </div>
        <div class="topnavigationArea">
            <div class="container"><!-- InstanceBeginEditable name="menï¿½" -->
                <div class="topnavigationgroup">
                    <ul id="MenuBar1" class="MenuBarHorizontal">
                        <li style="border-right-style: solid;"><a href="#" title="home" target="index.html">HOME</a></li>
                    </ul>
                </div>
                <!-- InstanceEndEditable -->
                <div style="clear:both;"></div>
            </div>
        </div>
        <div class="contentArea">
            <div class="container"><!-- InstanceBeginEditable name="content" -->
                <div class="contentleft">
                    <h1>Hai bisogno di un aiuto?</h1>
                    <p>Sed aliquam, nunc eget euismod ullamcorper, lectus nunc ullamcorper orci, fermentum bibendum enim nibh eget ipsum. Donec porttitor ligula eu dolor. Maecenas vitae nulla consequat libero cursus venenatis.</p>
                    <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Morbi commodo, ipsum sed pharetra gravida, orci magna rhoncus neque, id pulvinar odio lorem non turpis. Nullam sit amet enim. Suspendisse id velit vitae ligula volutpat condimentum. Aliquam erat volutpat. Sed quis velit. Nulla facilisi. Nulla libero. Vivamus pharetra posuere sapien. Nam consectetuer. Sed aliquam, nunc eget euismod ullamcorper, <a href="#" >lectus nunc ullamcorper</a> orci, fermentum bibendum enim nibh eget ipsum. Donec porttitor ligula eu dolor. Maecenas vitae nulla consequat libero cursus venenatis.                  </p>
                    <p><img src="images/GIMP-file/help-sm.jpg" alt="helpHome" width="379" height="319" border="0" align="left" usemap="#Map" />
                        <map name="Map" id="Map">
                            <area shape="poly" coords="173,185,178,174,195,167,215,162,234,162,261,168,277,179,285,189,285,200,282,214,284,229,277,243,261,252,242,259,212,258,191,250,173,230,172,229" href="index.html" target="_parent" alt="helpSearch" />
                            <area shape="poly" coords="173,226,173,228,174,225,173,192" href="#" />
                        </map>
                    </p>

                   
                        <form id="registerForm" name="registerForm" action="<%=request.getContextPath()%>/services/auth/register" method="post">
                            <fieldset>
                                <legend>Registration</legend>

                                <div>
                                    <label for="fname">First Name</label> 
                                    <input type="text" id="fname" name="fname"/>
                                </div>
                                <div>
                                    <label for="lname">Last Name</label> 
                                    <input type="text" id="lname" name="lname"/>
                                </div>

                                <div>
                                    <label for="email">Email</label> 
                                    <input type="text" id="email" name="email"/>
                                </div>
                                <div>
                                    <label for="password1">Password</label> 
                                    <input type="password" id="password1" name="password1"/>
                                </div>
                                <div>
                                    <label for="password2">Password (repeat)</label> 
                                    <input type="password" id="password2" name="password2"/>
                                </div>

                                <div class="buttonRow">
                                    <input type="submit" value="Register and Login" />
                                </div>

                            </fieldset>
                        </form> 
                    
                   
                        <form id="loginForm" name="loginForm" action="<%=request.getContextPath()%>/services/auth/login" method="post">
                            <fieldset>
                                <legend>Login</legend>

                                <div>
                                    <label for="email">Email</label> 
                                    <input type="text" id="email" name="email"/>
                                </div>
                                <div>
                                    <label for="password">Password</label> 
                                    <input type="password" id="password" name="password"/>
                                </div>

                                <div class="buttonRow">
                                    <input type="submit" value="Login" />
                                </div>

                            </fieldset>
                        </form> 
                    
                    <p>&nbsp;</p>
                </div>
                <!-- InstanceEndEditable --><!-- InstanceBeginEditable name="sidebar" -->
                <div class="contentright">
                    <h2>Cras tempor. Morbi egestas.</h2>
                    <img class="imageright" src="images/page_image.jpg" border="0" />
                    <p>Urna non consequat tempus, nunc arcu mollis enim, eu aliquam erat nulla non nibh consectetuer malesuada velit. Nam ante nulla, interdum vel, tristique ac, condimentum non, tellus. Proin ornare feugiat nisl.</p>
                    <p>Suspendisse dolor nisl, ultrices at, eleifend vel, consequat at, dolor. Vivamus auctor leo vel dui. Aliquam erat volutpat. Phasellus nibh.</p>
                </div>
                <!-- InstanceEndEditable -->
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
            var MenuBar1 = new Spry.Widget.MenuBar("MenuBar1", {imgDown:"SpryAssets/SpryMenuBarDownHover.gif", imgRight:"SpryAssets/SpryMenuBarRightHover.gif"});
            //-->
        </script>
    </body>
    <!-- InstanceEnd --></html>
