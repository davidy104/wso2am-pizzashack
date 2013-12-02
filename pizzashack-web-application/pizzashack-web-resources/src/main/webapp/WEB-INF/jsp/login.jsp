<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<script type="text/javascript" src="/static/js/pizza.form.js"></script>
<script type="text/javascript" src="/static/js/pizza.add.js"></script>
<title></title>
</head>
<body>
	<h1>
		<spring:message code="login.title" />
	</h1>
	<form:errors path="account" cssClass="errorBlock" element="div" />
	<form:form action="/login" cssClass="well" commandName="account"
		method="POST">
		<div id="control-group-username" class="control-group">
			<label for="account-username"><spring:message
					code="account.label.username" />:</label>
			<div class="controls">
				<form:input id="account-username" path="username" />
				<form:errors id="error-username" path="username"
					cssClass="help-inline" />
			</div>
		</div>
		<div id="control-group-password" class="control-group">
			<label for="account-password"><spring:message
					code="account.label.password" />:</label>

			<div class="controls">
				<form:input id="account-password" type="password" path="password" />
				<form:errors id="error-password" path="password"
					cssClass="help-inline" />
			</div>
		</div>

		<div class="form-buttons">
			<button id="add-contact-button" type="submit" class="btn btn-primary">
				<spring:message code="account.login.button.label" />
			</button>
		</div>
	</form:form>
</body>
</html>