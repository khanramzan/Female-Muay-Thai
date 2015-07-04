function addFighterLoaded() {
	// document.getElementById("fighterId").hide();
	var editButton = document.getElementById("editId");
	if (editButton != null) {
		document.getElementById("promoterNameId").readOnly = true;
		document.getElementById("country").readOnly = true;
		document.getElementById("commentsId").readOnly = true;

		// alert("loaded");
	}

	//refresh1();
};

var readOnly = true;

$("#editId").click(function() {

	if (readOnly) {
		document.getElementById("promoterNameId").readOnly = false;
		document.getElementById("country").readOnly = false;
		document.getElementById("commentsId").readOnly = false;
		$("#editId").html("Save");
		$("#clearId").hide();
		// $("#countries").hide();

		readOnly = false;
		// alert("Is tha Hallo")
	} else {
		document.getElementById("promoterNameId").readOnly = true;
		document.getElementById("country").readOnly = true;
		document.getElementById("commentsId").readOnly = true;
		$("#editId").html("Edit");
		// $("#countries").show();
		$("#formId").serialize();
		$("#formId").submit();
		readOnly = true;
		// alert("Is tha Hallo")
	}
});


$("#country").autocomplete({
	
	source: function( request, response ) {
        $.ajax({
            url: "/admin/countries.php",
            dataType: "json",
            data: {term: request.term},
            success: function(data) {
                        response($.map(data, function(item) {
                        return {
                            label: item.label,
                            value: item.value
                            //abbrev: item.abbrev
                            };
                    }));
                }
            });
        },

	change: function(event, ui) {
        $('#countryId1').val(ui.item.value);
        $('#country').val(ui.item.label);
    }

});


$("#saveId").click(function() {
	$("#formId").serialize();
	$("#formId").submit();
	// alert("ASDF" + this.id)
});

$("#clearId").click(function() {
	
	document.getElementById("formId").reset();
	/*
	document.getElementById("promoterName").value = "";
	document.getElementById("clubNameId").value = "";
	document.getElementById("country").value = "";
	document.getElementById("commentsId").value = "";
	*/

});

$("#deleteId").click(function() {
	var myvar = window.location.search;
	var url = "/admin/promoter/deletePromoter/" + myvar;
	$.post(url);
	//window.location.replace("/uploaded");

	// alert("ASDF" + url)
});

function getMedia(promoterId, mediaId) {
	// alert("Hello Rams " + mediaId);
	var url = "/admin/promoter/" + promoterId + "/addImage/" + mediaId;
	$.post(url);

};