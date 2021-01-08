	mouseHighlight = function(){
	  var i, tr = document.getElementsByTagName('tr');
	  for(i = 0; i<tr.length; i++){
	    if(tr[i].className=='mouseOut'){
	      tr[i].onmouseover = function(){this.className = 'mouseOn'};
	      tr[i].onmouseout = function(){this.className = 'mouseOut'};
	    }
	  }
	}
	window.onload = mouseHighlight;