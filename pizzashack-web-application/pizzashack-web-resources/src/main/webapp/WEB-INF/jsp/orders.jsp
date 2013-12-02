<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<script type="text/javascript" src="/static/js/pizza.home.js"></script>
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


		<div id="pizza-list">
			<c:choose>
				<c:when test="${empty orders}">
					<p>
						<spring:message code="order.list.label.no.orders" />
					</p>
				</c:when>
				<c:otherwise>
					<c:forEach items="${orders}" var="found">
						<div class="well pizza-list-item">
							<c:if test="${not empty found.pizzaType}">
								<abbr
									title="<spring:message code="view.order.pizzaType.title"/>">
									<spring:message code="view.order.pizzaType.label" />
								</abbr>
								<c:out value="${found.pizzaType}" />
								<br />
							</c:if>

							<c:if test="${not empty found.customerName}">
								<abbr
									title="<spring:message code="view.order.customername.title"/>">
									<spring:message code="view.order.customername.label" />
								</abbr>
								<c:out value="${found.customerName}" />
								<br />
							</c:if>

							<c:if test="${not empty found.delivered}">
								<abbr
									title="<spring:message code="view.order.delivered.title"/>">
									<spring:message code="view.order.delivered.label" />
								</abbr>
								<c:out value="${found.delivered}" />
								<br />
							</c:if>
							<a href="/order/${found.orderId}" class="btn btn-primary"><spring:message
									code="order.label.check.link" /></a>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

</body>
</html>