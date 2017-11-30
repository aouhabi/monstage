<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="en">
<head>

<link rel="stylesheet" type="text/css"
	href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

<!-- 
	<spring:url value="/css/main.css" var="springCss" />
	<link href="${springCss}" rel="stylesheet" />
	 -->
<c:url value="/css/main.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />

</head>
<body> 
	       		        <br /> 
	       		        <form method="post" action="http://localhost:8070/ProjetWeb/service/"> 
	       		            Localisation : <select name="location"> 
	       		 							 <option value="2">à l'exterieur du bureau</option> 
	       								     <option value="1" selected>à l'intérieur du bureau</option> 
	       		 						 </select><br /> 
	       		            Capteur de luminosité exterieur: <input type="text" name="le" id="le" /><br /> 
	       		            Capteur de luminosité interieur: <input type="text" name="li" id="li" /><br /> 
	       		            Capteur de temperature          :<input type="text" name="temp" id="temp" /><br /> 
	       		            Capteur de la qualité de l'aire :<input type="text" name="air" id="air" /><br /> 
	       		            <input type="submit" value="Submit" /> 
	       		        </form> 
	       		    </body> 

</html>
