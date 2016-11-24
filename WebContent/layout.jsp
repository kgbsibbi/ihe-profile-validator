<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IHE Profile Validator</title>
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:300,400,500,700" type="text/css">
<link rel="stylesheet" href="./css/material.min.css">
<link rel="stylesheet" href="./css/style.css">
<script src="./js/material.min.js"></script>
</head>
<body>
<div class="layout-transparent mdl-layout mdl-js-layout">
	<header class="mdl-layout__header mdl-layout__header--transparent">
	    <div class="mdl-layout__header-row">
		    <!-- Title -->
		    <span class="mdl-layout-title">
		    <a class="mdl-navigation__link" href="index.do">IHE Profile Validator</a>
		    </span>
		    <!-- Add spacer, to align navigation to the right -->
		    <div class="mdl-layout-spacer"></div>
		    <!-- Navigation -->
		    <nav class="mdl-navigation">
		    	<a class="mdl-navigation__link" href="index.do">About</a>
		    	<a class="mdl-navigation__link" href="phmrcda.do">PHMR CDA</a>
		    </nav>
	    </div>
	</header>
	<div class="mdl-layout__drawer">
   		<span class="mdl-layout-title">Title</span>
   		<nav class="mdl-navigation">
     		<a class="mdl-navigation__link" href="">Link</a>
   		</nav>
 	</div>
 	<main class="mdl-layout__content page-content">
		<c:choose>
			<c:when test="${content != null }">
				<jsp:include page="${content }"/>
			</c:when>
			<c:otherwise>
				<jsp:include page="common/error.jsp"/>
			</c:otherwise>
		</c:choose>
	</main>
	<!-- Footer -->
	<jsp:include page="common/footer.jsp"/>
</div>

</body>
</html>