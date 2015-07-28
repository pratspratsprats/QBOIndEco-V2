
function posttointuit(order)
{
	 var  xmlhttp = new XMLHttpRequest();
	 var url = "http://localhost:8080/InvisibleAccounting/v1/accounting";
     xmlhttp.open('POST',url,true);
     xmlhttp.setRequestHeader("Content-type", "application/json");
     xmlhttp.setRequestHeader("Accept", "text/plain")
     var converttojson = convert(order);
     xmlhttp.send(converttojson);
     xmlhttp.onreadystatechange = function() {
		            if (xmlhttp.readyState == 4) {
			if (xmlhttp.status == 200) {

				alert("[Status:" + xmlhttp.status + ", Response:"
						+ xmlhttp.responseText + "]");

			} else if (xmlhttp.status >= 400) {
				alert("[Status:" + xmlhttp.status + ", Response:"
						+ xmlhttp.responseText + "]");
			} else if (xmlhttp.status >= 500) {
				alert("[Status:" + xmlhttp.status + ", Response:"
						+ xmlhttp.responseText + "]");
			} else {
				alert("[Status:" + xmlhttp.status + ", Response:"
						+ xmlhttp.responseText + "]");
			}
		}
	};
}

function convert(order)
{
	var json=  JSON.stringify(order);
	alert(json);
	return json;
}
