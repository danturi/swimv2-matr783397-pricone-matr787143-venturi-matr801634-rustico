<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	<!-- TemplateBeginEditable name="doctitle" -->
	<title>SWIMv2</title>
	<!-- TemplateEndEditable -->
	<meta name="Description" content="Designed and developed by Codify Design Studio - codifydesign.com" />
<link rel="stylesheet" type="text/css" href="images/stylesheet.css" />
		
		<link href="SpryAssets/SpryMenuBarHorizontal.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="list.min.js"></script>
		<!-- TemplateBeginEditable name="head" -->
		<!-- TemplateEndEditable -->
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
			<div class="container"><!-- TemplateBeginEditable name="men�" -->
			  <div class="topnavigationgroup">
			    <ul id="MenuBar1" class="MenuBarHorizontal">
			      <li style="border-right-style: solid;"><a href="#">LOGOUT</a>		          </li>
			      <li><a href="#">AMICI</a></li>
			      <li><a href="profiloUser.html">PROFILO</a></li>
			      <li class="MenuBarHorizontal"><a href="index.html" title="home" target="_parent">HOME</a></li>
		        </ul>
		    </div>
			<!-- TemplateEndEditable -->
			  <div style="clear:both;"></div>
			</div>
		</div>
		<div class="contentArea">
			<div class="container"><!-- TemplateBeginEditable name="contentLeft" -->
			  <div class="contentleft">
              	<div class="middle">
		    	  <h1>Lista Utenti</h1>
					<p>&nbsp;</p>
		    	  <p>Ecco chi ha le carratteristiche che cercavi:</p>
              	
                        <div id="user-list">
                          <input class="search" size="30" placeholder="Cerca utente" />
                          <ul class="sort-by">
                            <li class="sort btn" data-sort="name">Ordina per nome (A/Z - Z/A)</li>
                            
                          </ul>
                          <ul class="filter">
                            
                           
                           </ul>
                           <div class="wrapper">
								<ul class="list">
        
                                <li><span class="name"><a>Marco</span><img src="images/GIMP-file/utente_incognito.png" alt="..." height="100" width="120"/></a></li>
                                 <li><span class="name"><a>Sebi</span><img src="images/GIMP-file/utente_incognito.png" alt="..." height="100" width="120"/></a></li>
                                 <li><span class="name"><a>Davide</span><img src="images/GIMP-file/utente_incognito.png" alt="..." height="100" width="120"/></a></li>
                                 <li><span class="name"><a>Gennaro</span><img src="images/GIMP-file/utente_incognito.png" alt="..." height="100" width="120"/></a></li>
                                 <li><span class="name"><a>Gattuso</span><img src="images/GIMP-file/utente_incognito.png" alt="..." height="100" width="120"/></a></li>
                                 <li><span class="name"><a>Luxuria</span><img src="images/GIMP-file/utente_incognito.png" alt="..." height="100" width="120"/></a></li>
                                 <li><span class="name"><a>Putin</span><img src="images/GIMP-file/utente_incognito.png" alt="..." height="100" width="120"/></a></li>
                                 <li><span class="name"><a>Abaco</span><img src="images/GIMP-file/utente_incognito.png" alt="..." height="100" width="120"/></a></li>
                            </ul></div>
                  </div>
                        
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
        <script type="text/javascript">
		
			var options = {
				valueNames: [ 'name' ]
			};
		
			var featureList = new List('user-list', options);
			
			/*filtro uomini
			$('#filter-men').click(function() {
				featureList.filter(function(item) {
					if(item.values().gender == "uomo") {
						return true;
					} else {
						return false;
					}
				});
				return false;
			});
			*/
			
		
		</script>
	</body>
</html>
