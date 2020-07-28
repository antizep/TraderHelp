/**
 * 
 */
window.onload = function() {
	var form = document.forms.addOrderForm;
	// прикрепляем обработчик кнопки
	form.submit.addEventListener("click", sendRequest);

	// обработчик нажатия
	function sendRequest(event) {
		
		  event.preventDefault();
		  var formData = new FormData(form);
		  
		  var request = new XMLHttpRequest();
		  
		  request.open("POST", "/order/add");
		  
		  request.onreadystatechange = function () { if (request.readyState ==
		  4 && request.status == 200)
		  document.getElementById("output").innerHTML=request.responseText; }
		  request.send(formData);
		 
	}

	/*
	 * let form = document.createElement('form'); form.action =
	 * 'https://google.com/search'; form.method = 'GET';
	 * 
	 * form.innerHTML = '<input name="q" value="test">'; // перед отправкой
	 * формы, её нужно вставить в документ document.body.append(form);
	 * 
	 * form.submit();
	 */
}