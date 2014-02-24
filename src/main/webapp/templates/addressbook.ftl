<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="/addressbook/js/exercise.js"></script>

<title>Coding Exercise - Address Book</title>

<style>
.dataSection{
border-style:solid;
border-width:5px;
padding:80px;
}

.controlPanel{
border-style:double;
border-width:5px;
padding:20px;
}

.resultsSection{
background-color:red;
border-style:solid;
border-width:5px;
padding:80px;
}
</style>

</head>

<body>

<div class="controlPanel">
	<h3>Query selection</h3>
	<br></br>
	<span>Select and press a button from below to execute a query: </span>
	<br></br>
	<div>
		<button id="getEldestBtn" type="button" value="Get Eldest">Get Eldest</button> 
	</div>
	<br><br>
	<div>
		Select gender: 
		<select id="genderSelect">
			<option selected value="">choose a gender...</option>
			<option value="Male">Male</option>
			<option value="Female">Female</option>
		</select>
		<button id="getGenderBtn" type="button" value="Get By Gender">Get By Gender</button> 
	</div>  
	<br><br>
	<div>
		Name of first contact: 
		<input id="dobDiffName1" type="text" value""></input>
		Name of second contact: 
		<input id="dobDiffName2" type="text" value""></input>
		<button id="getDOBDiffBtn" type="button" value="Get DOB Difference">Get DOB Difference</button>
	</div>
</div>

<br><br>

<div id="queryDiv" class="resultsSection">
	Query results returned:
	<br></br>
	<div id="queryResultsDiv">
	</div>
</div>
<br><br>


<#if contacts??>
	<div class="dataSection">
	Address book contacts:
	<ul>
		<#list contacts as contact>
			<li>${contact}</li>
		</#list>
	</ul>
	</div>
</#if>

</body>
</html>  