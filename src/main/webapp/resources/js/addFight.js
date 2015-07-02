

// autocomplet : this function will be executed every time we change the text
$( "#country_id" ).autocomplete({
    source: "/countries.php",
 
    select: function( event, ui ) {
    	//alert(ui.item.label);
    	//$( "#country_id" ).val("");
    	
    	
    },
    
    _renderItem: function( ul, item ) {
    	this.value=item.label;
    	  return $( "<li>" )
    	    .attr( "data-value", item.label )
    	    .append( item.label )
    	    .appendTo( ul );
    	}
    
  });


$("#saveId").click(function() {
	$("#formId").submit();
	//alert("ASDF" + this.id)
});



$( "#fightDateId" ).datepicker();