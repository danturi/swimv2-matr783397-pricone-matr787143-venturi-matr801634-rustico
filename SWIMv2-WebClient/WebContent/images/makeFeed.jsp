<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	<!-- TemplateBeginEditable name="doctitle" -->
	<title>SWIMv2</title>
	<!-- TemplateEndEditable -->
	<meta name="Description" content="Designed and developed by Codify Design Studio - codifydesign.com" />
    <link rel="stylesheet" type="text/css" href="images/stylesheet.css" />
            
            
    <script type="text/javascript" src="images/starFeedback.js"></script>
    <link href="images/star.css" rel="stylesheet" type="text/css" />
    <link href="SpryAssets/SpryMenuBarHorizontal.css" rel="stylesheet" type="text/css" />
	<link href="SpryAssets/SpryMenuBarVertical.css" rel="stylesheet" type="text/css" />
</head>
	<body>
	
		<div class="bannerArea">
			
<div class="container"><!-- TemplateBeginEditable name="banner_men�" -->
			  <div class="bannernav"><a href="#" >Privacy Policy</a> &bull; <a href="#" >Contact Us</a> &bull; <a href="#" >Site Map</a></div>
			<!-- TemplateEndEditable -->
			  <div class="toplogo"><a href="#"><a href="#"></a><img src="images/GIMP-file/swim-titolo_b.png" width="223" height="51" alt="titolo" /></div>
              <div style="clear:both;"></div>
          </div>
		</div>
		<div class="topnavigationArea">
			<div class="container">
			  <div class="topnavigationgroup">
			    <ul id="MenuBar1" class="MenuBarHorizontal">
			      <li style="border-right-style: solid;"><a href="#">LOGOUT</a></li>
			      <li><a href="#">AMICI</a></li>
			      <li><a href="profiloUser.html">PROFILO</a></li>
			      <li class="MenuBarHorizontal"><a href="index.html" title="home" target="_parent">HOME</a></li>
		        </ul>
		      </div>
<div style="clear:both;"></div>
			</div>
		</div>
		<div class="contentArea">
			<div class="container"><!-- TemplateBeginEditable name="contentLeft" -->
			  <div class="contentleft">
              	<div class="middle">
			    <h1>Lascia il tuo feedback</h1>
			    <p>&nbsp;</p>
			    <p>Dai una valutazione sull'aiuto ricevuto e fornisci una breve descrizione</p>
			    <p>&nbsp;</p>
			    <%if(request.getParameter("vote")==null){
			    	System.out.println("********** QUI if****");
			    	out.write("<span class=\"output\">Hai votato" + request.getParameter("vote")+" stelle!</span>");
			    } else {
			    	System.out.println("********** QUI else****");
			    	out.write("<p>Vota:<script type=\"text/javascript\">star(0);</script></p>");
			    }
			    %>
                
              	<p>Descrizione:</p>
              	<form id="form1" name="form1" method="post" action="<%=request.getContextPath()%>/makeFeed.jsp?par=<%=request.getParameter("voto")%>">
              	  <p>
              	    <label>
              	      <textarea name="feedDescr" cols="50" rows="5" class="formLabel" id="feedDescr"></textarea>
           	        </label>
           	      </p>
           	      <p>
              	    <label>
              	      <input name="voto" type="hidden" id="voto" />
           	        </label>
           	      </p>
              	  <p>
              	    <label>
              	      <input type="submit" name="sendFeed" id="sendFeed" value="Invia" />
              	    </label>
              	  </p>
              	</form>
              	</div>
		    </div>
			<!-- TemplateEndEditable --><!-- TemplateBeginEditable name="contentRight" -->
			<div class="contentright">
			  <h2>&nbsp;</h2>
			  <h2>Le tue notifiche:</h2>
			  <p>&nbsp;</p>
			  <p>Richieste di aiuto</p>
			  <p>&nbsp;</p>
			  <p>Richieste di amicizia</p>
			  <p>&nbsp;</p>
			  <p>Richieste abilit&agrave;<img src="images/omino_msg.jpg" alt="omino_msg" width="158" height="165" align="right" /></p>
			  <p>&nbsp;</p>
			  <p>&nbsp;</p>
			  </div>
			<!-- TemplateEndEditable -->
			<div style="clear:both;"></div>
			</div>
		</div>
		<div class="footerArea">
			<div class="container"><!-- TemplateBeginEditable name="footer" -->
			  <div class="copyright">&copy; 2013 SWIMv2 - Social Network by Marco Pricone,Venturi Davide,Rustico Sebastiano.  All rights reserved.</div>
			<!-- TemplateEndEditable --></div>
		</div>
        <script type="text/javascript">
<!--
var MenuBar1 = new Spry.Widget.MenuBar("MenuBar1", {imgDown:"SpryAssets/SpryMenuBarDownHover.gif", imgRight:	"SpryAssets/SpryMenuBarRightHover.gif"});
//-->
        </script>
	</body>
</html>
