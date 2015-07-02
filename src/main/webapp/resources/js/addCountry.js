/**
 * 
 */

$( document ).tooltip();


var readOnly = true;

$("#editId").click(function() {

	if (readOnly) {
		document.getElementById("countryId1").readOnly = false;
		document.getElementById("countryNameId").readOnly = false;
		
		$("#editId").html("Save");
		$("#clearId").hide();
		// $("#countries").hide();

		readOnly = false;
		// alert("Is tha Hallo")
	} else {
		document.getElementById("countryId1").readOnly = true;
		document.getElementById("countryNameId").readOnly = true;
		
		$("#editId").html("Edit");
		// $("#countries").show();
		$("#formId").submit();
		readOnly = true;
		// alert("Is tha Hallo")
	}
});



$("#saveId").click(function() {
	$("#formId").submit();
	// alert("ASDF" + this.id)
});

$("#clearId").click(function() {
	document.getElementById("countryId1").value = "";
	document.getElementById("countryNameId").value = "";

});

$("#deleteId").click(function() {
	var myvar = window.location.search;
	var url = "/admin/country/deleteCountry/" + myvar;
	$.post(url);
	window.location.replace("/uploaded");

	// alert("ASDF" + url)
});



