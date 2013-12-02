<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<script type="text/javascript" src="/static/js/pizza.form.js"></script>
<script type="text/javascript" src="/static/js/pizza.view.js"></script>
<title></title>
</head>
<body>
	<div id="order-id" class="hidden">${order.orderId}</div>
	<h1>
		<c:out value="${order.pizzaType}" />
	</h1>
	<div>
		<a href="/index" class="btn btn-primary"><spring:message
				code="pizza.label.pizza.link" /></a> <a href="/order/myOrders"
			class="btn btn-primary"><spring:message
				code="pizza.label.orders.link" /></a> <a href="/logout"
			class="btn btn-primary"><spring:message
				code="pizza.label.logout.link" /></a>


		<div class="well">
			<order> <c:if test="${not empty order.pizzaType}">
				<abbr title="<spring:message code="view.order.pizzaType.title"/>">
					<spring:message code="view.order.pizzaType.label" />
				</abbr>
				<c:out value="${order.pizzaType}" />
				<br />
			</c:if> <c:if test="${not empty order.customerName}">
				<abbr title="<spring:message code="view.order.customername.title"/>">
					<spring:message code="view.order.customername.label" />
				</abbr>
				<c:out value="${order.customerName}" />
				<br />
			</c:if> <c:if test="${not empty order.customerEmail}">
				<abbr
					title="<spring:message code="view.order.customerEmail.title"/>">
					<spring:message code="view.order.customerEmail.label" />
				</abbr>
				<c:out value="${order.customerEmail}" />
				<br />
			</c:if> <c:if test="${not empty order.delivered}">
				<abbr title="<spring:message code="view.order.delivered.title"/>">
					<spring:message code="view.order.delivered.label" />
				</abbr>
				<c:out value="${order.delivered}" />
				<br />
			</c:if> <c:if test="${not empty order.quantity}">
				<abbr title="<spring:message code="view.order.quantity.title"/>">
					<spring:message code="view.order.quantity.label" />
				</abbr>
				<c:out value="${order.quantity}" />
				<br />
			</c:if> <c:if test="${not empty order.creditCardNumber}">
				<abbr
					title="<spring:message code="view.order.creditCardNumber.title"/>">
					<spring:message code="view.order.creditCardNumber.label" />
				</abbr>
				<c:out value="${order.creditCardNumber}" />
				<br />
			</c:if> <c:if test="${not empty order.address}">
				<abbr title="<spring:message code="view.order.address.title"/>">
					<spring:message code="view.order.address.label" />
				</abbr>
				<c:out value="${order.address}" />
				<br />
			</c:if> </order>

			<div>
				<a href="/order/update/${order.orderId}" class="btn btn-primary">
					<spring:message code="update.order.button.label" /></a>
				<a id="deliver-order-link" class="btn btn-primary"> <spring:message
						code="deliver.order.button.label" /></a>
				<a id="delete-order-link" class="btn btn-primary"> <spring:message
						code="delete.order.button.label" /></a>
						
			</div>
		</div>

	</div>
	
	<script id="template-deliver-order-confirmation-dialog"
		type="text/x-handlebars-template">
    <div id="deliver-order-confirmation-dialog" class="modal">
        <div class="modal-header">
            <button class="close" data-dismiss="modal">×</button>
            <h3><spring:message code="deliver.order.dialog.title"/></h3>
        </div>
        <div class="modal-body">
            <p><spring:message code="deliver.order.dialog.message"/></p>
        </div>
        <div class="modal-footer">
            <a id="deliver-order-button" href="#" class="btn"><spring:message code="deliver.label"/></a>
            <a id="deliver-order-button" href="#" class="btn btn-primary"><spring:message code="deliver.order.button.label"/></a>
        </div>
    </div>
	</script>

	<script id="template-delete-order-confirmation-dialog"
		type="text/x-handlebars-template">
    <div id="delete-order-confirmation-dialog" class="modal">
        <div class="modal-header">
            <button class="close" data-dismiss="modal">×</button>
            <h3><spring:message code="delete.order.dialog.title"/></h3>
        </div>
        <div class="modal-body">
            <p><spring:message code="delete.order.dialog.message"/></p>
        </div>
        <div class="modal-footer">
            <a id="cancel-order-button" href="#" class="btn"><spring:message code="cancel.label"/></a>
            <a id="delete-order-button" href="#" class="btn btn-primary"><spring:message code="delete.order.button.label"/></a>
        </div>
    </div>
	</script>
</body>
</html>