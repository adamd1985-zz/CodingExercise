<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="/addressbook/js/exercise.js"></script>

<title>Coding Exercise - Address Book</title>


</head>

<body>

${MsTime}

<#macro queryResultMacro queryResult>
	<#if queryResponse?? && queryResponse.type=="${queryResult}">
		<#if queryResponse.msg?? && queryResponse.msg!="">
			<span>${queryResponse.msg}</span>
		<#elseif queryResponse.contacts??>
			Search results follow:
			<ul>
			<#list queryResponse.contacts as contact>
				<li>${contact}</li>
			</#list>
			</ul>
		</#if>
	</#if>
</#macro>

<div>
<button id="getEldestBtn" type="button" value="Get Eldest">Get Eldest</button>
<@queryResultMacro queryResult="getEldest"/>  
</div>
<br><br>
  
<div>
Select gender: 
<select id="genderSelect">
<option selected value="">choose a gender...</option>
<option selected value="Male">Male</option>
<option selected value="Female">Female</option>
</select>
<button id="getGenderBtn" type="button" value="Get By Gender">Get By Gender</button> 
<@queryResultMacro queryResult="searchGender"/> 
</div>  
<br><br>



<div>
Name of first contact: 
<input id="dobDiffName1" type="text" value""></input>
Name of second contact: 
<input id="dobDiffName2" type="text" value""></input>
<button id="getDOBDiffBtn" type="button" value="Get DOB Difference">Get DOB Difference</button>
<@queryResultMacro queryResult="getAgeDifference"/> 
</div>
<br><br>

</body>
</html>  