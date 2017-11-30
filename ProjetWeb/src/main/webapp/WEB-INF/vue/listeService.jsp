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
 <form method="post" action="http://localhost:8070/ProjetWeb/service/listeService">
 
   <fieldset>
       <legend>Groupe objectif  controle de luminosit�</legend> <!-- Titre du fieldset --> 
			 Priorit� : <select name="servicelumiere">
	       		 							 <option value="" selected>Desactiver</option>
	       								     <option value="1" >1</option>
	       								     <option value="2" >2</option>
	       								     <option value="3" >3</option>
	       								     <option value="4" >4</option>

	       		 		 </select><br />

       <label >Service de c�ntrole de luminosit� avec Store Priorit�</label>
       Priorit� : <select name="sln">
	       		 							 <option value="" selected>Desactiver</option>
	       								     <option value="1" >1</option>
	       								     <option value="2" >2</option>
	       								     <option value="3" >3</option>

	       		 		 </select><br />

       <label >Service augmentation de la lumiere avec le Lampe </label>
       Priorit� : <select name="sla">
	       		 							 <option value="" selected>Desactiver</option>
	       								     <option value="1" >1</option>
	       								     <option value="2" >2</option>
	       								     <option value="3" >3</option>

	       		 		 </select><br />

      
   </fieldset>
 
  <fieldset>
       <legend>Groupe objectif  controle de temperature </legend> <!-- Titre du fieldset --> 
			 Priorit� : <select name="serviceCanicule">
	       		 							 <option value="" selected>Desactiver</option>
	       								     <option value="1" >1</option>
	       								     <option value="2" >2</option>
	       								     <option value="3" >3</option>
	       								     <option value="4" >4</option>

	       		 		 </select><br />

       <label >Service de c�ntrole de temperature avec Store  </label>
       Priorit� : <select name="sca">
	       		 							 <option value="" selected>Desactiver</option>
	       								     <option value="1" >1</option>
	       								     <option value="2" >2</option>
	       								     <option value="3" >3</option>

	       		 		 </select><br />

       <label >Service de c�ntrole de temperature avec  chauffage  </label>
       Priorit� : <select name="sch">
	       		 							 <option value="" selected>Desactiver</option>
	       								     <option value="1" >1</option>
	       								     <option value="2" >2</option>
	       								     <option value="3" >3</option> 
	       		 </select><br />
   </fieldset>

  <fieldset>
       <legend>Groupe objectif controle de l'�nergie </legend> <!-- Titre du fieldset --> 
			 Priorit� : <select name="serviceEconomie">
	       		 							 <option value="" selected>Desactiver</option>
	       								     <option value="1" >1</option>
	       								     <option value="2" >2</option>
	       								     <option value="3" >3</option>
	       								     <option value="4" >4</option>

	       		 		 </select><br />

       <label >Service d'economie d'�nergie  avec  Lampe </label>
       Priorit� : <select name="sle1">
	       		 							 <option value="" selected>Desactiver</option>
	       								     <option value="1" >1</option>
	       								     <option value="2" >2</option>
	       								     <option value="3" >3</option>

	       		 		 </select><br />

       <label >Service d'economie d'�nergie   chauffage  </label>
       Priorit� : <select name="sech">
	       		 							 <option value="" selected>Desactiver</option>
	       								     <option value="1" >1</option>
	       								     <option value="2" >2</option>
	       								     <option value="3" >3</option>
	       			</select><br />
      
   </fieldset>

    <fieldset>
       <legend>Groupe objectif controle de la qualit�e de l'air </legend> <!-- Titre du fieldset --> 
			 Priorit� : <select name="serviceAire">
	       		 							 <option value="" selected>Desactiver</option>
	       								     <option value="1" >1</option>
	       								     <option value="2" >2</option>
	       								     <option value="3" >3</option>
	       								     <option value="4" >4</option>

	       		 		 </select><br />

       <label >Service controle de la qualit� de l'air avec  fenetre  </label>
       Priorit� : <select name="sqa">
	       		 							 <option value="" selected>Desactiver</option>
	       								     <option value="1" >1</option>
	       								     <option value="2" >2</option>
	       								     <option value="3" >3</option>

	       		 		 </select><br />

      
   </fieldset>
   <fieldset>
       <legend>Groupe objectif controle securit� </legend>
       Mode securit� :<select name="serviceSecu"> 
	       		 							 <option value="0">activer</option>
	       								     <option value="" selected>desactiver</option>
	       		 						 </select><br />
        </fieldset>
    <input type="submit" value="valider" />

</form>
</body>
</html>
