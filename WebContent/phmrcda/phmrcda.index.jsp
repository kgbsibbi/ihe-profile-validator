<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h6>PHMR CDA Validator</h6>
<form action="validate.do?type=phmrcda" method="post" enctype="multipart/form-data">
  <div class="mdl-textfield mdl-js-textfield">
    <input class="mdl-textfield__input " type="file" id="file" name="file" required>
    <label class="mdl-textfield__label" for="file"></label>
  </div>
  <input class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored"type="submit" value="VALIDATE">
</form>
<p>${result}</p>