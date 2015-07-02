function addFightTypeLoaded() {
	// document.getElementById("fighterId").hide();
	var editButton = document.getElementById("editId");
	if (editButton != null) {
		document.getElementById("fightTypeId").readOnly = true;
		document.getElementById("commentsId").readOnly = true;
		document.getElementById("fightTypecDescriptionId").readOnly = true;
		

		// alert("loaded");
	}

	//refresh1();
};

var readOnly = true;

$("#editId").click(function() {

	if (readOnly) {
		document.getElementById("fightTypeId").readOnly = false;
		document.getElementById("commentsId").readOnly = false;
		document.getElementById("fightTypecDescriptionId").readOnly = false;

		$("#editId").html("Save");
		$("#clearId").hide();
		// $("#countries").hide();

		readOnly = false;
		// alert("Is tha Hallo")
	} else {
		document.getElementById("fightTypeId").readOnly = true;
		document.getElementById("commentsId").readOnly = true;
		document.getElementById("fightTypecDescriptionId").readOnly = true;

		
		$("#editId").html("Edit");
		// $("#countries").show();
		$("#formId").serialize();
		
		$("#formId").submit();
		readOnly = true;
		// alert("Is tha Hallo")
	}
});


$("#saveId").click(function() {
	
	$("#formId").serialize();
	
	$("#formId").submit();
	// alert("ASDF" + this.id)
});


$("#clearId").click(function() {
	document.getElementById("formId").reset();
	

});

$("#deleteId").click(function() {
	var myvar = window.location.search;
	var url = "/admin/fighter/deleteFighter/" + myvar;
	$.post(url);
	window.location.replace("/uploaded");

	// alert("ASDF" + url)
});
