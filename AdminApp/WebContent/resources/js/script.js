function toogleForm(){
	var selector=document.getElementById("type").value;
	var ic=document.getElementById("identity_card");
	var p=document.getElementById("passport");
	var dl=document.getElementById("driving_licence");
	if(selector=="IDENTITY_CARD"){
		ic.style.display="none";
		p.style.display="block";
		dl.style.display="block";
	}else if(selector=="PASSPORT"){
		ic.style.display="block";
		p.style.display="none";
		dl.style.display="block";
	}
	else{
		ic.style.display="block";
		p.style.display="block";
		dl.style.display="none";
	}
}
