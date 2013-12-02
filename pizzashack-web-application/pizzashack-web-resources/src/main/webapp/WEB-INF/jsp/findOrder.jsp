<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<script type="text/javascript" src="/static/js/pizza.home.js"></script>
<script type="text/javascript" src="/static/js/pizza.form.js"></script>
<title></title>
</head>
<body>
	<h1>
		<spring:message code="my.orders.title" />
	</h1>
	<div>
		<a href="/index" class="btn btn-primary"><spring:message
				code="pizza.label.pizza.link" /></a> <a href="/order/myOrders"
			class="btn btn-primary"><spring:message
				code="pizza.label.orders.link" /></a> <a href="/logout"
			class="btn btn-primary"><spring:message
				code="pizza.label.logout.link" /></a>



		<form:errors path="order" cssClass="errorBlock" element="div" />
		<form:form action="/order/findOrders" cssClass="well"
			commandName="order" method="POST">

			<div id="control-group-customerName" class="control-group">
				<label for="order-customerName"> <spring:message
						code="order.label.customerName" />:
				</label>

				<div class="controls">
					<form:input id="order-customerName" path="customerName" />
					<form:errors id="error-customerName" path="customerName"
						cssClass="help-inline" />
				</div>
			</div>

			<div id="control-group-customerEmail" class="control-group">
				<label for="order-customerEmail"> <spring:message
						code="order.label.customerEmail" />:
				</label>

				<div class="controls">
					<form:input id="order-customerEmail" path="customerEmail" />
					<form:errors id="error-customerEmail" path="customerEmail"
						cssClass="help-inline" />
				</div>
			</div>


			<div class="form-buttons">
				<button id="search-order-button" type="submit"
					class="btn btn-primary">
					<spring:message code="search.button.label" />
				</button>
			</div>
		</form:form>

	</div>

</body>
</html>