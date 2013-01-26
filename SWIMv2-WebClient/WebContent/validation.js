
function validateForm()
{
	var x=document.forms["registerForm"]["fname"].value;
	if (x==null || x=="")
	{
		alert("Inserisci nome utente");
		return false;
	}
	if (x.length<2)
	{
		alert("Nome utente troppo corto");
		return false;
	}

	var y=document.forms["registerForm"]["lname"].value;

	if (y==null || y=="" )
	{
		alert("Inserici cognome utente");
		return false;
	}
	if (y.length<2)
	{
		alert("Cognome utente troppo corto");
		return false;
	}




	var z=document.forms["registerForm"]["email"].value;


	if (z==null || z=="" )
	{
		alert("Inserici email utente");
		return false;
	}
	if (z.length<4)
	{
		alert("Email utente troppo corta");
		return false;
	}
	
	if(!validateEmail(z))
		{
		alert("Indirizzo email non valido");
		return false;
		
		}

	var a=document.forms["registerForm"]["password1"].value;
	var b=document.forms["registerForm"]["password2"].value;

	if (a==null || a=="" )
	{
		alert("Inserici password");
		return false;
	}
	if (a.length<4)
	{
		alert("Password troppo corta");
		return false;
	}
	if (a!=b)
	{
		alert("Password non inserita correttamente");
		return false;
	}




}

function validateEmail(email) { 
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
} 
