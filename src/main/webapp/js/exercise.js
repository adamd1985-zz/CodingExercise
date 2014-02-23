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
    $("#getEldestBtn").click(function() {
	$.get("exercise/getEldestQuery", function() {

	});
    });
    
    $("#getGenderBtn").click(function() {
	$.get("exercise/getGenderQuery", function() {

	});
    });
    
    
    $("#getDOBDiffBtn").click(function() {
	$.get("exercise/getAgeDifferenceQuery", function() {

	});
    });
});
