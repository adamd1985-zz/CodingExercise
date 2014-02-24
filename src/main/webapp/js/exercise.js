/**
 * JQUERY powered fontend to push requests to the server for the coding
 * exercise.
 * 
 * @author adamd
 * @version 1
 */

/**
 * On page load function.
 */
$(function() {

    $("#queryDiv").hide();

    $("#getEldestBtn").click(function() {
	$.getJSON("exercise/getEldestQuery", function(response) {
	    if (response.error) {
		alert("Get eldest contact failed. " + response.errorMsg);
	    } else if (response.value != null) {
		$("#queryDiv").show();
		
		var contact = response.value[0];

		$("#queryResultsDiv").html(contact.name + ", " + contact.gender + ", " + new Date(contact.dateOfBirth).toLocaleDateString());
	    } else {
		alert("No data in the database!");
	    }
	});
    });

    $("#getGenderBtn").click(function() {
	var genderStr = $("#genderSelect").val();
	
	if (genderStr !== ""){
        	$.getJSON("exercise/getGenderQuery", {genderParam: genderStr}, function(response) {
        	    if (response.error) {
        		alert("Get eldest contact failed. " + response.errorMsg);
        	    } else if (response.value != null && response.value.length > 0) {
        		$("#queryDiv").show();
        
        		var contacts = response.value;
        
        		$("#queryResultsDiv").html("<ul>");
        		for ( var i = 0; i < contacts.length; ++i) {
        		    var contact = contacts[i];
        		    $("#queryResultsDiv").append("<li>" + contact.name + ", " + contact.gender + ", " + new Date(contact.dateOfBirth).toLocaleDateString() + "</li>");
        		}
        		$("#queryResultsDiv").append("</ul>");
        	    } else {
        		alert("No contacts with that gender found!");
        	    }
        	});
	}
	else{
	    alert("Please select a Gender");
	}
    });

    $("#getDOBDiffBtn").click(function() {
	var dobDiffName1 = $("#dobDiffName1").val();
	var dobDiffName2 = $("#dobDiffName2").val();
	
	if ((dobDiffName1!=="") && (dobDiffName2!=="")){    
        	$.getJSON("exercise/getAgeDifferenceQuery", {name1 : dobDiffName1, name2 : dobDiffName2 }, function(response) {
        	    if (response.error) {
        		alert("Get eldest contact failed. " + response.errorMsg);
        	    } else if (response.value != null) {
        		$("#queryDiv").show();
        
        		$("#queryResultsDiv").html("DOB difference in days: " + response.value);
        
        	    } else {
        		alert("Some or all of the contacts weren't found!");
        	    }
        	});
	}
	else{
	    alert("Please enter the names of the contacts to compare.");
	}
    });
});
