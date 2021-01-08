var prevRow = null;
var whichUser = null;
function toggle(it, selectedUser) {
	
  if (it.className.substring(0, 3) == "sel")
  {
	  it.className = it.className.substring(3, 6);
	  prevRow = null;
	  
	  //Disable button delete user, when table is unchecked
	  document.getElementById("delete_user_button").disabled = true;
	  
	  //Reset value 
	  whichUser = null;
	  document.getElementById("user_to_delete").value = "";
  }
  else
  {
	  it.className = "sel" + it.className;
	  if (prevRow != null)
      {
		  prevRow.className = prevRow.className.substring(3, 6);
      }	  
	  prevRow = it;
	  
	  //Enable button delete user, when table is checked
	  document.getElementById("delete_user_button").disabled = false;
	  
	  //Change value of input field 
	  whichUser = selectedUser;	  	  
	  document.getElementById("user_to_delete").value = whichUser;
  }
  
  //document.getElementById("user_to_delete").value = whichUser;
  
}