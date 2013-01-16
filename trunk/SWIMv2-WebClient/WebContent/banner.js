/* This script and many more are available free online at
The JavaScript Source!! http://javascript.internet.com */
/* -----------------------------------------------
   Streaming banners - v.1.1
   (c) 2006 www.haan.net
   contact: jeroen@haan.net
   You may use this script but please leave the credits on top intact.
   Please inform us of any improvements made.
   When useful we will add your credits.
  ------------------------------------------------ */

/* usage
<body>
<div id="slideCont" style="position:relative;z-index:1;width:140px;left:0px;overflow:hidden;">
	<div id="slideA" style="position:absolute;z-index:1;top:0px;left:0px;width:140px;overflow:hidden;">
		your banners (images inside anchor tags)
		<div id="slideB" style="position:relative;z-index:1;top:0px;left:0px;width:140px;overflow:hidden;">
			your banners (images inside anchor tags)
		</div>
	</div>
</div>
</body>

In order the have the script working in FireFox as well you need a proper "DTD" to prevent the browser's "quirksmode".

Please see http://www.quirksmode.nl/ for more details.

Or in case you experience problems, copy and paste next line on top of your webpage:
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

*/

function clip() {
 	// width of the banner container
 	var contWidth = 150;
 	// height of the banner container
 	var contHeight = 350;

 	var id1 = document.getElementById('slideA');
 	var id2 = document.getElementById('slideB');
 	var height = id1.offsetHeight;

 	id1.style.top = parseInt(id1.style.top)-1 + 'px';

 	document.getElementById('slideCont').style.height = contHeight + "px";
 	document.getElementById('slideCont').style.clip = 'rect(auto,'+ contWidth +'px,' + contHeight +'px,auto)';
 	id2.style.display = '';
 	if(parseFloat(id1.style.top) == -(height/2)) {
  		id1.style.top = '0px';
 	}
 	setTimeout(clip,50)
}

// Multiple onload function created by: Simon Willison
// http://simon.incutio.com/archive/2004/05/26/addLoadEvent
function addLoadEvent(func) {
  var oldonload = window.onload;
  if (typeof window.onload != 'function') {
    window.onload = func;
  } else {
    window.onload = function() {
      if (oldonload) {
        oldonload();
      }
      func();
    }
  }
}

addLoadEvent(function() {
  clip();
});
