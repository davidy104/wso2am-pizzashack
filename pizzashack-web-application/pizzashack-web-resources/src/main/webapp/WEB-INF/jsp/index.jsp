<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<script type="text/javascript" src="/static/js/pizza.home.js"></script>
<title></title>
</head>
<body>
	<h1>
		<spring:message code="pizzashack.title" />
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
				<c:when test="${empty pizzas}">
					<p>
						<spring:message code="pizza.list.label.no.pizzas" />
					</p>
				</c:when>
				<c:otherwise>
					<c:forEach items="${pizzas}" var="pizza">
						<div class="well pizza-list-item">
							<c:if test="${not empty pizza.name}">
								<abbr title="<spring:message code="view.pizza.name.title"/>">
									<spring:message code="view.pizza.name.label" />
								</abbr>
								<c:out value="${pizza.name}" />
								<br />
							</c:if>

							<c:if test="${not empty pizza.description}">
								<abbr title="<spring:message code="view.pizza.desc.title"/>">
									<spring:message code="view.pizza.desc.label" />
								</abbr>
								<c:out value="${pizza.description}" />
								<br />
							</c:if>

							<c:if test="${not empty pizza.price}">
								<abbr title="<spring:message code="view.pizza.price.title"/>">
									<spring:message code="view.pizza.price.label" />
								</abbr>
								<c:out value="${pizza.price}" />
								<br />
							</c:if>
							<a href="/order/place/${pizza.name}" class="btn btn-primary"><spring:message
									code="pizza.label.order.link" /></a>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>