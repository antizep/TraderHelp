/**
 * 
 */
window.onload = function () {

    //получаем идентификатор элемента
    var a = document.getElementById('runScanner');
    
    //вешаем на него событие
    a.onclick = function() {

    	var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
             if (this.readyState == 4 && this.status == 200) {
                 alert(this.responseText);
             }
        };
        xhttp.open("POST", "/service/start", true);
        xhttp.setRequestHeader("Content-type", "application/json");
        xhttp.send();
        
    }
}