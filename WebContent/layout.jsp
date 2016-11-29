<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IHE Profile Validator</title>
<link rel="stylesheet" href="./css/bootstrap.min.css">
<link rel="stylesheet" href="./css/style.css">
<script src="./js/bootstrap.min.js"></script>
<script src="./js/jquery-3.1.1.min.js"></script>
<script src="./js/jquery.form.min.js"></script>
<script src="./js/upload.js"></script>
</head>
<body>
<!-- contents container-->
<div class="container">
	<!-- header -->
	<nav class="navbar navbar-dark">
		<a class="navbar-brand" href="index.do">IHE Profile Validator</a>
		<ul class="nav navbar-nav float-xs-right">
			<li class="nav-item active">
	      		<a class="nav-link" href="index.do">Guide</a>
	    	<li class="nav-item active">
	      		<a class="nav-link" href="phmrcda.do">PHMR CDA</a>
	    	</li>
	    </ul>
	</nav>
</div>
<!-- main contents -->
<div class="main-contents">
	<div class="container">
		<c:choose>
			<c:when test="${content != null }">
				<jsp:include page="${content }"/>
			</c:when>
			<c:otherwise>
				<jsp:include page="common/error.jsp"/>
			</c:otherwise>
		</c:choose>
	</div>
</div>

</body>
</html>