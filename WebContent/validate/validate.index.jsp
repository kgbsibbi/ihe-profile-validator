<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<p>Select File type and file to validate</p>
<form action="validate.do" method="post" enctype="multipart/form-data">
	<div class="form-group col-xs-3">
	  <label for="sel1">Document Type:</label>
	  <select name="type" class="form-control" id="sel1">
	    <option value="phmrcda">PHMR CDA</option>
	    <option>2</option>
	    <option>3</option>
	    <option>4</option>
	  </select>
	</div>
	<div class="input-group">
		<div class="input-group col-xs-12">
			<label>Document File:</label>
		</div>
		<div class="input-group col-xs-12">
			<input type="text" id="filename" class="form-control" readonly required aria-label="Selected file name">
			<span class="input-group-btn">
				<button class="btn btn-secondary" type="button" id="browsebutton">Browse</button>
				<input class="btn btn-primary" type="submit" id="validatebutton" value="VALIDATE">
			</span>
		</div>
		<input class="form-control file" type="file" id="fileinput" accept=".xml" name="fileinput" required aria-label="Hidden input for file selection">
	</div>
</form>
<c:if test="${not empty result }">
<div class="table-responsive">
	<table class="table table-hover">
		<thead><tr>
		<th></th>
		<th>File name</th>
		<th>Result</th>
		</tr></thead>
		<tbody>
		<c:forEach var="item" items="${result }">
		<th></th>
		<td>${item.fileName }</td>
		<td class="${item.result }"><a href="${item.resultMessage }">${item.resultMessage }</a></td>
		</c:forEach>
		</tbody>
	</table>
</div>
</c:if>
