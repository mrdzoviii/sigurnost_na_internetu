function toogleForm(){
	var selector=document.getElementById("ad_type").value;
	var paid=document.getElementById("ad_paid");
	var free=document.getElementById("ad_free");
	if(selector=="Free"){
		paid.style.display="none";
		free.style.display="block";
	}
	else{
		paid.style.display="block";
		free.style.display="none";
	}
}
