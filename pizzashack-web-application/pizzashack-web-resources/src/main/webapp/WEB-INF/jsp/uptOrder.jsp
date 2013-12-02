<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<script type="text/javascript" src="/static/js/pizza.form.js"></script>
<script type="text/javascript" src="/static/js/order.update.js"></script>
<title></title>
</head>
<body>
	<div id="order-id" class="hidden">${order.orderId}</div>
	<h1>
		<spring:message code="update.order.title" />
	</h1>

	<a href="/index" class="btn btn-primary"><spring:message
			code="pizza.label.pizza.link" /></a>
	<a href="/order/myOrders" class="btn btn-primary"><spring:message
			code="pizza.label.orders.link" /></a>
	<a href="/logout" class="btn btn-primary"><spring:message
			code="pizza.label.logout.link" /></a>

	<form:errors path="order" cssClass="errorBlock" element="div" />
	<form:form action="/order/update" cssClass="well" commandName="order"
		method="POST">
		<form:hidden path="orderId" />
		<form:hidden path="custId" />
		<div id="control-group-pizzaType" class="control-group">
			<label for="order-pizzaType"><spring:message
					code="order.label.pizzaType" />:</label>
			<div class="controls">
				<form:input id="order-pizzaType" path="pizzaType" readonly="true" />
				<form:errors id="error-pizzaType" path="pizzaType"
					cssClass="help-inline" />
			</div>
		</div>
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

		<div id="control-group-address" class="control-group">
			<label for="order-address"> <spring:message
					code="order.label.address" />:
			</label>

			<div class="controls">
				<form:textarea id="order-address" path="address" />
				<form:errors id="error-address" path="address"
					cssClass="help-inline" />
			</div>
		</div>

		<div id="control-group-quantity" class="control-group">
			<label for="order-quantity"> <spring:message
					code="order.label.quantity" />:
			</label>

			<div class="controls">
				<form:input id="order-quantity" path="quantity" />
				<form:errors id="error-quantity" path="quantity"
					cssClass="help-inline" />
			</div>
		</div>

		<div id="control-group-creditCardNumber" class="control-group">
			<label for="order-creditCardNumber"> <spring:message
					code="order.label.creditCardNumber" />:
			</label>

			<div class="controls">
				<form:input id="order-creditCardNumber" path="creditCardNumber" />
				<form:errors id="error-creditCardNumber" path="creditCardNumber"
					cssClass="help-inline" />
			</div>
		</div>

		<div class="form-buttons">
			<button id="cancel-order-button" class="btn">
				<spring:message code="cancel.label" />
			</button>
			<button id="update-order-button" type="submit"
				class="btn btn-primary">
				<spring:message code="update.order.button.label" />
			</button>
		</div>
	</form:form>
</body>
</html>